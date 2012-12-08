
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class ContentPane extends ComponentContainer 
{

	public ContentPane(Rectangle bounds) 
	{
		super(bounds);
		// TODO Auto-generated constructor stub
	}
	
	public void paint(Graphics2D gg)
	{
		// TODO Auto-generated method stub
//		if (Configuration.debugPaint)
			System.out.println("ContentPane.paint: bounds=" + bounds + " updateRegion=" + updateRegion + " fastUpdate=" + fastUpdate);
		super.paint(gg);
	}
}
