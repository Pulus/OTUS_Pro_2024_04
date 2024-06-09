import annotations.After;
import annotations.Before;
import annotations.Test;
import objects.DemoTest;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


public class RunTests {
    public static void main(String[] args) {
        runAllTestFo(DemoTest.class);
    }

    public static void runAllTestFo(Class<?> cls) {
        final List<Method> methods = List.of(cls.getMethods());

        try {
            checkForNumberAnnotations(methods);
            for (Method method : cls.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Test.class)) {
                    try {
                        Object obj = Class.forName(cls.getName()).getDeclaredConstructor().newInstance();

                        runAllBeforeMethods(methods, obj);
                        method.invoke(obj);
                        runAllAfterMethods(methods, obj);
                        System.out.println("\u001B[32m" + "Test " + method.getName() + " Completed" + "\u001B[37m");
                    } catch (Exception e) {
                        System.out.println("\u001B[31m" + "Test " + method.getName() + " uncompleted" + "\u001B[37m");
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runAllBeforeMethods(List<Method> methods, Object obj) {
        for (Method method : methods) {
            try {
                if (method.isAnnotationPresent(Before.class)) {
                    method.invoke(obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void runAllAfterMethods(List<Method> methods, Object obj) {
        for (Method method : methods) {
            try {
                if (method.isAnnotationPresent(After.class)) {
                    method.invoke(obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void checkForNumberAnnotations(List<Method> methods) throws Exception {
        for (Method method : methods) {
            if (Arrays.stream(method.getAnnotations()).count() > 1) {
                throw new Exception("Запрещено использование более одной аннотации у метода!");
            }
        }
    }
}
