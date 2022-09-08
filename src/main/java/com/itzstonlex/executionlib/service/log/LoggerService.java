package com.itzstonlex.executionlib.service.log;

import com.itzstonlex.executionlib.ExecutionAppContext;
import com.itzstonlex.executionlib.declare.DeclaredExecutor;
import com.itzstonlex.executionlib.declare.DeclaredExecutorProvider;
import com.itzstonlex.executionlib.declare.provider.DeclaredField;
import com.itzstonlex.executionlib.service.AbstractExecutionService;
import com.itzstonlex.executionlib.service.ExecutionServiceSupports;
import lombok.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.logging.Logger;

@ExecutionServiceSupports(supports = {LoggerGlobal.class, LoggerCustom.class})
public class LoggerService extends AbstractExecutionService {

    @Override
    protected void initExecutors(Hashtable<Class<? extends Annotation>, DeclaredExecutor<?, ?>> executors) {
        executors.put(LoggerGlobal.class, new DeclaredLoggerGlobalExecutor());
        executors.put(LoggerCustom.class, new DeclaredLoggerNameExecutor());
    }

    @Override
    public void run(@NonNull ExecutionAppContext context) throws Exception {
        DeclaredExecutor<LoggerGlobal, DeclaredExecutorProvider> loggerGlobalExecutor = executor(LoggerGlobal.class);

        for (Field field : context.getProjectScanner().getFieldsAnnotatedWith(LoggerGlobal.class)) {
            loggerGlobalExecutor.execute(field.getDeclaredAnnotation(LoggerGlobal.class), new DeclaredField() {

                @Override
                public Field asField() {
                    return field;
                }

                @Override
                public Class<?> asType() {
                    return field.getType();
                }

                @Override
                public void set(Object value) {
                    // todo
                }
            });
        }
    }

    private static class DeclaredLoggerGlobalExecutor implements DeclaredExecutor<LoggerGlobal, DeclaredField> {

        @Override
        public void execute(@NonNull LoggerGlobal annotation, @NonNull DeclaredField field) throws Exception {
            if (!field.asType().equals(Logger.class)) {
                throw new ClassCastException("field must be assignable java.util.logging.Logger");
            }

            field.set(Logger.getGlobal());
        }
    }

    private static class DeclaredLoggerNameExecutor implements DeclaredExecutor<LoggerCustom, DeclaredField> {

        @Override
        public void execute(@NonNull LoggerCustom annotation, @NonNull DeclaredField field) throws Exception {
            if (!field.asType().equals(Logger.class)) {
                throw new ClassCastException("field must be assignable java.util.logging.Logger");
            }

            String resourceBundleName = annotation.resourceBundleName();
            if (resourceBundleName.isEmpty()) {
                resourceBundleName = null;
            }

            field.set(Logger.getLogger(annotation.name(), resourceBundleName));
        }
    }

}
