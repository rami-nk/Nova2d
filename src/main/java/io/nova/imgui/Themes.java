package io.nova.imgui;

import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.flag.ImGuiCol;

import java.util.Random;

public class Themes {

    public static void setDarkTheme2() {
        ImGuiStyle style = ImGui.getStyle();
        style.setFrameRounding(4);
        style.setGrabRounding(4);
        style.setScrollbarRounding(4);
        style.setChildRounding(4);
        style.setPopupRounding(4);
        style.setWindowRounding(4);

        style.setColor(ImGuiCol.Text, 0.86f, 0.86f, 0.86f, 1.00f);
        style.setColor(ImGuiCol.TextDisabled, 0.48f, 0.48f, 0.48f, 1.00f);
        style.setColor(ImGuiCol.WindowBg, 0.13f, 0.13f, 0.13f, 1.00f);
        style.setColor(ImGuiCol.ChildBg, 0.20f, 0.20f, 0.20f, 1.00f);
        style.setColor(ImGuiCol.PopupBg, 0.13f, 0.13f, 0.13f, 1.00f);
        style.setColor(ImGuiCol.Border, 0.31f, 0.31f, 0.31f, 1.00f);
        style.setColor(ImGuiCol.BorderShadow, 0.00f, 0.00f, 0.00f, 0.00f);
        style.setColor(ImGuiCol.FrameBg, 0.20f, 0.20f, 0.20f, 1.00f);
        style.setColor(ImGuiCol.FrameBgHovered, 0.25f, 0.25f, 0.25f, 1.00f);
        style.setColor(ImGuiCol.FrameBgActive, 0.31f, 0.31f, 0.31f, 1.00f);
        style.setColor(ImGuiCol.TitleBg, 0.13f, 0.13f, 0.13f, 1.00f);
        style.setColor(ImGuiCol.TitleBgActive, 0.13f, 0.13f, 0.13f, 1.00f);
        style.setColor(ImGuiCol.TitleBgCollapsed, 0.13f, 0.13f, 0.13f, 1.00f);
        style.setColor(ImGuiCol.MenuBarBg, 0.20f, 0.20f, 0.20f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarBg, 0.13f, 0.13f, 0.13f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarGrab, 0.31f, 0.31f, 0.31f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarGrabHovered, 0.41f, 0.41f, 0.41f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarGrabActive, 0.51f, 0.51f, 0.51f, 1.00f);
        style.setColor(ImGuiCol.CheckMark, 0.86f, 0.86f, 0.86f, 1.00f);
        style.setColor(ImGuiCol.SliderGrab, 0.31f, 0.31f, 0.31f, 1.00f);
        style.setColor(ImGuiCol.SliderGrabActive, 0.51f, 0.51f, 0.51f, 1.00f);
        style.setColor(ImGuiCol.Button, 0.31f, 0.31f, 0.31f, 1.00f);
        style.setColor(ImGuiCol.ButtonHovered, 0.41f, 0.41f, 0.41f, 1.00f);
        style.setColor(ImGuiCol.ButtonActive, 0.51f, 0.51f, 0.51f, 1.00f);
        style.setColor(ImGuiCol.Header, 0.31f, 0.31f, 0.31f, 1.00f);
        style.setColor(ImGuiCol.HeaderHovered, 0.41f, 0.41f, 0.41f, 1.00f);
        style.setColor(ImGuiCol.HeaderActive, 0.51f, 0.51f, 0.51f, 1.00f);
        style.setColor(ImGuiCol.Separator, 0.31f, 0.31f, 0.31f, 1.00f);
        style.setColor(ImGuiCol.SeparatorHovered, 0.41f, 0.41f, 0.41f, 1.00f);
        style.setColor(ImGuiCol.SeparatorActive, 0.51f, 0.51f, 0.51f, 1.00f);
        style.setColor(ImGuiCol.ResizeGrip, 0.31f, 0.31f, 0.31f, 1.00f);
        style.setColor(ImGuiCol.ResizeGripHovered, 0.41f, 0.41f, 0.41f, 1.00f);
        style.setColor(ImGuiCol.ResizeGripActive, 0.51f, 0.51f, 0.51f, 1.00f);
        style.setColor(ImGuiCol.Tab, 0.13f, 0.13f, 0.13f, 1.00f);
        style.setColor(ImGuiCol.TabHovered, 0.31f, 0.31f, 0.31f, 1.00f);
        style.setColor(ImGuiCol.TabActive, 0.20f, 0.20f, 0.20f, 1.00f);
        style.setColor(ImGuiCol.TabUnfocused, 0.13f, 0.13f, 0.13f, 1.00f);
        style.setColor(ImGuiCol.TabUnfocusedActive, 0.20f, 0.20f, 0.20f, 1.00f);
        style.setColor(ImGuiCol.DockingPreview, 0.00f, 0.69f, 0.85f, 0.70f);
        style.setColor(ImGuiCol.DockingEmptyBg, 0.20f, 0.20f, 0.20f, 1.00f);
        style.setColor(ImGuiCol.PlotLines, 0.86f, 0.86f, 0.86f, 1.00f);
        style.setColor(ImGuiCol.PlotLinesHovered, 1.00f, 0.43f, 0.35f, 1.00f);
        style.setColor(ImGuiCol.PlotHistogram, 0.86f, 0.86f, 0.86f, 1.00f);
        style.setColor(ImGuiCol.PlotHistogramHovered, 1.00f, 0.43f, 0.35f, 1.00f);
        style.setColor(ImGuiCol.TextSelectedBg, 0.00f, 0.69f, 0.85f, 1.00f);
        style.setColor(ImGuiCol.ModalWindowDimBg, 0.20f, 0.20f, 0.20f, 0.35f);

        style.setColor(ImGuiCol.Tab, 0.11f, 0.11f, 0.11f, 1.00f);
        style.setColor(ImGuiCol.TabHovered, 0.18f, 0.18f, 0.18f, 1.00f);
        style.setColor(ImGuiCol.TabActive, 0.26f, 0.26f, 0.26f, 1.00f);
        style.setColor(ImGuiCol.TabUnfocused, 0.08f, 0.08f, 0.08f, 1.00f);
        style.setColor(ImGuiCol.TabUnfocusedActive, 0.15f, 0.15f, 0.15f, 1.00f);

        style.setTabRounding(4.0f);
        style.setFrameRounding(4.0f);
        style.setIndentSpacing(12.0f);
        style.setItemInnerSpacing(6.0f, 2.0f);
        style.setItemSpacing(12.0f, 8.0f);
    }

