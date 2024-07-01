package ru.otus.logger;

import static java.lang.reflect.Proxy.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    private static class MethodInfo {
        private final String name;
        private final Object[] args;

        public MethodInfo (Method method) {
            this.name = method.getName();
            this.args = method.getParameterTypes();
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

    private static class ProxyInvocationHandler<T> implements InvocationHandler {
        private final T instance;
        private final Set<MethodInfo> listMethodsInfo;

        public ProxyInvocationHandler(T instance) {
            this.instance = instance;
            this.listMethodsInfo = findMethodsWithAnnotation(instance);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (listMethodsInfo.contains(new MethodInfo(method))) {
                logger.info("executed method: {}, param:{}", method.getName(), args);
            }

            return method.invoke(instance, args);
        }

        private Set<MethodInfo> findMethodsWithAnnotation(T instance) {
            return Arrays.stream(instance.getClass().getMethods())
                    .filter(m -> m.isAnnotationPresent(Log.class))
                    .map(MethodInfo::new).
                    collect(Collectors.toSet());
        }
    }
}
