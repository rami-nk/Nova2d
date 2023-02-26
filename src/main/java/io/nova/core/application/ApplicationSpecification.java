package io.nova.core.application;

public class ApplicationSpecification {

    private String name;
    private String workingDirectory;
    private ApplicationCommandLineArgs commandLineArgs;

    public ApplicationSpecification(String workingDirectory, ApplicationCommandLineArgs commandLineArgs) {
        this("Nova2d", workingDirectory, commandLineArgs);
    }

    public ApplicationSpecification(String name, String workingDirectory, ApplicationCommandLineArgs commandLineArgs) {
        this.name = name;
        this.workingDirectory = workingDirectory;
        this.commandLineArgs = commandLineArgs;
    }

    public String getName() {
        return name;
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    public ApplicationCommandLineArgs getCommandLineArgs() {
        return commandLineArgs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public void setCommandLineArgs(ApplicationCommandLineArgs commandLineArgs) {
        this.commandLineArgs = commandLineArgs;
    }
}