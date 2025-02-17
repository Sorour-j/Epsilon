/*******************************************************************************
 * Copyright (c) 2012-2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - refactoring
 ******************************************************************************/
package org.eclipse.epsilon.egl;

import java.io.File;
import java.net.URI;
import java.util.*;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.TokenStream;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonParser;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.egl.dom.GenerationRule;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.execute.context.EgxContext;
import org.eclipse.epsilon.egl.execute.context.IEgxContext;
import org.eclipse.epsilon.egl.parse.EgxLexer;
import org.eclipse.epsilon.egl.parse.EgxParser;
import org.eclipse.epsilon.egl.traceability.Template;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.Import;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.erl.ErlModule;
import org.eclipse.epsilon.erl.dom.NamedRuleList;

/**
 * 
 * @since 1.6 extends ERL rather than EGL.
 */
public class EgxModule extends ErlModule implements IEgxModule {

	protected NamedRuleList<GenerationRule> generationRules;
	protected NamedRuleList<GenerationRule> declaredGenerationRules = new NamedRuleList<>();
	protected Collection<Template> invokedTemplates = new ArrayList<>();
	
	public EgxModule() {
		this((IEgxContext) null);
	}
	
	/**
	 * Instantiates the module and the context configured for outputting generated files
	 * to the specified directory.
	 * 
	 * @param outputRoot The base location to send output files to.
	 * @throws EglRuntimeException If the path could not be resolved.
	 * @since 1.6
	 */
	public EgxModule(String outputRoot) throws EglRuntimeException {
		this();
		setFileGeneratingTemplateFactory(outputRoot);
	}
	
	/**
	 * Instantiates the module with the specified execution context.
	 * @param context The execution context
	 */
	public EgxModule(IEgxContext context) {
		super(context != null ? context : new EgxContext());
	}
	
	public EgxModule(EglTemplateFactory templateFactory) {
		this(new EgxContext(templateFactory));
	}
	
	/**
	 * Convenience method for initialising a {@link EglFileGeneratingTemplateFactory} with
	 * the specified output path.
	 * 
	 * @param outputRoot
	 * @throws EglRuntimeException
	 * @since 1.6
	 */
	protected void setFileGeneratingTemplateFactory(String outputRoot) throws EglRuntimeException {
		getContext().setTemplateFactory(new EglFileGeneratingTemplateFactory(new File(outputRoot).getAbsolutePath()));
	}
	
	@Override
	public Collection<Template> getInvokedTemplates() {
		return invokedTemplates;
	}
	
	@Override
	protected Lexer createLexer(ANTLRInputStream inputStream) {
		return new EgxLexer(inputStream);
	}
	
	@Override
	public EpsilonParser createParser(TokenStream tokenStream) {
		return new EgxParser(tokenStream);
	}

	@Override
	public String getMainRule() {
		return "egxModule";
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		
		List<AST> generationRuleChildren = AstUtil.getChildren(cst, EgxParser.GENERATE);
		declaredGenerationRules.ensureCapacity(generationRuleChildren.size());
		// Parse the transform rules
		for (AST generationRuleAst : generationRuleChildren) {
			declaredGenerationRules.add((GenerationRule) module.createAst(generationRuleAst, this));
		}
		
		getParseProblems().addAll(calculateSuperRules(getGenerationRules()));
	}
	
	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		switch (cst.getType()) {
			case EgxParser.GENERATE: return createGenerationRule(cst);
			case EgxParser.PARAMETERS: return new ExecutableBlock<>(Map.class);
			case EgxParser.TARGET:
			case EgxParser.TEMPLATE:
				return new ExecutableBlock<>(String.class);
			case EgxParser.OVERWRITE:
			case EgxParser.GUARD:
			case EgxParser.MERGE:
				return new ExecutableBlock<>(Boolean.class);
			case EgxParser.PRE:
			case EgxParser.POST: {
				if (parentAst instanceof GenerationRule) {
					return new ExecutableBlock<>(Void.class);
				}
			}
		}
		return super.adapt(cst, parentAst);
	}
	
	/**
	 * Subclasses may override this method to change the implementation of
	 * {@link GenerationRule} that is instantiated after parsing an EGX
	 * program.
	 */
	protected GenerationRule createGenerationRule(AST generationRuleAst) {
		return new GenerationRule();
	}
	
	@Override
	public List<GenerationRule> getDeclaredGenerationRules() {
		return declaredGenerationRules;
	}
	
	@Override
	public boolean parse(File file) throws Exception {
		boolean result = super.parse(file);
		if (result) getContext().getTemplateFactory().initialiseRoot(file.getAbsoluteFile().getParentFile().toURI());
		return result;
	}
	
	@Override
	public boolean parse(URI uri) throws Exception {
		boolean result = super.parse(uri);
		if (result) getContext().getTemplateFactory().initialiseRoot(uri);
		return result;
	}
	
	@Override
	public boolean parse(String code, File file) throws Exception {
		boolean result = super.parse(code, file);
		if (result && file != null) getContext().getTemplateFactory().initialiseRoot(file.getAbsoluteFile().getParentFile().toURI());
		return result;
	}

	@Override
	protected void prepareContext() throws EolRuntimeException {
		super.prepareContext();
		getContext().getTemplateFactory().getContext().copyFrom(getContext(), true);
	}
	
	@Override
	protected Object processRules() throws EolRuntimeException {
		IEgxContext context = getContext();
		for (GenerationRule rule : getGenerationRules()) {
			for (Object element : rule.getAllElements(context)) {
				rule.generate(element, this);
			}
		}
		return null;
	}
	
	@Override
	public HashMap<String, Class<?>> getImportConfiguration() {
		HashMap<String, Class<?>> importConfiguration = super.getImportConfiguration();
		importConfiguration.put("egx", EgxModule.class);
		return importConfiguration;
	}
	
	@Override
	public List<GenerationRule> getGenerationRules() {
		if (generationRules == null) {
			generationRules = new NamedRuleList<>();
			for (Import import_ : imports) {
				if (import_.isLoaded() && (import_.getModule() instanceof IEgxModule)) {
					IEgxModule module = (IEgxModule) import_.getModule();
					generationRules.addAll(module.getGenerationRules());
				}
			}
			generationRules.addAll(declaredGenerationRules);
		}
		return generationRules;
	}

	@Override
	protected int getPostBlockTokenType() {
		return EgxParser.POST;
	}

	@Override
	protected int getPreBlockTokenType() {
		return EgxParser.PRE;
	}
	
	@Override
	public IEgxContext getContext() {
		return (IEgxContext) super.getContext();
	}
}
