import io.nova.core.application.ApplicationCommandLineArgs;

public class Nova2dEditorApp {

    public static void main(String[] args) {
        Nova2dEditor.create(new ApplicationCommandLineArgs(args)).run();
    }
}