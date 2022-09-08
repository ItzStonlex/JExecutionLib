package com.itzstonlex.executionlib;

import com.itzstonlex.executionlib.service.ExecutionService;
import com.itzstonlex.executionlib.service.ExecutionServiceSupports;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.reflections.Reflections;
import org.reflections.scanners.*;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExecutionAppContext {

    public static void runServices(@NonNull Class<?> bootstrap, ExecutionAppConfiguration config)
    throws Exception {

        ExecutionAppContext context = new ExecutionAppContext(new Object(), bootstrap.getClassLoader(), config);

        context.loadReflections();
        context.loadAppServices();

        context.runAppServices();
    }

    public static void runServices(@NonNull Class<?> bootstrap)
    throws Exception {
        runServices(bootstrap, null);
    }

    private Object lock;

    private ClassLoader provideLoader;

    @Getter
    private ExecutionAppConfiguration config;

    @Getter
    @NonFinal
    private Set<ExecutionService> services;

    @Getter
    @NonFinal
    private Reflections projectScanner;

    private void loadReflections() {
        projectScanner = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forClassLoader(provideLoader))
                .setScanners(
                        new FieldAnnotationsScanner(),
                        new TypeAnnotationsScanner(),
                        new MethodAnnotationsScanner(),
                        new SubTypesScanner(),
                        new ResourcesScanner()
                ));
    }

    public void loadAppServices() {
        synchronized (lock) {
            services = projectScanner.getTypesAnnotatedWith(ExecutionServiceSupports.class).stream().map(new ClassInstanceMapper<ExecutionService>())
                    .collect(Collectors.toSet());
        }
    }

    public void runAppServices() throws Exception {
        synchronized (lock) {

            for (ExecutionService executionService : services) {
                executionService.run(this);
            }
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
