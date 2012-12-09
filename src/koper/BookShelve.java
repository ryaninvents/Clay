package koper;
import java.awt.image.BufferedImage;
import java.util.LinkedList;


public class BookShelve 
{
	LinkedList<Book> bookList = new LinkedList<Book>();
	String name = null;
	BufferedImage shelveImage = null;
	
	public BookShelve(String name)
	{
		this.name = name;
	}
	
	public void addBook(String filename)
	{
		bookList.add(Book.getBook(filename));
	}

	public int getBookCount() 
	{
		// TODO Auto-generated method stub
		return bookList.size();
	}
	
	public void setIcon(BufferedImage bi)
	{
		shelveImage = bi;
	}
	
	public BufferedImage getIcon()
	{
		return shelveImage;
	}

}
