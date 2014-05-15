import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class IconHelper
{
    private static HashMap<String, ImageIcon> icons = new HashMap<String, ImageIcon>();

    // Loop through every icon asset and add it to the hash map
    // TODO: add second hashmap for night time; check and default to day for duplicates
    static {
        File[] assets = new File("assets/icons_day/").listFiles();
        for(File f : assets) {
            icons.put(f.getName().substring(0,f.getName().indexOf(".")), new ImageIcon(f.getPath()));
        }
    }

    public static Icon getIcon(String name) {
        return icons.get(name);
    }

    public static Icon getIcon(String name, int width, int height) {
        Image img = icons.get(name).getImage();
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        g.drawImage(img, 0, 0, width, height, null);

        return new ImageIcon(bi);
    }
}
