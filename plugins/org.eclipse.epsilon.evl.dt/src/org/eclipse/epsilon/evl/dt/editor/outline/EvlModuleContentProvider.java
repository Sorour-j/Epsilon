package org.eclipse.epsilon.evl.dt.editor.outline;

import java.util.List;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.erl.dt.editor.outline.ErlModuleContentProvider;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.dom.ConstraintContext;

public class EvlModuleContentProvider extends ErlModuleContentProvider {
	
	@Override
	public List<ModuleElement> getVisibleChildren(ModuleElement moduleElement) {
		List<ModuleElement> visible = super.getVisibleChildren(moduleElement);
		
		if (moduleElement.getClass() == EvlModule.class) {
			EvlModule module = (EvlModule) moduleElement;
			visible.addAll(module.getImports());
			visible.addAll(module.getDeclaredModelDeclarations());
			visible.addAll(module.getDeclaredPre());
			visible.addAll(module.getDeclaredConstraintContexts());
			visible.addAll(module.getDeclaredPost());
			visible.addAll(module.getDeclaredOperations());
		}
		else if (moduleElement instanceof ConstraintContext) {
			ConstraintContext context = (ConstraintContext) moduleElement;
			visible.addAll(context.getConstraints());
		}
		
		return visible;
	}
	
	@Override
	public ModuleElement getFocusedModuleElement(ModuleElement moduleElement) {
		if (moduleElement instanceof ConstraintContext) {
			return ((ConstraintContext) moduleElement).getTypeExpression();
		}
		return super.getFocusedModuleElement(moduleElement);
	}
	
}
