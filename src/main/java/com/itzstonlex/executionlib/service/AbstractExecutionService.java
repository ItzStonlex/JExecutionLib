package com.itzstonlex.executionlib.service;

import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.lang.annotation.Annotation;
import java.util.Hashtable;

@FieldDefaults(makeFinal = true)
public abstract class AbstractExecutionService implements ExecutionService {

    private Hashtable<Class<? extends Annotation>, DeclaredExecutor<?>> executors = new Hashtable<>();

    public AbstractExecutionService() {
        initExecutors(executors);
    }

    protected abstract void initExecutors(Hashtable<Class<? extends Annotation>, DeclaredExecutor<?>> executors);

    @SuppressWarnings("unchecked")
    @Override
    public <A extends Annotation> DeclaredExecutor<A> executor(@NonNull Class<A> annotationType) {
        return (DeclaredExecutor<A>) executors.get(annotationType);
    }
}
