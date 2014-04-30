import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import acm.graphics.GCanvas;
import acm.graphics.GOval;


public class CurrentConditions extends WeatherPanel
{
	private Model m;
	public CurrentConditions()
	{
		m = new Model("95621");
		
		
		GCanvas canvas = new GCanvas();
		canvas.setPreferredSize(new Dimension(400, 200));
		this.add(canvas);
		
		GOval sun = new GOval(50, 50);
		sun.setFilled(true);
		sun.setColor(Color.YELLOW);
		canvas.add(sun, 100, 50);
		
		JLabel sunny = new JLabel(m.getWeather());
		Font f = new Font("Helvetica", Font.BOLD, 24);
		sunny.setFont(f);
		canvas.add(sunny, 175, 50);
		
		JLabel speed = new JLabel("Wind speed: " + m.getWindSpeed());
		speed.setFont(f);
		canvas.add(speed, 175, 80);
	}

	@Override
	public void onLocationChanged(Model newModel) {
		// This method will be called when a new location has been retrieved.
	}
}
