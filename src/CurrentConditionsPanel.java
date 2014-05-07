import acm.graphics.GCanvas;
import acm.graphics.GOval;

import javax.swing.*;

import java.awt.*;


@SuppressWarnings("serial")
public class CurrentConditionsPanel extends JPanel implements WeatherPanel
{
	private WundergroundModel m;
	public CurrentConditionsPanel()
	{
		
		/* 
		 * Change MockModel back to WundergroundModel when we want live data
		 * and un-comment the try block
		 */
        try
        {
            m = new WundergroundModel("95621");
        } catch (WundergroundModel.WundergroundException e)
        {
        }


        GCanvas canvas = new GCanvas();
		canvas.setPreferredSize(new Dimension(400, 200));
		this.add(canvas);
		
		/*GOval sun = new GOval(50, 50);
		sun.setFilled(true);
		sun.setColor(Color.YELLOW);
		canvas.add(sun, 100, 50);*/
		
		
		//assign variables for data from model
		double cTemp = m.getTemperature();
		//double hTemp = m.getHighTemperature();
		//double lTemp = m.getLowTemperature();
		String condition = m.getWeather();
		//
		//
		
		
		//Will change to getters, and adjust positions and fonts
		JLabel currentTemp = new JLabel(cTemp + "F");
		canvas.add(currentTemp, 30, 40);
		
		JLabel highTemp = new JLabel("High ##F");
		canvas.add(highTemp, 110, 40);
		
		JLabel lowTemp = new JLabel("Low ##F");
		canvas.add(lowTemp, 110, 70);
		
		JLabel conditionIcon = new JLabel("ICON");
		canvas.add(conditionIcon, 260, 50);
		
		JLabel conditionText = new JLabel(condition);
		canvas.add(conditionText, 260, 30);
		
		JLabel chanceRain = new JLabel("Chance of Rain ##%");
		canvas.add(chanceRain, 40, 110);
		
		JLabel feelsLike = new JLabel("Feels Like: ##F");
		canvas.add(feelsLike, 40, 130);
		
		JLabel humidity = new JLabel("Humidity: ##%");
		canvas.add(humidity, 40, 150);
		
		JLabel elevation = new JLabel("Elevation: ##ft");
		canvas.add(elevation, 40, 170);
		
		JLabel windDir = new JLabel("Wind Direction: NNW");
		canvas.add(windDir, 260, 110);
		
		JLabel windSpeed = new JLabel("Wind Speed: ##mph");
		canvas.add(windSpeed, 260, 150);
		
		
		
		/*JLabel sunny = new JLabel(m.getWeather());
		Font f = new Font("Helvetica", Font.BOLD, 24);
		sunny.setFont(f);
		canvas.add(sunny, 175, 50);
		
		JLabel speed = new JLabel("Wind speed: " + m.getWindSpeed());
		speed.setFont(f);
		canvas.add(speed, 175, 80);*/
		
		JButton refreshButton = new JButton("Refresh");
	    canvas.add(refreshButton, 300, 170);
	}


	@Override
	public void onLocationChanged(Model newModel) {
		// This method will be called when a new location has been retrieved.
	}
}