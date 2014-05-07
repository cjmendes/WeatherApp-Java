import acm.graphics.GCanvas;
import acm.graphics.GOval;

import javax.swing.*;

import java.awt.*;


@SuppressWarnings("serial")
public class CurrentConditionsPanel extends JPanel implements WeatherPanel
{
	private WundergroundModel m;
	private JLabel currentTemp, highTemp, lowTemp, conditionIcon, conditionText;
	private JLabel chanceRain, feelsLike, humidity, elevation, windDir, windSpeed;
	private double cTemp, hTemp;
	private String condition;
	public CurrentConditionsPanel()
	{
		
		
        GCanvas canvas = new GCanvas();
		canvas.setPreferredSize(new Dimension(400, 200));
		this.add(canvas);

		
		
		//Will change to getters, and adjust positions and fonts
		currentTemp = new JLabel(cTemp + "F");
		canvas.add(currentTemp, 30, 40);
		
		highTemp = new JLabel( "High: " + hTemp + "F");
		canvas.add(highTemp, 110, 40);
		
		lowTemp = new JLabel("Low: " + "##F");
		canvas.add(lowTemp, 110, 70);
		
		conditionIcon = new JLabel("ICON");
		canvas.add(conditionIcon, 260, 50);
		
		conditionText = new JLabel(condition);
		canvas.add(conditionText, 260, 30);
		
		chanceRain = new JLabel("Chance of Rain: ##%");
		canvas.add(chanceRain, 40, 110);
		
		feelsLike = new JLabel("Feels Like: ##F");
		canvas.add(feelsLike, 40, 130);
		
		humidity = new JLabel("Humidity: ##%");
		canvas.add(humidity, 40, 150);
		
		elevation = new JLabel("Elevation: ##ft");
		canvas.add(elevation, 40, 170);
		
		windDir = new JLabel("Wind Direction: NNW");
		canvas.add(windDir, 260, 110);
		
		windSpeed = new JLabel("Wind Speed: ##mph");
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
		cTemp = newModel.getTemperature();
		condition = newModel.getWeather();
	
		//Have model get current day High + Low Temperature
		//double hTemp = newModel.getForecast().getDayHigh(0);
		// This method will be called when a new location has been retrieved.
	}
}