package com.mypapyri.clay.display;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class SwingDisplay extends Display
{

	public JFrame frame = null;
	public JLabel icon;
	
	public SwingDisplay(int width, int height)
	{
		super(new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY));
		frame = new JFrame("Kobo Touch");
		frame.setUndecorated(true);
		frame.setResizable(false);
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
		icon.repaint(region.x, region.y, region.width, region.height);
	}

	public void repaint()
	{
		icon.repaint();
	}

	@Override
	public void repaint(Rectangle region, boolean fastUpdate) {
		repaint(region);
		
	}

}
