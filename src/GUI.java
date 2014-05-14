import acm.gui.TablePanel;
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
    private Model model;

    public GUI()
	{
		this.start();
		this.setSize(WeatherPanel.WIDTH+17, WeatherPanel.HEIGHT*3 + 100);
		this.setTitle("5Cast: A Better Forecast!");
	}
	
	public void init()
	{
		VPanel slots = new VPanel(0,0);

        JPanel searchArea = new TablePanel(1,4);
        searchField = new JTextField(25);
        //TODO: add ghost text in textfield
        //PromptSupport.setPrompt("Enter zipcode here", searchField);
        searchField.setActionCommand("search");
        searchField.addActionListener(this);
        JButton searchButton = new JButton(new ImageIcon("assets/search.png"));
        searchButton.setActionCommand("search");
        JButton refreshButton = new JButton(new ImageIcon("assets/refresh.png"));
        refreshButton.setActionCommand("refresh");
        JButton lunarButton = new JButton(new ImageIcon("assets/moon.png"));
        lunarButton.setActionCommand("switch");
        searchArea.add(searchField);
        searchArea.add(searchButton);
        searchArea.add(refreshButton);
        searchArea.add(lunarButton);

        currentConditions = new CurrentConditionsPanel();
        forecast = new ForecastPanel();
        radar = new RadarPanel();
		
		this.add(slots);
        slots.add(searchArea);
		slots.add(currentConditions);
		slots.add(forecast);
		slots.add(radar);

		currentConditions.setPreferredSize(new Dimension(WeatherPanel.WIDTH, WeatherPanel.HEIGHT));
		forecast.setPreferredSize(new Dimension(WeatherPanel.WIDTH, WeatherPanel.HEIGHT));
		radar.setPreferredSize(new Dimension(WeatherPanel.WIDTH, WeatherPanel.HEIGHT));

        addActionListeners();


        // Load initial location
        //TODO: get this from IP
        try 
        {
            model = new WundergroundModel("95747");
            updateLocation(model);
        } catch (WundergroundModel.WundergroundException e)
        {
            e.printStackTrace();
        }
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
                model = new WundergroundModel(searchField.getText());
                updateLocation(model);
            }
            catch (WundergroundModel.WundergroundException e)
            {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if("refresh".equals(action))
        {
            model.refresh();
        }
        if("switch".equals(action))
        {
        	//TODO: Replace currentCondition info with lunar info
        }
    }

    private void updateLocation(Model m)
    {
        currentConditions.onLocationChanged(m);
        forecast.onLocationChanged(m);
        radar.onLocationChanged(m);
    }
}
