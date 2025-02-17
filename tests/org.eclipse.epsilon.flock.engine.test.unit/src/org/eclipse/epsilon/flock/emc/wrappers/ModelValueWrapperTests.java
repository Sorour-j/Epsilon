/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.flock.emc.wrappers;

import static junit.framework.Assert.assertTrue;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isA;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.eclipse.epsilon.flock.test.unit.easymock.matchers.OneOfCollection.oneOf;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import org.eclipse.epsilon.hutn.test.model.families.DogBreed;
import org.junit.Test;

public class ModelValueWrapperTests {
	
	@Test
	public void wrapValue() {
		wrapValueTest("a model value", AttributeValue.class);
	}
	
	@Test
	public void wrapModelElement() {		
		wrapValueTest("dummy model element", ModelElement.class, "dummy model element");
	}
	
	@Test
	public void wrapCollectionOfValues() {
		final CollectionOfModelValues wrappedValues = wrapValueTest(Arrays.asList("a model value", "dummy model element"),
		                                                            CollectionOfModelValues.class,
		                                                            "dummy model element");
		
		for (ModelValue<?> wrappedValue : wrappedValues) {
			if (wrappedValue.unwrap().equals("dummy model element")) {
				assertWrappedValueTypeEquals(ModelElement.class, wrappedValue);
			} else {
				assertWrappedValueTypeEquals(AttributeValue.class, wrappedValue);
			}
		}
	}
	
	@Test
	public void wrapEnumerationValue() {
		wrapValueTest(DogBreed.LABRADOR, true, EnumValue.class);
	}
	
	@Test
	public void wrapingAWrappedValueShouldNotReturnADoublyWrappedValue() {
		final ModelValue<?> wrappedValue = wrapValue("a model value");
		
		// Unwrapping a doubly wrapped value is the same as unwrapping a wrapped value
		assertEquals(wrappedValue.unwrap(), wrapValue(wrappedValue).unwrap());
	}	
	
	
	
	private static <T extends ModelValue<?>> T wrapValueTest(Object unwrappedValue, Class<T> expectedWrappedType, Object... modelElements) {	
		return wrapValueTest(unwrappedValue, false, expectedWrappedType, modelElements);
	}
	
	private static <T extends ModelValue<?>> T wrapValueTest(Object unwrappedValue, boolean isEnumValue, Class<T> expectedWrappedType, Object... modelElements) {	
		final ModelValue<?> wrappedValue = wrapValue(unwrappedValue, isEnumValue ,modelElements);
		
		assertWrappedValueTypeEquals(expectedWrappedType, wrappedValue);
		
		return expectedWrappedType.cast(wrappedValue);
	}
	
	private static ModelValue<?> wrapValue(Object unwrappedValue, Object... modelElements) {	
		return wrapValue(unwrappedValue, false, modelElements);
	}
	
	private static ModelValue<?> wrapValue(Object unwrappedValue, boolean isEnumValue, Object... modelElements) {	
		final ModelValueWrapper wrapper = new ModelValueWrapper(createModelStubWithModelElements(isEnumValue, modelElements));
		
		return wrapper.wrapValue(unwrappedValue);
	}

	private static <T extends ModelValue<?>> void assertWrappedValueTypeEquals(Class<T> expectedWrappedType, ModelValue<?> wrappedValue) {
		assertTrue("Expected an instance of <" + expectedWrappedType.getCanonicalName() + "> " +
		           "but was " + (wrappedValue == null ? "null" : "an instance of <" + wrappedValue.getClass().getCanonicalName() + ">"),
		           expectedWrappedType.isInstance(wrappedValue));
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Model createModelStubWithModelElements(boolean isEnumValue, Object... modelElements) {
		final Model modelStub = createMock(Model.class);
		
		// Respond as to whether this is an enumeration value
		expect(modelStub.isEnumeration(isA(Object.class)))
			.andReturn(isEnumValue).anyTimes();
		
		// Return true for any object contained in modelElements
		expect(modelStub.isModelElement(oneOf(Arrays.asList(modelElements))))
			.andReturn(true);
		expectLastCall().anyTimes();
		
		// And return false for any object not contained in modelElements
		expect(modelStub.isModelElement(isA(Object.class)))
			.andReturn(false);
		expectLastCall().anyTimes();
		
		// Return dummy type for type of any object contained in modelElements
		expect(modelStub.getTypeNameOf(oneOf(Arrays.asList(modelElements))))
			.andReturn("dummy type");
		expectLastCall().anyTimes();

		expect(modelStub.getUnqualifiedTypeNameOf(oneOf(Arrays.asList(modelElements))))
			.andReturn("dummy unqualified type");
		expectLastCall().anyTimes();
		
		
		// CollectionOfModelValues will use model to wrap values
		expect(modelStub.wrap(oneOf(Arrays.asList(modelElements))))
			.andReturn((BackedModelValue)new ModelElement(modelStub, new ModelType(modelStub, "dummy type", "dummy unqualified type"), "dummy model element"));
		expectLastCall().anyTimes();
		
		expect(modelStub.wrap(isA(Object.class)))
			.andReturn((BackedModelValue)new AttributeValue(modelStub, "dummy wrapped value"));
		expectLastCall().anyTimes();
		
		replay(modelStub);
		
		return modelStub;
	}
}
