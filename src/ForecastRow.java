import acm.gui.VPanel;

import javax.swing.*;

@SuppressWarnings("serial")
public class ForecastRow extends VPanel {
		
	private JLabel dayLabel;
	
	public ForecastRow(int position) {
		dayLabel = new JLabel();
		dayLabel.setText("Day "+position);
		
		add(dayLabel);
	}
	
	public void update(int position, Model model) {
		// Update the various components here.
	}		
}