package io.nova.imgui;

import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;
import io.nova.core.application.Application;
import io.nova.core.layer.Layer;
import io.nova.event.Event;
import io.nova.event.EventDispatcher;
import io.nova.event.key.KeyPressedEvent;
import io.nova.event.key.KeyReleasedEvent;
import io.nova.event.mouse.MouseButtonPressedEvent;
import io.nova.event.mouse.MouseButtonReleasedEvent;
import io.nova.event.mouse.MouseMovedEvent;
import io.nova.event.mouse.MouseScrolledEvent;
import io.nova.event.window.WindowResizeEvent;
import org.lwjgl.glfw.GLFW;

public class ImGuiLayer extends Layer {
    private ImGuiImplGl3 imGuiImplGl3;
    private ImGuiImplGlfw imGuiImplGlfw;

    @Override
    public void onAttach() {
        ImGui.createContext();
        var io = ImGui.getIO();
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);

        ImGui.styleColorsDark();

        imGuiImplGlfw = new ImGuiImplGlfw();
        imGuiImplGl3 = new ImGuiImplGl3();

        var glfwWindow = Application.getWindow().getNativeWindow();

        imGuiImplGlfw.init(glfwWindow, true);
        imGuiImplGl3.init("#version 410");
    }

    public void begin() {
        imGuiImplGlfw.newFrame();
        ImGui.newFrame();
    }

    public void end() {
        var io = ImGui.getIO();
        io.setDisplaySize(Application.getWindow().getWidth(), Application.getWindow().getHeight());

        ImGui.render();
        imGuiImplGl3.renderDrawData(ImGui.getDrawData());

        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final long backupWindowPtr = org.lwjgl.glfw.GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(backupWindowPtr);
        }
    }

    @Override
    public void onDetach() {
        imGuiImplGlfw.dispose();
        imGuiImplGl3.dispose();
        ImGui.destroyContext();
    }

    @Override
    public void onEvent(Event event) {
        var dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(MouseButtonPressedEvent.class, this::onMouseButtonPressedEvent);
        dispatcher.dispatch(MouseButtonReleasedEvent.class, this::onMouseButtonReleasedEvent);
        dispatcher.dispatch(MouseMovedEvent.class, this::onMouseMovedEvent);
        dispatcher.dispatch(MouseScrolledEvent.class, this::onMouseScrolledEvent);
        dispatcher.dispatch(KeyPressedEvent.class, this::onKeyPressedEvent);
        dispatcher.dispatch(KeyReleasedEvent.class, this::onKeyReleasedEvent);
        dispatcher.dispatch(WindowResizeEvent.class, this::onWindowResizeEvent);
    }

    private boolean onMouseButtonPressedEvent(MouseButtonPressedEvent event) {
        var io = ImGui.getIO();
        io.setMouseDown(event.getMouseCode(), true);
        return false;
    }

    private boolean onMouseButtonReleasedEvent(MouseButtonReleasedEvent event) {
        var io = ImGui.getIO();
        io.setMouseDown(event.getMouseCode(), false);
        return false;
    }

    private boolean onMouseMovedEvent(MouseMovedEvent event) {
        var io = ImGui.getIO();
        io.setMousePos(event.getMouseX(), event.getMouseY());
        return false;
    }

    private boolean onMouseScrolledEvent(MouseScrolledEvent event) {
        var io = ImGui.getIO();
        io.setMouseWheelH(event.getXOffset());
        io.setMouseWheel(event.getYOffset());
        return false;
    }

    private boolean onKeyPressedEvent(KeyPressedEvent event) {
        var io = ImGui.getIO();
        io.setKeysDown(event.getKeyCode(), true);
        return false;
    }

    private boolean onKeyReleasedEvent(KeyReleasedEvent event) {
        var io = ImGui.getIO();
        io.setKeysDown(event.getKeyCode(), false);
        return false;
    }

    private boolean onWindowResizeEvent(WindowResizeEvent event) {
        var io = ImGui.getIO();
        io.setDisplaySize(event.getWidth(), event.getHeight());
        io.setDisplayFramebufferScale(1.0f, 1.0f);
//        glViewPort();

        return false;
    }
}