package com.itzstonlex.executionlib.service;

import com.itzstonlex.executionlib.ExecutionAppContext;
import com.itzstonlex.executionlib.declare.DeclaredExecutor;
import com.itzstonlex.executionlib.declare.DeclaredExecutorProvider;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Hashtable;

@FieldDefaults(makeFinal = true)
public abstract class AbstractExecutionService implements ExecutionService {

    private Hashtable<Class<? extends Annotation>, DeclaredExecutor<?, ?>> executors = new Hashtable<>();

    public AbstractExecutionService() {
        initExecutors(executors);
    }

    protected abstract void initExecutors(Hashtable<Class<? extends Annotation>, DeclaredExecutor<?, ?>> executors);

    @Override
    public void run(@NonNull ExecutionAppContext context) throws Exception {
        // override me.
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A extends Annotation, P extends DeclaredExecutorProvider> DeclaredExecutor<A, P> executor(@NonNull Class<A> annotationType) {
        return (DeclaredExecutor<A, P>) executors.get(annotationType);
    }

    @Override
    public boolean isAssignable(@NonNull Class<?> type) {
        return false;
    }

    @Override
    public boolean isAssignable(@NonNull Field field) {
        return false;
    }

    @Override
    public boolean isAssignable(@NonNull Method method) {
        return false;
    }
}
