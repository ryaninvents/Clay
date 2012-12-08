import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.BreakIterator;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


import net.htmlparser.jericho.CharacterReference;
import net.htmlparser.jericho.EndTag;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;
import net.htmlparser.jericho.Tag;


public class RenderedPagePanel extends AppletPanel 
{
	BlockBox curBlock = null;
	RenderedPages rp = new RenderedPages();
	int pageNumber = 0;
	Indicator ind = null;

	public RenderedPagePanel() 
	{
		super();
		// TODO Auto-generated constructor stub
		ind = new Indicator(new Rectangle(0,770,600,30), 1, 1);
	}

	public void event(Event ev)
	{
		// TODO Auto-generated method stub
		Logger.getLogger(getClass().getName()).log(Level.INFO, null);
		if (ev instanceof MouseEvent)
		{
			MouseEvent me = (MouseEvent) ev;
			System.out.printf("MouseEvent %s at x:%d, y:%d\n", me.type.toString(), me.x, me.y);
			switch (me.type)
			{
			case MouseLongClicked:
				AbstractBox hitBox = curBlock.getBoxAt(me.x, me.y);
				if (hitBox != null)
					if (hitBox instanceof TextBox)
					{
						TextBox textBox = (TextBox)hitBox;
						System.out.println("Mouse clicked on " + textBox.text);
					}
				break;
			case MouseClicked:
				if (me.x > 300)
				{
					if (pageNumber < rp.pageList.size() - 1)
					{
						System.out.println("Page right");
						pageNumber++;
						ind.forward();
						repaint();
					}
				}
				else
				{
					System.out.println("Page left");
					if (pageNumber > 0)
					{
						pageNumber--;
						ind.back();
						repaint();
					}
				}
			}
		}
	}

