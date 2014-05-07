import java.net.URL;
import java.awt.*;

import javax.swing.*;

import acm.graphics.GCanvas;

@SuppressWarnings("serial")
public class RadarPanel extends JPanel implements WeatherPanel {

	private URL radarURL = null;

	public RadarPanel() {
		GCanvas canvas = new GCanvas();
		canvas.setPreferredSize(new Dimension(500, 200));
		this.add(canvas);

		//This needs to get the URL from the WundergroundModel
		try {
			radarURL = new URL(
					"http://api.wunderground.com/api/0757ee8cfe589f60/animatedradar/q/95678.gif?width=500&height=200&newmaps=1&timelabel=1&timelabel.y=10&num=15&delay=25");
		} catch (java.net.MalformedURLException mue) {
			mue.printStackTrace();
		}

		Icon icon = new ImageIcon(radarURL);
		JLabel radarLabel = new JLabel(icon);

		canvas.add(radarLabel, 0, 0);
		canvas.setVisible(true);

	}

	@Override
	public void onLocationChanged(Model newModel) {
		// This method will be called when a new location has been retrieved.
	}

	/*
	 * You need to get the URL from WundergroundModel getRadar() This Should
	 * work...
	 */

}
