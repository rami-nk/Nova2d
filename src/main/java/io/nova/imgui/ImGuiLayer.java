package io.nova.imgui;

import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;
import imgui.type.ImBoolean;
import io.nova.core.application.Application;
import io.nova.core.layer.Layer;
import io.nova.event.Event;
import org.lwjgl.glfw.GLFW;

public class ImGuiLayer extends Layer {
    private ImGuiImplGl3 imGuiImplGl3;
    private ImGuiImplGlfw imGuiImplGlfw;


    @Override
    public void onAttach() {
        ImGui.createContext();
        var io = ImGui.getIO();
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);

        imGuiImplGlfw = new ImGuiImplGlfw();
        imGuiImplGl3 = new ImGuiImplGl3();

        imGuiImplGlfw.init(Application.getWindow().getNativeWindow(), true);
        imGuiImplGl3.init("#version 330");
    }

    @Override
    public void onDetach() {
        imGuiImplGlfw.dispose();
        imGuiImplGl3.dispose();
        ImGui.destroyContext();
    }


    @Override
    public void onUpdate() {
        imGuiImplGlfw.newFrame();
        ImGui.newFrame();

        ImGui.showDemoWindow(new ImBoolean(true));

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
    public void onEvent(Event event) { }
}