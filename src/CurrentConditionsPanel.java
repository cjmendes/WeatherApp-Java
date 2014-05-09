import acm.graphics.GCanvas;
import acm.graphics.GOval;

import javax.swing.*;

import java.awt.*;
import java.text.DecimalFormat;


@SuppressWarnings("serial")
public class CurrentConditionsPanel extends JPanel implements WeatherPanel
{
	private WundergroundModel m;
	private JLabel currentTemp, highTemp, lowTemp, conditionIcon, conditionText;
	private JLabel precipPercent, feelsLike, humidity, elevation, windDir, windSpeed;
	//Assigns current temp to variable cT so that it may be converted to an int
	private double cT;
	//private int cTI;
	public CurrentConditionsPanel()
	{
		
		
        GCanvas canvas = new GCanvas();
		canvas.setPreferredSize(new Dimension(500, 200));
		this.add(canvas);

		Font conditionFont = new Font("Helvetica", Font.BOLD, 80);
		//DecimalFormat currentCFormat = new DecimalFormat( "##0" );
		//cTI = (int) cT;

		
		
		//Will change to getters, and adjust positions and fonts
		currentTemp = new JLabel("Current Temp");
		currentTemp.setFont(conditionFont);
		currentTemp.setForeground(Color.lightGray);
		canvas.add(currentTemp, 10, 10);
		
		highTemp = new JLabel( "High: ####F");
		canvas.add(highTemp, 110, 40);
		
		lowTemp = new JLabel("Low: ####F");
		canvas.add(lowTemp, 110, 70);
		
		conditionIcon = new JLabel("ICON");
		canvas.add(conditionIcon, 260, 50);
		
		conditionText = new JLabel( "Partly Cloudy" );
		canvas.add(conditionText, 260, 30);
		
		precipPercent = new JLabel("Chance of Rain: ##%");
		canvas.add(precipPercent, 40, 110);
		
		feelsLike = new JLabel("Feels Like: ##F");
		canvas.add(feelsLike, 40, 130);
		
		humidity = new JLabel("Humidity: ##%");
		canvas.add(humidity, 40, 150);
		
		elevation = new JLabel("Elevation: ##ft");
		canvas.add(elevation, 40, 170);
		
		windDir = new JLabel("Wind Direction: NNW");
		canvas.add(windDir, 260, 110);
		
		windSpeed = new JLabel("Wind Speed: " + windSpeed + "mph");
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
		conditionText.setText(newModel.getWeather());
		
		windSpeed.setText("Wind Speed: " + newModel.getWindSpeed() + "mph");
		
		cT = newModel.getTemperature();
		currentTemp.setText( cT + "");
		
		conditionText.setText(newModel.getIconText());
		
		elevation.setText("Elevation: " + newModel.getElevation() + "ft");
		
		humidity.setText("Humidity: " + newModel.getHumidity() + "%");
		
		precipPercent.setText("Precipitation: " + newModel.getPrecipitation() + "%");
		
		feelsLike.setText("Feels Like: " + newModel.getFeelsLike() + "");
		
		windDir.setText(newModel.getWindDir());
		
	}
}