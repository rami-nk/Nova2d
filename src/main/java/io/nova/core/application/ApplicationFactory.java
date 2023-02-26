package io.nova.core.application;

public class ApplicationFactory {

    public static Application createApplication(ApplicationCommandLineArgs args) {
        var specification = new ApplicationSpecification(args);
        var application = Application.getInstance();
        application.init(specification);
        return application;
    }
}
