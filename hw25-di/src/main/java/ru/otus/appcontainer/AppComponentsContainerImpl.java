package ru.otus.appcontainer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import org.reflections.Reflections;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?>... initialConfigClass) {
        List<Class<?>> classes = sortedConfigByOrder(initialConfigClass);

        for (var configClass : classes) {
            checkConfigClass(configClass);

            try {
                Object instance = getInstance(configClass);

                Method[] methods = configClass.getMethods();
                List<Method> collect = sortedMethodsByOrder(methods);

                for (var m : collect) {
                    Class<?>[] parameters = m.getParameterTypes();
                    AppComponent annotation = m.getAnnotation(AppComponent.class);

                    boolean containedValue = appComponentsByName.containsValue(annotation.name());
                    if (containedValue) {
                        throw new RuntimeException("Бин с таким параметром уже есть!");
                    }

                    Object[] args = getArgsForBean(parameters);

                    Object bean = m.invoke(instance, args);
                    addBean(bean, annotation.name());
                }
            } catch (NoSuchMethodException
                    | InvocationTargetException
                    | InstantiationException
                    | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> collect =
                appComponents.stream().filter(componentClass::isInstance).toList();

        if (collect.isEmpty()) {
            throw new RuntimeException("Запрашиваемый бин не найден");
        } else if (collect.size() != 1) {
            throw new RuntimeException("Дубликат бинов");
        } else {
            return (C) collect.get(0);
        }
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        Object bean = appComponentsByName.get(componentName);

        if (bean == null) {
            throw new RuntimeException("Запрашиваемый бин не найден");
        }

        return (C) bean;
    }

    private Object getInstance(Class<?> configClass)
            throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Constructor<?> constructor = configClass.getConstructor();
        return constructor.newInstance();
    }

    private List<Method> sortedMethodsByOrder(Method[] methods) {
        return Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(AppComponent.class))
                .sorted((Comparator.comparingInt(
                        o -> o.getDeclaredAnnotation(AppComponent.class).order())))
                .toList();
    }

    private List<Class<?>> sortedConfigByOrder(Class<?>[] configClass) {
        return Arrays.stream(configClass)
                .sorted((Comparator.comparingInt(o -> o.getDeclaredAnnotation(AppComponentsContainerConfig.class)
                        .order())))
                .toList();
    }

    private void addBean(Object bean, String annotationName) {
        appComponents.add(bean);
        appComponentsByName.put(annotationName, bean);
    }

    private Object[] getArgsForBean(Class<?>[] parameters) {
        List<Object> args = new ArrayList<>();

        Arrays.stream(parameters).forEach(p -> args.add(getAppComponent(p)));

        return args.toArray();
    }
}
