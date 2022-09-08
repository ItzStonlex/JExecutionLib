package com.itzstonlex.executionlib.service;

import lombok.NonNull;

import java.lang.annotation.Annotation;

public interface ExecutionService {

    <A extends Annotation> DeclaredExecutor<A> executor(@NonNull Class<A> annotationType);
}
