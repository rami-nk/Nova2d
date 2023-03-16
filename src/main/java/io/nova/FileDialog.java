package io.nova;

import org.lwjgl.util.tinyfd.TinyFileDialogs;

public class FileDialog {

    public static void openFileDialog(String filter) {
        var file = TinyFileDialogs.tinyfd_openFileDialog(
                "Open existing project",
                "",
                null,
                "*",
                false);
        System.out.println(file);
    }
}