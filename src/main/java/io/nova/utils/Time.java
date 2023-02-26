package io.nova.utils;

public class Time {
    public static long applicationStartTime = System.nanoTime();

    public static double getElapsedTimeSinceApplicationStartInSeconds() {
        return (System.nanoTime() - applicationStartTime) * 1E-9;
    }
}