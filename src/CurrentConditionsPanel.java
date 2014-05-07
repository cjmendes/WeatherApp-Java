import acm.graphics.GCanvas;
import acm.graphics.GOval;

import javax.swing.*;

import java.awt.*;


@SuppressWarnings("serial")
public class CurrentConditionsPanel extends JPanel implements WeatherPanel
{
    private final JLabel weather;
    private final JLabel speed;

	public CurrentConditionsPanel()
	{
        GCanvas canvas = new GCanvas();
		canvas.setPreferredSize(new Dimension(500, 200));
		this.add(canvas);
		
		GOval sun = new GOval(50, 50);
		sun.setFilled(true);
		sun.setColor(Color.YELLOW);
		canvas.add(sun, 100, 50);

        weather = new JLabel("FAKEFAKEFAKEFAKEFAKE");
		Font f = new Font("Helvetica", Font.BOLD, 24);
		weather.setFont(f);
		canvas.add(weather, 175, 50);

        speed = new JLabel("Wind speed: FAKEFAKEFAKEFAKEFAKE");
		speed.setFont(f);
		canvas.add(speed, 175, 80);

        //TODO: this should probably move to the GUI
		JButton refreshButton = new JButton("Refresh");
	    canvas.add(refreshButton, 300, 170);
	}


	@Override
	public void onLocationChanged(Model newModel) {
		weather.setText(newModel.getWeather());
        speed.setText("Wind speed: "+newModel.getWindSpeed());
	}
}
