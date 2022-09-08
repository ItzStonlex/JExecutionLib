package com.itzstonlex.executionlib.service;

import com.itzstonlex.executionlib.ExecutionAppContext;
import com.itzstonlex.executionlib.declare.DeclaredExecutor;
import com.itzstonlex.executionlib.declare.DeclaredExecutorProvider;
import lombok.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public interface ExecutionService {

    boolean isAssignable(@NonNull Class<?> type);

    boolean isAssignable(@NonNull Field field);

    boolean isAssignable(@NonNull Method method);

    void run(@NonNull ExecutionAppContext executionAppContext) throws Exception;

    <A extends Annotation, P extends DeclaredExecutorProvider> DeclaredExecutor<A, P> executor(@NonNull Class<A> annotationType);
}
