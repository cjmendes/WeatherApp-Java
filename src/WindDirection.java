import javax.swing.*;
import java.awt.*;

public class WindDirection extends JPanel
{
    private double rotation;
    private final BasicStroke stroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);;

    public void setAngle(int degrees)
    {
        rotation = Math.toRadians(degrees - 90); // -90 because 0 degrees is North
        invalidate(); // forces re-draw
    }

    @Override
    public void paint(Graphics g)
    {
        // The object passed into paint() is actually a Graphics2D which has more functionality.
        // Double check this, and cast it
        if(!(g instanceof Graphics2D)) return;
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        g2d.setStroke(stroke);

        // Turn on anti-aliasing to make it less jagged
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Figure out how big the circle can be
        int circleSize = (width < height) ? width : height;// shorthand for: if(width < height) { circleSize = width } else { circlesize = height }.
        // Subtract width of stroke so it doesnt get clipped
        circleSize -= stroke.getLineWidth()/2;

        // Move to the center
        g2d.translate(width / 2, height / 2);
        // Draw our circle
        g2d.drawOval(-circleSize / 2, -circleSize / 2, circleSize, circleSize);
        // Rotate our perspective of the canvas
        g2d.rotate(rotation);
        // Draw the arm of the arrow
        g2d.drawLine(0, 0, circleSize/2, 0);
        // Draw the head of the arrow as a polygon. First array is list of x coords, second is list of y coords
        g2d.fillPolygon(new int[]{circleSize / 4, circleSize / 2, circleSize / 4}, new int[]{-circleSize / 6, 0, circleSize / 6}, 3);
    }
}
