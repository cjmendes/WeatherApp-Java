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
    private JLabel windLabel;
    private JLabel divLabel;

    private WindDirection windDirection;
    private Font CT2Font, CT4Font;
    

    private CurrentConditionsPanel curr;


	public ForecastColumn(int position) {
        if(position % 2 == 0) {
            setBackground(new Color(160, 160, 160, 80));
        } else {
            setBackground(new Color(190, 190, 190, 110));
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
        try {
            //create the font to use. Specify the size
        	CT4Font = Font.createFont(Font.TRUETYPE_FONT, new File("assets/Fonts/Roboto-Bold.ttf")).deriveFont(13f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("assets/Fonts/Roboto-Bold.ttf")));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        catch(FontFormatException e1){
            e1.printStackTrace();   
        }
        
		dayLabel = new JLabel();
        dayLabel.setHorizontalAlignment(JLabel.CENTER);
        dayLabel.setForeground(Color.WHITE);
        dayLabel.setFont(CT2Font);
		add(dayLabel);
		

        HPanel tempArea = new HPanel();
        tempArea.setOpaque(false);
        lowLabel = new JLabel();
        lowLabel.setHorizontalAlignment(JLabel.RIGHT);
        lowLabel.setForeground(new Color(210, 210, 255));
        lowLabel.setFont(CT4Font);
        tempArea.add(lowLabel);
        divLabel = new JLabel("  | ");
        divLabel.setForeground(Color.WHITE);
        tempArea.add(divLabel);
        highLabel = new JLabel();
        highLabel.setForeground(new Color(255, 210, 210));
        highLabel.setFont(CT4Font);
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
        windLabel.setForeground(Color.WHITE);
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