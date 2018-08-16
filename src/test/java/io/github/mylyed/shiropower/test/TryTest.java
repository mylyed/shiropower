package io.github.mylyed.shiropower.test;

import java.io.Closeable;

/**
 * Created by lilei on 2018/8/10.
 */
public class TryTest {

    public static class TT implements Closeable {

        @Override
        public void close() {
            System.out.println("调用关闭方法");
        }
    }


    public static void main(String[] args) {
        try (TT t = new TT()) {
            System.out.println(t.toString());
            System.out.println("===结束===");
        } finally {
            System.out.println("finally");
        }
    }


}
