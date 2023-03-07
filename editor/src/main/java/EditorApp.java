import io.nova.core.application.ApplicationCommandLineArgs;

public class EditorApp {

    public static void main(String[] args) {
        Editor.create(new ApplicationCommandLineArgs(args)).run();
    }
}