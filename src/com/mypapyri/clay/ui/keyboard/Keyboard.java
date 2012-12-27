package com.mypapyri.clay.ui.keyboard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;

import com.mypapyri.clay.ui.Panel;

public class Keyboard extends Panel {
	private static final long serialVersionUID = 8867527218534567643L;
	
	public Keyboard(){
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton btnQ = new JButton("Q");
		GridBagConstraints gbc_btnQ = new GridBagConstraints();
		gbc_btnQ.fill = GridBagConstraints.BOTH;
		gbc_btnQ.insets = new Insets(0, 0, 5, 5);
		gbc_btnQ.gridx = 0;
		gbc_btnQ.gridy = 0;
		add(btnQ, gbc_btnQ);
		
		JButton btnW = new JButton("W");
		GridBagConstraints gbc_btnW = new GridBagConstraints();
		gbc_btnW.fill = GridBagConstraints.BOTH;
		gbc_btnW.insets = new Insets(0, 0, 5, 5);
		gbc_btnW.gridx = 1;
		gbc_btnW.gridy = 0;
		add(btnW, gbc_btnW);
		
		JButton btnE = new JButton("E");
		GridBagConstraints gbc_btnE = new GridBagConstraints();
		gbc_btnE.fill = GridBagConstraints.BOTH;
		gbc_btnE.insets = new Insets(0, 0, 5, 5);
		gbc_btnE.gridx = 2;
		gbc_btnE.gridy = 0;
		add(btnE, gbc_btnE);
		
		JButton btnR = new JButton("R");
		GridBagConstraints gbc_btnR = new GridBagConstraints();
		gbc_btnR.fill = GridBagConstraints.BOTH;
		gbc_btnR.insets = new Insets(0, 0, 5, 5);
		gbc_btnR.gridx = 3;
		gbc_btnR.gridy = 0;
		add(btnR, gbc_btnR);
		
		JButton btnT = new JButton("T");
		GridBagConstraints gbc_btnT = new GridBagConstraints();
		gbc_btnT.fill = GridBagConstraints.BOTH;
		gbc_btnT.insets = new Insets(0, 0, 5, 5);
		gbc_btnT.gridx = 4;
		gbc_btnT.gridy = 0;
		add(btnT, gbc_btnT);
		
		JButton btnY = new JButton("Y");
		GridBagConstraints gbc_btnY = new GridBagConstraints();
		gbc_btnY.fill = GridBagConstraints.BOTH;
		gbc_btnY.insets = new Insets(0, 0, 5, 5);
		gbc_btnY.gridx = 5;
		gbc_btnY.gridy = 0;
		add(btnY, gbc_btnY);
		
		JButton btnU = new JButton("U");
		GridBagConstraints gbc_btnU = new GridBagConstraints();
		gbc_btnU.fill = GridBagConstraints.BOTH;
		gbc_btnU.insets = new Insets(0, 0, 5, 5);
		gbc_btnU.gridx = 6;
		gbc_btnU.gridy = 0;
		add(btnU, gbc_btnU);
		
		JButton btnI = new JButton("I");
		GridBagConstraints gbc_btnI = new GridBagConstraints();
		gbc_btnI.fill = GridBagConstraints.BOTH;
		gbc_btnI.insets = new Insets(0, 0, 5, 5);
		gbc_btnI.gridx = 7;
		gbc_btnI.gridy = 0;
		add(btnI, gbc_btnI);
		
		JButton btnO = new JButton("O");
		GridBagConstraints gbc_btnO = new GridBagConstraints();
		gbc_btnO.fill = GridBagConstraints.BOTH;
		gbc_btnO.insets = new Insets(0, 0, 5, 5);
		gbc_btnO.gridx = 8;
		gbc_btnO.gridy = 0;
		add(btnO, gbc_btnO);
		
		JButton btnP = new JButton("P");
		GridBagConstraints gbc_btnP = new GridBagConstraints();
		gbc_btnP.fill = GridBagConstraints.BOTH;
		gbc_btnP.insets = new Insets(0, 0, 5, 0);
		gbc_btnP.gridx = 9;
		gbc_btnP.gridy = 0;
		add(btnP, gbc_btnP);
		
		JButton btnA = new JButton("A");
		GridBagConstraints gbc_btnA = new GridBagConstraints();
		gbc_btnA.fill = GridBagConstraints.BOTH;
		gbc_btnA.insets = new Insets(0, 0, 5, 5);
		gbc_btnA.gridx = 0;
		gbc_btnA.gridy = 1;
		add(btnA, gbc_btnA);
		
		JButton btnS = new JButton("S");
		GridBagConstraints gbc_btnS = new GridBagConstraints();
		gbc_btnS.fill = GridBagConstraints.BOTH;
		gbc_btnS.insets = new Insets(0, 0, 5, 5);
		gbc_btnS.gridx = 1;
		gbc_btnS.gridy = 1;
		add(btnS, gbc_btnS);
		
		JButton btnD = new JButton("D");
		GridBagConstraints gbc_btnD = new GridBagConstraints();
		gbc_btnD.fill = GridBagConstraints.BOTH;
		gbc_btnD.insets = new Insets(0, 0, 5, 5);
		gbc_btnD.gridx = 2;
		gbc_btnD.gridy = 1;
		add(btnD, gbc_btnD);
		
		JButton btnF = new JButton("F");
		GridBagConstraints gbc_btnF = new GridBagConstraints();
		gbc_btnF.fill = GridBagConstraints.BOTH;
		gbc_btnF.insets = new Insets(0, 0, 5, 5);
		gbc_btnF.gridx = 3;
		gbc_btnF.gridy = 1;
		add(btnF, gbc_btnF);
		
		JButton btnG = new JButton("G");
		GridBagConstraints gbc_btnG = new GridBagConstraints();
		gbc_btnG.fill = GridBagConstraints.BOTH;
		gbc_btnG.insets = new Insets(0, 0, 5, 5);
		gbc_btnG.gridx = 4;
		gbc_btnG.gridy = 1;
		add(btnG, gbc_btnG);
		
		JButton btnH = new JButton("H");
		GridBagConstraints gbc_btnH = new GridBagConstraints();
		gbc_btnH.fill = GridBagConstraints.BOTH;
		gbc_btnH.insets = new Insets(0, 0, 5, 5);
		gbc_btnH.gridx = 5;
		gbc_btnH.gridy = 1;
		add(btnH, gbc_btnH);
		
		JButton btnJ = new JButton("J");
		GridBagConstraints gbc_btnJ = new GridBagConstraints();
		gbc_btnJ.fill = GridBagConstraints.BOTH;
		gbc_btnJ.insets = new Insets(0, 0, 5, 5);
		gbc_btnJ.gridx = 6;
		gbc_btnJ.gridy = 1;
		add(btnJ, gbc_btnJ);
		
		JButton btnK = new JButton("K");
		GridBagConstraints gbc_btnK = new GridBagConstraints();
		gbc_btnK.fill = GridBagConstraints.BOTH;
		gbc_btnK.insets = new Insets(0, 0, 5, 5);
		gbc_btnK.gridx = 7;
		gbc_btnK.gridy = 1;
		add(btnK, gbc_btnK);
		
		JButton btnL = new JButton("L");
		GridBagConstraints gbc_btnL = new GridBagConstraints();
		gbc_btnL.fill = GridBagConstraints.BOTH;
		gbc_btnL.insets = new Insets(0, 0, 5, 5);
		gbc_btnL.gridx = 8;
		gbc_btnL.gridy = 1;
		add(btnL, gbc_btnL);
		
		JButton button_1 = new JButton("\u232b");
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 5, 0);
		gbc_button_1.fill = GridBagConstraints.BOTH;
		gbc_button_1.gridx = 9;
		gbc_button_1.gridy = 1;
		add(button_1, gbc_button_1);
		
