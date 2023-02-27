package io.nova.imgui;

import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;
import io.nova.core.application.Application;
import io.nova.core.layer.Layer;
import org.lwjgl.glfw.GLFW;

public class ImGuiLayer extends Layer {
    private ImGuiImplGl3 imGuiImplGl3;
    private ImGuiImplGlfw imGuiImplGlfw;

    @Override
    public void onAttach() {
        imGuiImplGlfw = new ImGuiImplGlfw();
        imGuiImplGl3 = new ImGuiImplGl3();

        ImGui.createContext();
        var io = ImGui.getIO();
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);

        var glfwWindow = Application.getWindow().getNativeWindow();
        imGuiImplGlfw.init(glfwWindow, true);
        imGuiImplGl3.init("#version 330");
    }

    public void startFrame() {
        imGuiImplGlfw.newFrame();
        ImGui.newFrame();
    }

    public void endFrame() {
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
}