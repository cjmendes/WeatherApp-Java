import acm.program.Program;

import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class GUI  extends Program
{
	
	private Image background;

    private JTextField searchField;
    
    private JLabel locationLabel;
    
    /*lunarButton needs to be declared as instance field so both init() 
    and actionPerformed can see it, for the switching images.*/
    private JButton lunarButton;
    
    /*Called here so the actionPerformed doesn't load the images 
    everytime button is clicked.*/
    private ImageIcon sun = new ImageIcon("assets/sun.gif");
	private ImageIcon moon = new ImageIcon("assets/Buttons/moon.png");
	
	/*count to keep track of lunarButton clicks to switch between images*/
	int count = 1;

    private CurrentConditionsPanel currentConditions;
    private ForecastPanel forecast;
    private RadarPanel radar;
    private Model model;
    private Font CT3Font;
    

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
		this.setSize(WeatherPanel.WIDTH+17 + 20, WeatherPanel.HEIGHT*3 + 85);
		this.setTitle("5Cast: A Better Forecast!");
		try {
            //create the font to use. Specify the size
			CT3Font = Font.createFont(Font.TRUETYPE_FONT, new File("assets/Fonts/Roboto-Thin.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("assets/Fonts/Roboto-Thin.ttf")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(FontFormatException e){
            e.printStackTrace();
        }
		
	}
	
	public void init()
	{
		BackgroundPanel slots = new BackgroundPanel();
//        slots.setBackground("assets/bg/Background.png");

        JPanel searchArea = new JPanel();
        searchArea.setLayout(new GridBagLayout());
        searchArea.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        searchField = new JTextField(25);
        searchField.setOpaque(true);
        PromptSupport.setPrompt("Enter location here", searchField);
        searchField.setActionCommand("search");
        searchField.addActionListener(this);
        c.gridwidth=1;
        c.gridy = 0;
        searchArea.add(searchField, c);

        JButton searchButton = new JButton(new ImageIcon("assets/Buttons/search.png"));
        searchButton.setActionCommand("search");
        c.gridwidth=1;
        c.gridy = 0;
        searchArea.add(searchButton, c);

        JButton locationButton = new JButton(new ImageIcon("assets/Buttons/location1.png"));
        locationButton.setActionCommand("locate");
        c.gridwidth=1;
        c.gridy = 0;
        searchArea.add(locationButton, c);

        JButton refreshButton = new JButton(new ImageIcon("assets/Buttons/refresh.png"));
        refreshButton.setActionCommand("refresh");
        c.gridwidth=1;
        c.gridy = 0;
        searchArea.add(refreshButton, c);

        lunarButton = new JButton(new ImageIcon("assets/Buttons/moon.png"));
        lunarButton.setActionCommand("switch");
        c.gridwidth=1;
        c.gridy = 0;
        searchArea.add(lunarButton, c);

        locationLabel = new JLabel();
        locationLabel.setHorizontalAlignment(JLabel.CENTER);
        locationLabel.setForeground(Color.WHITE);
        locationLabel.setFont(CT3Font);
        c.gridwidth=5;
        c.gridy=1;
        searchArea.add(locationLabel, c);


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
        	count++;
			if (count % 2 == 0)
            {
				lunarButton.setIcon(sun);            	
            }
            else
            {
            	lunarButton.setIcon(moon);
            }
        	
        }
    }

    @SuppressWarnings("unchecked")
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

                @SuppressWarnings("rawtypes")
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
        //currentConditions.onLocationChanged(model);
    }

    private void updateLocation(Model m)
    {
        invalidate();
        repaint();

        locationLabel.setText(m.getLocation());

        currentConditions.onLocationChanged(m);
        forecast.onLocationChanged(m);
        radar.onLocationChanged(m);
    }
}
