package io.nova.core.window;

import io.nova.renderer.OpenGlContext;
import io.nova.window.Nova2dWindow;

public class WindowFactory {

    public static Window create(WindowProps windowProps) {
        return new Nova2dWindow(new OpenGlContext(), windowProps);
    }
}