    public static void setDeepDarkTheme() {
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

    public static void setDeepDarkTheme2() {
        var style = ImGui.getStyle();
        style.setColor(ImGuiCol.Text, 1.00f, 1.00f, 1.00f, 1.00f);
        style.setColor(ImGuiCol.TextDisabled, 0.50f, 0.50f, 0.50f, 1.00f);
        style.setColor(ImGuiCol.WindowBg, 0.10f, 0.10f, 0.10f, 1.00f);
        style.setColor(ImGuiCol.ChildBg, 0.00f, 0.00f, 0.00f, 0.00f);
        style.setColor(ImGuiCol.PopupBg, 0.19f, 0.19f, 0.19f, 0.92f);
        style.setColor(ImGuiCol.Border, 0.19f, 0.19f, 0.19f, 0.29f);
        style.setColor(ImGuiCol.BorderShadow, 0.00f, 0.00f, 0.00f, 0.24f);
        style.setColor(ImGuiCol.FrameBg, 0.05f, 0.05f, 0.05f, 0.54f);
        style.setColor(ImGuiCol.FrameBgHovered, 0.19f, 0.19f, 0.19f, 0.54f);
        style.setColor(ImGuiCol.FrameBgActive, 0.20f, 0.22f, 0.23f, 1.00f);
        style.setColor(ImGuiCol.TitleBg, 0.00f, 0.00f, 0.00f, 1.00f);
        style.setColor(ImGuiCol.TitleBgActive, 0.06f, 0.06f, 0.06f, 1.00f);
        style.setColor(ImGuiCol.TitleBgCollapsed, 0.00f, 0.00f, 0.00f, 1.00f);
        style.setColor(ImGuiCol.MenuBarBg, 0.14f, 0.14f, 0.14f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarBg, 0.05f, 0.05f, 0.05f, 0.54f);
        style.setColor(ImGuiCol.ScrollbarGrab, 0.34f, 0.34f, 0.34f, 0.54f);
        style.setColor(ImGuiCol.ScrollbarGrabHovered, 0.40f, 0.40f, 0.40f, 0.54f);
        style.setColor(ImGuiCol.ScrollbarGrabActive, 0.56f, 0.56f, 0.56f, 0.54f);
        style.setColor(ImGuiCol.CheckMark, 0.33f, 0.67f, 0.86f, 1.00f);
        style.setColor(ImGuiCol.SliderGrab, 0.34f, 0.34f, 0.34f, 0.54f);
        style.setColor(ImGuiCol.SliderGrabActive, 0.56f, 0.56f, 0.56f, 0.54f);
        style.setColor(ImGuiCol.Button, 0.05f, 0.05f, 0.05f, 0.54f);
        style.setColor(ImGuiCol.ButtonHovered, 0.19f, 0.19f, 0.19f, 0.54f);
        style.setColor(ImGuiCol.ButtonActive, 0.20f, 0.22f, 0.23f, 1.00f);
        style.setColor(ImGuiCol.Header, 0.00f, 0.00f, 0.00f, 0.52f);
        style.setColor(ImGuiCol.HeaderHovered, 0.00f, 0.00f, 0.00f, 0.36f);
        style.setColor(ImGuiCol.HeaderActive, 0.20f, 0.22f, 0.23f, 0.33f);
        style.setColor(ImGuiCol.Separator, 0.28f, 0.28f, 0.28f, 0.29f);
        style.setColor(ImGuiCol.SeparatorHovered, 0.44f, 0.44f, 0.44f, 0.29f);
        style.setColor(ImGuiCol.SeparatorActive, 0.40f, 0.44f, 0.47f, 1.00f);
        style.setColor(ImGuiCol.ResizeGrip, 0.28f, 0.28f, 0.28f, 0.29f);
        style.setColor(ImGuiCol.ResizeGripHovered, 0.44f, 0.44f, 0.44f, 0.29f);
        style.setColor(ImGuiCol.ResizeGripActive, 0.40f, 0.44f, 0.47f, 1.00f);
        style.setColor(ImGuiCol.Tab, 0.00f, 0.00f, 0.00f, 0.52f);
        style.setColor(ImGuiCol.TabHovered, 0.14f, 0.14f, 0.14f, 1.00f);
        style.setColor(ImGuiCol.TabActive, 0.20f, 0.20f, 0.20f, 0.36f);
        style.setColor(ImGuiCol.TabUnfocused, 0.00f, 0.00f, 0.00f, 0.52f);
        style.setColor(ImGuiCol.TabUnfocusedActive, 0.14f, 0.14f, 0.14f, 1.00f);
        style.setColor(ImGuiCol.DockingPreview, 0.33f, 0.67f, 0.86f, 1.00f);
        style.setColor(ImGuiCol.DockingEmptyBg, 1.00f, 0.00f, 0.00f, 1.00f);
        style.setColor(ImGuiCol.PlotLines, 1.00f, 0.00f, 0.00f, 1.00f);
        style.setColor(ImGuiCol.PlotLinesHovered, 1.00f, 0.00f, 0.00f, 1.00f);
        style.setColor(ImGuiCol.PlotHistogram, 1.00f, 0.00f, 0.00f, 1.00f);
        style.setColor(ImGuiCol.PlotHistogramHovered, 1.00f, 0.00f, 0.00f, 1.00f);
        style.setColor(ImGuiCol.TableHeaderBg, 0.00f, 0.00f, 0.00f, 0.52f);
        style.setColor(ImGuiCol.TableBorderStrong, 0.00f, 0.00f, 0.00f, 0.52f);
        style.setColor(ImGuiCol.TableBorderLight, 0.28f, 0.28f, 0.28f, 0.29f);
        style.setColor(ImGuiCol.TableRowBg, 0.00f, 0.00f, 0.00f, 0.00f);
        style.setColor(ImGuiCol.TableRowBgAlt, 1.00f, 1.00f, 1.00f, 0.06f);
        style.setColor(ImGuiCol.TextSelectedBg, 0.20f, 0.22f, 0.23f, 1.00f);
        style.setColor(ImGuiCol.DragDropTarget, 0.33f, 0.67f, 0.86f, 1.00f);
        style.setColor(ImGuiCol.NavHighlight, 1.00f, 0.00f, 0.00f, 1.00f);
        style.setColor(ImGuiCol.NavWindowingHighlight, 1.00f, 0.00f, 0.00f, 0.70f);
        style.setColor(ImGuiCol.NavWindowingDimBg, 1.00f, 0.00f, 0.00f, 0.20f);
        style.setColor(ImGuiCol.ModalWindowDimBg, 1.00f, 0.00f, 0.00f, 0.35f);

        style.setWindowPadding(8.00f, 8.00f);
        style.setFramePadding(5.00f, 2.00f);
        style.setCellPadding(6.00f, 6.00f);
        style.setItemSpacing(6.00f, 6.00f);
        style.setItemInnerSpacing(6.00f, 6.00f);
        style.setTouchExtraPadding(0.00f, 0.00f);
        style.setIndentSpacing(25);
        style.setScrollbarSize(15);
        style.setGrabMinSize(10);
        style.setWindowBorderSize(1);
        style.setChildBorderSize(1);
        style.setPopupBorderSize(1);
        style.setFrameBorderSize(1);
        style.setTabBorderSize(1);
        style.setWindowRounding(7);
        style.setChildRounding(4);
        style.setFrameRounding(3);
        style.setPopupRounding(4);
        style.setScrollbarRounding(9);
        style.setGrabRounding(3);
        style.setLogSliderDeadzone(4);
        style.setTabRounding(4);
    }

    public static void setDarkTheme() {
        var style = ImGui.getStyle();
        style.setColor(ImGuiCol.Text, 1.00f, 1.00f, 1.00f, 1.00f);
        style.setColor(ImGuiCol.TextDisabled, 0.50f, 0.50f, 0.50f, 1.00f);
        style.setColor(ImGuiCol.WindowBg, 0.13f, 0.14f, 0.15f, 1.00f);
        style.setColor(ImGuiCol.ChildBg, 0.13f, 0.14f, 0.15f, 1.00f);
        style.setColor(ImGuiCol.PopupBg, 0.13f, 0.14f, 0.15f, 1.00f);
        style.setColor(ImGuiCol.Border, 0.43f, 0.43f, 0.50f, 0.50f);
        style.setColor(ImGuiCol.BorderShadow, 0.00f, 0.00f, 0.00f, 0.00f);
        style.setColor(ImGuiCol.FrameBg, 0.25f, 0.25f, 0.25f, 1.00f);
        style.setColor(ImGuiCol.FrameBgHovered, 0.38f, 0.38f, 0.38f, 1.00f);
        style.setColor(ImGuiCol.FrameBgActive, 0.67f, 0.67f, 0.67f, 0.39f);
        style.setColor(ImGuiCol.TitleBg, 0.08f, 0.08f, 0.09f, 1.00f);
        style.setColor(ImGuiCol.TitleBgActive, 0.08f, 0.08f, 0.09f, 1.00f);
        style.setColor(ImGuiCol.TitleBgCollapsed, 0.00f, 0.00f, 0.00f, 0.51f);
        style.setColor(ImGuiCol.MenuBarBg, 0.14f, 0.14f, 0.14f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarBg, 0.02f, 0.02f, 0.02f, 0.53f);
        style.setColor(ImGuiCol.ScrollbarGrab, 0.31f, 0.31f, 0.31f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarGrabHovered, 0.41f, 0.41f, 0.41f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarGrabActive, 0.51f, 0.51f, 0.51f, 1.00f);
        style.setColor(ImGuiCol.CheckMark, 0.11f, 0.64f, 0.92f, 1.00f);
        style.setColor(ImGuiCol.SliderGrab, 0.11f, 0.64f, 0.92f, 1.00f);
        style.setColor(ImGuiCol.SliderGrabActive, 0.08f, 0.50f, 0.72f, 1.00f);
        style.setColor(ImGuiCol.Button, 0.25f, 0.25f, 0.25f, 1.00f);
        style.setColor(ImGuiCol.ButtonHovered, 0.38f, 0.38f, 0.38f, 1.00f);
        style.setColor(ImGuiCol.ButtonActive, 0.67f, 0.67f, 0.67f, 0.39f);
        style.setColor(ImGuiCol.Header, 0.22f, 0.22f, 0.22f, 1.00f);
        style.setColor(ImGuiCol.HeaderHovered, 0.25f, 0.25f, 0.25f, 1.00f);
        style.setColor(ImGuiCol.HeaderActive, 0.67f, 0.67f, 0.67f, 0.39f);
        style.setColor(ImGuiCol.Separator, 0.43f, 0.43f, 0.50f, 0.50f);
        style.setColor(ImGuiCol.SeparatorHovered, 0.41f, 0.42f, 0.44f, 1.00f);
        style.setColor(ImGuiCol.SeparatorActive, 0.26f, 0.59f, 0.98f, 0.95f);
        style.setColor(ImGuiCol.ResizeGrip, 0.00f, 0.00f, 0.00f, 0.00f);
        style.setColor(ImGuiCol.ResizeGripHovered, 0.29f, 0.30f, 0.31f, 0.67f);
        style.setColor(ImGuiCol.ResizeGripActive, 0.26f, 0.59f, 0.98f, 0.95f);
        style.setColor(ImGuiCol.Tab, 0.08f, 0.08f, 0.09f, 0.83f);
        style.setColor(ImGuiCol.TabHovered, 0.33f, 0.34f, 0.36f, 0.83f);
        style.setColor(ImGuiCol.TabActive, 0.23f, 0.23f, 0.24f, 1.00f);
        style.setColor(ImGuiCol.TabUnfocused, 0.08f, 0.08f, 0.09f, 1.00f);
        style.setColor(ImGuiCol.TabUnfocusedActive, 0.13f, 0.14f, 0.15f, 1.00f);
        style.setColor(ImGuiCol.DockingPreview, 0.26f, 0.59f, 0.98f, 0.70f);
        style.setColor(ImGuiCol.DockingEmptyBg, 0.20f, 0.20f, 0.20f, 1.00f);
        style.setColor(ImGuiCol.PlotLines, 0.61f, 0.61f, 0.61f, 1.00f);
        style.setColor(ImGuiCol.PlotLinesHovered, 1.00f, 0.43f, 0.35f, 1.00f);
        style.setColor(ImGuiCol.PlotHistogram, 0.90f, 0.70f, 0.00f, 1.00f);
        style.setColor(ImGuiCol.PlotHistogramHovered, 1.00f, 0.60f, 0.00f, 1.00f);
        style.setColor(ImGuiCol.TextSelectedBg, 0.26f, 0.59f, 0.98f, 0.35f);
        style.setColor(ImGuiCol.DragDropTarget, 0.11f, 0.64f, 0.92f, 1.00f);
        style.setColor(ImGuiCol.NavHighlight, 0.26f, 0.59f, 0.98f, 1.00f);
        style.setColor(ImGuiCol.NavWindowingHighlight, 1.00f, 1.00f, 1.00f, 0.70f);
        style.setColor(ImGuiCol.NavWindowingDimBg, 0.80f, 0.80f, 0.80f, 0.20f);
        style.setColor(ImGuiCol.ModalWindowDimBg, 0.80f, 0.80f, 0.80f, 0.35f);
        style.setFrameRounding(2.3f);
        style.setGrabRounding(style.getFrameRounding());
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

    public static void setWhiteTheme2() {
        // General
        var style = ImGui.getStyle();
        style.setFrameRounding(2.0f);
        style.setWindowPadding(4.0f, 3.0f);
        style.setFramePadding(4.0f, 4.0f);
        style.setItemSpacing(4.0f, 3.0f);
        style.setIndentSpacing(12);
        style.setScrollbarSize(12);
        style.setGrabMinSize(9);

        // Sizes
        style.setWindowBorderSize(0.0f);
        style.setChildBorderSize(0.0f);
        style.setPopupBorderSize(0.0f);
        style.setFrameBorderSize(0.0f);
        style.setTabBorderSize(0.0f);

        style.setWindowRounding(0.0f);
        style.setChildRounding(0.0f);
        style.setFrameRounding(0.0f);
        style.setPopupRounding(0.0f);
        style.setGrabRounding(2.0f);
        style.setScrollbarRounding(12.0f);
        style.setTabRounding(0.0f);

        style.setColor(ImGuiCol.Text, 0.15f, 0.15f, 0.15f, 1.00f);
        style.setColor(ImGuiCol.TextDisabled, 0.60f, 0.60f, 0.60f, 1.00f);
        style.setColor(ImGuiCol.WindowBg, 0.87f, 0.87f, 0.87f, 1.00f);
        style.setColor(ImGuiCol.ChildBg, 0.87f, 0.87f, 0.87f, 1.00f);
        style.setColor(ImGuiCol.PopupBg, 0.87f, 0.87f, 0.87f, 1.00f);
        style.setColor(ImGuiCol.Border, 0.89f, 0.89f, 0.89f, 1.00f);
        style.setColor(ImGuiCol.BorderShadow, 0.00f, 0.00f, 0.00f, 0.00f);
        style.setColor(ImGuiCol.FrameBg, 0.93f, 0.93f, 0.93f, 1.00f);
        style.setColor(ImGuiCol.FrameBgHovered, 1.00f, 0.69f, 0.07f, 0.69f);
        style.setColor(ImGuiCol.FrameBgActive, 1.00f, 0.82f, 0.46f, 0.69f);
        style.setColor(ImGuiCol.TitleBg, 1.00f, 1.00f, 1.00f, 1.00f);
        style.setColor(ImGuiCol.TitleBgActive, 1.00f, 1.00f, 1.00f, 1.00f);
        style.setColor(ImGuiCol.TitleBgCollapsed, 1.00f, 1.00f, 1.00f, 1.00f);
        style.setColor(ImGuiCol.MenuBarBg, 1.00f, 1.00f, 1.00f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarBg, 0.87f, 0.87f, 0.87f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarGrab, 1.00f, 1.00f, 1.00f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarGrabHovered, 1.00f, 0.69f, 0.07f, 0.69f);
        style.setColor(ImGuiCol.ScrollbarGrabActive, 1.00f, 0.82f, 0.46f, 0.69f);
        style.setColor(ImGuiCol.CheckMark, 0.01f, 0.01f, 0.01f, 0.63f);
        style.setColor(ImGuiCol.SliderGrab, 1.00f, 0.69f, 0.07f, 0.69f);
        style.setColor(ImGuiCol.SliderGrabActive, 1.00f, 0.82f, 0.46f, 0.69f);
        style.setColor(ImGuiCol.Button, 0.83f, 0.83f, 0.83f, 1.00f);
        style.setColor(ImGuiCol.ButtonHovered, 1.00f, 0.69f, 0.07f, 0.69f);
        style.setColor(ImGuiCol.ButtonActive, 1.00f, 0.82f, 0.46f, 0.69f);
        style.setColor(ImGuiCol.Header, 0.67f, 0.67f, 0.67f, 1.00f);
        style.setColor(ImGuiCol.HeaderHovered, 1.00f, 0.69f, 0.07f, 1.00f);
        style.setColor(ImGuiCol.HeaderActive, 1.00f, 0.82f, 0.46f, 0.69f);
        style.setColor(ImGuiCol.Separator, 1.00f, 1.00f, 1.00f, 1.00f);
        style.setColor(ImGuiCol.SeparatorHovered, 1.00f, 0.69f, 0.07f, 1.00f);
        style.setColor(ImGuiCol.SeparatorActive, 1.00f, 0.82f, 0.46f, 0.69f);
        style.setColor(ImGuiCol.ResizeGrip, 1.00f, 1.00f, 1.00f, 0.18f);
        style.setColor(ImGuiCol.ResizeGripHovered, 1.00f, 0.69f, 0.07f, 1.00f);
        style.setColor(ImGuiCol.ResizeGripActive, 1.00f, 0.82f, 0.46f, 0.69f);
        style.setColor(ImGuiCol.Tab, 0.16f, 0.16f, 0.16f, 0.00f);
        style.setColor(ImGuiCol.TabHovered, 1.00f, 0.69f, 0.07f, 1.00f);
        style.setColor(ImGuiCol.TabActive, 1.00f, 0.69f, 0.07f, 1.00f);
        style.setColor(ImGuiCol.TabUnfocused, 1.00f, 1.00f, 1.00f, 1.00f);
        style.setColor(ImGuiCol.TabUnfocusedActive, 0.87f, 0.87f, 0.87f, 1.00f);
        style.setColor(ImGuiCol.DockingPreview, 1.00f, 0.82f, 0.46f, 0.69f);
        style.setColor(ImGuiCol.DockingEmptyBg, 1.00f, 1.00f, 1.00f, 1.00f);
        style.setColor(ImGuiCol.PlotLines, 1.00f, 1.00f, 1.00f, 1.00f);
        style.setColor(ImGuiCol.PlotLinesHovered, 0.90f, 0.70f, 0.00f, 1.00f);
        style.setColor(ImGuiCol.PlotHistogram, 0.90f, 0.70f, 0.00f, 1.00f);
        style.setColor(ImGuiCol.PlotHistogramHovered, 1.00f, 0.60f, 0.00f, 1.00f);
        style.setColor(ImGuiCol.TextSelectedBg, 1.00f, 0.69f, 0.07f, 1.00f);
        style.setColor(ImGuiCol.DragDropTarget, 1.00f, 0.69f, 0.07f, 1.00f);
        style.setColor(ImGuiCol.NavHighlight, 1.00f, 0.69f, 0.07f, 1.00f);
        style.setColor(ImGuiCol.NavWindowingHighlight, 1.00f, 1.00f, 1.00f, 0.70f);
        style.setColor(ImGuiCol.NavWindowingDimBg, 0.87f, 0.87f, 0.87f, 1.00f);
        style.setColor(ImGuiCol.ModalWindowDimBg, 0.20f, 0.20f, 0.20f, 0.35f);
    }
}