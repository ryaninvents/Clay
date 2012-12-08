import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class AppletButton extends Button 
{

	public AppletButton(Rectangle bounds, String string,
			BufferedImage buttonImage) {
		super(bounds, string, buttonImage, false);
	}

	
	@Override
	public void paint(Graphics2D gg)
	{
		pressed = false;
		super.paint(gg);
	}

}
