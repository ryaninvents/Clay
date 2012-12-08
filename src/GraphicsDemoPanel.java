import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.Timer;



public class GraphicsDemoPanel extends AppletPanel implements ActionListener, ButtonListener
{

	BufferedImage canvas;
	int xpos = 10;
	int ypos = 10;
	int loop = 0;
	Timer timer = null;
	int pause = 0;
	int speed = 200;
	ImageIcon icon;
	BufferedImage image;
	Button startButton;
	Button stopButton;
	
	public int find_closest_palette_color(int rgb)
	{
//		if (rgb > 0)
//			return Color.WHITE.getRGB();
//		else
//			return Color.BLACK.getRGB();
		return rgb / 256;
	}
	
	public GraphicsDemoPanel() 
	{
		super();
		startButton = new Button(new Rectangle(100,500,150,50), "Start");
		stopButton = new Button(new Rectangle(350,500,150,50), "Stop");
		startButton.addButtonListener(this);
		stopButton.addButtonListener(this);
		getContentPane().add(startButton);
		getContentPane().add(stopButton);
		java.net.URL url = this.getClass().getResource("Newtons.gif" );
		icon = new ImageIcon(url);
		try {
			url = this.getClass().getResource("icon_mail.gif" );
			System.out.println("Button: imageName: " + "icon_mail.gif" + " url: " + url);
		    image = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		canvas = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_BYTE_GRAY);
		int oldPixel;
		int newPixel;
		int quantError;
		for (int y = 1; y < image.getHeight()-1; y++)
			for (int x = 1; x < image.getWidth()-1; x++)
			{
				oldPixel = image.getRGB(x, y);                   	// oldpixel := pixel[x][y]
			    newPixel = find_closest_palette_color(oldPixel); 	// newpixel := find_closest_palette_color(oldpixel)
			    image.setRGB(x, y, newPixel);					 	//	pixel[x][y] := newpixel
			    quantError = oldPixel - newPixel;	      			//quant_error := oldpixel - newpixel
			    image.setRGB(x+1, y  , image.getRGB(x+1, y  ) + (7 * quantError)/16);	      //pixel[x+1][y] := pixel[x+1][y] + 7/16 * quant_error
			    image.setRGB(x-1, y+1, image.getRGB(x-1, y+1) + (3 * quantError)/16);	      //pixel[x-1][y+1] := pixel[x-1][y+1] + 3/16 * quant_error
			    image.setRGB(x  , y+1, image.getRGB(x  , y+1) + (5 * quantError)/16);	      //pixel[x][y+1] := pixel[x][y+1] + 5/16 * quant_error
			    image.setRGB(x+1, y  , image.getRGB(x+1, y  ) + (1 * quantError)/16);	      //pixel[x+1][y+1] := pixel[x+1][y+1] + 1/16 * quant_error
//				image.setRGB(x+1, y+1, Color.BLACK.getRGB());
//				canvas.setRGB(x*2+1, y*2+1, Color.BLACK.getRGB());				
			}
        timer = new Timer(speed, this);
        timer.setInitialDelay(pause);
//        timer.start();
	}
		
	public void paint(Graphics2D gg)
	{
		if (Configuration.debugPaint)
			System.out.println("GraphicsDemoPanel.paint");
		super.paint(gg);
		gg.drawImage(image, null, 100 + xpos, 100 + ypos);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("GraphicsDemoPanel.actionPerformed");
		if (loop < 60)
		{
			xpos++;
			ypos++;
			loop++;
//			invalidate(new Rectangle(xpos, ypos, 200, 200), true);
			System.out.println("GraphicsDemoPanel.invalidate");
			repaint();
		}
		else
		{
			xpos = 0;
			ypos = 0;
			loop = 0;
		}
	}

	@Override
	public void buttonClicked(Button source) 
	{
		// TODO Auto-generated method stub
		if (source == startButton)
			timer.start();
		else if (source == stopButton)
			timer.stop();
	}

	@Override
	public void buttonLongClicked(Button source) {
		// TODO Auto-generated method stub
		
	}

}
