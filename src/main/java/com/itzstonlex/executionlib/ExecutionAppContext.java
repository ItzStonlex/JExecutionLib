package com.itzstonlex.executionlib;

import com.itzstonlex.executionlib.service.ExecutionService;
import com.itzstonlex.executionlib.service.ExecutionServiceSupports;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExecutionAppContext {

    public static void runServices(@NonNull Class<?> bootstrap, ExecutionAppConfiguration config) {
        ExecutionAppContext context = new ExecutionAppContext(new Object(), bootstrap.getClassLoader(), config);

        context.loadReflections();
        context.loadAppServices();
        // todo
    }

    public static void runServices(@NonNull Class<?> bootstrap) {
        runServices(bootstrap, null);
    }

    private Object lock;

    private ClassLoader provideLoader;

    private ExecutionAppConfiguration config;

    @NonFinal
    private Set<ExecutionService> services;

    @NonFinal
    private Reflections reflections;

    private void loadReflections() {
        reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forClassLoader(provideLoader))
                .setScanners(
                        new SubTypesScanner(),
                        new TypeAnnotationsScanner(),
                        new MethodAnnotationsScanner(),
                        new ResourcesScanner()
                ));
    }

    public void loadAppServices() {
        synchronized (lock) {
            services = reflections.getTypesAnnotatedWith(ExecutionServiceSupports.class).stream().map(new ClassInstanceMapper<ExecutionService>())
                    .collect(Collectors.toSet());
        }
    }

    private static class ClassInstanceMapper<T> implements Function<Class<?>, T> {

        @SuppressWarnings("unchecked")
        @Override
        public T apply(Class<?> aClass) {
            try {
                return (T) aClass.newInstance();
            }
            catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }
}
