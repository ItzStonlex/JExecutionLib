package com.itzstonlex.executionlib.test;

import com.itzstonlex.executionlib.service.concurrent.Async;
import com.itzstonlex.executionlib.service.log.LoggerGlobal;
import com.itzstonlex.executionlib.service.schedule.RunForce;
import com.itzstonlex.executionlib.service.schedule.RunForceLate;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class FunctionsTest {

    @LoggerGlobal
    private Logger logger;

    @RunForceLate(delay = 5, unit = TimeUnit.SECONDS)
    public void testFunction() {
        System.out.println("testFunction() - Current Thread: " + Thread.currentThread().getName());
    }

    @Async
    @RunForce
    public void testAsyncCalculate() {
        System.out.printf("[Thread %s]: %s%n", Thread.currentThread().getName(), 60 << 2 * 5 + 12);
    }
}
