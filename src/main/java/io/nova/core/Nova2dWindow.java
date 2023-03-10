package io.nova.core;

import imgui.ImGui;
import io.nova.core.imgui.ImGuiLayer;
import io.nova.core.listener.KeyListener;
import io.nova.core.listener.MouseListener;
import io.nova.core.renderer.Renderer;
import io.nova.core.scene.*;
import io.nova.core.utils.Time;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Nova2dWindow {

    private static Nova2dWindow nova2dWindow;
    private final int width;
    private final int height;
    private final String title;
    private long glfwWindow;
    private double red, green, blue;
    private MenuScene menuScene;
    private ImGuiLayer imGuiLayer;

    private Nova2dWindow(String title, int height, int width) {
        this.title = title;
        this.height = height;
        this.width = width;
        this.red = 1.0f;
        this.green = 1.0f;
        this.blue = 1.0f;
    }

    public static Nova2dWindow getInstance() {
        if (Objects.isNull(nova2dWindow)) {
            nova2dWindow = new Nova2dWindow("Nova2d", 500, 500);
        }
        return nova2dWindow;
    }

    private static void printVersion() {
        System.out.printf("Hello LWJGL %s !%n", Version.getVersion());
    }

    private static void terminateGLFWAndFreeErrorCallback() {
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public static int getWidth() {
        return getInstance().width;
    }

    public static int getHeight() {
        return getInstance().height;
    }

    public void run() {
        printVersion();

        init();
        loop();

        dispose();
    }

    private void dispose() {
        freeWindowCallbacksAndDestroyWindow();
        terminateGLFWAndFreeErrorCallback();
        imGuiLayer.dispose();
    }

    private void init() {
        // Set up an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        // Create the window
        glfwWindow = glfwCreateWindow(width, height, title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::cursorPositionCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        imGuiLayer = new ImGuiLayer(glfwWindow);
        imGuiLayer.init();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        GLUtil.setupDebugMessageCallback();

        // Register Scenes
        menuScene = new MenuScene(null);
        menuScene.setCurrentScene(menuScene);

        menuScene.registerScene("Clear color", ClearColorScene.class);
        menuScene.registerScene("Zoom texture", ZoomTextureScene.class);
        menuScene.registerScene("Simple colored square", SimpleColoredSquareScene.class);
        menuScene.registerScene("OpenGL logo", OpenGlLogoScene.class);
        menuScene.registerScene("Nova2d logo", Nova2dLogoScene.class);
        menuScene.registerScene("Batch", BatchScene.class);
        menuScene.registerScene("Sprite sheet", SpriteSheetScene.class);
    }

    private void loop() {
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        double startTime = Time.getElapsedTimeSinceApplicationStartInSeconds();
        double endTime;
        double deltaTime = -1;

        while (!glfwWindowShouldClose(glfwWindow) && !KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();

            Renderer.setClearColor((float) red, (float) green, (float) blue, 0.0f);
            Renderer.clear();

            imGuiLayer.startFrame();

            var currentScene = menuScene.getCurrentScene();
            if (!Objects.isNull(currentScene) && deltaTime >= 0) {
                currentScene.update(deltaTime);
                currentScene.render();


                ImGui.begin("Nova2d");
                if (currentScene != menuScene && ImGui.button("<-")) {
                    menuScene.setCurrentScene(menuScene);
                }

                currentScene.imGuiRender();
                ImGui.end();

                if (KeyListener.isKeyPressed(GLFW_KEY_BACKSPACE) && currentScene != menuScene) {
                    menuScene.setCurrentScene(menuScene);
                }
            }

            imGuiLayer.endFrame();

            glfwSwapBuffers(glfwWindow); // swap the color buffers

            endTime = Time.getElapsedTimeSinceApplicationStartInSeconds();
            deltaTime = endTime - startTime;
            startTime = endTime;
        }
    }

    private void freeWindowCallbacksAndDestroyWindow() {
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);
    }

    public void changeColorBy(double redOffset, double greenOffset, double blueOffset) {
        red += redOffset;
        green += greenOffset;
        blue += blueOffset;
    }

    public void changeColorTo(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
}