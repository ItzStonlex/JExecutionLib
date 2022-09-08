package com.itzstonlex.executionlib.declare.provider;

import com.itzstonlex.executionlib.declare.DeclaredExecutorProvider;

import java.lang.reflect.Method;

public interface DeclaredTask extends DeclaredExecutorProvider {

    Method asFunction();

    Object invoke() throws Exception;
}