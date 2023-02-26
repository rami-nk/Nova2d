package io.nova.core.application;

public class ApplicationCommandLineArgs {
    private final int count;
    private final String[] args;

    public ApplicationCommandLineArgs(int count, String[] args) {
        this.count = count;
        this.args = args;
    }

    public String get(int index) {
        return args[index];
    }
}
