package koper;
import java.awt.Rectangle;


public class EInkDisplay extends Display
{
	EInkFB fb = null;
	
	public EInkDisplay(EInkFB einkfb)
	{
		super(einkfb.getImage());
		System.out.println("Create EInkDisplay");
		// TODO Auto-generated constructor stub
		fb = einkfb;
	}

	@Override
	public void repaint(Rectangle region)
	{
		// TODO Auto-generated method stub
		System.out.println("EInkDisplay.repaint(region) " + region.toString());
//		fb.update(region, EInkFB.UPDATE_WAIT);
		fb.update(region, EInkFB.UPDATE_MODE_PARTIAL);

	}

	public void repaint(Rectangle region, boolean fastUpdate)
	{
		// TODO Auto-generated method stub
		System.out.println("EInkDisplay.repaint(region) " + region.toString() + " fast=" + fastUpdate);
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
		System.out.println("EInkDisplay.repaint");
		fb.update(new Rectangle(0,0,fb.getWidth(),fb.getHeight()), EInkFB.UPDATE_WAIT);
	}

}
