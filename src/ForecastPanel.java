import javax.swing.*;
import java.awt.*;


@SuppressWarnings("serial")
public class ForecastPanel extends JPanel implements WeatherPanel {



	private static final int NUM_COLS = 7;

	private ForecastColumn[] days = new ForecastColumn[NUM_COLS];

	public ForecastPanel() {
        setOpaque(false);
        BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
//        layout.
        this.setLayout(layout);
		for(int i = 0; i < NUM_COLS; i++) {
			days[i] = new ForecastColumn(i);
            days[i].setPreferredSize(new Dimension(WeatherPanel.WIDTH/7, WeatherPanel.HEIGHT));
            days[i].setMaximumSize(new Dimension(WeatherPanel.WIDTH / 7, WeatherPanel.HEIGHT));
			add(days[i]);
		}

	}

    @Override
	public void onLocationChanged(Model newModel) {
		for(int i = 0; i < NUM_COLS; i++) {
			days[i].update(i, newModel);
			//TODO:
			//days[i].updateLunarInfo(i, newModel);
		}
	}
}
