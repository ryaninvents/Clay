package koper;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import javax.imageio.ImageIO;


public class Book 
{

	String filename = null;
	String name = null;
	String author = null;
	BufferedImage cover = null;
	EPub epub;
	Integer bookID = 0;

	static HashMap<String, Book> booklist = new HashMap<String, Book>();	
	static LinkedList<Book> booklistByID = new LinkedList<Book>();
	
	static Book getBook(String filename)
	{
		if (booklist.containsKey(filename))
			return booklist.get(filename);
		else
		{
			Book b = new Book(filename);
			booklist.put(filename, b);
			booklistByID.add(b);
			return b;
		}
	}
	
	static Book getBookByID(Integer id)
	{
		if (id < booklistByID.size())
			return booklistByID.get(id -1);
		else
			return null;
	}
	private Book(Integer id)
	{
		bookID = id;
	}
	
	
	private Book(String filename)
	{
		this.filename = filename;
		try 
		{
			bookID = newBookID();
			epub = new EPub(filename);
			name = epub.getTitle();
			author = epub.getAuthor();
			cover = epub.getCoverImage();
			if (cover == null)
			{
				java.net.URL url = this.getClass().getResource("no_cover.png");
			    try 
			    {
					cover = ImageIO.read(url);
				} 
			    catch (IOException e) 
			    {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			// copy file in 
			File imagefile = new File(Configuration.getDirectory() + File.separator + Configuration.bookRepositoryDir + File.separator + bookID.toString() + ".png");
			try 
			{
				ImageIO.write(cover, "PNG", new FileOutputStream(imagefile));
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public int newBookID()
	{
		Configuration.bookID++;
		return Configuration.bookID;
	}
	
	public static void loadList()
	{
		File bookfile = new File(Configuration.getDirectory() + File.separator + "booklist.xml");
		if (bookfile.exists())
		{
			try 
			{
				BufferedReader br = new BufferedReader(new FileReader(bookfile));
				String line;
				while ((line = br.readLine()) != null)
				{
					Integer id = Integer.valueOf(line.substring(0, line.indexOf("--")));
					String filename = line.substring(line.indexOf("--") + 2);
					Book b = new Book(id);
					b.filename = filename;
					java.net.URL url = Book.class.getResource("no_cover.png");
				    try 
				    {
						b.cover = ImageIO.read(url);
					} 
				    catch (IOException e) 
				    {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					booklist.put(filename, b);
					booklistByID.add(b);
				}
				
			} 
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	

	public static void saveList() 
	{
		File bookfile = new File(Configuration.getDirectory() + File.separator + "booklist.xml");
		try 
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(bookfile));
			for (Book b : booklistByID)
			{
				bw.write(b.bookID + "--" + b.filename + "\n");
			}
			bw.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
