package ru.otus;

import ru.otus.logger.LoggerService;
import ru.otus.myclass.TestClass;
import ru.otus.myclass.TestClassImpl;

public class Main {
    public static void main(String[] args) {
        TestClass test = LoggerService.create(new TestClassImpl());
        test.calculation(6);
        test.calculation(9, 10, "Test");
        test.calculation(7, 8);
        test.calculation(7, 8);
    }
}
