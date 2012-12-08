import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.FileNotFoundException;



public class NewsPanel extends AppletPanel
{

	EPub epub = null;
	RenderedPagePanel rpp = null;	
	Font fontTitle = new Font("Arial", Font.BOLD, 32);
	Font fontDate = new Font("Arial", Font.BOLD, 18);
	int articleOffset = 0;
	int articlesPerPage = 8;
	Indicator pageIndicator = null;
	
	public NewsPanel()
	{
		super();
		// TODO Auto-generated constructor stub
//		readNews();
	}

	
	public void init()
	{
//		printNews();
		try 
		{
			epub = new EPub("ZeitOnline.epub");
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		pageIndicator = new Indicator(new Rectangle(0,730,600,30), epub.chapters.size() / articlesPerPage + 1, 1);
		showTeaser();
	}
	
	public void showTeaser()
	{
		int height = 70;
		int dy = 120;
		int y = 0;
		int maxArticleCount = 0;
		if (articleOffset + articlesPerPage < epub.chapters.size())
			maxArticleCount = articlesPerPage;
		else
			maxArticleCount = epub.chapters.size() - articleOffset;
		getContentPane().components.clear();
		for (int i = 0; i < maxArticleCount; i++)
		{
			final Chapter c = epub.chapters.get(articleOffset + i);
			Button b = new NewsTeaser(new Rectangle(10, dy + y, 580, height), c.name, c.teaser);
			b.setAlignment(Button.Alignment.LEFT);
			b.setFont(new Font("Arial", Font.PLAIN, 20));
			b.addButtonListener(new ButtonListener(){

				@Override
				public void buttonClicked(Button source) 
				{
					System.out.println(c.name + " was clicked " + source.toString());
					rpp = new RenderedPagePanel();
					rpp.display = display;
					rpp.printNews("TEMP/OEBPS/" + c.reference);
					repaint();
				}

				@Override
				public void buttonLongClicked(Button source) {
					// TODO Auto-generated method stub
					
				}
			});
			getContentPane().add(b);
			y = y + height + 5;

		}
		getContentPane().add(pageIndicator);
		
	}
	
	public void event(Event ev)
	{
		if (rpp != null)
			rpp.event(ev);
		else
			super.event(ev);
	}
	
	@Override
	public void paint(Graphics2D gg)
	{
		if (rpp != null)
		{
//			rpp.parent = this;
			rpp.paint(gg);
		}
		else
		{
			super.paint(gg);
			gg.setColor(Color.black);
			FontMetrics metrics = null;
			gg.setFont(fontTitle);
			metrics = gg.getFontMetrics();
			Rectangle textBounds = metrics.getStringBounds(epub.getTitle(), gg).getBounds();
			gg.drawString(epub.getTitle(), (bounds.width - textBounds.width) / 2, 80 );
			gg.setFont(fontDate);
			metrics = gg.getFontMetrics();
			textBounds = metrics.getStringBounds(epub.getDate(), gg).getBounds();
			gg.drawString(epub.getDate(), (bounds.width - textBounds.width) / 2, 110 );
		}
	}
	
	public void processSwipeEvent(SwipeEvent ev) 
	{
		// TODO Auto-generated method stub
		if (rpp != null)
		{
			if ((ev.type == EventType.SwipeUp) || (ev.type == EventType.SwipeDown))
			{
				rpp = null;
				repaint();
			}
		}
		else 
		{
			System.out.println("NewsPanel.processSwipeEvent: " + ev.type);
			if (ev.type == EventType.SwipeRight)
			{
				if (articleOffset >= articlesPerPage)
				{
					articleOffset = articleOffset - articlesPerPage;
					pageIndicator.back();
					showTeaser();
					repaint();
				}
			} 
			else if (ev.type == EventType.SwipeLeft)
			{
				if (articleOffset + articlesPerPage < epub.chapters.size())
				{
					articleOffset = articleOffset + articlesPerPage;
					pageIndicator.forward();
					showTeaser();
					repaint();
				}
			}
		}

	}

	
}
