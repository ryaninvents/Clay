import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;



public class TimePicker extends Component 
{
	int number = 0;
	
	public TimePicker(Rectangle bounds)
	{
		super(bounds);
	}
	
	@Override
	public void paint(Graphics2D gg)
	{
		// TODO Auto-generated method stub
		if (Configuration.debugPaint)
			System.out.println("TimePicker.paint");
		gg.setStroke(new BasicStroke(2));
		gg.setColor(new Color(80, 80, 80));
//		gg.drawRoundRect(0, 0, bounds.width, bounds.height, 10, 10);
		gg.setColor(Color.BLACK);
		gg.setFont(new Font("Arial", Font.BOLD, 18));
		FontMetrics metrics = null;
		metrics = gg.getFontMetrics();
		Rectangle textBounds;
//		for (int i = 0; i < 12; i++)
//		{
//			textBounds = metrics.getStringBounds(String.valueOf(i), gg).getBounds();
//			gg.drawString(String.valueOf(i), i * (bounds.width / 12) + (bounds.width/12 - textBounds.width)/2, textBounds.height);
//			if (i < 11)
//			gg.drawLine((i +1) * (bounds.width / 12), 0, (i+1) * (bounds.width / 12), bounds.height);
//			textBounds = metrics.getStringBounds(String.valueOf(i * 5), gg).getBounds();
//			gg.drawString(String.valueOf(i * 5), i * (bounds.width / 12) + (bounds.width/12 - textBounds.width)/2, 2 * textBounds.height);
//			
//		}
		
		gg.setStroke(new BasicStroke(1));
		gg.setColor(Color.BLACK);
		int height = bounds.height/3;
		
		gg.drawRect(0, 0, bounds.width, height);
		textBounds = metrics.getStringBounds("+", gg).getBounds();
		gg.drawString("+", (bounds.width - textBounds.width)/2, textBounds.height);
		
		gg.drawRect(0, height + 2, bounds.width, height);
		textBounds = metrics.getStringBounds(String.valueOf(number), gg).getBounds();
		gg.drawString(String.valueOf(number), (bounds.width - textBounds.width)/2, height + 2 + textBounds.height);

		gg.drawRect(0, 2 * (height + 2), bounds.width, height);
		textBounds = metrics.getStringBounds("-", gg).getBounds();
		gg.drawString("-", (bounds.width - textBounds.width)/2, 2 * (height + 2) + textBounds.height);
		
	}
	
	public void event(Event ev)
	{
		System.out.println("TimePicker.event");
		if (ev.type == EventType.MouseClicked)
		{
			MouseEvent me = (MouseEvent)ev;
		}
		
	}
}
