import javax.swing.JPanel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.net.URL;


@SuppressWarnings("serial")
public class RadarPanel extends JPanel implements WeatherPanel {

	private WundergroundModel m;
	@Override
	public void onLocationChanged(Model newModel) {
		// This method will be called when a new location has been retrieved.
	}
	
	/*
	 * You need to get the URL from WundergroundModel getRadar()
	 * This Should work...
	 *
	public void showRadar()
	{
	    URL radarURL = m.getRadar();
		Icon icon = new ImageIcon(radarURL);
	    JLabel label = new JLabel(icon);
	    JFrame rad = new JFrame("Radar");
	    rad.getContentPane().add(label);
	    rad.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    rad.pack();
	    rad.setLocationRelativeTo(null);
	    rad.setVisible(true);
	}*/

}
