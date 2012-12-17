import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.mypapyri.clay.event.KoboTouchInput;
import com.mypapyri.clay.event.SwingMouseInput;
import com.mypapyri.clay.ui.App;
import com.ramuller.clay.display.Display;
import com.ramuller.clay.display.SwingDisplay;


public class ClayOS extends App{

	private static final long serialVersionUID = 3343633470257570334L;
	

	public ClayOS(Display d) {
		super(d);
		JPanel panel = this;
		panel.setBackground(Color.red);
		panel.setLayout(new BorderLayout());
		JButton button1,button2,button3,button4,button5;
		button1=new JButton("North");
		button2=new JButton("South");
		button3=new JButton("East");
		button4=new JButton("West");
		button5=new JButton("Center");

		panel.add(button1,BorderLayout.NORTH);
		panel.add(button2,BorderLayout.SOUTH);
		panel.add(button3,BorderLayout.EAST);
		panel.add(button4,BorderLayout.WEST);
		panel.add(button5,BorderLayout.CENTER);
		panel.getLayout().layoutContainer(panel);
		
		reflow();
	}
	

	public static void main(String[] args) {
		EInkFB fb = null;
		Display mainDisplay = null;
		ClayOS su;
		if (System.getProperty("user.home").length() < 4) {
			try {
				fb = new EInkFB("/dev/fb0");
				mainDisplay = new EInkDisplay(fb);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			catch (UnsatisfiedLinkError e) {
				e.printStackTrace();
				// System.out.println("Anscheinend kein EInk-Display vorhanden, deshalb Wechsel zu einem Swing-Display!");

			}

			su = new ClayOS(mainDisplay);

			KoboTouchInput kti = null;
			try {
				kti = new KoboTouchInput("/dev/input/event1");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			kti.addEventListener(su);
			kti.start();

		}

		if (mainDisplay == null) {
			mainDisplay = new SwingDisplay(600, 800);

			su = new ClayOS(mainDisplay);

			
			SwingMouseInput smi = new SwingMouseInput();
			((SwingDisplay)mainDisplay).icon.addMouseListener(smi);
			smi.addEventListener(su);
			

			su.repaint();
			/*
			 * SwingMouseInput smi = null; SwingKeyInput ski = null; smi = new
			 * SwingMouseInput(); ski = new SwingKeyInput();
			 * ((SwingDisplay)mainDisplay).frame.addMouseListener(smi);
			 * ((SwingDisplay)mainDisplay).frame.addMouseMotionListener(smi);
			 * ((SwingDisplay)mainDisplay).frame.addKeyListener(ski);
			 * smi.addEventListener(appMan); ski.addEventListener(appMan);
			 */

		}

	}
}
