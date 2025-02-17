/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol;

import java.util.*;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.TokenStream;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.module.ModuleMarker;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonParser;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.common.util.ListSet;
import org.eclipse.epsilon.eol.compile.context.EolCompilationContext;
import org.eclipse.epsilon.eol.dom.*;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.Return;
import org.eclipse.epsilon.eol.execute.context.EolContext;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.parse.EolLexer;
import org.eclipse.epsilon.eol.parse.EolParser;
import org.eclipse.epsilon.eol.tools.EolSystem;

public class EolModule extends AbstractModule implements IEolModule {
	
	protected StatementBlock main;
	protected IEolContext context;
	protected List<Statement> postOperationStatements = new ArrayList<>();
	protected OperationList declaredOperations = new OperationList();
	protected List<Import> imports = new ArrayList<>();
	protected OperationList operations = new OperationList();
	protected List<ModelDeclaration> declaredModelDeclarations;
	protected Set<ModelDeclaration> modelDeclarations;
	protected EolCompilationContext compilationContext;
	private IEolModule parent;
	
	/**
	 * The type of {@link #context} when using {@link #getContext()} and {@link #setContext(IEolContext)}.
	 * @since 1.6
	 */
	Class<? extends IEolContext> expectedContextType = IEolContext.class;
	
	public EolModule() {
		this(null);
	}
	
	/**
	 * Instantiates the module with the specified execution context.
	 * 
	 * @param context The execution context
	 * @since 1.6
	 */
	@SuppressWarnings("unchecked")
	public EolModule(IEolContext context) {
		setContext(context != null ? context : new EolContext());
		// Ensure that setContext is consistent with getContext
		try {
			expectedContextType = (Class<? extends IEolContext>) getClass().getMethod("getContext").getReturnType();
		}
		catch (NoSuchMethodException | SecurityException | ClassCastException ex) {
			// Use the default - no need to do anything here.
		}
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		checkImports(cst);
		
		for (Map.Entry<String, Class<?>> entry : getImportConfiguration().entrySet()) {
			imports.addAll(getImportsByExtension(cst, entry.getKey(), entry.getValue()));
		}
		
		for (AST operationAst : AstUtil.getChildren(cst, EolParser.HELPERMETHOD)) {
			declaredOperations.add((Operation) createAst(operationAst, this));
		}
		
		List<AST> modelDeclarationAsts = AstUtil.getChildren(cst, EolParser.MODELDECLARATION);
		declaredModelDeclarations = new ArrayList<>(modelDeclarationAsts.size());
		for (AST modelDeclarationAst : modelDeclarationAsts) {
			declaredModelDeclarations.add((ModelDeclaration) createAst(modelDeclarationAst, this));
		}
		
		if (AstUtil.getChild(cst, EolParser.BLOCK) != null) {
			main = (StatementBlock) createAst(AstUtil.getChild(cst, EolParser.BLOCK), this);
			
			for (AST child : cst.getChildren()) {
				int type = child.getType();
				if (type == EolParser.BLOCK ||
					type == EolParser.ANNOTATIONBLOCK ||
					type == EolParser.HELPERMETHOD ||
					type == EolParser.MODELDECLARATION ||
					type == EolParser.IMPORT) {
						continue;
				}
				
				ModuleElement exprAst = module.createAst(child, this);
				final Expression expression;
				if (exprAst instanceof ReturnStatement) {
					expression = ((ReturnStatement) exprAst).getReturnedExpression();
				}
				else expression = (Expression) exprAst;
				
				ExpressionStatement expressionStatement = new ExpressionStatement(expression);
				expressionStatement.setParent(this);
				postOperationStatements.add(expressionStatement);
			}
		}
		
		operations.addAll(this.getDeclaredOperations());
		for (Import import_ : imports) {
			if (import_.isLoaded() && import_.getModule() instanceof IEolModule) {
				operations.addAll(((IEolModule)import_.getModule()).getOperations());
			}
		}
	}
	
	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		
		if (cst == null) return null;
		if (cst.getParent() != null && cst.getParent().getType() == EolParser.EOLMODULE && cst.getType() == EolParser.BLOCK) {
			return new StatementBlock();
		}
		
		OperatorExpressionFactory operatorExpressionFactory = new OperatorExpressionFactory();
		
		if (parentAst == null) return this;
		
