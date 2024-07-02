package ru.otus.myclass;

import ru.otus.annotation.Log;

public class TestClassImpl implements TestClass {

    @Log
    public void calculation(int param1) {
        // test
    }

    @Log
    public void calculation(int param1, int param2) {
        // test
    }

    @Log
    public void calculation(int param1, int param2, String param3) {
        // test
    }
}
