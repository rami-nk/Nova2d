package io.nova.imgui;

import imgui.ImGui;
import imgui.flag.ImGuiCol;

import java.util.Random;

public class Themes {

    public static void setDarkTheme() {
        var style = ImGui.getStyle();

        style.setColor(ImGuiCol.Header, 0.2f, 0.2f, 0.2f, 1.0f);
        style.setColor(ImGuiCol.HeaderHovered, 0.3f, 0.3f, 0.3f, 1.0f);
        style.setColor(ImGuiCol.HeaderActive, 0.15f, 0.15f, 0.15f, 1.0f);

        style.setColor(ImGuiCol.Button, 0.3f, 0.3f, 0.3f, 1.0f);
        style.setColor(ImGuiCol.ButtonHovered, 0.4f, 0.4f, 0.4f, 1.0f);
        style.setColor(ImGuiCol.ButtonActive, 0.15f, 0.15f, 0.15f, 1.0f);

        style.setColor(ImGuiCol.FrameBg, 0.3f, 0.3f, 0.3f, 1.0f);
        style.setColor(ImGuiCol.FrameBgHovered, 0.4f, 0.4f, 0.4f, 1.0f);
        style.setColor(ImGuiCol.FrameBgActive, 0.15f, 0.15f, 0.15f, 1.0f);

        style.setColor(ImGuiCol.TitleBg, 0.15f, 0.15f, 0.15f, 1.0f);
        style.setColor(ImGuiCol.TitleBgActive, 0.15f, 0.15f, 0.15f, 1.0f);
        style.setColor(ImGuiCol.TitleBgCollapsed, 0.15f, 0.15f, 0.15f, 1.0f);

        style.setColor(ImGuiCol.MenuBarBg, 0.15f, 0.15f, 0.15f, 1.0f);

        style.setColor(ImGuiCol.ScrollbarBg, 0.2f, 0.2f, 0.2f, 1.0f);
        style.setColor(ImGuiCol.ScrollbarGrab, 0.3f, 0.3f, 0.3f, 1.0f);
        style.setColor(ImGuiCol.ScrollbarGrabHovered, 0.4f, 0.4f, 0.4f, 1.0f);
        style.setColor(ImGuiCol.ScrollbarGrabActive, 0.15f, 0.15f, 0.15f, 1.0f);

        style.setColor(ImGuiCol.CheckMark, 0.8f, 0.8f, 0.8f, 1.0f);

        style.setColor(ImGuiCol.SliderGrab, 0.3f, 0.3f, 0.3f, 1.0f);
        style.setColor(ImGuiCol.SliderGrabActive, 0.6f, 0.6f, 0.6f, 1.0f);

        style.setColor(ImGuiCol.Tab, 0.15f, 0.15f, 0.15f, 1.0f);
        style.setColor(ImGuiCol.TabHovered, 0.38f, 0.38f, 0.38f, 1.0f);
        style.setColor(ImGuiCol.TabActive, 0.28f, 0.28f, 0.28f, 1.0f);
        style.setColor(ImGuiCol.TabUnfocused, 0.15f, 0.15f, 0.15f, 1.0f);
        style.setColor(ImGuiCol.TabUnfocusedActive, 0.2f, 0.2f, 0.2f, 1.0f);

        style.setColor(ImGuiCol.TextSelectedBg, 0.0f, 0.0f, 1.0f, 0.35f);
        style.setColor(ImGuiCol.DragDropTarget, 1.0f, 1.0f, 0.0f, 0.9f);

        style.setColor(ImGuiCol.NavHighlight, 0.4f, 0.4f, 0.4f, 1.0f);
        style.setColor(ImGuiCol.NavWindowingHighlight, 1.0f, 1.0f, 1.0f, 0.7f);
        style.setColor(ImGuiCol.NavWindowingDimBg, 0.8f, 0.8f, 0.8f, 0.2f);
        style.setColor(ImGuiCol.ModalWindowDimBg, 0.8f, 0.8f, 0.8f, 0.35f);

        style.setColor(ImGuiCol.DockingEmptyBg, 0.15f, 0.15f, 0.15f, 1.0f);
        style.setColor(ImGuiCol.DockingPreview, 0.5f, 0.5f, 0.5f, 0.7f);

        style.setColor(ImGuiCol.ResizeGrip, 0.3f, 0.3f, 0.3f, 1.0f);
        style.setColor(ImGuiCol.ResizeGripHovered, 0.4f, 0.4f, 0.4f, 1.0f);
        style.setColor(ImGuiCol.ResizeGripActive, 0.15f, 0.15f, 0.15f, 1.0f);
    }

