import java.net.URL;
import java.awt.*;

import javax.swing.*;

import acm.graphics.GCanvas;

@SuppressWarnings("serial")
public class RadarPanel extends JPanel implements WeatherPanel {

	private URL radarURL = null;
	private JLabel radarLabel; 
	
	public RadarPanel() {
		GCanvas canvas = new GCanvas();
		canvas.setPreferredSize(new Dimension(500, 200));
		this.add(canvas);

		
			
		
		
		radarLabel = new JLabel();

		canvas.add(radarLabel, 0, 0);
		canvas.setVisible(true);

	}

	@Override
	public void onLocationChanged(Model newModel) {
		
		radarLabel.setIcon(new ImageIcon(newModel.getRadar()));
			

			
			
			
	}
	/*
	 * You need to get the URL from WundergroundModel getRadar() This Should
	 * work...
	 */

}
