package io.nova;

import org.lwjgl.util.tinyfd.TinyFileDialogs;

public class FileDialog {

    // TODO: Use filter
    public static String openFileDialog(String filter) {
        return TinyFileDialogs.tinyfd_openFileDialog(
                "Open existing project",
                null,
                null,
                "Nova project files (*.nova)",
                false);
    }

    public static String saveFileDialog(String filter) {
        var filePath = TinyFileDialogs.tinyfd_saveFileDialog(
                "Save project",
                null,
                null,
                "Nova project files (*.nova)"
        );
        return filePath;
    }
}