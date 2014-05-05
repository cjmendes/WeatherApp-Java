import acm.gui.HPanel;
import acm.gui.VPanel;
import acm.program.Program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class GUI  extends Program
{

    private JTextField searchField;
    private CurrentConditionsPanel currentConditions;
    private ForecastPanel forecast;
    private RadarPanel radar;

    public GUI()
	{
		this.start();
		this.setSize(400, 700);
		this.setTitle("5Cast");
	}
	
	public void init()
	{
		VPanel slots = new VPanel();

        JPanel searchArea = new HPanel();
        searchField = new JTextField(10);
        searchField.setActionCommand("search");
        searchField.addActionListener(this);
        JButton searchButton = new JButton("search");
        searchArea.add(searchField);
        searchArea.add(searchButton);

        currentConditions = new CurrentConditionsPanel();
        forecast = new ForecastPanel();
        radar = new RadarPanel();
		
		this.add(slots);
        slots.add(searchArea);
		slots.add(currentConditions);
		slots.add(forecast);
		slots.add(radar);
		
		currentConditions.setPreferredSize(new Dimension(400, 200));
		forecast.setPreferredSize(new Dimension(400, 200));
		radar.setPreferredSize(new Dimension(400, 200));

        addActionListeners();
	}
	
	public static void main(String[] args)
	{
		new GUI();
	}

    public void actionPerformed(ActionEvent ev)
    {
        String action = ev.getActionCommand();
        if("search".equals(action))
        {
            try
            {
                Model m = new WundergroundModel(searchField.getText());
                updateLocation(m);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void updateLocation(Model m)
    {
        currentConditions.onLocationChanged(m);
        forecast.onLocationChanged(m);
        radar.onLocationChanged(m);
    }
}
