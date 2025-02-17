/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.etl.execute.context.concurrent;

import org.eclipse.epsilon.erl.execute.context.concurrent.ErlContextParallel;
import org.eclipse.epsilon.etl.IEtlModule;
import org.eclipse.epsilon.etl.strategy.ITransformationStrategy;
import org.eclipse.epsilon.etl.trace.TransformationTrace;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EtlContextParallel extends ErlContextParallel implements IEtlContextParallel {

	protected TransformationTrace transformationTrace;
	protected ITransformationStrategy transformationStrategy;
	
	public EtlContextParallel() {
		this(0);
	}

	public EtlContextParallel(int parallelism) {
		super(parallelism);
		transformationTrace = new TransformationTrace(true);
	}
	
	@Override
	public TransformationTrace getTransformationTrace() {
		return transformationTrace;
	}

	@Override
	public ITransformationStrategy getTransformationStrategy() {
		return transformationStrategy;
	}

	@Override
	public void setTransformationStrategy(ITransformationStrategy strategy) {
		this.transformationStrategy = strategy;
	}
	
	@Override
	public IEtlModule getModule() {
		return (IEtlModule) super.getModule();
	}
}
