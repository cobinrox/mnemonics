package com.clowngineering.cheat.model;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.text.StyledDocument;

import com.clowngineering.cheat.Utils;
class CheatSubTopic
{
	public String subTopic;
}
public class CheatDefinition implements Serializable {

	CheatSubTopic subTopic;
	CheatTitle  title;
	String notes;
	String htmlNotes;
	String pre;
	String htmlPre;
	String image;
	String htmlImage;
	CheatBook book;
	Chapter chapter;
	//String id;
	//String getTitle() { return title.getTitle(); }
	static boolean didCancel;
	public static CheatDefinition createCheat(Component parentComponent, CheatBook book, Chapter chapter, String longCheatName)
	{
		try
		{
			CheatDefinition cd = new CheatDefinition();
			CheatTitle ct = new CheatTitle();
			ct.title = longCheatName;
			cd.title = ct;
			cd.chapter = chapter;
			cd.book = book;

			cd.buildUI(cd);
			didCancel = false;
			cd.jd.setVisible(true);
			if( didCancel ) return null;
			ct.title = cd.titleTF.getText().trim();
			ct.id = Utils.makeSafeFileName(cd.titleTF.getText().trim());
			cd.notes = cd.textTP.getText();
			return cd;
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
		return null;
	}
	public String getHtmlValue()
	{
		return notes;
				
	}
	public CheatTitle getTitle()
	{
		return title;
	}
	protected void buildUI(CheatDefinition cd) throws Throwable
	{
		buildTextStuff(cd);
		
		jd.setTitle("Fill in Cheat Text");
		jd.getContentPane().add(jp);
		jd.setModal(true);
		jd.pack();
		
	}
	JDialog    jd     = new JDialog();
	JPanel     jp;
	JTextField bookTF = new JTextField(" ",30);
	JTextField chapTF = new JTextField(" ",30);
	JTextField titleTF = new JTextField(" ",30);
	JTextPane  textTP = new JTextPane();
	JButton    okBT   = new JButton("OK");
	JButton    canBT  = new JButton("Cancel");
	
	protected void buildTextStuff(CheatDefinition cd) throws Throwable
	{
		jp = new JPanel (new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2,2,2,2);
		
		// BOOK LABEL
		gbc.gridx=0;gbc.gridy=0;gbc.anchor=gbc.EAST;
		JLabel jl = new JLabel("Book Title:");
		jp.add(jl, gbc);
		
		// BOOK Text
		gbc.gridx++;gbc.anchor=gbc.WEST;
		bookTF.setText(cd.book.title);
		jp.add(bookTF,gbc);
		
		// CHAPTER label
		gbc.gridx=0;gbc.gridy++;gbc.anchor=gbc.EAST;
		jl = new JLabel("Chapter Title:");
		jp.add(jl,gbc);
		
		// Chapter Name
		gbc.gridx++;gbc.anchor=gbc.WEST;
		chapTF.setText(cd.chapter.chapTitle);
		jp.add(chapTF,gbc);
		
		// Cheat Name label
		gbc.gridx=0; gbc.gridy++;gbc.anchor=gbc.EAST;
		jl = new JLabel("Cheat Title:");
		jp.add(jl,gbc);
		
		// Cheat Name value
		gbc.gridx++;gbc.anchor=gbc.WEST;
		titleTF.setText(cd.getTitle().getTitle());
		jp.add(titleTF,gbc);
		
		
		// Cheat text label
		gbc.gridx=0;gbc.gridy++;gbc.anchor=gbc.EAST;
		jl = new JLabel("Cheat Text:");
		jp.add(jl,gbc);
		
		// Cheat text
		gbc.gridx++;gbc.anchor=gbc.WEST;
		gbc.fill=GridBagConstraints.BOTH;//HORIZONTAL;
        gbc.weightx=1; gbc.weighty=1;
        textTP.setFont(Utils.MONO_FONT);
        textTP.setBorder(new LineBorder(null));
		//textTP.setBounds(0,0,200,400);
		//textTP.setPreferredSize(new Dimension(200,400));
		StyledDocument doc = textTP.getStyledDocument();
		doc.insertString(0,
		 "This cheat is intended to blah...blahh blahh blah blah blah blah blah yak<br \\>\n" +
		 "<br \\>\n"+
		 "<pre><br \\>\n"+
		 "   int i = 0;\n"+
		 "   int k = 0;\n"+
		 "</pre><br \\>\n"+
		 "<br \\>\n", null );

		jp.add(textTP,gbc);
		
		// ok button
		okBT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				didCancel = false;
				jd.setVisible(false);
			}
		});
		JPanel btPn = new JPanel();
		gbc.gridx=1;gbc.gridy++;gbc.anchor=gbc.WEST;
		gbc.fill=GridBagConstraints.NONE;
        gbc.weightx=0;
        btPn.add(okBT);
		
		// cancel button
		//gbc.gridx++;
        canBT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				didCancel = true;
				jd.setVisible(false);

			}
		});
		btPn.add(canBT);
		
		jp.add(btPn,gbc);
		
		
		
	}

}
