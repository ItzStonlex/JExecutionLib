package com.itzstonlex.executionlib.test;

import com.itzstonlex.executionlib.ExecutionAppConfiguration;
import com.itzstonlex.executionlib.ExecutionAppContext;
import com.itzstonlex.executionlib.service.concurrent.ConcurrentService;

public class BootstrapTest {

    public static void main(String[] args)
    throws Exception {

        ExecutionAppConfiguration configuration = new ExecutionAppConfiguration();
        // todo - configuration management.

        ExecutionAppContext.runServices(BootstrapTest.class, configuration);

        // test library functions.
        test(ConcurrentService.wrapProxy(new FunctionsTest()));
    }

    private static void test(FunctionsTest functionsTest) {
        functionsTest.testFunction();
        functionsTest.testAsyncCalculate();
    }

}
