package panels.contentbrowser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    private static Map<File, ImageAsset> imageAssets = new HashMap<>();

    public static void addImageAsset(File file, ImageAsset imageAsset) {
        imageAssets.put(file, imageAsset);
    }

    public static void addImageAsset(File file) {
        imageAssets.put(file, new ImageAsset(file));
    }

    public static ImageAsset getImageAsset(File file) {
        var asset = imageAssets.get(file);
        if (asset == null) {
            addImageAsset(file);
            return imageAssets.get(file);
        }
        return asset;
    }

    public static void removeImageAsset(File file) {
        imageAssets.remove(file);
    }

    public static void clear() {
        imageAssets.clear();
    }
}