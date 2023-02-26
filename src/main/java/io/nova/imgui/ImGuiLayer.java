package io.nova.imgui;

import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;
import org.lwjgl.glfw.GLFW;

public class ImGuiLayer {
    private final ImGuiImplGlfw imGuiGlfw;
    private final ImGuiImplGl3 imGuiImplGl3;
    private final long glfwWindow;

    public ImGuiLayer(long glfwWindow) {
        this.glfwWindow = glfwWindow;
        imGuiGlfw = new ImGuiImplGlfw();
        imGuiImplGl3 = new ImGuiImplGl3();
    }

    public void dispose() {
        imGuiGlfw.dispose();
        imGuiImplGl3.dispose();
        disposeImGui();
    }

    /**
     * Method to destroy Dear ImGui context.
     */
    private void disposeImGui() {
        ImGui.destroyContext();
    }

    /**
     * Method called at the beginning of the main cycle.
     * It clears OpenGL buffer and starts an ImGui frame.
     */
    public void startFrame() {
        imGuiGlfw.newFrame();
        ImGui.newFrame();
    }

    /**
     * Method to initialize Dear ImGui context. Could be overridden to do custom Dear ImGui setup before application start.
     */
    private void initImGui() {
        ImGui.createContext();
        var io = ImGui.getIO();
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
    }

    public void init() {
        initImGui();
        imGuiGlfw.init(glfwWindow, true);
        imGuiImplGl3.init();
    }

    /**
     * Method called in the end of the main cycle.
     * It renders ImGui and swaps GLFW buffers to show an updated frame.
     */
    public void endFrame() {
        ImGui.render();
        imGuiImplGl3.renderDrawData(ImGui.getDrawData());
        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final long backupWindowPtr = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(backupWindowPtr);
        }
    }
}