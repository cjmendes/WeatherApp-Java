import javax.swing.JLabel;

import acm.gui.VPanel;

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