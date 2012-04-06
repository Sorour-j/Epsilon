/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.dom.print;

import org.eclipse.epsilon.eol.dom.DomElement;
import org.eclipse.epsilon.eol.dom.EnumerationLiteralExpression;

public class EnumerationLiteralExpressionPrinter implements DomElementPrinter {

	public String print(DomElement e, DomElementPrinterFactory f) {
		String s = "";
		EnumerationLiteralExpression ex = (EnumerationLiteralExpression) e;
		if (ex.getModel() != null) {
			s = ex.getModel() + "!";
		}
		s = s + ex.getEnumeration() + "#" + ex.getLiteral();
		return s;
	}

}
