import acm.graphics.GCanvas;

import javax.swing.*;

import java.awt.*;


@SuppressWarnings("serial")
public class CurrentConditionsPanel extends JPanel implements WeatherPanel
{
	
	private JLabel currentTemp, highTemp, lowTemp, conditionIcon, conditionText;
	private JLabel precipPercent, feelsLike, humidity, elevation, windDir, windSpeed;
	//Assigns current temp to variable cT so that it may be converted to an int
	public CurrentConditionsPanel()
	{
		
		
        GCanvas canvas = new GCanvas();
		canvas.setPreferredSize(new Dimension(WeatherPanel.WIDTH, WeatherPanel.HEIGHT));
		this.add(canvas);

		Font conditionFont = new Font("VERDANA", Font.LAYOUT_LEFT_TO_RIGHT, 51);
		Font smallFont = new Font("TAHOMA", Font.CENTER_BASELINE, 11);
		//DecimalFormat currentCFormat = new DecimalFormat( "##0" );
		//cTI = (int) cT;

		
		
		//Will change to getters, and adjust positions and fonts
		currentTemp = new JLabel("Current Temp");
		currentTemp.setFont(conditionFont);
		currentTemp.setForeground(Color.white);
		canvas.setBackground(Color.darkGray);
		canvas.add(currentTemp, 10, 30);
		
		precipPercent = new JLabel("Chance of Rain: ##.# in");
		precipPercent.setFont(smallFont);
		precipPercent.setForeground(Color.white);
		canvas.add(precipPercent, 40, 110);
		
		feelsLike = new JLabel("Feels Like: ##.#°F");
		feelsLike.setFont(smallFont);
		feelsLike.setForeground(Color.white);
		canvas.add(feelsLike, 40, 130);
		
		humidity = new JLabel("Humidity: ##%");
		humidity.setFont(smallFont);
		humidity.setForeground(Color.white);
		canvas.add(humidity, 40, 150);
		
		elevation = new JLabel("Elevation: #######ft");
		elevation.setFont(smallFont);
		elevation.setForeground(Color.white);
		canvas.add(elevation, 40, 170);
		
		highTemp = new JLabel( "High: ###.#°F");
		highTemp.setFont(smallFont);
		highTemp.setForeground(Color.white);
		canvas.add(highTemp, 260, 10);
		
		lowTemp = new JLabel("Low: ###.#°F");
		lowTemp.setFont(smallFont);
		lowTemp.setForeground(Color.white);
		canvas.add(lowTemp, 260, 30);
		
		conditionIcon = new JLabel("ICON");
		conditionIcon.setForeground(Color.white);
		conditionIcon.setPreferredSize(new Dimension(109, 75));
		canvas.add(conditionIcon, 260, 50);
		
		conditionText = new JLabel( "Partly Cloudy" );
		conditionText.setFont(smallFont);
		conditionText.setForeground(Color.white);
		canvas.add(conditionText, 260, 130);
		
		windDir = new JLabel("Wind Direction: South");
		windDir.setFont(smallFont);
		windDir.setForeground(Color.white);
		canvas.add(windDir, 260, 150);
		
		windSpeed = new JLabel("Wind Speed: #.#mph");
		windSpeed.setFont(smallFont);
		windSpeed.setForeground(Color.white);
		canvas.add(windSpeed, 260, 170);
		
	}


	@Override
	public void onLocationChanged(Model newModel) {
		// This method will be called when a new location has been retrieved.
		
		windSpeed.setText("Wind Speed: " + newModel.getWindSpeed() + "mph");
		
		currentTemp.setText(newModel.getTemperature() + "°F");
		
		conditionIcon.setIcon(IconHelper.getIcon(newModel.getIconText()+ "1"));
		
		conditionText.setText(newModel.getWeather());
		
		highTemp.setText("High: " + newModel.getDHigh(0) + "°F");
		
		lowTemp.setText("Low: " + newModel.getDLow(0) + "°F");
		
		elevation.setText("Elevation: " + newModel.getElevation());
		
		humidity.setText("Humidity: " + newModel.getHumidity());
		
		precipPercent.setText("Precipitation: " + newModel.getPrecipitation() + " in");
		
		feelsLike.setText("Feels Like: " + newModel.getFeelsLike() + "°F");
		
		windDir.setText("Wind Direction: " + newModel.getWindDir());
		
	}
}