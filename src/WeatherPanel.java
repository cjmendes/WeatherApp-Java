import javax.swing.JPanel;


public abstract class WeatherPanel extends JPanel {
	public abstract void onLocationChanged(Model newModel);
}
