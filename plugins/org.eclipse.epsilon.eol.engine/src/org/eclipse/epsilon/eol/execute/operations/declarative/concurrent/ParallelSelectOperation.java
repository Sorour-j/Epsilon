package org.eclipse.epsilon.eol.execute.operations.declarative.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Future;
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
import org.eclipse.epsilon.eol.execute.operations.declarative.FirstOrderOperation;
import org.eclipse.epsilon.eol.types.EolCollectionType;

public class ParallelSelectOperation extends FirstOrderOperation {
	
	@Override
	public Collection<?> execute(Object target, Variable iterator, Expression expression, IEolContext context_) throws EolRuntimeException {
		
		IEolContextParallel context = context_ instanceof IEolContextParallel ?
			(EolContextParallel) context_ : new EolContextParallel(context_);
		context.goParallel();
		
		Collection<Object> source = CollectionUtil.asCollection(target);
		Collection<Object> resultsCol = EolCollectionType.createSameType(source);
		EolExecutorService executor = context.newExecutorService();
		Collection<Future<Optional<?>>> futures = new ArrayList<>(source.size());
		
		for (Object item : source) {
			futures.add(executor.submit(() -> {
				Optional<?> intermediateResult = null;
				if (iterator.getType() == null || iterator.getType().isKind(item)) {
					FrameStack scope = context.getFrameStack();
					scope.enterLocal(FrameType.UNPROTECTED, expression,
						Variable.createReadOnlyVariable(iterator.getName(), item));
					
					Object bodyResult = context.getExecutorFactory().execute(expression, context);
					
					if (bodyResult instanceof Boolean && ((boolean) bodyResult)) {
						intermediateResult = Optional.ofNullable(item);
					}
					
					scope.leaveLocal(expression);
				}
				return intermediateResult;
			}));
		}
		
		executor.collectResults(futures, true)
			.stream()
			.filter(opt -> opt != null)
			.map(opt -> opt.orElse(null))
			.forEach(resultsCol::add);
		
		//context.endParallel();
		
		return resultsCol;
	}
}
