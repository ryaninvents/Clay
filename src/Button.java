
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;


public class Button extends Component
{
	String buttonText = null;
	String imageName = null;
	BufferedImage image = null;
	boolean drawBorder = true;
	boolean pressed = false;
	Font font = new Font("Arial", Font.BOLD, 28);
	Font fontPressed = new Font("Arial", Font.BOLD, 46);
	Alignment align = Alignment.CENTER;
	Action buttonAction = null;
	
	enum Alignment {LEFT, CENTER, RIGHT, TOP, BOTTOM};
	
	LinkedList <ButtonListener> buttonListeners = new LinkedList<ButtonListener>();
	LinkedList <ActionListener> actionListeners = new LinkedList<ActionListener>();
	
	
	public Button(Rectangle bounds, String string)
	{
		super(bounds);
		// TODO Auto-generated constructor stub
		buttonText = string;
	}

	public Button(Rectangle bounds, String string, boolean drawBorder)
	{
		super(bounds);
		// TODO Auto-generated constructor stub
		buttonText = string;
		this.drawBorder = drawBorder;
	}

	public Button(Rectangle bounds, String string, String imageName)
	{
		this(bounds, string, imageName, true);		
	}
	
	public Button(Rectangle bounds, String string, String imageName, boolean drawBorder)
	{
		super(bounds);
		// TODO Auto-generated constructor stub
		buttonText = string;
		this.imageName = imageName;
		this.drawBorder = drawBorder;
		if (imageName != null)
		{
			try 
			{
				java.net.URL url = this.getClass().getResource(imageName );
				System.out.println("Button: imageName: " + imageName + " url: " + url);
			    image = ImageIO.read(url);
			} 
			catch (IOException e) 
			{
				System.out.println("Button: Image not found!");
			}	
		}
		
	}
	
	public Button(Rectangle bounds, String string, BufferedImage buttonImage)
	{
		super(bounds);
		// TODO Auto-generated constructor stub
		buttonText = string;
		image = buttonImage;
		
	}
	
	public Button(Rectangle bounds, String string, BufferedImage buttonImage, boolean drawBorder)
	{
		super(bounds);
		// TODO Auto-generated constructor stub
		buttonText = string;
		image = buttonImage;
		this.drawBorder = drawBorder;
	}
	
	public void addButtonListener(ButtonListener bl)
	{
		if (bl != null)
			buttonListeners.add(bl);
	}

	public void removeButtonListener(ButtonListener bl)
	{
		if (bl != null)
			buttonListeners.remove(bl);
	}

	public void addActionListener(ActionListener al)
	{
		if (al != null)
			actionListeners.add(al);	
	}
	
	public void removeActionListener(ActionListener al)
	{
		if (al != null)
			actionListeners.remove(al);	
	}
	
	public void setAction(Action a)
	{
		buttonAction = a;
	}
	
	public void setFont(Font f)
	{
		font = f;
	}
	
	public void setAlignment(Alignment a)
	{
		align = a;
	}
	
