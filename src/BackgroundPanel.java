import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BackgroundPanel extends JPanel
{

    Image bg;

    public BackgroundPanel()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        try
        {
            bg = ImageIO.read(new File("assets/bg/Background.png"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics graphics)
    {
        graphics.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
        super.paint(graphics);
    }
}
