import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.plaf.metal.MetalLookAndFeel;

import au.notzed.fb.EInkFB;

import com.mypapyri.clay.ClaySystem;
import com.mypapyri.clay.display.Display;
import com.mypapyri.clay.display.SwingDisplay;
import com.mypapyri.clay.event.KoboTouchInput;
import com.mypapyri.clay.event.SwingMouseInput;
import com.mypapyri.clay.ui.App;
import com.mypapyri.clay.ui.Button;
import com.mypapyri.clay.ui.TextField;
import com.mypapyri.clay.ui.laf.ClayTheme;

public class Calc extends App implements ActionListener{

	private static final long serialVersionUID = 3343633470257570334L;
	
	TextField textField;
	Button buttonEquals;
	Button buttonClear;
	ScriptEngine engine;
	Button btnRefresh;
	Button btnSqrt;
	
	JLabel label;

	public Calc() {
		super();
		
		engine = new ScriptEngineManager().getEngineByName("JavaScript");
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		label = new JLabel("=");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.BOTH;
		gbc_label.gridwidth = 5;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		add(label, gbc_label);
		
		textField = new TextField();
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 5;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		add(textField, gbc_textField);
		textField.setColumns(10);
		
		buttonClear = new Button("C");
		GridBagConstraints gbc_buttonC = new GridBagConstraints();
		gbc_buttonC.fill = GridBagConstraints.BOTH;
		gbc_buttonC.insets = new Insets(0, 0, 5, 5);
		gbc_buttonC.gridx = 0;
		gbc_buttonC.gridy = 2;
		add(buttonClear, gbc_buttonC);
		
		JButton buttonOpenParen = new JButton("(");
		GridBagConstraints gbc_buttonOpenParen = new GridBagConstraints();
		gbc_buttonOpenParen.fill = GridBagConstraints.BOTH;
		gbc_buttonOpenParen.insets = new Insets(0, 0, 5, 5);
		gbc_buttonOpenParen.gridx = 1;
		gbc_buttonOpenParen.gridy = 2;
		add(buttonOpenParen, gbc_buttonOpenParen);
		
		JButton buttonCloseParen = new JButton(")");
		GridBagConstraints gbc_buttonCloseParen = new GridBagConstraints();
		gbc_buttonCloseParen.fill = GridBagConstraints.BOTH;
		gbc_buttonCloseParen.insets = new Insets(0, 0, 5, 5);
		gbc_buttonCloseParen.gridx = 2;
		gbc_buttonCloseParen.gridy = 2;
		add(buttonCloseParen, gbc_buttonCloseParen);
		
		JButton buttonDivide = new JButton("/");
		GridBagConstraints gbc_buttonDivide = new GridBagConstraints();
		gbc_buttonDivide.fill = GridBagConstraints.BOTH;
		gbc_buttonDivide.insets = new Insets(0, 0, 5, 5);
		gbc_buttonDivide.gridx = 3;
		gbc_buttonDivide.gridy = 2;
		add(buttonDivide, gbc_buttonDivide);
		
		JButton buttonMultiply = new JButton("*");
		GridBagConstraints gbc_buttonMultiply = new GridBagConstraints();
		gbc_buttonMultiply.fill = GridBagConstraints.BOTH;
		gbc_buttonMultiply.insets = new Insets(0, 0, 5, 0);
		gbc_buttonMultiply.gridx = 4;
		gbc_buttonMultiply.gridy = 2;
		add(buttonMultiply, gbc_buttonMultiply);
		
		btnSqrt = new Button("\u221a");
		GridBagConstraints gbc_btnSqrt = new GridBagConstraints();
		gbc_btnSqrt.fill = GridBagConstraints.BOTH;
		gbc_btnSqrt.insets = new Insets(0, 0, 5, 5);
		gbc_btnSqrt.gridx = 0;
		gbc_btnSqrt.gridy = 3;
		add(btnSqrt, gbc_btnSqrt);
		
		JButton button7 = new JButton("7");
		GridBagConstraints gbc_button7 = new GridBagConstraints();
		gbc_button7.fill = GridBagConstraints.BOTH;
		gbc_button7.insets = new Insets(0, 0, 5, 5);
		gbc_button7.gridx = 1;
		gbc_button7.gridy = 3;
		add(button7, gbc_button7);
		
		JButton button8 = new JButton("8");
		GridBagConstraints gbc_button8 = new GridBagConstraints();
		gbc_button8.fill = GridBagConstraints.BOTH;
		gbc_button8.insets = new Insets(0, 0, 5, 5);
		gbc_button8.gridx = 2;
		gbc_button8.gridy = 3;
		add(button8, gbc_button8);
		
		JButton button9 = new JButton("9");
		GridBagConstraints gbc_button9 = new GridBagConstraints();
		gbc_button9.fill = GridBagConstraints.BOTH;
		gbc_button9.insets = new Insets(0, 0, 5, 5);
		gbc_button9.gridx = 3;
		gbc_button9.gridy = 3;
		add(button9, gbc_button9);
		
		JButton buttonMinus = new JButton("-");
		GridBagConstraints gbc_buttonMinus = new GridBagConstraints();
		gbc_buttonMinus.fill = GridBagConstraints.BOTH;
		gbc_buttonMinus.insets = new Insets(0, 0, 5, 0);
		gbc_buttonMinus.gridx = 4;
		gbc_buttonMinus.gridy = 3;
		add(buttonMinus, gbc_buttonMinus);
		
		JButton button4 = new JButton("4");
		GridBagConstraints gbc_button4 = new GridBagConstraints();
		gbc_button4.fill = GridBagConstraints.BOTH;
		gbc_button4.insets = new Insets(0, 0, 5, 5);
		gbc_button4.gridx = 1;
		gbc_button4.gridy = 4;
		add(button4, gbc_button4);
		
		JButton button5 = new JButton("5");
		GridBagConstraints gbc_button5 = new GridBagConstraints();
		gbc_button5.fill = GridBagConstraints.BOTH;
		gbc_button5.insets = new Insets(0, 0, 5, 5);
		gbc_button5.gridx = 2;
		gbc_button5.gridy = 4;
		add(button5, gbc_button5);
		
		JButton button6 = new JButton("6");
		GridBagConstraints gbc_button6 = new GridBagConstraints();
		gbc_button6.fill = GridBagConstraints.BOTH;
		gbc_button6.insets = new Insets(0, 0, 5, 5);
		gbc_button6.gridx = 3;
		gbc_button6.gridy = 4;
		add(button6, gbc_button6);
		
		JButton buttonPlus = new JButton("+");
		GridBagConstraints gbc_buttonPlus = new GridBagConstraints();
		gbc_buttonPlus.fill = GridBagConstraints.BOTH;
		gbc_buttonPlus.insets = new Insets(0, 0, 5, 0);
		gbc_buttonPlus.gridx = 4;
		gbc_buttonPlus.gridy = 4;
		add(buttonPlus, gbc_buttonPlus);
		
		JButton button1 = new JButton("1");
		GridBagConstraints gbc_button1 = new GridBagConstraints();
		gbc_button1.fill = GridBagConstraints.BOTH;
		gbc_button1.insets = new Insets(0, 0, 5, 5);
		gbc_button1.gridx = 1;
		gbc_button1.gridy = 5;
		add(button1, gbc_button1);
		
		JButton button2 = new JButton("2");
		GridBagConstraints gbc_button2 = new GridBagConstraints();
		gbc_button2.fill = GridBagConstraints.BOTH;
		gbc_button2.insets = new Insets(0, 0, 5, 5);
		gbc_button2.gridx = 2;
		gbc_button2.gridy = 5;
		add(button2, gbc_button2);
		
		JButton button3 = new JButton("3");
		GridBagConstraints gbc_button3 = new GridBagConstraints();
		gbc_button3.fill = GridBagConstraints.BOTH;
		gbc_button3.insets = new Insets(0, 0, 5, 5);
		gbc_button3.gridx = 3;
		gbc_button3.gridy = 5;
		add(button3, gbc_button3);
		
		buttonEquals = new Button("=");
		GridBagConstraints gbc_buttonEquals = new GridBagConstraints();
		gbc_buttonEquals.fill = GridBagConstraints.BOTH;
		gbc_buttonEquals.gridheight = 2;
		gbc_buttonEquals.gridx = 4;
		gbc_buttonEquals.gridy = 5;
		add(buttonEquals, gbc_buttonEquals);
		
		btnRefresh = new Button("\u21e3");
		btnRefresh.setText("Rfsh");
		GridBagConstraints gbc_btnRefresh = new GridBagConstraints();
		gbc_btnRefresh.fill = GridBagConstraints.BOTH;
		gbc_btnRefresh.insets = new Insets(0, 0, 0, 5);
		gbc_btnRefresh.gridx = 0;
		gbc_btnRefresh.gridy = 6;
		add(btnRefresh, gbc_btnRefresh);
		btnRefresh.addActionListener(this);
		
		JButton button0 = new JButton("0");
		GridBagConstraints gbc_button0 = new GridBagConstraints();
		gbc_button0.fill = GridBagConstraints.BOTH;
		gbc_button0.gridwidth = 2;
		gbc_button0.insets = new Insets(0, 0, 0, 5);
		gbc_button0.gridx = 1;
		gbc_button0.gridy = 6;
		add(button0, gbc_button0);
		
		JButton buttonPeriod = new JButton(".");
		GridBagConstraints gbc_buttonPeriod = new GridBagConstraints();
		gbc_buttonPeriod.fill = GridBagConstraints.BOTH;
		gbc_buttonPeriod.insets = new Insets(0, 0, 0, 5);
		gbc_buttonPeriod.gridx = 3;
		gbc_buttonPeriod.gridy = 6;
		add(buttonPeriod, gbc_buttonPeriod);

		buttonClear.addActionListener(this);
		buttonCloseParen.addActionListener(this);
		buttonOpenParen.addActionListener(this);
		buttonDivide.addActionListener(this);
		buttonMultiply.addActionListener(this);
		buttonMinus.addActionListener(this);
		buttonPlus.addActionListener(this);
		buttonPeriod.addActionListener(this);
		button0.addActionListener(this);
		button3.addActionListener(this);
		button2.addActionListener(this);
		button1.addActionListener(this);
		button6.addActionListener(this);
		button5.addActionListener(this);
		button4.addActionListener(this);
		button9.addActionListener(this);
		button8.addActionListener(this);
		button7.addActionListener(this);
		buttonEquals.addActionListener(this);
		btnSqrt.addActionListener(this);
		
		textField.setFont(ClaySystem.getFont("Comfortaa-Regular",48));
		
		reflow();
	}
	

