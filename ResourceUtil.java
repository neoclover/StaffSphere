package Employee_Management;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.ImageIcon;

public class ResourceUtil {
    public static ImageIcon loadIcon(String resourcePath) {
        URL url = findResource(resourcePath);
        if (url != null) {
            return new ImageIcon(url);
        }

        BufferedImage placeholder = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        return new ImageIcon(placeholder);
    }

    public static Image loadScaledImage(String resourcePath, int width, int height) {
        ImageIcon icon = loadIcon(resourcePath);
        if (icon.getImage() != null) {
            return icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        }
        return null;
    }

    private static URL findResource(String resourcePath) {
        ClassLoader loader = ResourceUtil.class.getClassLoader();
        URL resource = loader.getResource(resourcePath);
        if (resource != null) {
            return resource;
        }

        String normalized = resourcePath.replace('\\', '/');
        String fileName = normalized.substring(normalized.lastIndexOf('/') + 1);

        Path projectRoot = Paths.get("").toAbsolutePath();
        Path srcRoot = projectRoot.resolve("src").resolve("Employee_Management");

        Path[] candidateDirs = {
            srcRoot.resolve("icon"),
            srcRoot.resolve("Icon")
        };

        for (Path dir : candidateDirs) {
            Path candidate = dir.resolve(fileName);
            if (Files.exists(candidate)) {
                try {
                    return candidate.toUri().toURL();
                } catch (IOException ignored) {
                }
            }
        }

        return null;
    }
}
