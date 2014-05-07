import acm.gui.VPanel;

import javax.swing.*;

@SuppressWarnings("serial")
public class ForecastRow extends VPanel {
		
	private JLabel dayLabel;
	
	public ForecastRow() {
		dayLabel = new JLabel();
		add(dayLabel);

	}
	
	public void update(int position, Model model) {
		// Update the various components here.
        dayLabel.setText(model.getForecast().getDay(position));
	}		
}