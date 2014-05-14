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
    private WindDirection windDirection;

	public ForecastColumn(int position) {
        if(position % 2 == 0) {
            setBackground(Color.WHITE);
        } else {
            setBackground(Color.LIGHT_GRAY);
        }

        Font smallFont = new Font("TAHOMA", Font.BOLD, 11);
		dayLabel = new JLabel();
        dayLabel.setHorizontalAlignment(JLabel.CENTER);
        dayLabel.setFont(smallFont);
		add(dayLabel);
		

        HPanel tempArea = new HPanel();
        tempArea.setOpaque(false);
        lowLabel = new JLabel();
        lowLabel.setHorizontalAlignment(JLabel.RIGHT);
        lowLabel.setForeground(Color.blue);
        lowLabel.setFont(smallFont);
        tempArea.add(lowLabel);
        tempArea.add(new JLabel("  |"));
        highLabel = new JLabel();
        highLabel.setForeground(Color.red);
        highLabel.setFont(smallFont);
        highLabel.setHorizontalAlignment(JLabel.LEFT);
        tempArea.add(highLabel);
        add(tempArea);

        conditionIcon = new JLabel();
        conditionIcon.setHorizontalAlignment(JLabel.CENTER);
        add(conditionIcon);
        /*
        conditionLabel = new JLabel();
        conditionLabel.setMaximumSize(new Dimension(60, Integer.MAX_VALUE));
        conditionLabel.setHorizontalAlignment(JLabel.CENTER);
        conditionLabel.setFont(smallFont);
        add(conditionLabel);
        */

        windLabel = new JLabel();
        windLabel.setHorizontalAlignment(JLabel.CENTER);
        windLabel.setFont(smallFont);
        add(windLabel);
        windDirection = new WindDirection();
        windDirection.setPreferredSize(new Dimension(30, 30));
        add(windDirection);
	}
	
	public void update(int position, Model model) {
		// Update the various components here.
        dayLabel.setText(model.getDay(position));
        lowLabel.setText("" + model.getDayLow(position));
        highLabel.setText("" + model.getDayHigh(position));
        conditionIcon.setIcon(IconHelper.getIcon(model.getIconString(position)));
        //conditionLabel.setText(f.getConditions(position));
        windLabel.setText(model.getWindspeed(position) + " mph");
        windDirection.setAngle(model.getWindDirection(position));
	}
}