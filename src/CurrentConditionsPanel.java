import acm.graphics.GCanvas;

import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;


@SuppressWarnings("serial")
public class CurrentConditionsPanel extends JPanel implements WeatherPanel
{
	
	private JLabel currentTemp, highTemp, lowTemp, conditionIcon, conditionText;
	private JLabel precipPercent, feelsLike, humidity, elevation, windDir, windSpeed;

	private Font CTFont, CT2Font;

	GCanvas canvas;
	
	//private Model model;

	public int lunarNum = 0;

	public CurrentConditionsPanel()
	{
		setOpaque(false);
		
        canvas = new GCanvas();
        canvas.setOpaque(false);
		canvas.setPreferredSize(new Dimension(WeatherPanel.WIDTH, WeatherPanel.HEIGHT));
		this.add(canvas);
		try {
            //create the font to use. Specify the size
			CTFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/Fonts/Quicksand_Light.otf")).deriveFont(70f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("assets/Fonts/Quicksand_Light.otf")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(FontFormatException e){
            e.printStackTrace();
        }
        try {
            //create the font to use. Specify the size
        	CT2Font = Font.createFont(Font.TRUETYPE_FONT, new File("assets/Fonts/Roboto-Medium.ttf")).deriveFont(15f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("assets/Fonts/Roboto-Medium.ttf")));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        catch(FontFormatException e1){
            e1.printStackTrace();   
        }
        
		currentTemp = new JLabel("Current Temp");
		currentTemp.setFont(CTFont);
		currentTemp.setForeground(Color.white);
		canvas.add(currentTemp, 30, 10);
		
		precipPercent = new JLabel("Precipitation: ###.# in");
		precipPercent.setFont(CT2Font);
		precipPercent.setForeground(Color.white);
		canvas.add(precipPercent, 40, 90);
		
		feelsLike = new JLabel("Feels Like: ###.#\u00B0F");
		feelsLike.setFont(CT2Font);
		feelsLike.setForeground(Color.white);
		canvas.add(feelsLike, 45, 110);
		
		humidity = new JLabel("Humidity: ###%");
		humidity.setFont(CT2Font);
		humidity.setForeground(Color.white);
		canvas.add(humidity, 50, 130);
		
		elevation = new JLabel("Elevation: #######ft");
		elevation.setFont(CT2Font);
		elevation.setForeground(Color.white);
		canvas.add(elevation, 47, 150);
		
		highTemp = new JLabel( "High: ###.#\u00B0F");
		highTemp.setFont(CT2Font);
		highTemp.setForeground(Color.white);
		canvas.add(highTemp, 290, 10);
		
		lowTemp = new JLabel("Low: ###.#\u00B0F");
		lowTemp.setFont(CT2Font);
		lowTemp.setForeground(Color.white);
		canvas.add(lowTemp, 390, 10);
		
		conditionIcon = new JLabel("");
		conditionIcon.setForeground(Color.white);
		conditionIcon.setPreferredSize(new Dimension(109, 75));
		canvas.add(conditionIcon, 350, 33);
		
		conditionText = new JLabel( "Something went very wrong here!" );
		conditionText.setFont(CT2Font);
		conditionText.setForeground(Color.white);
		canvas.add(conditionText, 370, 110);
		
		windDir = new JLabel("Wind Direction: ToTheUniverseAndBeyond");
		windDir.setFont(CT2Font);
		windDir.setForeground(Color.white);
		canvas.add(windDir, 310, 130);
		
		windSpeed = new JLabel("Wind Speed: ###.#mph");
		windSpeed.setFont(CT2Font);
		windSpeed.setForeground(Color.white);
		canvas.add(windSpeed, 310, 150);
		
	}


	@Override
	public void onLocationChanged(Model newModel) {
		// This method will be called when a new location has been retrieved.
		
		windSpeed.setText("Wind Speed: " + newModel.getWindSpeed() + "mph");
		
		double x = newModel.getTemperature();
		int xInt = (int)x;
		currentTemp.setText( xInt + "\u00B0F");
		
		conditionIcon.setIcon(IconHelper.getIcon(newModel.getIconText()));
		
		changeLunarInfo(newModel);
		
		conditionText.setText(newModel.getWeather());
		
		highTemp.setText("High: " + newModel.getDHigh(0) + "\u00B0F");
		
		lowTemp.setText("Low: " + newModel.getDLow(0) + "\u00B0F");
		
		elevation.setText("Elevation: " + newModel.getElevation());
		
		humidity.setText("Humidity: " + newModel.getHumidity());
		
		precipPercent.setText("Precipitation: " + newModel.getPrecipitation() + " in");
		
		feelsLike.setText("Feels Like: " + newModel.getFeelsLike() + "\u00B0F");
		
		windDir.setText("Wind Direction: " + newModel.getWindDir());
		

        // Magic view fixer
        for(int i = 0; i < canvas.getComponentCount(); i++) {
            canvas.getComponent(i).setSize(canvas.getComponent(i).getPreferredSize());
        }
	}
	
	public void changeLunarInfo(Model newModel){
		if (lunarNum == 0)
		{
			conditionIcon.setIcon(IconHelper.getIcon(newModel.getIconText()));
		}
		else
		{
			conditionIcon.setIcon(IconHelper.getIcon(newModel.getMoonPhase(0)));
		}
	}
}