import acm.graphics.GCanvas;
import acm.graphics.GOval;

import javax.swing.*;

import java.awt.*;


@SuppressWarnings("serial")
public class CurrentConditionsPanel extends JPanel implements WeatherPanel
{
	private MockModel m;
	//private WundergroundModel m;
	
	public CurrentConditionsPanel()
	{
		
        m = new MockModel("95621");
            
        /* 
    	 * When we want live data comment above and un-comment below
        try
        {
            m = new WundergroundModel("95621");
        } catch (WundergroundModel.WundergroundException e)
        {
        }*/


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
		
		JButton refreshButton = new JButton("Refresh");
	    canvas.add(refreshButton, 300, 170);
	}


	@Override
	public void onLocationChanged(Model newModel) {
		// This method will be called when a new location has been retrieved.
	}
}
