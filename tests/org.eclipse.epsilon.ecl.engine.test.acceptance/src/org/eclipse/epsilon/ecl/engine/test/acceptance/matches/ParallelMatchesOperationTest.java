/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.ecl.engine.test.acceptance.matches;

import java.util.function.Supplier;
import org.eclipse.epsilon.ecl.IEclModule;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class ParallelMatchesOperationTest extends MatchesOperationTest {

	public ParallelMatchesOperationTest(Supplier<? extends IEclModule> moduleGetter) {
		super(moduleGetter);
		module = moduleGetter.get();
		//TODO: see why this is failing (on relatively rare occasions, but still)
		script = "CompareInstanceParallel.ecl";
	}
}
