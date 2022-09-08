package com.itzstonlex.executionlib.service;

import lombok.NonNull;

import java.lang.annotation.Annotation;

public interface DeclaredExecutor<A extends Annotation> {

    void execute(@NonNull A annotation, @NonNull DeclaredTask task) throws Exception;

    interface DeclaredTask {

        void invoke() throws Exception;
    }
}
