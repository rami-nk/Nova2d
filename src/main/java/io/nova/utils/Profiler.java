package io.nova.utils;

public class Profiler {
    public static void profile(Runnable method) {
        long startTime = System.nanoTime();
        System.out.println(method.toString());
        method.run();
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Method took " + elapsedTime * 10E-6 + " ms to run.");
    }
}