		JButton btnShift = new JButton("\u21e7");
		GridBagConstraints gbc_btnShift = new GridBagConstraints();
		gbc_btnShift.fill = GridBagConstraints.BOTH;
		gbc_btnShift.insets = new Insets(0, 0, 5, 5);
		gbc_btnShift.gridx = 0;
		gbc_btnShift.gridy = 2;
		add(btnShift, gbc_btnShift);
		
		JButton btnZ = new JButton("Z");
		GridBagConstraints gbc_btnZ = new GridBagConstraints();
		gbc_btnZ.fill = GridBagConstraints.BOTH;
		gbc_btnZ.insets = new Insets(0, 0, 5, 5);
		gbc_btnZ.gridx = 1;
		gbc_btnZ.gridy = 2;
		add(btnZ, gbc_btnZ);
		
		JButton btnX = new JButton("X");
		GridBagConstraints gbc_btnX = new GridBagConstraints();
		gbc_btnX.fill = GridBagConstraints.BOTH;
		gbc_btnX.insets = new Insets(0, 0, 5, 5);
		gbc_btnX.gridx = 2;
		gbc_btnX.gridy = 2;
		add(btnX, gbc_btnX);
		
		JButton btnC = new JButton("C");
		GridBagConstraints gbc_btnC = new GridBagConstraints();
		gbc_btnC.fill = GridBagConstraints.BOTH;
		gbc_btnC.insets = new Insets(0, 0, 5, 5);
		gbc_btnC.gridx = 3;
		gbc_btnC.gridy = 2;
		add(btnC, gbc_btnC);
		
