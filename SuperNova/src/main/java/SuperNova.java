import io.nova.core.application.ApplicationCommandLineArgs;

public class SuperNova {

    public static void main(String[] args) {
        SuperNovaEditor.create(new ApplicationCommandLineArgs(args)).run();
    }
}