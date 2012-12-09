package koper;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedHashMap;
import java.util.Map;



public class TagCloud extends ComponentContainer 
{

	int tagWeight[] = {22, 28, 36, 42, 48};
	Margin margin = new Margin(10, 20, 10, 20);
	Map<String, Integer> tags = new LinkedHashMap<String, Integer>();
	ButtonListener buttonListener = null;

	
	public TagCloud(Rectangle bounds) 
	{
		super(bounds);
		// TODO Auto-generated constructor stub
//		tags.put("Politik Deutschland", 10);
//		tags.put("Politik Ausland", 4);
//		tags.put("Wirtschaft", 6);
//		tags.put("Geld", 10);
//		tags.put("Fussball", 4);
//		tags.put("EM 2012", 5);
//		tags.put("Olympia 2012", 8);
//		tags.put("Kultur", 2);
//		tags.put("Reise", 5);
//		tags.put("Heise News", 2);
//		tags.put("Telepolis", 4);
	}
	
	public void addButtonListener(ButtonListener bl)
	{
		buttonListener = bl;
	}
	
	public void addTag(String s, int w)
	{
		tags.put(s, w);
	}
	
	public void layout(Graphics2D gg)
	{
		int x = margin.left;
		int y = margin.top;
		int dx = 30;
		int dy = 30;
		int maxHeight = 0;
		int maxWeight = 0;
		components.clear();
		for (String word : tags.keySet())
		{
			if (tags.get(word) > maxWeight)
				maxWeight = tags.get(word);
		}
		maxWeight++;
		for (String word : tags.keySet())
		{
			System.out.println(getClass().getName() + "layout: " + word);
			gg.setFont(new Font("Arial", Font.PLAIN, tagWeight[tags.get(word) * tagWeight.length / maxWeight]));
			FontMetrics metrics = gg.getFontMetrics();
			Rectangle textbounds = metrics.getStringBounds(word, gg).getBounds();
			if (textbounds.height > maxHeight)
				maxHeight = textbounds.height;
			if (x + textbounds.width > bounds.width - margin.right)
			{
				x = margin.left;
				y = y + maxHeight + dy;
				maxHeight = 0;
			}
			if (tags.get(word) > 6)
				gg.setColor(Color.black);
			else
				gg.setColor(Color.gray);

//			gg.drawString(word, x, y + textbounds.height);
			Button b = new Button(new Rectangle(x, y, textbounds.width + 10, textbounds.height + 5), word);
			b.setFont(gg.getFont());
			b.setBorder(false);
			b.addButtonListener(buttonListener);
			add(b);
			x = x + textbounds.width + dx;
			
		}
		
	}
	
	public void paint(Graphics2D gg)
	{
		layout(gg);
		super.paint(gg);
		
//		int x = margin.left;
//		int y = margin.top;
//		int dx = 20;
//		int dy = 20;
//		int maxHeight = 0;
//		for (String word : tags.keySet())
//		{
//			gg.setFont(new Font("Arial", Font.PLAIN, 10 + tags.get(word) * 3));
//			FontMetrics metrics = gg.getFontMetrics();
//			Rectangle textbounds = metrics.getStringBounds(word, gg).getBounds();
//			if (textbounds.height > maxHeight)
//				maxHeight = textbounds.height;
//			if (x + textbounds.width > bounds.width - margin.right)
//			{
//				x = margin.left;
//				y = y + maxHeight + dy;
//				maxHeight = 0;
//			}
//			if (tags.get(word) > 6)
//				gg.setColor(Color.black);
//			else
//				gg.setColor(Color.gray);
//
//			gg.drawString(word, x, y + textbounds.height);
//			x = x + textbounds.width + dx;
//			
//		}
//		
	}

}
