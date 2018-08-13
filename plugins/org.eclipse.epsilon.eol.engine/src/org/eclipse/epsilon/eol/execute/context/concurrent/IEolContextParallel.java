package org.eclipse.epsilon.eol.execute.context.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.eclipse.epsilon.common.concurrent.ConcurrencyUtils;
import org.eclipse.epsilon.common.concurrent.ConcurrentExecutionStatus;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.concurrent.EolNestedParallelismException;
import org.eclipse.epsilon.eol.execute.concurrent.executors.EolExecutorService;
import org.eclipse.epsilon.eol.execute.concurrent.executors.EolThreadPoolExecutor;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public interface IEolContextParallel extends IEolContext {
	
	/**
	 * Maximum supported level of parallel nesting.
	 */
	static final int PARALLEL_NEST_THRESHOLD = 1;
	
	/**
	 * Indicates the scalability of this Context when more processing nodes are added.
	 * 
	 * @return the number of threads.
	 */
	int getParallelism();
	
	/**
	 * This method signals the start of parallel execution. A typical implementation
	 * should initialise thread-local data structures (if not already done so) and
	 * make non-thread-local structures thread-safe. It should also set a flag to
	 * indicate that parallel execution has begun; so that {@linkplain #isParallel()}
	 * returns true.
	 */
	void goParallel();
	
	/**
	 * This method signals the end of parallel execution. A typical implementation
	 * should merge all useful data from thread-local structures into the original
	 * structures, dispose of any variables and structures used during parallel
	 * execution and shutdown the cached EolExecutorService.
	 */
	void endParallel();
	
	/**
	 * This method will typically return true if execution of the associated
	 * {@link IEolModule} has begun, and will return false if execution has ended or not started.
	 * 
	 * @return whether this Context is currently executing in parallel mode.
	 */
	boolean isParallel();

	default EolExecutorService beginParallelJob(ModuleElement entryPoint) throws EolNestedParallelismException {
		EolExecutorService executor = getExecutorService();
		enterParallelNest(entryPoint);
		executor.getExecutionStatus().begin();
		return executor;
	}
	
	/**
	 * Executes all of the tasks in parallel, blocking until they have completed.
	 * @param jobs The tasks to execute.
	 * @return The result set in the {@link ConcurrentExecutionStatus}, if any.
	 * @throws EolRuntimeException If any of the jobs throw an exception.
	 */
	default Object executeParallel(Collection<Runnable> jobs) throws EolRuntimeException {
		EolExecutorService executor = beginParallelJob(getModule());
		ArrayList<Future<?>> futures = new ArrayList<>(jobs.size());
		for (Runnable job : jobs) {
			futures.add(executor.submit(job));
		}
		Object result = executor.awaitCompletion(futures);
		exitParallelNest();
		return result;
	}
	
	/**
	 * Allows for recycling of an {@linkplain EolExecutorService},
	 * as derived from {@link #newExecutorService()}.
	 * 
	 * @return A cached EolExecutorService.
	 * @see #newExecutorService()
	 */
	EolExecutorService getExecutorService();
	
	/**
	 * Constructs a one-shot EolExecutorService. Unlike {@link #getExecutorService()},
	 * this method will always return a new instance of {@linkplain EolExecutorService}.
	 * 
	 * @return a new EolExecutorService
	 */
	default EolExecutorService newExecutorService() {
		return EolThreadPoolExecutor.defaultExecutor(getParallelism());
	}
	
	/**
	 * This method is used to signal nesting of parallel jobs. This method records
	 * the beginning of a nesting level associated with a module element.
	 * 
	 * @param entryPoint The module element which started this parallelism nesting.
	 * @throws EolNestedParallelismException if the maximum supported nesting level is exceeded.
	 * @see #exitParallelNest()
	 */
	void enterParallelNest(ModuleElement entryPoint) throws EolNestedParallelismException;
	
	/**
	 * Leaves the parallel nest. Typical implementations will simply
	 * decrement the nest count as returned by {@link #getNestedParallelism()}.
	 * 
	 * @see #enterParallelNest(ModuleElement)
	 */
	void exitParallelNest();
	
	//Convenience methods
	
	default void handleException(Exception exception, EolExecutorService executor) {
		// Cache the Epsilon stack trace
		if (exception instanceof EolRuntimeException)
			exception.getMessage();
	
		if (executor != null)
			executor.getExecutionStatus().completeExceptionally(exception);
	}
	
	default <R> R parallelGet(ThreadLocal<? extends R> threadLocal, Supplier<? extends R> originalValueGetter) {
		return isParallel() && !ConcurrencyUtils.isMainThread() ? threadLocal.get() : originalValueGetter.get();
	}
	
	default <T> void parallelSet(T value, ThreadLocal<? super T> threadLocal, Consumer<? super T> originalValueSetter) {
		if (isParallel() && !ConcurrencyUtils.isMainThread())
			threadLocal.set(value);
		else
			originalValueSetter.accept(value);
	}
	
	@SuppressWarnings("unchecked")
	static <C extends IEolContext, P extends IEolContextParallel> P convertToParallel(C context_, Class<P> parallelContextClass, Function<C, ? extends P> parallelConstructor) {
		P context = parallelContextClass.isInstance(context_) ?
			(P) context_ : parallelConstructor.apply(context_);

		context.goParallel();
		return context;
	}
}
