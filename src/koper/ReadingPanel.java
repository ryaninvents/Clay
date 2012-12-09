package koper;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;



public class ReadingPanel extends AppletPanel implements ButtonListener, ActionListener
{

	HashMap<String, BookShelve> shelves = new HashMap<String, BookShelve>();
	BookShelve selectedShelve = null;
	TagCloud shelvesCloud = null;
	Indicator pageIndicator = null;
	int shelvesPerPage = 9;
	int shelveOffset = 0;
	int bookOffset = 0;
	int booksPerPage = 8;
	RenderedPagePanel rpp = null;	
	
	public ReadingPanel() 
	{
		super();
		shelves.put("Uncategorized", new BookShelve("Uncategorized"));
		// TODO Auto-generated constructor stub
	}
	
	public void init()
	{
		Book.loadList();
		updateShelves();
//		showBookShelves();
//		saveBookShelves();
		update();
	}
	
	public void update()
	{
		getContentPane().components.clear();
		shelvesCloud = new TagCloud(new Rectangle(0,200,600,500));
		shelvesCloud.addButtonListener(this);
		getContentPane().add(shelvesCloud);
		showShelvesCloud();
//		updateRecentBooks();
		getContentPane().add(new RecentBooksView(200));
		
	}
	
	public void quit()
	{
		Book.saveList();
	}
	
	// to keep navigation easy only one level of bookshelves is supported 
	// sub-levels are just ignored
	public void updateShelves()
	{
		File dir = new File(Configuration.getDirectory() + File.separator + Configuration.bookRepositoryDir);
		BookShelve uncat = shelves.get("Uncategorized");
		if (dir.exists() && dir.isDirectory()) 
		{
			File[] files  = dir.listFiles();
			for (int i=0; i<files.length; i++) 	
			{
				if (files[i].isDirectory())
				{
					BookShelve tmpBS = new BookShelve(files[i].getName());
					File imageFile = new File(files[i].getAbsolutePath() + File.separator + "shelveicon.png");
					if (imageFile.exists())
					{
						try 
						{
							tmpBS.setIcon(ImageIO.read(imageFile));
						} 
						catch (IOException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					shelves.put(files[i].getName(), tmpBS);
					File[] subFiles  = files[i].listFiles();
					for (int j=0; j<subFiles.length; j++) 	
					{
						if (subFiles[j].isFile())
						{
							if (subFiles[j].getName().endsWith(".epub"))
							{
//								System.out.println("adding book " + Configuration.bookRepositoryDir + File.separator + files[i].getName() + File.separator + subFiles[j].getName());
								tmpBS.addBook(Configuration.bookRepositoryDir + File.separator + files[i].getName() + File.separator + subFiles[j].getName());
							}
						
						}
					}
				}
				else
				{
					if (files[i].getName().endsWith(".epub"))
					{
						uncat.addBook(Configuration.bookRepositoryDir + File.separator + files[i].getName());
					}
				}
			}
		}
		else
			dir.mkdir();
	}
	
	public void showBookShelves()
	{
		int maxBookShelvesCount = 0;
		if (shelveOffset + shelvesPerPage < shelves.size())
			maxBookShelvesCount = shelvesPerPage;
		else
			maxBookShelvesCount = shelves.size() - shelveOffset;
		Object ks[] = shelves.keySet().toArray();
		for (int i = 0; i < maxBookShelvesCount; i++)
		{
			
			getContentPane().add(new BookShelveView(new Rectangle(10 + (i % 3) * 200, 195 + (i / 3) * 180 ,180,180), shelves.get((String)ks[shelveOffset + i])));
		}
		pageIndicator = new Indicator(new Rectangle(0,740,600,30), shelves.size() / shelvesPerPage + 1, 1);
		if (shelves.size() > shelvesPerPage)
			getContentPane().add(pageIndicator);

	}
	
	public void saveBookShelves()
	{
		
		File booksFile = new File(Configuration.allBooksFile);
		BufferedWriter writer;
		try 
		{
			writer = new BufferedWriter(new FileWriter(booksFile));
			writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<copper>\n");

			for (String s : shelves.keySet())
			{
				writer.write("<category name=\"" + s + "\">\n");
				writer.write("<booklist>\n");
				for (Book b : shelves.get(s).bookList)
				{
					writer.write("<book id=\"" + 1 + "\">\n");
					writer.write("<name>\n");
					writer.write(b.name);
					writer.write("</name>\n");
					writer.write("</book>\n");
					
				}
				writer.write("</booklist>\n");
				writer.write("</category>\n");
			}
			writer.write("</copper>\n");

			writer.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

	}
	
	public void showShelvesCloud()
	{
		for (String s : shelves.keySet())
		{
			shelvesCloud.addTag(s, shelves.get(s).getBookCount());
			
		}
	}
	
	public void showBookList()
	{
		int maxBooksCount = 0;
		if (bookOffset + booksPerPage < selectedShelve.bookList.size())
			maxBooksCount = booksPerPage;
		else
			maxBooksCount = selectedShelve.bookList.size() - bookOffset;
		
		for (int i = 0; i < maxBooksCount; i++)
		{
			BookView bv = new BookView(new Rectangle(0, i * 100 ,600,90), selectedShelve.bookList.get(bookOffset + i), BookView.State.Expanded);
			bv.addActionListener(this);
			bv.setAction(new BookSelectAction(selectedShelve.bookList.get(bookOffset + i)));
			getContentPane().add(bv);
		}

		
	}
	

	@Override
	public void paint(Graphics2D gg)
	{
		super.paint(gg);
//		final float dash1[] = {1.0f};
//		gg.setStroke(new BasicStroke(1.0f,
//				BasicStroke.CAP_BUTT,
//                BasicStroke.JOIN_MITER,
//                10.0f, dash1, 0.0f));
//		gg.drawLine (5, 215, 590, 215);
//		Font font = new Font("Arial", Font.PLAIN, 14);
//		gg.setFont(font);
//		gg.drawString(Configuration.ressourceStrings.get("Recent Books"), 10, 40);
	}

	@Override
	public void buttonClicked(Button source) 
	{
		// TODO Auto-generated method stub
		System.out.println(getClass().getName() + "buttonClicked: " + source.buttonText);	
		BookShelve bs = shelves.get(source.buttonText);
		System.out.println(getClass().getName() + "buttonClicked: " + bs.name);	
		getContentPane().components.clear();
		selectedShelve = bs;
		showBookList();
		repaint();
	}

	@Override
	public void buttonLongClicked(Button source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performAction(Action a) 
	{
		if (a instanceof BookSelectAction)
		{
			Book b = ((BookSelectAction)a).selectedBook;
			System.out.println(getClass().getName() + ".performAction: " + b.filename);	
			rpp = new RenderedPagePanel();
			rpp.display = display;
			rpp.printNews("TEMP/OEBPS/" + b.epub.getChapterList().get(0).reference);
			repaint();
			
		}
	}
	
	public void processSwipeEvent(SwipeEvent ev) 
	{
		// TODO Auto-generated method stub
		if (selectedShelve != null)
		{
			if ((ev.type == EventType.SwipeUp) || (ev.type == EventType.SwipeDown))
			{
				selectedShelve = null;
				update();
				repaint();
			}
		}
	}

}