	public static void main(String[] args) {
		MetalLookAndFeel.setCurrentTheme(new ClayTheme());
		EInkFB fb = null;
		Display display = null;
		if (System.getProperty("user.home").length() < 4) {
			try {
				fb = new EInkFB("/dev/fb0");
				display = new EInkDisplay(fb);

			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}

			App.setDisplay(display);
			Calc su = new Calc();
			ClaySystem.setActiveApp(su);

			KoboTouchInput kti = null;
			try {
				kti = new KoboTouchInput("/dev/input/event1");
			} catch (IOException e) {
				e.printStackTrace();
			}
			kti.addListener(su);
			kti.start();
			
			try{
				while(true){
					su.repaint();
					Thread.sleep(1000);
				}
			}catch(Exception e){
				
			}

		}

		if (display == null) {
			display = new SwingDisplay(600, 800);

			while(display==null){
				System.out.print('.');
			}

			App.setDisplay(display);
			Calc su = new Calc();
			ClaySystem.setActiveApp(su);
			
			while(su.getDisplay()==null){
				System.out.print('.');
				su.repaint();
			}


			SwingMouseInput smi = new SwingMouseInput();
			((SwingDisplay)display).icon.addMouseListener(smi);
			smi.addListener(su);
			
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


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==buttonEquals){
			String str = textField.getText();
			label.setText(str+"=");
			str = str.replace("\u221a", "Math.sqrt");
			try {
				Object out = engine.eval(str);
				if(out!=null)
					textField.setText(out.toString());
			} catch (ScriptException e1) {
				label.setText("Invalid expression, wah wah.");
				textField.setText("");
			}
		}else if(e.getSource()==btnRefresh){
			getDisplay().clear();
		}else if(e.getSource()==buttonClear){
			textField.setText("");
		}else{
			textField.setText(textField.getText()+((JButton)e.getSource()).getText());
		}
		//text.setCaretPosition(3);
	}
}
