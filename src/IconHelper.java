import javax.swing.*;
import java.io.File;
import java.util.HashMap;

public class IconHelper
{
    private static HashMap<String, Icon> icons = new HashMap<String, Icon>();

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
}
