/**
 * 
 */



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

import com.ramuller.sumerian.display.Display;
import com.ramuller.sumerian.display.SwingDisplay;


/**
 * @author ryan
 *
 */
public class Sumerian extends JPanel implements ActionListener {
	Display display;
	JCheckBox checkbox;
	JEditorPane html;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4228329570825316212L;
	
	public Sumerian(Display display){
		this.setSize(display.getWidth(), display.getHeight());
		this.setPreferredSize(
				new Dimension(display.getWidth(), display.getHeight()));
		this.display = display;
		
		this.setLayout(new BorderLayout());

        checkbox = new JCheckBox("Show Title", true);
        checkbox.setSize(300, 50);
        this.add(checkbox,BorderLayout.NORTH);
        
        //try {
        	//URL start = getClass().getClassLoader().getResource("index.html");
			//html = new JEditorPane(start);
        	html = new JEditorPane();
			html.setSize(new Dimension(600,800));
			html.setPreferredSize(new Dimension(600,800));
			this.add(html,BorderLayout.CENTER);
			
		/*} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
	}
	
	public void redraw(){
		Graphics2D gg = (Graphics2D) display.graphics.create();

		/*Image im = html.createImage(600, 800);
		gg.drawImage(im, 0, 0, null);*/
		
		html.repaint();
		gg.setClip(0,0,600,800);
		gg.setColor(getBackground());
		gg.fillRect(0,0,600,800);  
		html.paint(gg); 
		display.repaint();
	}
	
	public void paint(Graphics2D g){
		super.paintAll((Graphics)g);
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {	    
		EInkFB fb = null;
		Display mainDisplay = null;
		Sumerian su;
		if(System.getProperty("user.home").length()<4){
			try
			{
				fb = new EInkFB("/dev/fb0");
				mainDisplay = new EInkDisplay(fb);
				
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			catch (UnsatisfiedLinkError e)
			{
				e.printStackTrace();
	//			System.out.println("Anscheinend kein EInk-Display vorhanden, deshalb Wechsel zu einem Swing-Display!");
				
			}
		}
		
		if (mainDisplay == null)
		{
			
			mainDisplay = new SwingDisplay(600,800);
			/*
			SwingMouseInput smi = null;
			SwingKeyInput ski = null;
			smi = new SwingMouseInput();
			ski = new SwingKeyInput();
			((SwingDisplay)mainDisplay).frame.addMouseListener(smi);
			((SwingDisplay)mainDisplay).frame.addMouseMotionListener(smi);
			((SwingDisplay)mainDisplay).frame.addKeyListener(ski);
			smi.addEventListener(appMan);
			ski.addEventListener(appMan);*/
			
		}

		su = new Sumerian(mainDisplay);
		try{Thread.sleep(1000);}catch(Exception e){}
		su.redraw();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
