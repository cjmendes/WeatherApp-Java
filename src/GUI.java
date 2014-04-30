import javax.swing.JPanel;
import acm.gui.*;
import acm.program.*;
import java.awt.Dimension;

public class GUI  extends Program
{
	public GUI()
	{
		this.start();
		this.setSize(400, 600);
		this.setTitle("5Cast");
	}
	
	public void init()
	{
		VPanel slots = new VPanel();
		
		JPanel top = new CurrentConditions();
		JPanel middle = new ForecastPanel();
		JPanel bottom = new RadarPanel();
		
		this.add(slots);
		slots.add(top);
		slots.add(middle);
		slots.add(bottom);
		
		top.setPreferredSize(new Dimension(400, 200));
		middle.setPreferredSize(new Dimension(400, 200));
		bottom.setPreferredSize(new Dimension(400, 200));
	}
	
	public static void main(String[] args)
	{
		new GUI();
	}
}