	public void printNews(String filename)
	{

		System.out.println("RenderedPagePanel.printNews: " + filename);
		
		Map<String, Font> fontMap = new HashMap<String, Font>();

		fontMap.put("body", new Font("Arial", Font.BOLD, 24));
		fontMap.put("h1", new Font("Arial", Font.BOLD, 24));
		fontMap.put("h2", new Font("Arial", Font.PLAIN, 20));
		fontMap.put("p", new Font("Arial", Font.PLAIN, 16));

		Source source = null;
		String anchor;
		if (filename.indexOf("#") > 0)
		{
			anchor = filename.substring(filename.indexOf("#") + 1);
			filename = filename.substring(0, filename.indexOf("#"));
		}
		try
		{
			//	        	source = new Source(new FileInputStream("testnews.xhtml"));
			//	        	source = new Source(new FileInputStream("Der_Schatz_im_Silbersee_1_kurz.xhtml"));
			source = new Source(new FileInputStream(Configuration.getDirectory() + File.separator + filename));
		}
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		net.htmlparser.jericho.Element bodyElem = source.getFirstElement("body");
		net.htmlparser.jericho.Tag tag2 = bodyElem.getFirstStartTag();
		System.out.println(tag2.getName() + ":" + tag2.getElement().getContent().toString());
		//	    	System.out.println(tag2.getName() + ":" + tag2.getElement().getContent().toString());
		Deque<BlockBox> blockStack = new ArrayDeque<BlockBox>();
		Deque<Font> fontStack = new ArrayDeque<Font>();
		int blockCount = 0;
		Font curFont = fontMap.get("body");
		for (Segment segment : source) 
		{
			if (segment instanceof StartTag) 
			{
				Tag tag=(StartTag)segment;
				// HANDLE STARTTAG
				//	    			System.out.println("StartTag " + tag.getName().toString());
				if (tag.getName().toString().equals("body"))
				{
					System.out.println("Body-Blockelement");
					curBlock = new BlockBox(600, 800);
				}
				// if tag is a block tag
				if (net.htmlparser.jericho.HTMLElements.getBlockLevelElementNames().contains(tag.getName().toString()))
				{
					if (curBlock != null)
					{
						BlockBox tmpBlock = new BlockBox(curBlock.bounds.width - curBlock.margin.left - curBlock.margin.right);
						curBlock.addBox(tmpBlock);
						blockStack.push(curBlock);
						curBlock = tmpBlock;
						blockCount = blockCount + 1;
						//	        			System.out.printf("Start Blockelement %s count: %d\n",tag.getName().toString(), blockCount);
						fontStack.push(curFont);
						//	    				System.out.println("push " + curFont.toString());
						curFont = fontMap.get(tag.getName().toString());
					}

				}
				else
				{
					if (tag.getName().toString().equals("strong"))
					{
						fontStack.push(curFont);
						//	        				System.out.println("push " + curFont.toString());
						curFont = curFont.deriveFont(Font.BOLD);
					}
					if ((tag.getName().toString().equals("i")) || (tag.getName().toString().equals("em")))
					{
						fontStack.push(curFont);
						//	        				System.out.println("push " + curFont.toString());
						curFont = curFont.deriveFont(Font.ITALIC);
					}
				}
			} 
			else if (segment instanceof EndTag) 
			{
				Tag tag=(EndTag)segment;
				// HANDLE ENDTAG
				//	    			System.out.println("EndTag " + tag.getName().toString());
				if (tag.getName().toString().equals("body"))
				{
					//	        			System.out.println("End Body-Blockelement");
					//	        	    	System.out.println("Begin layouting ##################################");
					rp.newPage();
					curBlock.render(rp);
//					rp.write("Tile");
					//	        			curBlock.layout(gg);
				}
				if (net.htmlparser.jericho.HTMLElements.getBlockLevelElementNames().contains(tag.getName().toString()))
				{
					if (curBlock != null)
					{
						curBlock = blockStack.pop();
					}
					//	        			System.out.printf("End Blockelement %s count: %d\n",tag.getName().toString(), blockCount);
					curFont = fontStack.pop();
					//	    				System.out.println("pop " + curFont.toString());

				}
				else
				{
					if  ((tag.getName().toString().equals("strong")) || (tag.getName().toString().equals("i"))  || (tag.getName().toString().equals("em")))
					{
						curFont = fontStack.pop();
						//	        				System.out.println("pop " + curFont.toString());
					}
				}
			} 
			else if (segment instanceof CharacterReference) 
			{
//				CharacterReference characterReference=(CharacterReference)segment;
				// HANDLE CHARACTER REFERENCE
				// Uncomment the following line to decode all character references instead of copying them verbatim:
				// characterReference.appendCharTo(writer); continue;
			} 
			else 
			{
				// HANDLE ENDTAG
				if (curBlock != null)
				{
					if (!segment.isWhiteSpace())
					{
						String s = segment.toString();
						//	    					System.out.println("plain text " + s);
						BreakIterator iter = BreakIterator.getLineInstance();
						iter.setText(s);

						for ( int last = iter.first(),next = iter.next(); 
								next != BreakIterator.DONE; 
								last = next, next = iter.next() ) 
						{ 
							String part = s.substring( last, next ); 

							if (Character.isWhitespace(part.charAt(part.length()-1)))
							{
								part = part.trim();
								curBlock.addInlineBox(new TextBox(part, curFont));
								curBlock.addInlineBox(new WhitespaceBox());
							}
							else
								curBlock.addInlineBox(new TextBox(part, curFont));

							//	    							System.out.println("new TextBox : #" + part + "#" );

						} 

					}
					else
					{
						//	    					System.out.println("whitespace ");
						//	    					curBlock.addInlineBox(new WhitespaceBox());
					}
				}	
				// HANDLE PLAIN TEXT
			}
			// unless specific handling has prevented getting to here, simply output the segment as is:
			//	    		System.out.println("unspecified " + segment.toString());
		}    	
		//	    	System.out.println("End rendering ##################################");
		ind.setMax(rp.pageList.size());
	}

	@Override
	public void paint(Graphics2D gg)
	{
		// TODO Auto-generated method stub
		if (Configuration.debugPaint)
			System.out.println("NewsPanel.paint");
		gg.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );		
		gg.setColor(Color.white);
		gg.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		gg.setColor(Color.black);
		// print example news
		//		printNews(gg);
		gg.drawImage(rp.getPage(pageNumber), bounds.x, bounds.y, bounds.width, bounds.height, null);
		//		display.repaint();
		gg.translate(0, 750);
		ind.paint(gg);
		gg.translate(0, -750);
	}



}
