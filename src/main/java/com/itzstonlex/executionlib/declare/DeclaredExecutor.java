package com.itzstonlex.executionlib.declare;

import lombok.NonNull;

import java.lang.annotation.Annotation;

public interface DeclaredExecutor<A extends Annotation, P extends DeclaredExecutorProvider> {

    void execute(@NonNull A annotation, @NonNull P provider) throws Exception;
}
