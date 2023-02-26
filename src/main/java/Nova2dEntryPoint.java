import io.nova.core.application.ApplicationCommandLineArgs;
import io.nova.core.application.ApplicationFactory;

public class Nova2dEntryPoint {

    public static void main(String[] args) {
        var application = ApplicationFactory.createApplication(new ApplicationCommandLineArgs(args));
        application.run();
    }
}