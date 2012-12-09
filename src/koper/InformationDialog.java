package koper;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;



public class InformationDialog extends ModalDialog implements ButtonListener
{

	Button okButton = null;
	Label titleLabel = null; 
	Label messageLabel = null;
	ContentPane cp = null;
	
	public InformationDialog(String title, String message)
	{
		super();
		getContentPane().setBgColor(Color.LIGHT_GRAY);
		opaque = true;
		cp = new ContentPane(new Rectangle(50, 100, 500, 600));
		cp.hasBorder = true;
		titleLabel = new Label(new Rectangle(50,100,400,40), title, new Font("Arial", Font.BOLD, 40), Label.Alignment.CENTER);
		cp.add(titleLabel);
		messageLabel = new Label(new Rectangle(20,200,460,40), message,  new Font("Arial", Font.PLAIN, 18));
		cp.add(messageLabel);
		okButton = new Button(new Rectangle(100,400,200,40), "OK");
		okButton.addButtonListener(this);
		cp.add(okButton);
		getContentPane().add(cp);
	}

	@Override
	public void buttonClicked(Button source) 
	{
		// TODO Auto-generated method stub
		if (source == okButton)
		{
			System.out.println("OK pressed");
			closeDialog();
		}
	}

	@Override
	public void buttonLongClicked(Button source) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
