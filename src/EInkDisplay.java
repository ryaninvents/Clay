

import java.awt.Rectangle;

import com.mypapyri.clay.ClaySystem;
import com.ramuller.clay.display.Display;


public class EInkDisplay extends Display
{
	EInkFB fb = null;
	
	public EInkDisplay(EInkFB einkfb)
	{
		super(einkfb.getImage());
		fb = einkfb;
	}

	@Override
	public void repaint(Rectangle region)
	{
//		fb.update(region, EInkFB.UPDATE_WAIT);
	//	fb.update(region, EInkFB.UPDATE_MODE_PARTIAL);
		repaint(region,ClaySystem.getQuickRefresh());

	}

	public void repaint(Rectangle region, boolean fastUpdate)
	{
//		fb.update(region, EInkFB.UPDATE_WAIT);
		if (fastUpdate)
			fb.update(region, EInkFB.UPDATE_MONOCHROME);
		else
			fb.update(region, EInkFB.UPDATE_MODE_PARTIAL);
			

	}

	@Override
	public void repaint()
	{
		// TODO Auto-generated method stub
		fb.update(new Rectangle(0,0,fb.getWidth(),fb.getHeight()), EInkFB.UPDATE_WAIT);
	}

}
