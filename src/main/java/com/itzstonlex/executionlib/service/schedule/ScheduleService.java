package com.itzstonlex.executionlib.service.schedule;

import com.itzstonlex.executionlib.ExecutionAppContext;
import com.itzstonlex.executionlib.declare.DeclaredExecutor;
import com.itzstonlex.executionlib.service.AbstractExecutionService;
import com.itzstonlex.executionlib.service.ExecutionServiceSupports;
import lombok.NonNull;

import java.lang.annotation.Annotation;
import java.util.Hashtable;

@ExecutionServiceSupports(supports = {RunForce.class, RunForceLate.class, RunForceLoop.class})
public class ScheduleService extends AbstractExecutionService {

    @Override
    protected void initExecutors(Hashtable<Class<? extends Annotation>, DeclaredExecutor<?, ?>> executors) {

    }

    @Override
    public void run(@NonNull ExecutionAppContext context) {
    }

}
