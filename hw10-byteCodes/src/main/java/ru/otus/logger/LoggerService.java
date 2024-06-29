package ru.otus.logger;

import static java.lang.reflect.Proxy.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotation.Log;
import ru.otus.myclass.TestClass;

public class LoggerService {
    private static final Logger logger = LoggerFactory.getLogger(LoggerService.class);

    public static TestClass create(TestClass instance) {
        InvocationHandler handler = new ProxyInvocationHandler<>(instance);
        return (TestClass) newProxyInstance(
                instance.getClass().getClassLoader(),
                instance.getClass().getInterfaces(),
                handler);
    }

    @Builder
    static class MethodInfo {
        String name;
        Object[] args;

        static MethodInfo buildMethodInfo(Method method) {
            return MethodInfo.builder()
                    .name(method.getName())
                    .args(method.getParameterTypes())
                    .build();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MethodInfo that = (MethodInfo) o;
            return Objects.equals(name, that.name) && Objects.deepEquals(args, that.args);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, Arrays.hashCode(args));
        }
    }

    static class ProxyInvocationHandler<T> implements InvocationHandler {
        private final T instance;
        private final List<MethodInfo> listMethodsInfo;

        public ProxyInvocationHandler(T instance) {
            this.instance = instance;
            this.listMethodsInfo = findMethodsWithAnnotation(instance);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (listMethodsInfo.contains(MethodInfo.buildMethodInfo(method))) {
                logger.info("executed method: {}, param:{}", method.getName(), args);
            }

            return method.invoke(instance, args);
        }

        private List<MethodInfo> findMethodsWithAnnotation(T instance) {
            return Arrays.stream(instance.getClass().getMethods())
                    .filter(m -> m.isAnnotationPresent(Log.class))
                    .map(MethodInfo::buildMethodInfo)
                    .toList();
        }
    }
}