		switch (cst.getType()) {
			case EolParser.FOR: return new ForStatement();
			case EolParser.WHILE: return new WhileStatement();
			case EolParser.DEFAULT:
			case EolParser.CASE: return new Case();
			case EolParser.SWITCH: return new SwitchStatement();
			case EolParser.IF: return new IfStatement();
			case EolParser.ITEMSELECTOR: return new ItemSelectorExpression();
			case EolParser.ARROW:
			case EolParser.POINT: {
				AST secondChild = cst.getSecondChild();
				if (!secondChild.hasChildren()) {
					return new PropertyCallExpression();
				}
				else if (secondChild.getExtraTokens().size() >= 2) {
					if (secondChild.getChildren().stream().anyMatch(ast -> ast.getType() == EolParser.LAMBDAEXPR)) {
						return new ComplexOperationCallExpression();
					}
					else {
						return new FirstOrderOperationCallExpression();
					}
				}
				else {
					return new OperationCallExpression();
				}
			}
			case EolParser.NAME: {
				if (cst.hasChildren() && cst.getFirstChild().getType() == EolParser.PARAMLIST) {
					return new FirstOrderOperationCallExpression();
				}
				else {
					return new NameExpression();
				}
			}
			case EolParser.FORMAL: return new Parameter();
			case EolParser.BLOCK: return new StatementBlock();
			case EolParser.FEATURECALL: {
				if (cst.hasChildren() && cst.getFirstChild().getType() == EolParser.PARAMETERS && 
						((cst.getParent().getType() != EolParser.ARROW && cst.getParent().getType() != EolParser.POINT) ||
						(cst.getParent().getType() == EolParser.ARROW || cst.getParent().getType() == EolParser.POINT) && cst.getParent().getFirstChild() == cst)) {
					return new OperationCallExpression(true);
				}
				else {
					return new NameExpression();
				}
			}
			case EolParser.STRING: return new StringLiteral();
			case EolParser.INT: return new IntegerLiteral();
			case EolParser.BOOLEAN: return new BooleanLiteral();
			case EolParser.FLOAT: return new RealLiteral();
			case EolParser.ASSIGNMENT: return new AssignmentStatement();
			case EolParser.SPECIAL_ASSIGNMENT: return new SpecialAssignmentStatement();
			case EolParser.EXPRESSIONINBRACKETS: return new ExpressionInBrackets();
			case EolParser.VAR: return new VariableDeclaration();
			case EolParser.NEW: {
				if (cst.getFirstChild().getType() == EolParser.TYPE) {
					return new NewInstanceExpression();
				}
				else {
					return new VariableDeclaration();
				}
			}
			case EolParser.TYPE: return new TypeExpression();
			case EolParser.IMPORT: return new Import();
			case EolParser.OPERATOR: {
				if (cst.getText().equals("=") && ((
										(parentAst instanceof IfStatement || parentAst instanceof ForStatement || parentAst instanceof WhileStatement) 
										&& (cst.getParent().getFirstChild() != cst)) || 
										parentAst instanceof StatementBlock /*|| 
										"BLOCK".equals(parentAst.getText())*/)) {
					return new AssignmentStatement();
				}
				else {
					return operatorExpressionFactory.createOperatorExpression(cst);
				}
			}
			case EolParser.CONTINUE: return new ContinueStatement();
			case EolParser.DELETE: return new DeleteStatement();
			case EolParser.HELPERMETHOD: return new Operation();
			case EolParser.RETURN: return new ReturnStatement();
			case EolParser.ENUMERATION_VALUE: return new EnumerationLiteralExpression();
			case EolParser.Annotation: return new SimpleAnnotation();
			case EolParser.EXECUTABLEANNOTATION: return new ExecutableAnnotation();
			case EolParser.ANNOTATIONBLOCK: return new AnnotationBlock();
			case EolParser.MAP: return new MapLiteralExpression();
			case EolParser.COLLECTION: return new CollectionLiteralExpression();
			case EolParser.BREAK: return new BreakStatement(false);
			case EolParser.BREAKALL: return new BreakStatement(true);
			case EolParser.THROW: return new ThrowStatement();
			case EolParser.ABORT: return new AbortStatement();
			case EolParser.TRANSACTION: return new TransactionStatement();
			case EolParser.MODELDECLARATION: return new ModelDeclaration();
			case EolParser.MODELDECLARATIONPARAMETER: return new ModelDeclarationParameter();
			
			default: return null;
		}
	}
	
	@Override
	protected Lexer createLexer(ANTLRInputStream inputStream) {
		return new EolLexer(inputStream);
	}
	
	@Override
	public EpsilonParser createParser(TokenStream tokenStream) {
		return new EolParser(tokenStream);
	}
	
	@Override
	public String getMainRule() {
//		String className = getClass().getSimpleName();
//		return Character.toLowerCase(className.charAt(0)) + className.substring(1);
		return "eolModule";
	}
	
	@Override
	public OperationList getDeclaredOperations() {
		return declaredOperations;
	}
	
	protected HashMap<String, Class<?>> getImportConfiguration() {
		HashMap<String, Class<?>> importConfiguration = new HashMap<>(8);
		importConfiguration.put("eol", EolModule.class);
		return importConfiguration;
	}
	
	@Override
	public EolCompilationContext getCompilationContext() {
		if (compilationContext == null) {
			compilationContext = new EolCompilationContext();
			compilationContext.setModelDeclarations(getDeclaredModelDeclarations());
		}
		compilationContext.setRuntimeContext(getContext());
		return compilationContext;
	}

	protected void prepareContext() throws EolRuntimeException {
		IEolContext context = getContext();
		
		EolSystem system = new EolSystem();
		system.setContext(context);
		context.setModule(this);
		
		context.getFrameStack().putGlobal(
			Variable.createReadOnlyVariable("null", null),
			Variable.createReadOnlyVariable("System", system)
		);
	}

	@Override
	public List<Import> getImports() {
		return imports;
	}
	
	@Override
	public String toString() {
		if (this.sourceFile != null) {
			return this.sourceFile.getAbsolutePath();
		}
		else return super.toString();
	}

	@Override
	public Set<ModelDeclaration> getModelDelcarations() {
		if (modelDeclarations == null) {
			modelDeclarations = new ListSet<>();
			for (Import import_ : imports) {
				if (import_.isLoaded() && import_.getModule() instanceof IEolModule){
					modelDeclarations.addAll(((IEolModule)import_.getModule()).getModelDelcarations());
				}
			}
			modelDeclarations.addAll(this.getDeclaredModelDeclarations());
		}
		return modelDeclarations;
	}
	
	@Override
	public OperationList getOperations() {
		return operations;
	}
	
	protected Collection<Import> getImportsByExtension(AST cst, String extension, Class<?> moduleImplClass) {
		List<AST> importAsts = AstUtil.getChildren(cst, EolParser.IMPORT);
		List<Import> imports = new ArrayList<>(importAsts.size());
		
		for (AST importAst : importAsts) {
			IModule module = null;
			try {
				module = (IModule) moduleImplClass.getDeclaredConstructor().newInstance();
				if (module instanceof IEolModule) {
					((IEolModule)module).setParentModule(this);
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			
			Import import_ = (Import) createAst(importAst, this);
			
			if (!import_.getPath().endsWith("." + extension)) continue;
			
			import_.setParentModule(this);
			import_.setImportedModule(module);
			
			if (sourceUri == null && sourceFile == null) {
				import_.load(null);
			
			} else if (sourceUri != null) {
				import_.load(sourceUri);
				
			} else {
				import_.load(sourceFile.toURI());
			}
			if (!import_.isLoaded()) {
				ParseProblem problem = new ParseProblem();
				problem.setLine(import_.getRegion().getStart().getLine());
				String reason;
				if (!import_.isFound()) {
					reason = "File " + import_.getPath() + " not found";
				}
				else {
					reason = "File " + import_.getPath() + " contains errors: " + import_.getModule().getParseProblems();
				}
				problem.setReason(reason);
				getParseProblems().add(problem);
			}
			imports.add(import_);
		}
		return imports;
	}
	
	protected void checkImports(AST cst) {
		for (AST importAst : AstUtil.getChildren(cst, EolParser.IMPORT)) {
			String importedFile = importAst.getFirstChild().getText();
			boolean validExtension = false;
			for (String extension : getImportConfiguration().keySet()) {
				if (importedFile.endsWith("." + extension)) {
					validExtension = true;
				}
			}
			if (!validExtension) {
				ParseProblem problem = new ParseProblem();
				problem.setLine(importAst.getLine());
				problem.setReason("Importing " + importAst.getFirstChild().getText() + " is not supported in this language");
				problem.setSeverity(ParseProblem.WARNING);
				getParseProblems().add(problem);
			}
		}		
	}

	@Override
	public List<ModelDeclaration> getDeclaredModelDeclarations() {
		return declaredModelDeclarations;
	}

	@Override
	public IEolModule getParentModule() {
		return parent;
	}

	@Override
	public void setParentModule(IEolModule parent) {
		this.parent = parent;
	}
	
	@Override
	public Object execute() throws EolRuntimeException {
		IEolContext context = getContext();
		prepareContext();
		return context.getExecutorFactory().execute(this, context);
	}
	
	public Object executeImpl() throws EolRuntimeException {
		IEolContext context = getContext();
		return Return.getValue(context.getExecutorFactory().execute(main, context));
	}
	
	@Override
	public List<ModuleMarker> compile() {
		EolCompilationContext context = getCompilationContext();
		for (ModelDeclaration modelDeclaration : getDeclaredModelDeclarations()) {
			modelDeclaration.compile(context);
		}
		for (Operation operation : getDeclaredOperations()) {
			operation.compile(context);
		}
		if (main != null) {
			main.compile(context);
		}
		return context.getMarkers();
	}
	
	@Override
	public List<Statement> getPostOperationStatements() {
		return postOperationStatements;
	}
	
	@Override
	public StatementBlock getMain() {
		return main;
	}

	public void setMain(StatementBlock main) {
		this.main = main;
	}
	
	@Override
	public IEolContext getContext() {
		return context;
	}

	@Override
	public void setContext(IEolContext context) {
		if (context != null && !expectedContextType.isInstance(context)) {
			throw new IllegalArgumentException(
				"Invalid context type: expected "+expectedContextType.getName()
				+ " but got "+context.getClass().getName()
			);
		}
		if (this.context != context) {
			this.context = context;
			for (Import import_ : getImports()) {
				import_.setContext(context);
			}
		}
	}

	/**
	 * Clear all cached results and type information, and all extended
	 * properties. Useful for rerunning the same EolModule with different sets
	 * of models, without having to parse it again.
	 */
	public void clearCache() {
		for (Operation op : getOperations()) {
			op.clearCache();
		}
		getContext().getExtendedProperties().clear();
	}
}