    public static void setWildColorTheme() {
        var style = ImGui.getStyle();

        var random = new Random();
        for (int i = 0; i < ImGuiCol.COUNT; i++) {
            style.setColor(i, random.nextFloat(), random.nextFloat(), random.nextFloat(), 1.0f);
        }
    }

    public static void setContrastWhiteColorTheme() {
        var style = ImGui.getStyle();

        style.setColor(ImGuiCol.Text, 0.0f, 0.0f, 0.0f, 1.0f);
        style.setColor(ImGuiCol.TextDisabled, 0.5f, 0.5f, 0.5f, 1.0f);
        style.setColor(ImGuiCol.WindowBg, 0.94f, 0.94f, 0.94f, 1.0f);
        style.setColor(ImGuiCol.ChildBg, 1.0f, 1.0f, 1.0f, 1.0f);
        style.setColor(ImGuiCol.PopupBg, 1.0f, 1.0f, 1.0f, 1.0f);
        style.setColor(ImGuiCol.Border, 0.0f, 0.0f, 0.0f, 0.0f);
        style.setColor(ImGuiCol.BorderShadow, 0.0f, 0.0f, 0.0f, 0.0f);
        style.setColor(ImGuiCol.FrameBg, 1.0f, 1.0f, 1.0f, 1.0f);
        style.setColor(ImGuiCol.FrameBgHovered, 0.26f, 0.59f, 0.98f, 0.4f);
        style.setColor(ImGuiCol.FrameBgActive, 0.26f, 0.59f, 0.98f, 0.67f);
        style.setColor(ImGuiCol.TitleBg, 0.96f, 0.96f, 0.96f, 1.0f);
        style.setColor(ImGuiCol.TitleBgActive, 0.82f, 0.82f, 0.82f, 1.0f);
        style.setColor(ImGuiCol.TitleBgCollapsed, 0.0f, 0.0f, 0.0f, 0.51f);
        style.setColor(ImGuiCol.MenuBarBg, 0.86f, 0.86f, 0.86f, 1.0f);
        style.setColor(ImGuiCol.ScrollbarBg, 0.98f, 0.98f, 0.98f, 0.53f);
        style.setColor(ImGuiCol.ScrollbarGrab, 0.69f, 0.69f, 0.69f, 0.8f);
        style.setColor(ImGuiCol.ScrollbarGrabHovered, 0.49f, 0.49f, 0.49f, 0.8f);
        style.setColor(ImGuiCol.ScrollbarGrabActive, 0.49f, 0.49f, 0.49f, 1.0f);
        style.setColor(ImGuiCol.CheckMark, 0.26f, 0.59f, 0.98f, 1.0f);
        style.setColor(ImGuiCol.SliderGrab, 0.24f, 0.52f, 0.88f, 1.0f);
        style.setColor(ImGuiCol.SliderGrabActive, 0.26f, 0.59f, 0.98f, 1.0f);
        style.setColor(ImGuiCol.Button, 0.26f, 0.59f, 0.98f, 0.4f);
        style.setColor(ImGuiCol.ButtonHovered, 0.26f, 0.59f, 0.98f, 1.0f);
        style.setColor(ImGuiCol.ButtonActive, 0.06f, 0.53f, 0.98f, 1.0f);
        style.setColor(ImGuiCol.Header, 0.26f, 0.59f, 0.98f, 0.31f);
        style.setColor(ImGuiCol.HeaderHovered, 0.26f, 0.59f, 0.98f, 0.8f);
        style.setColor(ImGuiCol.HeaderActive, 0.26f, 0.59f, 0.98f, 1.0f);
        style.setColor(ImGuiCol.Separator, 0.39f, 0.39f, 0.39f, 1.0f);
        style.setColor(ImGuiCol.SeparatorHovered, 0.14f, 0.44f, 0.80f, 0.78f);
        style.setColor(ImGuiCol.SeparatorActive, 0.14f, 0.44f, 0.80f, 1.0f);
        style.setColor(ImGuiCol.ResizeGrip, 0.26f, 0.59f, 0.98f, 0.25f);
        style.setColor(ImGuiCol.ResizeGripHovered, 0.26f, 0.59f, 0.98f, 0.67f);
        style.setColor(ImGuiCol.ResizeGripActive, 0.26f, 0.59f, 0.98f, 0.95f);
        style.setColor(ImGuiCol.Tab, 0.86f, 0.86f, 0.86f, 0.97f);
        style.setColor(ImGuiCol.TabHovered, 0.26f, 0.59f, 0.98f, 0.8f);
        style.setColor(ImGuiCol.TabActive, 0.26f, 0.59f, 0.98f, 1.0f);
        style.setColor(ImGuiCol.TabUnfocused, 0.96f, 0.96f, 0.96f, 0.97f);
        style.setColor(ImGuiCol.TabUnfocusedActive, 0.86f, 0.86f, 0.86f, 1.0f);
        style.setColor(ImGuiCol.PlotLines, 0.39f, 0.39f, 0.39f, 1.0f);
        style.setColor(ImGuiCol.PlotLinesHovered, 0.26f, 0.59f, 0.98f, 1.0f);
        style.setColor(ImGuiCol.PlotHistogram, 0.39f, 0.39f, 0.39f, 1.0f);
        style.setColor(ImGuiCol.PlotHistogramHovered, 0.26f, 0.59f, 0.98f, 1.0f);
        style.setColor(ImGuiCol.TextSelectedBg, 0.26f, 0.59f, 0.98f, 0.35f);
        style.setColor(ImGuiCol.DragDropTarget, 1.0f, 1.0f, 0.0f, 0.9f);
        style.setColor(ImGuiCol.NavHighlight, 0.26f, 0.59f, 0.98f, 1.0f);
        style.setColor(ImGuiCol.NavWindowingHighlight, 1.0f, 1.0f, 1.0f, 0.7f);
        style.setColor(ImGuiCol.NavWindowingDimBg, 0.8f, 0.8f, 0.8f, 0.2f);
        style.setColor(ImGuiCol.ModalWindowDimBg, 0.8f, 0.8f, 0.8f, 0.35f);
    }

