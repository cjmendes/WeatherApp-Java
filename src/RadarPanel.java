import acm.graphics.GCanvas;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class RadarPanel extends JPanel implements WeatherPanel {

	private JLabel radarLabel; 
	
	public RadarPanel() {
		GCanvas radarCanvas = new GCanvas();
		radarCanvas.setPreferredSize(new Dimension(WeatherPanel.WIDTH, WeatherPanel.HEIGHT));
		this.add(radarCanvas);
		radarCanvas.setBackground(Color.darkGray);
		
		
		radarLabel = new JLabel();
        radarLabel.setPreferredSize(radarCanvas.getPreferredSize());

		radarCanvas.add(radarLabel, 0, 0);
		radarCanvas.setVisible(true);
	}

	@Override
	public void onLocationChanged(Model newModel) {
		
		radarLabel.setIcon(new ImageIcon(newModel.getRadar()));
			

			
			
			
	}

}
