import java.awt.Color;
import java.awt.Graphics2D;



public class HTMLViewPanel extends AppletPanel 
{

	protected String content = null;
	
	public HTMLViewPanel() 
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void paint(Graphics2D gg)
	{
		// TODO Auto-generated method stub
		System.out.println("HTMLViewPanel.paint");
		gg.setColor(Color.white);
		gg.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		gg.setColor(Color.black);
		gg.drawString(content, 10, 40);
		display.repaint();
	}
	
	public void setContent(String content)
	{
		this.content = content;
	}
}
