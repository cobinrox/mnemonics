package com.clowngineering.cheat;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Textpain {
	JTextPane textPane = new JTextPane();
	public static void main(String args[]) throws Throwable
	{
		Textpain tp = new Textpain();
		tp.go();
	}
	public void go() throws Throwable
	{
		JFrame jf = new JFrame("THIS IS A TEST HOO HAH A");
		JPanel jp = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx=0; gbc.gridy=0;
		jp.add(new JLabel("Your text"),gbc);
		
		gbc.gridx++;
		jp.add(textPane,gbc);
		jf.getContentPane().add(jp);
		
		
	textPane.setText( "original text" );
	StyledDocument doc = textPane.getStyledDocument();

	//  Define a keyword attribute

	SimpleAttributeSet keyWord = new SimpleAttributeSet();
	StyleConstants.setForeground(keyWord, Color.RED);
	StyleConstants.setBackground(keyWord, Color.YELLOW);
	StyleConstants.setBold(keyWord, true);


	    doc.insertString(0, "Start of text\n", null );
	    doc.insertString(doc.getLength(), "\nEnd of text", keyWord );
	    jf.pack();
	    jf.setVisible(true);
	}
}
