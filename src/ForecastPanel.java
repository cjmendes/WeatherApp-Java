import acm.gui.HPanel;

import java.awt.*;


@SuppressWarnings("serial")
public class ForecastPanel extends HPanel implements WeatherPanel {
	
	
	
	private static final int NUM_COLS = 7;
	
	private ForecastColumn[] days = new ForecastColumn[NUM_COLS];
	
	public ForecastPanel() {
        super(0, 0);

		for(int i = 0; i < NUM_COLS; i++) {
			days[i] = new ForecastColumn(i);
            days[i].setPreferredSize(new Dimension(WeatherPanel.WIDTH/7, WeatherPanel.HEIGHT));
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
