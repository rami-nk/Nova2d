import io.nova.core.application.Application;
import io.nova.core.application.ApplicationCommandLineArgs;
import io.nova.core.application.ApplicationSpecification;

public class Editor extends Application {
    public Editor(ApplicationSpecification specification) {
        super(specification);
        this.pushLayer(new EditorLayer());
    }

    public static Editor create(ApplicationCommandLineArgs args) {
        var specification = new ApplicationSpecification(
                "Sandbox",
                ".",
                args
        );
        return new Editor(specification);
    }
}
