import acm.graphics.GCanvas;

import javax.swing.*;

import java.awt.*;


@SuppressWarnings("serial")
public class CurrentConditionsPanel extends JPanel implements WeatherPanel
{
	
	private JLabel currentTemp, highTemp, lowTemp, conditionIcon, conditionText;
	private JLabel precipPercent, feelsLike, humidity, elevation, windDir, windSpeed;
	public CurrentConditionsPanel()
	{
		
		
        GCanvas canvas = new GCanvas();
		canvas.setPreferredSize(new Dimension(WeatherPanel.WIDTH, WeatherPanel.HEIGHT));
		this.add(canvas);
		
/*
		
		try {
            //create the font to use. Specify the size
     
			
            Font CTFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/Fonts/Quicksand_Bold_Oblique.otf")).deriveFont(70f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("assets/Fonts/Quicksand_Bold_Oblique.otf")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(FontFormatException e)
        {
            e.printStackTrace();
        }
         //use the font
         canvas.setFont(CTFont);
       	
		*/

		Font conditionFont = new Font("VERDANA", Font.LAYOUT_LEFT_TO_RIGHT, 70);
		Font smallFont = new Font("TAHOMA", Font.CENTER_BASELINE, 11);

		
		currentTemp = new JLabel("Current Temp");
		currentTemp.setFont(conditionFont);
		currentTemp.setForeground(Color.white);
		canvas.setBackground(Color.darkGray);
		canvas.add(currentTemp, 30, 10);
		
		precipPercent = new JLabel("Precipitation: ###.# in");
		precipPercent.setFont(smallFont);
		precipPercent.setForeground(Color.white);
		canvas.add(precipPercent, 40, 110);
		
		feelsLike = new JLabel("Feels Like: ###.#\u00B0F");
		feelsLike.setFont(smallFont);
		feelsLike.setForeground(Color.white);
		canvas.add(feelsLike, 45, 130);
		
		humidity = new JLabel("Humidity: ###%");
		humidity.setFont(smallFont);
		humidity.setForeground(Color.white);
		canvas.add(humidity, 50, 150);
		
		elevation = new JLabel("Elevation: #######ft");
		elevation.setFont(smallFont);
		elevation.setForeground(Color.white);
		canvas.add(elevation, 47, 170);
		
		highTemp = new JLabel( "High: ###.#\u00B0F");
		highTemp.setFont(smallFont);
		highTemp.setForeground(Color.white);
		canvas.add(highTemp, 280, 10);
		
		lowTemp = new JLabel("Low: ###.#\u00B0F");
		lowTemp.setFont(smallFont);
		lowTemp.setForeground(Color.white);
		canvas.add(lowTemp, 380, 10);
		
		conditionIcon = new JLabel("ICON");
		conditionIcon.setForeground(Color.white);
		conditionIcon.setPreferredSize(new Dimension(109, 75));
		canvas.add(conditionIcon, 320, 40);
		
		conditionText = new JLabel( "Something went very wrong here!" );
		conditionText.setFont(smallFont);
		conditionText.setForeground(Color.white);
		canvas.add(conditionText, 350, 120);
		
		windDir = new JLabel("Wind Direction: ToTheUniverseAndBeyond");
		windDir.setFont(smallFont);
		windDir.setForeground(Color.white);
		canvas.add(windDir, 300, 140);
		
		windSpeed = new JLabel("Wind Speed: ###.#mph");
		windSpeed.setFont(smallFont);
		windSpeed.setForeground(Color.white);
		canvas.add(windSpeed, 300, 165);
		
	}


	@Override
	public void onLocationChanged(Model newModel) {
		// This method will be called when a new location has been retrieved.
		
		windSpeed.setText("Wind Speed: " + newModel.getWindSpeed() + "mph");
		
		double x = newModel.getTemperature();
		int xInt = (int)x;
		currentTemp.setText( xInt + "\u00B0F");
		
		
		conditionIcon.setIcon(IconHelper.getIcon(newModel.getIconText()+ "1"));
		
		conditionText.setText(newModel.getWeather());
		
		highTemp.setText("High: " + newModel.getDHigh(0) + "\u00B0F");
		
		lowTemp.setText("Low: " + newModel.getDLow(0) + "\u00B0F");
		
		elevation.setText("Elevation: " + newModel.getElevation());
		
		humidity.setText("Humidity: " + newModel.getHumidity());
		
		precipPercent.setText("Precipitation: " + newModel.getPrecipitation() + " in");
		
		feelsLike.setText("Feels Like: " + newModel.getFeelsLike() + "\u00B0F");
		
		windDir.setText("Wind Direction: " + newModel.getWindDir());
		
	}
}