package io.nova.core.application;

public class ApplicationCommandLineArgs {
    private final String[] args;

    public ApplicationCommandLineArgs(String[] args) {
        this.args = args;
    }

    public String get(int index) {
        return args[index];
    }
}
