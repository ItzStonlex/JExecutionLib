package com.itzstonlex.executionlib.service;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExecutionServiceSupports {

    Class<? extends Annotation>[] supports();
}
