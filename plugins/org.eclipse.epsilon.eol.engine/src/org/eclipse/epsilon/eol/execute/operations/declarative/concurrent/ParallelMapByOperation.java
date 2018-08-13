/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.declarative.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.concurrent.executors.EolExecutorService;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.execute.context.concurrent.EolContextParallel;
import org.eclipse.epsilon.eol.execute.context.concurrent.IEolContextParallel;
import org.eclipse.epsilon.eol.execute.operations.declarative.MapByOperation;
import org.eclipse.epsilon.eol.types.EolMap;
import org.eclipse.epsilon.eol.types.EolSequence;
import org.eclipse.epsilon.eol.types.EolType;

public class ParallelMapByOperation extends MapByOperation {

	@Override
	public EolMap<?, EolSequence<Object>> execute(Object target, Variable iterator, Expression expression,
			IEolContext context_) throws EolRuntimeException {
		
		Collection<?> source = CollectionUtil.asCollection(target);
		if (source.isEmpty()) return new EolMap<>();
		
		EolType iteratorType = iterator.getType();
		String iteratorName = iterator.getName();
		IEolContextParallel context = EolContextParallel.convertToParallel(context_);
		Collection<Future<Entry<?, ?>>> futures = new ArrayList<>(source.size());
		
		EolExecutorService executor = context.beginParallelJob(expression);
		
		for (Object item : source) {
			if (iteratorType == null || iteratorType.isKind(item)) {
				futures.add(executor.submit(() -> {
					
					FrameStack scope = context.getFrameStack();
					scope.enterLocal(FrameType.UNPROTECTED, expression,
						new Variable(iteratorName, item, iteratorType, true)
					);
					
					Object bodyResult = context.getExecutorFactory().execute(expression, context);
					
					scope.leaveLocal(expression);
					return new SimpleEntry<>(bodyResult, item);
				}));
			}
		}
		
		Collection<Entry<?, ?>> intermediates = executor.collectResults(futures);
		
		context.exitParallelNest();
		
		return intermediates.stream()
			.collect(Collectors.toMap(
				Entry::getKey,
				entry -> {
					EolSequence<Object> value = new EolSequence<>();
					value.add(entry.getValue());
					return value;
				},
				(oldVal, newVal) -> {
					oldVal.addAll(newVal);
					return oldVal;
				},
				EolMap::new
			)
		);
	}
}
