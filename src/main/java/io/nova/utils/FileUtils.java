package io.nova.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    public static boolean isImage(File file) {
        try {
            var contentType = Files.probeContentType(file.toPath());
            return contentType != null && contentType.startsWith("image");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isNovaProjectFile(String path) {
        return Path.of(path).getFileName().toString().split("\\.")[1].equals("nova");
    }
}