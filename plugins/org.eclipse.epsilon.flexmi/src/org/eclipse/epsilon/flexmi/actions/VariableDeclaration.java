package org.eclipse.epsilon.flexmi.actions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.flexmi.FlexmiResource;

public class VariableDeclaration extends Action {
	
	protected EObject eObject;
	protected String name;
	protected VariableDeclarationType type;
	protected ModuleElement entryPoint;
	
	public VariableDeclaration(EObject eObject, String name, VariableDeclarationType type) {
		super();
		this.eObject = eObject;
		this.name = name;
		this.type = type;
	}
	
	@Override
	public void perform(FlexmiResource resource) throws Exception {
		
		Variable variable = Variable.createReadOnlyVariable(name, eObject);
		if (type == VariableDeclarationType.GLOBAL) {
			resource.getFrameStack().putGlobal(variable);
		}
		else if (type == VariableDeclarationType.REGULAR) {
			resource.getFrameStack().put(variable);
		}
		else {
			resource.getFrameStack().enterLocal(FrameType.PROTECTED, entryPoint);
			resource.getFrameStack().put(variable);
		}
	}
	
	public VariableDeclarationType getType() {
		return type;
	}
	
	public void setEntryPoint(ModuleElement entryPoint) {
		this.entryPoint = entryPoint;
	}
	
	public enum VariableDeclarationType {
		LOCAL,
		GLOBAL,
		REGULAR
	}
}