		JButton btnV = new JButton("V");
		GridBagConstraints gbc_btnV = new GridBagConstraints();
		gbc_btnV.fill = GridBagConstraints.BOTH;
		gbc_btnV.insets = new Insets(0, 0, 5, 5);
		gbc_btnV.gridx = 4;
		gbc_btnV.gridy = 2;
		add(btnV, gbc_btnV);
		
		JButton btnB = new JButton("B");
		GridBagConstraints gbc_btnB = new GridBagConstraints();
		gbc_btnB.fill = GridBagConstraints.BOTH;
		gbc_btnB.insets = new Insets(0, 0, 5, 5);
		gbc_btnB.gridx = 5;
		gbc_btnB.gridy = 2;
		add(btnB, gbc_btnB);
		
		JButton btnN = new JButton("N");
		GridBagConstraints gbc_btnN = new GridBagConstraints();
		gbc_btnN.fill = GridBagConstraints.BOTH;
		gbc_btnN.insets = new Insets(0, 0, 5, 5);
		gbc_btnN.gridx = 6;
		gbc_btnN.gridy = 2;
		add(btnN, gbc_btnN);
		
		JButton btnM = new JButton("M");
		GridBagConstraints gbc_btnM = new GridBagConstraints();
		gbc_btnM.fill = GridBagConstraints.BOTH;
		gbc_btnM.insets = new Insets(0, 0, 5, 5);
		gbc_btnM.gridx = 7;
		gbc_btnM.gridy = 2;
		add(btnM, gbc_btnM);
		
		JButton btnComma = new JButton(",");
		GridBagConstraints gbc_btnComma = new GridBagConstraints();
		gbc_btnComma.fill = GridBagConstraints.BOTH;
		gbc_btnComma.insets = new Insets(0, 0, 5, 5);
		gbc_btnComma.gridx = 8;
		gbc_btnComma.gridy = 2;
		add(btnComma, gbc_btnComma);
		
		JButton btnPeriod = new JButton(".");
		GridBagConstraints gbc_btnPeriod = new GridBagConstraints();
		gbc_btnPeriod.fill = GridBagConstraints.BOTH;
		gbc_btnPeriod.insets = new Insets(0, 0, 5, 0);
		gbc_btnPeriod.gridx = 9;
		gbc_btnPeriod.gridy = 2;
		add(btnPeriod, gbc_btnPeriod);
		
		JButton button = new JButton("...");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.BOTH;
		gbc_button.insets = new Insets(0, 0, 0, 5);
		gbc_button.gridx = 0;
		gbc_button.gridy = 3;
		add(button, gbc_button);
		
		JButton btnSpace = new JButton(" ");
		GridBagConstraints gbc_btnSpace = new GridBagConstraints();
		gbc_btnSpace.gridwidth = 8;
		gbc_btnSpace.fill = GridBagConstraints.BOTH;
		gbc_btnSpace.insets = new Insets(0, 0, 0, 5);
		gbc_btnSpace.gridx = 1;
		gbc_btnSpace.gridy = 3;
		add(btnSpace, gbc_btnSpace);
		
		JButton btnRet = new JButton("\u21b5");
		GridBagConstraints gbc_btnRet = new GridBagConstraints();
		gbc_btnRet.fill = GridBagConstraints.BOTH;
		gbc_btnRet.gridx = 9;
		gbc_btnRet.gridy = 3;
		add(btnRet, gbc_btnRet);
	}
	
}
