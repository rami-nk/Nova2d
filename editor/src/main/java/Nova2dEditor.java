import io.nova.core.application.Application;
import io.nova.core.application.ApplicationCommandLineArgs;
import io.nova.core.application.ApplicationSpecification;

public class Nova2dEditor extends Application {
    public Nova2dEditor(ApplicationSpecification specification) {
        super(specification);
        this.pushLayer(new Nova2dDefaultEditorLayer());
    }

    public static Nova2dEditor create(ApplicationCommandLineArgs args) {
        var specification = new ApplicationSpecification(
                "Sandbox",
                ".",
                args
        );
        return new Nova2dEditor(specification);
    }
}
