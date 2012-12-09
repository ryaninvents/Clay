package koper;


public class BookSelectAction extends Action 
{
	Book selectedBook = null;
	
	public BookSelectAction(Book b)
	{
		selectedBook = b;
	}
}
