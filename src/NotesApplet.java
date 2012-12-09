


public class NotesApplet extends Applet 
{

	@Override
	public void init()
	{
		// TODO Auto-generated method stub
		setIcon("notes_bold.png");
		
//		b2 = new Button(new Rectangle( 210, 210, 150, 150), "", "icon_calendar.png");
//		mainPanel.add(b2);
//		b2.setVisible();
		setMainPanel(new NotesPanel());

	}

	@Override
	public void event(KeyEvent ev) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Notes";
	}

}
