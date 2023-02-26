package io.nova.core;

public class Application {

    private ApplicationSpecification specification;
    private Nova2dWindow window;

    public Application(ApplicationSpecification specification) {
        this.specification = specification;
    }

    private void run() {

    }

//    private boolean onWindowClosed(WindowCloseEvent event) {
//
//    }
//
//    private boolean onWindowResize(WindowResizeEvent event) {
//
//    }

    public Nova2dWindow getWindow() {
        return window;
    }

    public void onEvent() {

    }
}

class ApplicationSpecification {
    public String name = "Nova2d";
    public String workingDirectory;
    public ApplicationCommandLineArgs commandLineArgs;
}

class ApplicationCommandLineArgs {
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