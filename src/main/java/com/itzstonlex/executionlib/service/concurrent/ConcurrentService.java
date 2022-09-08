package com.itzstonlex.executionlib.service.concurrent;

import com.itzstonlex.executionlib.service.AbstractExecutionService;
import com.itzstonlex.executionlib.service.DeclaredExecutor;
import com.itzstonlex.executionlib.service.ExecutionService;
import com.itzstonlex.executionlib.service.ExecutionServiceSupports;
import lombok.NonNull;

import java.lang.annotation.Annotation;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ExecutionServiceSupports(supports = {Async.class})
public class ConcurrentService extends AbstractExecutionService {

    @Override
    protected void initExecutors(Hashtable<Class<? extends Annotation>, DeclaredExecutor<?>> executors) {
        executors.put(Async.class, new DeclaredAsyncExecutor());
    }

    private static class DeclaredAsyncExecutor implements DeclaredExecutor<Async> {

        private static final ExecutorService THREADS_POOL
                = Executors.newCachedThreadPool();

        @Override
        public void execute(@NonNull Async annotation, @NonNull DeclaredTask task) throws Exception {
            THREADS_POOL.submit(() -> {

                try {
                    task.invoke();
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
        }
    }
}
