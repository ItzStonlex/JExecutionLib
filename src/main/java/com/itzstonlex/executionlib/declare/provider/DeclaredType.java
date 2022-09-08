package com.itzstonlex.executionlib.declare.provider;

import com.itzstonlex.executionlib.declare.DeclaredExecutorProvider;

public interface DeclaredType extends DeclaredExecutorProvider {

    Class<?> asType();
}
