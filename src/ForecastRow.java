import acm.gui.HPanel;
import acm.gui.VPanel;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class ForecastRow extends VPanel {

    private JLabel dayLabel;
    private JLabel lowLabel;
    private JLabel highLabel;
    private JLabel conditionIcon;

	public ForecastRow(int position) {
        if(position % 2 == 0) {
            setBackground(Color.WHITE);
        } else {
            setBackground(Color.LIGHT_GRAY);
        }

		dayLabel = new JLabel();
        dayLabel.setHorizontalAlignment(JLabel.CENTER);
		add(dayLabel);

        HPanel tempArea = new HPanel();
        tempArea.setOpaque(false);
        lowLabel = new JLabel();
        lowLabel.setHorizontalAlignment(JLabel.CENTER);
        lowLabel.setForeground(Color.blue);
        tempArea.add(lowLabel);
        tempArea.add(new JLabel("|"));
        highLabel = new JLabel();
        highLabel.setHorizontalAlignment(JLabel.CENTER);
        highLabel.setForeground(Color.red);
        tempArea.add(highLabel);
        add(tempArea);

        conditionIcon = new JLabel();
        conditionIcon.setHorizontalAlignment(JLabel.CENTER);
        add(conditionIcon);
	}
	
	public void update(int position, Model model) {
		// Update the various components here.
        ForecastModel f = model.getForecast();
        dayLabel.setText(f.getDay(position));
        lowLabel.setText(""+f.getDayLow(position));
        highLabel.setText(""+f.getDayHigh(position));
        conditionIcon.setIcon(IconHelper.getIcon(f.getIconString(position)));
	}
}