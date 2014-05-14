import acm.graphics.GImage;
import acm.gui.TablePanel;
import acm.gui.VPanel;
import acm.program.Program;

import javax.swing.*;

import org.jdesktop.swingx.prompt.PromptSupport;

import com.sun.imageio.plugins.common.ImageUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class GUI  extends Program
{
	
	private Image background;

    private JTextField searchField;
    
    private JLabel locationLabel;

    private CurrentConditionsPanel currentConditions;
    private ForecastPanel forecast;
    private RadarPanel radar;
    private Model model;

    public GUI()
	{
		this.start();
		
		try
		{
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        MediaTracker tracker = new MediaTracker(this);
        background = toolkit.getImage("assets/bg/Background.png");
        tracker.addImage(background,0);
        tracker.waitForAll();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		
		}
        
		this.setSize(500,700);
		this.setSize(WeatherPanel.WIDTH+17, WeatherPanel.HEIGHT*3 + 85);
		this.setTitle("5Cast: A Better Forecast!");
		
	}
	
	public void init()
	{
		VPanel slots = new VPanel(0,0);

        JPanel searchArea = new TablePanel(2,5);
        searchField = new JTextField(25);
        //TextPrompt ghostText = new TextPrompt("Enter Location", searchField);
        //ghostText.setForeground(Color.LIGHT_GRAY);
        //TODO: add ghost text in textfield
       PromptSupport.setPrompt("Enter zipcode here", searchField);
        searchField.setActionCommand("search");
        searchField.addActionListener(this);
        JButton searchButton = new JButton(new ImageIcon("assets/search.png"));
        searchButton.setActionCommand("search");
        JButton locationButton = new JButton(new ImageIcon("assets/location.png"));
        locationButton.setActionCommand("locate");
        JButton refreshButton = new JButton(new ImageIcon("assets/refresh.png"));
        refreshButton.setActionCommand("refresh");
        JButton lunarButton = new JButton(new ImageIcon("assets/moon.png"));
        lunarButton.setActionCommand("switch");
        locationLabel = new JLabel();
        locationLabel.setHorizontalAlignment(JLabel.CENTER);
        Font locationFont = new Font("Verdana", Font.BOLD, 24);
        locationLabel.setFont(locationFont);

        searchArea.add(searchField);
        searchArea.add(searchButton);
        searchArea.add(locationButton);
        searchArea.add(refreshButton);
        searchArea.add(lunarButton);
        searchArea.add(locationLabel, "gridwidth=5");

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

        useLocation();
        
       
    }
	
	public void paintComponents(Graphics g)
	{
		g.drawImage(background,0,0,null);
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
            search(searchField.getText());
        }
        if("locate".equals(action))
        {
            useLocation();
        }
        if("refresh".equals(action))
        {
            try
            {
                model.refresh();
            }
            catch (WundergroundModel.WundergroundException e)
            {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if("switch".equals(action))
        {
        	changeLunar();
        	//TODO: Replace currentCondition info with lunar info
        }
    }

    private void search(String query)
    {
        try
        {
            model = new WundergroundModel(query);
            updateLocation(model);
        }
        catch (WundergroundModel.WundergroundException e)
        {
            if(e instanceof WundergroundModel.MultipleResultsException)
            {
                WundergroundModel.MultipleResultsException mre = (WundergroundModel.MultipleResultsException) e;

                JList list = new JList(mre.getNames().toArray());
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                JOptionPane.showMessageDialog(this, list, "Multiple results found", JOptionPane.PLAIN_MESSAGE);

                int i = list.getSelectedIndex();
                if(i != -1)
                {
                    search(mre.getCodes().get(i));
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void useLocation()
    {
        try
        {
            model = new WundergroundModel("autoip");
            //searchField.setText(model.getLocation());
            updateLocation(model);
        } catch (WundergroundModel.WundergroundException e)
        {
            e.printStackTrace();
        }
    }
    
    private void changeLunar()
    {
        if(currentConditions.lunarNum == 0)
        	currentConditions.lunarNum++;
        else 
        	currentConditions.lunarNum--;
        
        currentConditions.changeLunarInfo(model);
        //forecast.onLocationChanged(model);
    }

    private void updateLocation(Model m)
    {
        locationLabel.setText(m.getLocation());

        currentConditions.onLocationChanged(m);
        forecast.onLocationChanged(m);
        radar.onLocationChanged(m);
    }
}
