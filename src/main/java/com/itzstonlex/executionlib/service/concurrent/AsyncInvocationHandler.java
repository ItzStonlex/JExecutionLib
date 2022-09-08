package com.itzstonlex.executionlib.service.concurrent;

import com.itzstonlex.executionlib.declare.provider.DeclaredTask;
import javassist.util.proxy.MethodHandler;
import lombok.experimental.FieldDefaults;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@FieldDefaults(makeFinal = true)
public class AsyncInvocationHandler implements MethodHandler {

    private Map<String, Method> unproxyFunctionsMap = new HashMap<>();

    private ConcurrentService concurrentService;

    private Object target;

    AsyncInvocationHandler(ConcurrentService concurrentService, Object target) {
        this.target = target;
        this.concurrentService = concurrentService;

        for(Method method: target.getClass().getDeclaredMethods()) {
            unproxyFunctionsMap.put(method.getName(), method);
        }
    }

    @Override
    public Object invoke(Object self, Method method, Method proceed, Object[] args) throws Throwable {
        if (concurrentService.isAssignable(method)) {
            CompletableFuture<Object> completableFuture = new CompletableFuture<>();

            concurrentService.executor(Async.class).execute(method.getAnnotation(Async.class), new DeclaredTask() {
                @Override
                public Method asFunction() {
                    return method;
                }

                @Override
                public Object invoke() throws Exception {
                    Object returnValue = method.invoke(target, args);
                    completableFuture.complete(returnValue);

                    return returnValue;
                }
            });

            return completableFuture.get();
        }

        return unproxyFunctionsMap.get(method.getName()).invoke(target, args);
    }
}
