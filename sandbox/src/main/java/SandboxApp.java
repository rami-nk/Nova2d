import io.nova.core.application.ApplicationCommandLineArgs;

public class SandboxApp {

    public static void main(String[] args) {
        Sandbox.create(new ApplicationCommandLineArgs(args)).run();
    }
}
