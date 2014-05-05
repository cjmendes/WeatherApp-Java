import javax.swing.JPanel;


@SuppressWarnings("serial")
public class RadarPanel extends JPanel implements WeatherPanel {

	@Override
	public void onLocationChanged(Model newModel) {
		// This method will be called when a new location has been retrieved.
	}

}
