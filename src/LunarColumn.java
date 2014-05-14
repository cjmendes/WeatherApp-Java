import acm.gui.VPanel;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class LunarColumn extends VPanel {

    private JLabel dayLabel;
    private JLabel lunarIcon;
    private JLabel lunarLabel;

	public LunarColumn(int position) {
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
		

        lunarIcon = new JLabel();
        lunarIcon.setHorizontalAlignment(JLabel.CENTER);
        add(lunarIcon);
        lunarLabel = new JLabel();
        lunarLabel.setMaximumSize(new Dimension(60, Integer.MAX_VALUE));
        lunarLabel.setHorizontalAlignment(JLabel.CENTER);
        lunarLabel.setFont(smallFont);
        add(lunarLabel);

	}
	
	public void update(int position, Model model) {
		// Update the various components here.
        ForecastModel f = model.getForecast();
        dayLabel.setText(f.getDay(position));
        lunarIcon.setIcon(IconHelper.getIcon(f.getIconString(position)));
        lunarLabel.setText(f.getConditions(position));
	}
}