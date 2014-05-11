import acm.gui.HPanel;
import acm.gui.VPanel;

import javax.swing.*;

import java.awt.*;

@SuppressWarnings("serial")
public class ForecastColumn extends VPanel {

    private JLabel dayLabel;
    private JLabel lowLabel;
    private JLabel highLabel;
    private JLabel conditionIcon;
    private JLabel conditionLabel;
    private JLabel windLabel;
    private JLabel windDirectionLabel;

	public ForecastColumn(int position) {
        if(position % 2 == 0) {
            setBackground(Color.WHITE);
        } else {
            setBackground(Color.LIGHT_GRAY);
        }

        Font smallFont = new Font("TAHOMA", Font.CENTER_BASELINE, 11);
		dayLabel = new JLabel();
        dayLabel.setHorizontalAlignment(JLabel.CENTER);
        dayLabel.setFont(smallFont);
		add(dayLabel);
		

        HPanel tempArea = new HPanel();
        tempArea.setOpaque(false);
        lowLabel = new JLabel();
        lowLabel.setHorizontalAlignment(JLabel.CENTER);
        lowLabel.setForeground(Color.blue);
        //lowlabel.setFont(smallFont);
        tempArea.add(lowLabel);
        tempArea.add(new JLabel("|"));
        highLabel = new JLabel();
        highLabel.setForeground(Color.red);
        //highlabel.setFont(smallFont);
        tempArea.add(highLabel);
        add(tempArea);

        conditionIcon = new JLabel();
        conditionIcon.setHorizontalAlignment(JLabel.CENTER);
        add(conditionIcon);
        conditionLabel = new JLabel();
        conditionLabel.setMaximumSize(new Dimension(60, Integer.MAX_VALUE));
        conditionLabel.setHorizontalAlignment(JLabel.CENTER);
        add(conditionLabel);

        windLabel = new JLabel();
        windLabel.setHorizontalAlignment(JLabel.CENTER);
        add(windLabel);
        windDirectionLabel = new JLabel();
        windDirectionLabel.setHorizontalAlignment(JLabel.CENTER);
        add(windDirectionLabel);
	}
	
	public void update(int position, Model model) {
		// Update the various components here.
        ForecastModel f = model.getForecast();
        dayLabel.setText(f.getDay(position));
        lowLabel.setText(""+f.getDayLow(position));
        highLabel.setText(""+f.getDayHigh(position));
        conditionIcon.setIcon(IconHelper.getIcon(f.getIconString(position)));
        conditionLabel.setText(f.getConditions(position));
        windLabel.setText(f.getWindspeed(position)+" mph");
        windDirectionLabel.setText(f.getWindDirection(position));
	}
}