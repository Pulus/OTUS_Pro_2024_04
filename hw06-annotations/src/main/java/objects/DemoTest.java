package objects;

import annotations.*;


public class DemoTest {
    @Before
    public void beforeMethod() {

    }

    @Before
    public void afterMethod() {
    }

    @Test
    public void fooTest1() throws Exception {
        throw new Exception();

    }
    @Test
    public void fooTest2() {

    }
    @Test
    public void fooTest3() {

    }
}