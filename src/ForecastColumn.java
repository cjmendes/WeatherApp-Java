import acm.gui.HPanel;
import acm.gui.VPanel;

import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class ForecastColumn extends VPanel {

    private JLabel dayLabel;
    private JLabel lowLabel;
    private JLabel highLabel;
    private JLabel conditionIcon;
    private JLabel conditionLabel;
    private JLabel windLabel;
    private WindDirection windDirection;
    private Font CT2Font;
    private CurrentConditionsPanel curr;

	public ForecastColumn(int position) {
        if(position % 2 == 0) {
            setBackground(new Color(255, 255, 255, 150));
        } else {
            setBackground(new Color(200, 200, 200, 150));
        }

       
        try {
            //create the font to use. Specify the size
        	CT2Font = Font.createFont(Font.TRUETYPE_FONT, new File("assets/Fonts/Roboto-Medium.ttf")).deriveFont(15f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("assets/Fonts/Roboto-Medium.ttf")));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        catch(FontFormatException e1){
            e1.printStackTrace();   
        }
        
		dayLabel = new JLabel();
        dayLabel.setHorizontalAlignment(JLabel.CENTER);
        dayLabel.setFont(CT2Font);
		add(dayLabel);
		

        HPanel tempArea = new HPanel();
        tempArea.setOpaque(false);
        lowLabel = new JLabel();
        lowLabel.setHorizontalAlignment(JLabel.RIGHT);
        lowLabel.setForeground(Color.blue);
        lowLabel.setFont(CT2Font);
        tempArea.add(lowLabel);
        tempArea.add(new JLabel("  |"));
        highLabel = new JLabel();
        highLabel.setForeground(Color.red);
        highLabel.setFont(CT2Font);
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
        windLabel.setFont(CT2Font);
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
        //updateLunarInfo(position, model);
        windLabel.setText(model.getWindspeed(position) + " mph");
        windDirection.setAngle(model.getWindDirection(position));
	}
	
	//TODO:
	/*public void updateLunarInfo(int position, Model newModel){
		if (curr.lunarNum == 0)
		{
			conditionIcon.setIcon(IconHelper.getIcon(newModel.getIconString(position)));
		}
		else
		{
			conditionIcon.setIcon(IconHelper.getIcon("" + newModel.getMoonPhase(position)));
		}
	}*/
}