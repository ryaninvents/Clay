import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class SwingDisplay extends Display
{

	JFrame frame = null;
	JLabel icon;
	
	public SwingDisplay(int width, int height)
	{
		super(new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY));
		System.out.println("Creating SwingDisplay...");
		frame = new JFrame("Virtual Kobo Touch");
		frame.setLocation(600, 50);
		frame.getContentPane().setLayout(new BorderLayout());
		icon = new JLabel(new ImageIcon(image));
		frame.getContentPane().add(icon , BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	@Override
	public void repaint(Rectangle region)
	{
		// TODO Auto-generated method stub
		System.out.println("SwingDisplay.repaint(region) " + region.toString());
		icon.repaint(region.x, region.y, region.width, region.height);

	}

	public void repaint()
	{
		// TODO Auto-generated method stub
		System.out.println("SwingDisplay.repaint");
		icon.repaint();

	}

	@Override
	public void repaint(Rectangle region, boolean fastUpdate) {
		// TODO Auto-generated method stub
		System.out.println("SwingDisplay.repaint");
		icon.repaint();
		
	}

}
