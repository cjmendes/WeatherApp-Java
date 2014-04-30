import javax.swing.JPanel;


@SuppressWarnings("serial")
public abstract class WeatherPanel extends JPanel {
	public abstract void onLocationChanged(Model newModel);
}
