package com.itzstonlex.executionlib.test;

import com.itzstonlex.executionlib.ExecutionAppConfiguration;
import com.itzstonlex.executionlib.ExecutionAppContext;
import com.itzstonlex.executionlib.service.concurrent.Async;
import com.itzstonlex.executionlib.service.schedule.RunForce;
import com.itzstonlex.executionlib.service.schedule.RunForceLate;

import java.util.concurrent.TimeUnit;

public class BootstrapTest {

    public static void main(String[] args) {
        ExecutionAppConfiguration configuration = new ExecutionAppConfiguration();
        // todo - configuration management.

        ExecutionAppContext.runServices(BootstrapTest.class, configuration);
    }

    @RunForceLate(delay = 5, unit = TimeUnit.SECONDS)
    public void testFunction() {
        System.out.println(Thread.currentThread().getName());
    }

    @Async
    @RunForce
    public void testAsyncCalculate() {
        System.out.printf("[Thread %s]: %s%n", Thread.currentThread().getName(), 60 << 2 * 5 + 12);
    }
}