	@Override
	public void paint(Graphics2D gg)
	{
		// TODO Auto-generated method stub
		if (Configuration.debugPaint)
		{
			System.out.println("Button.paint bounds " + bounds.toString());
			if (gg.getClipBounds() != null)
				System.out.println("Button.paint clip " + gg.getClipBounds().toString());
		}
		gg.setColor(Color.black);
		FontMetrics metrics = null;
		if (opaque)
		{
//			if (!pressed)
//				gg.setColor(bgColor);
//			else 
//				gg.setColor(Color.BLACK);
			if (!Configuration.isBlackAndWhite)
				gg.setColor(bgColor);
			else
				gg.setColor(Color.WHITE);
			gg.fillRoundRect(0, 0, bounds.width, bounds.height, 10, 10);
		}
		if (image != null)
		{
			int height = bounds.height;
			
			gg.drawImage(image, 2 , 2 , bounds.width, height, null);
//			gg.drawImage(image, 2 + (bounds.width - 4 - image.getWidth())/2, 2 + (bounds.height - 4 - image.getHeight())/2, bounds.width, bounds.height, null);
//			gg.drawImage(image, 2, 2, 2 + bounds.width/2 - 4, 2 + bounds.height/2 - 4, 
//			 0, 0, image.getWidth(), image.getHeight(), null);
//			gg.drawImage(image, 2, 2, 2 + bounds.width - 4, 2 + bounds.height - 4, 
//						 0, 0, image.getWidth(), image.getHeight(), null);
		}
		if (drawBorder)
		{
//			gg.setStroke(new BasicStroke(2));
//			gg.drawRoundRect(0, 0, bounds.width, bounds.height, 5, 5);
//			gg.setColor(new Color(192, 192, 192));
//			gg.drawLine(0, 0, bounds.width, 0);
//			gg.drawLine(0, 0, 0, bounds.height);
//			gg.setStroke(new BasicStroke(3));
//			gg.setColor(new Color(192, 192, 192));
//			gg.drawRoundRect(2, 2, bounds.width -4, bounds.height -4, 5, 5);
//			gg.setStroke(new BasicStroke(2));
//			gg.setColor(new Color(112, 112, 112));
//			gg.drawRoundRect(0, 0, bounds.width, bounds.height, 5, 5);
			gg.setStroke(new BasicStroke(2));
			gg.setColor(Color.BLACK);
			gg.drawRect(0, 0, bounds.width, bounds.height);
			
		}
//		if (!pressed)
//			gg.setColor(Color.BLACK);
//		else
//			gg.setColor(bgColor);
		
		gg.setColor(Color.BLACK);
		if (!pressed)
			gg.setFont(font);
		else
			gg.setFont(fontPressed);
		metrics = gg.getFontMetrics();
		Rectangle textBounds = metrics.getStringBounds(buttonText, gg).getBounds();
		int height = metrics.getAscent() + metrics.getDescent();
//		gg.drawRect((bounds.width - textBounds.width)/2, (bounds.height  - height)/2, textBounds.width, height);
		if (buttonText != null)
		{
			String tempButtonText = new String(buttonText);
			if (!pressed)
			{
				if (textBounds.width > bounds.width - 10)
				{
					int len = tempButtonText.length();
					len = ((bounds.width - 10) * len) / textBounds.width - 5;
					tempButtonText = tempButtonText.substring(0, len) + "...";
				}
			}
			if (!pressed)
			{
//				gg.drawString(buttonText, (bounds.width - textBounds.width)/2, (bounds.height - 6 - textBounds.height)/2 + textBounds.height);

				switch (align)
				{
					case LEFT: 
						gg.drawString(tempButtonText, 5, (bounds.height -  height)/2 + metrics.getAscent() );
						break;
					case CENTER: 
						gg.drawString(tempButtonText, (bounds.width - textBounds.width)/2, (bounds.height -  height)/2 + metrics.getAscent() );
						break;
					case RIGHT: 
						gg.drawString(tempButtonText, (bounds.width - textBounds.width), (bounds.height -  height)/2 + metrics.getAscent() );
						break;
					case TOP: 
						gg.drawString(tempButtonText, (bounds.width - textBounds.width)/2, metrics.getAscent() );
						break;
					case BOTTOM: 
						gg.drawString(tempButtonText, (bounds.width - textBounds.width)/2, bounds.height);
						break;
				}
			}
			else
				gg.drawString(tempButtonText, (bounds.width - textBounds.width)/2, (bounds.height -  height)/2 + metrics.getAscent() );
		}
	}

	public void event(Event ev)
	{
		if (Configuration.debugEvents)
			System.out.println("Button.event");
		if (ev instanceof MouseEvent)
		{
			MouseEvent me = (MouseEvent)ev;
			if (ev.type == EventType.MousePressed)
			{
				pressed = true;
//				invalidate(new Rectangle(bounds.x + 4, bounds.y + 4, bounds.width - 8, bounds.height - 8), true);
				invalidateInterior(new Rectangle(4, 4, bounds.width - 8, bounds.height - 8), true);
			}
			else if (ev.type == EventType.MouseReleased)
			{
				pressed = false;
				invalidate(new Rectangle(bounds.x + 4, bounds.y + 4, bounds.width - 8, bounds.height - 8), true);
			}

		}
		for (ButtonListener l : buttonListeners) 
		{
			if (ev.type == EventType.MouseClicked)
				l.buttonClicked(this);
			if (ev.type == EventType.MouseLongClicked)
				l.buttonLongClicked(this);
		}
		if (buttonAction != null)
		{
			for (ActionListener l : actionListeners) 
			{
				if (ev.type == EventType.MouseClicked)
					l.performAction(buttonAction);
				if (ev.type == EventType.MouseLongClicked)
					l.performAction(buttonAction);
			}
		}
	}

	public void setBorder(boolean b) 
	{
		// TODO Auto-generated method stub
		hasBorder = b;
		drawBorder = b;
		
	}
	

}
