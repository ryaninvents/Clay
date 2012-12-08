import java.awt.Rectangle;



public class ModalDialog extends AppletPanel
{

	public ModalDialog() 
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	protected void showModal()
	{
		AppManager.getAppManager().showModalDialog(this);
	}

	protected void closeDialog()
	{
		AppManager.getAppManager().closeModalDialog(this);
	}

}
