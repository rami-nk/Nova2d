import io.nova.core.application.Application;
import io.nova.core.application.ApplicationCommandLineArgs;
import io.nova.core.application.ApplicationSpecification;

public class SuperNovaEditor extends Application {
    public SuperNovaEditor(ApplicationSpecification specification) {
        super(specification);
//        this.pushLayer(new SuperNovaDefaultEditorLayer());
        this.pushLayer(new EditorLayer());
    }

    public static SuperNovaEditor create(ApplicationCommandLineArgs args) {
        var specification = new ApplicationSpecification(
                "SuperNova",
                ".",
                args
        );
        return new SuperNovaEditor(specification);
    }
}
