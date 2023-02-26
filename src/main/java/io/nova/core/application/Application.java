package io.nova.core.application;

import io.nova.core.Nova2dWindow;
import io.nova.event.window.WindowClosedEvent;
import io.nova.event.window.WindowResizeEvent;

public class Application {

    private Application application;
    private ApplicationSpecification specification;
    private Nova2dWindow window;

    public Application(ApplicationSpecification specification) {
        this.specification = specification;

        application = this;
        if (specification.getWorkingDirectory().isEmpty()) {
            var currentDir = System.getProperty("user.dir");
            specification.setWorkingDirectory(currentDir);
        }

        window = Nova2dWindow.getInstance();
    }

    private void run() {

    }

    private boolean onWindowClosed(WindowClosedEvent event) {
        return false;
    }

    private boolean onWindowResize(WindowResizeEvent event) {
        return false;
    }

    public Nova2dWindow getWindow() {
        return window;
    }

    public void onEvent() {

    }
}