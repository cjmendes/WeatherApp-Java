import javax.swing.*;
import java.awt.*;


@SuppressWarnings("serial")
public class ForecastPanel extends JPanel implements WeatherPanel {
	
	
	
	private static final int NUM_COLS = 7;
	
	private ForecastRow[] days = new ForecastRow[NUM_COLS];
	
	public ForecastPanel() {
		for(int i = 0; i < NUM_COLS; i++) {
			days[i] = new ForecastRow(i);
			add(days[i]);
		}
		
		setBackground(Color.darkGray);
	}

	@Override
	public void onLocationChanged(Model newModel) {
		for(int i = 0; i < NUM_COLS; i++) {
			days[i].update(i, newModel);
		}
	}
}
