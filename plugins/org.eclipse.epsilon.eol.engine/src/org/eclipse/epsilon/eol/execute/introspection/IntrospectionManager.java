/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.introspection;

import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertySetter;
import org.eclipse.epsilon.eol.models.IModel;

public class IntrospectionManager {
	
	protected IPropertyGetter defaultPropertyGetter = new JavaPropertyGetter();
	protected IPropertySetter defaultPropertySetter = new JavaPropertySetter();
	
	public IPropertySetter getPropertySetterFor(Object instance, String property, IEolContext context) {
		final IPropertySetter propertySetter;
		
		if (property.startsWith("~")) {
			propertySetter = new ExtendedPropertySetter(context);
		}
		else {
			IModel knowsModel = getModelThatKnowsAboutProperty(instance, property, context);
			propertySetter = knowsModel != null ? knowsModel.getPropertySetter() : defaultPropertySetter;
		}
		
		propertySetter.setObject(instance);
		propertySetter.setProperty(property);
		propertySetter.setContext(context);
		
		return propertySetter;
	}
	
	public IPropertyGetter getPropertyGetterFor(Object instance, String property, IEolContext context) {
		final IPropertyGetter propertyGetter;
		
		if (property.startsWith("~")) {
			propertyGetter = new ExtendedPropertyGetter(context);
		}
		else { 
			IModel knowsModel = getModelThatKnowsAboutProperty(instance, property, context);
			propertyGetter = knowsModel != null ? knowsModel.getPropertyGetter() : defaultPropertyGetter;
		}
		
		propertyGetter.setContext(context);
		
		return propertyGetter;
	}
	
	public boolean isModelBasedProperty(Object instance, String property, IEolContext context) {
		return getModelThatKnowsAboutProperty(instance, property, context) != null;
	}
	
	public static IModel getModelThatKnowsAboutProperty(Object instance, String property, IEolContext context) {
		for (IModel model : context.getModelRepository().getModels()) {
			if (model.knowsAboutProperty(instance, property))
				return model;
		}
		return null;
	}

	public IPropertyGetter getDefaultPropertyGetter() {
		return defaultPropertyGetter;
	}

	public void setDefaultPropertyGetter(IPropertyGetter defaultPropertyGetter) {
		this.defaultPropertyGetter = defaultPropertyGetter;
	}

	public IPropertySetter getDefaultPropertySetter() {
		return defaultPropertySetter;
	}

	public void setDefaultPropertySetter(IPropertySetter defaultPropertySetter) {
		this.defaultPropertySetter = defaultPropertySetter;
	}
}
