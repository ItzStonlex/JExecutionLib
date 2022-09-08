package com.itzstonlex.executionlib.service.concurrent;

import com.itzstonlex.executionlib.declare.DeclaredExecutor;
import com.itzstonlex.executionlib.declare.provider.DeclaredTask;
import com.itzstonlex.executionlib.service.AbstractExecutionService;
import com.itzstonlex.executionlib.service.ExecutionServiceSupports;
import javassist.util.proxy.ProxyFactory;
import lombok.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ExecutionServiceSupports(supports = {Async.class})
public class ConcurrentService extends AbstractExecutionService {

    private static final Map<Class<?>, AsyncInvocationHandler> concurrentProxies
            = new ConcurrentHashMap<>();

    private static ConcurrentService instance; {
        instance = this;
    }

    @SuppressWarnings("unchecked")
    public static <T> T wrapProxy(T source) {
        Class<T> sourceType = (Class<T>) source.getClass();

        ProxyFactory proxyFactory = new ProxyFactory();

        proxyFactory.setSuperclass(sourceType);
        proxyFactory.setFilter(method -> method.isAnnotationPresent(Async.class));

        try {
            return (T) proxyFactory.create(new Class[0], new Object[0],
                    concurrentProxies.computeIfAbsent(sourceType, __ -> new AsyncInvocationHandler(instance, source)));
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    protected void initExecutors(Hashtable<Class<? extends Annotation>, DeclaredExecutor<?, ?>> executors) {
        executors.put(Async.class, new DeclaredAsyncExecutor());
    }

    @Override
    public boolean isAssignable(@NonNull Method method) {
        return method.isAnnotationPresent(Async.class);
    }

    private static class DeclaredAsyncExecutor implements DeclaredExecutor<Async, DeclaredTask> {

        private static final ExecutorService THREADS_POOL
                = Executors.newCachedThreadPool();

        @Override
        public void execute(@NonNull Async annotation, @NonNull DeclaredTask task) {
            THREADS_POOL.submit(task::invoke);
        }
    }
}
