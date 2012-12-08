import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;



public class SwingKeyInput extends SwingEventInput implements KeyListener
{
	
	public void addEventListener(KeyEventListener el)
	{
		super.addEventListener(el);
	}
	

	@Override
	public void keyTyped(java.awt.event.KeyEvent e)
	{
		// TODO Auto-generated method stub
		System.out.println("keyTyped");
		Logger.getLogger(getClass().getName()).log(Level.INFO, e.toString());
		if (e.getKeyChar() == ' ')
			event(new KeyEvent(EventType.HomeButton, System.currentTimeMillis(), e.getKeyChar()));
		else if (e.getKeyChar() == java.awt.event.KeyEvent.VK_ESCAPE)
			event(new KeyEvent(EventType.PowerButton, System.currentTimeMillis(), e.getKeyChar()));
		
	}

	
	@Override
	public void keyPressed(java.awt.event.KeyEvent e)
	{
		// TODO Auto-generated method stub
		System.out.println("keyPressed");
		
	}


	@Override
	public void keyReleased(java.awt.event.KeyEvent e)
	{
		// TODO Auto-generated method stub
		System.out.println("keyReleased");
		
	}

}
