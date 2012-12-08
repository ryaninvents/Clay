import java.awt.Graphics2D;
import java.awt.Rectangle;





public class NotesPanel extends AppletPanel implements MenuItemListener 
{

	TextField titleField = null;
	TextArea textField = null;
	MenuItem newItem = null;
	MenuItem saveItem = null;
	MenuItem cancelItem = null;
	MenuBar menuBar = null;
	
	public NotesPanel()
	{
		super();
		// TODO Auto-generated constructor stub
		showKeyboard(false);
		menuBar = new MenuBar();
		opaque = false;
		getContentPane().add(new Label(new Rectangle(10, 10, 50, 20), "Title"));
		titleField = new TextField(new Rectangle(60, 10, 530, 30), "");
		getContentPane().add(titleField);
		textField = new TextArea(new Rectangle(10, 50, 580, 400), "");
		getContentPane().add(textField);
		newItem = new MenuItem("New");
		newItem.addMenuItemListener(this);
		menuBar.add(newItem);
		menuBar.add(new MenuSpacer());
		saveItem = new MenuItem("Save");
		saveItem.addMenuItemListener(this);
		menuBar.add(saveItem);
		menuBar.add(new MenuSpacer());
		cancelItem = new MenuItem("Cancel");
		cancelItem.addMenuItemListener(this);
		menuBar.add(cancelItem);
		textField.showCursor = true;
		vKeyboard.addKeyEventListener(textField);
		setMenuBar(menuBar);
	}

	public void paint(Graphics2D gg)
	{
		if (Configuration.debugPaint)
			System.out.println("NotesPanel.paint");
		super.paint(gg);
	}

	@Override
	public void menuItemClicked(MenuItem source) 
	{
		// TODO Auto-generated method stub
		if (source == newItem)
		{
			System.out.println("NewItem clicked");
			textField.fieldText = "";
			repaint();
		}
		else if (source == saveItem)
			System.out.println("SaveItem clicked");
		else if (source == cancelItem)
			System.out.println("CancelItem clicked");
		
	}

}
