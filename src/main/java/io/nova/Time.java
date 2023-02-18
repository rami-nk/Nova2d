package io.nova;

public class Time {
    public static double applicationStartTime = System.nanoTime();

    public static double getElapsedTimeSinceApplicationStartInSeconds() {
        return (System.nanoTime() - applicationStartTime) * 1E-9;
    }
}
