import io.nova.core.application.Application;
import io.nova.core.application.ApplicationCommandLineArgs;
import io.nova.core.application.ApplicationSpecification;

public class Sandbox extends Application {
    public Sandbox(ApplicationSpecification specification) {
        super(specification);
//        this.pushLayer(new Sandbox2d());
        this.pushLayer(new EcsLayer());
    }

    public static Sandbox create(ApplicationCommandLineArgs args) {
        var specification = new ApplicationSpecification(
                "Sandbox",
                ".",
                args
        );
        return new Sandbox(specification);
    }
}