    public static void setWhiteTheme() {
        var style = ImGui.getStyle();

        style.setColor(ImGuiCol.Header, 0.86f, 0.86f, 0.86f, 1.0f);
        style.setColor(ImGuiCol.HeaderHovered, 0.86f, 0.86f, 0.86f, 1.0f);
        style.setColor(ImGuiCol.HeaderActive, 0.86f, 0.86f, 0.86f, 1.0f);

        style.setColor(ImGuiCol.Button, 0.86f, 0.86f, 0.86f, 1.0f);
        style.setColor(ImGuiCol.ButtonHovered, 0.86f, 0.86f, 0.86f, 1.0f);
        style.setColor(ImGuiCol.ButtonActive, 0.86f, 0.86f, 0.86f, 1.0f);

        style.setColor(ImGuiCol.Text, 0.0f, 0.0f, 0.0f, 1.0f);
        style.setColor(ImGuiCol.TextDisabled, 0.6f, 0.6f, 0.6f, 1.0f);
        style.setColor(ImGuiCol.TextSelectedBg, 0.0f, 0.0f, 0.0f, 0.0f);

        style.setColor(ImGuiCol.WindowBg, 0.94f, 0.94f, 0.94f, 1.0f);
        style.setColor(ImGuiCol.ChildBg, 0.0f, 0.0f, 0.0f, 0.0f);
        style.setColor(ImGuiCol.PopupBg, 1.0f, 1.0f, 1.0f, 1.0f);

        style.setColor(ImGuiCol.Border, 0.0f, 0.0f, 0.0f, 0.0f);
        style.setColor(ImGuiCol.BorderShadow, 0.0f, 0.0f, 0.0f, 0.0f);

        style.setColor(ImGuiCol.FrameBg, 1.0f, 1.0f, 1.0f, 1.0f);
        style.setColor(ImGuiCol.FrameBgHovered, 0.4f, 0.4f, 0.4f, 1.0f);
        style.setColor(ImGuiCol.FrameBgActive, 0.4f, 0.4f, 0.4f, 1.0f);

        style.setColor(ImGuiCol.TitleBg, 0.86f, 0.86f, 0.86f, 1.0f);
        style.setColor(ImGuiCol.TitleBgActive, 0.86f, 0.86f, 0.86f, 1.0f);
        style.setColor(ImGuiCol.TitleBgCollapsed, 0.0f, 0.0f, 0.0f, 0.0f);

        style.setColor(ImGuiCol.MenuBarBg, 0.86f, 0.86f, 0.86f, 1.0f);

        style.setColor(ImGuiCol.ScrollbarBg, 0.2f, 0.2f, 0.2f, 1.0f);
        style.setColor(ImGuiCol.ScrollbarGrab, 0.3f, 0.3f, 0.3f, 1.0f);
        style.setColor(ImGuiCol.ScrollbarGrabHovered, 0.4f, 0.4f, 0.4f, 1.0f);
        style.setColor(ImGuiCol.ScrollbarGrabActive, 0.15f, 0.15f, 0.15f, 1.0f);

        style.setColor(ImGuiCol.CheckMark, 0.0f, 0.0f, 0.0f, 1.0f);
        style.setColor(ImGuiCol.SliderGrab, 0.3f, 0.3f, 0.3f, 1.0f);
        style.setColor(ImGuiCol.SliderGrabActive, 0.6f, 0.6f, 0.6f, 1.0f);

        style.setColor(ImGuiCol.Separator, 0.0f, 0.0f, 0.0f, 0.0f);
        style.setColor(ImGuiCol.SeparatorHovered, 0.0f, 0.0f, 0.0f, 0.0f);
        style.setColor(ImGuiCol.SeparatorActive, 0.0f, 0.0f, 0.0f, 0.0f);

        style.setColor(ImGuiCol.ResizeGrip, 0.0f, 0.0f, 0.0f, 0.0f);
        style.setColor(ImGuiCol.ResizeGripHovered, 0.0f, 0.0f, 0.0f, 0.0f);
        style.setColor(ImGuiCol.ResizeGripActive, 0.0f, 0.0f, 0.0f, 0.0f);

        style.setColor(ImGuiCol.Tab, 0.86f, 0.86f, 0.86f, 1.0f);
        style.setColor(ImGuiCol.TabHovered, 0.86f, 0.86f, 0.86f, 1.0f);
        style.setColor(ImGuiCol.TabActive, 0.86f, 0.86f, 0.86f, 1.0f);
        style.setColor(ImGuiCol.TabUnfocused, 0.86f, 0.86f, 0.86f, 1.0f);
        style.setColor(ImGuiCol.TabUnfocusedActive, 0.86f, 0.86f, 0.86f, 1.0f);

        style.setColor(ImGuiCol.PlotLines, 0.0f, 0.0f, 0.0f, 1.0f);
        style.setColor(ImGuiCol.PlotLinesHovered, 0.0f, 0.0f, 0.0f, 1.0f);
        style.setColor(ImGuiCol.PlotHistogram, 0.0f, 0.0f, 0.0f, 1.0f);
        style.setColor(ImGuiCol.PlotHistogramHovered, 0.0f, 0.0f, 0.0f, 1.0f);

        style.setColor(ImGuiCol.DragDropTarget, 0.0f, 0.0f, 0.0f, 0.0f);
        style.setColor(ImGuiCol.ModalWindowDimBg, 0.0f, 0.0f, 0.0f, 0.0f);

        style.setColor(ImGuiCol.NavHighlight, 0.0f, 0.0f, 0.0f, 0.0f);
        style.setColor(ImGuiCol.NavWindowingHighlight, 0.0f, 0.0f, 0.0f, 0.0f);
        style.setColor(ImGuiCol.NavWindowingDimBg, 0.0f, 0.0f, 0.0f, 0.0f);
    }
}