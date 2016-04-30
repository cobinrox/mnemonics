package com.clowngineering.cheat.model;

import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.clowngineering.cheat.Utils;

public class Chapter implements Serializable{

	String chapTitle;
	public String getChapTitle() {
		return chapTitle;
	}
	public void setChapTitle(String chapTitle) {
		this.chapTitle = chapTitle;
	}
	String id;
	File   chapFile;
	CheatBook book;
	List<CheatSubTopic> subTopics = new ArrayList<CheatSubTopic>();
	List<CheatDefinition> cheats = new ArrayList<CheatDefinition>();
	
	// chapter looks like this in xhtml
	/*
	 * <p id="Admin" class="eg">A D M I N I S T R A T I O N&nbsp;&nbsp;&nbsp;C H E A T S</p><hr />
       <br />
       <br />

	 */
	
	final static String CHT_CHAPTER_PREFIX = "<p id=\"";
	final static String CHT_CHAPTER_SUFFIX = "</p><hr />";
	
	public void write()
	{
		FileWriter fw = null;
		try
		{
			fw = new FileWriter(chapFile.getAbsolutePath());
			fw.write(Xhtml.xmlDef);
			fw.write(Xhtml.htmlDef);
			
			fw.write(Xhtml.headPrefix);
			
			fw.write(Xhtml.headTitlePrefix);
			fw.write(chapTitle);
			fw.write(Xhtml.headTitleSuffix);
			
			fw.write(Xhtml.headLink1Prefix);
			fw.write(getRelativeCssDir());
			fw.write(Xhtml.headLink1Suffix);
			
			fw.write(Xhtml.headLink2Prefix);
			fw.write(getRelativeCssDir());
			fw.write(Xhtml.headLink2Suffix);
			
			fw.write(Xhtml.headSuffix);
			
			fw.write(Xhtml.bodyPrefix);
			
			/*   D I V */
			fw.write(Xhtml.div1);fw.write("\n");
			
			/*  C H A P T E R    T I T L E  */
			fw.write("<p id=\"" + id + "\" class=\"eg\">");
			fw.write(Utils.spreadString(chapTitle));
			fw.write("</p><hr />\n");
			fw.write(Xhtml.br);
			fw.write(Xhtml.br);
			
			
			
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
		finally
        {
            if( fw != null )
            {
                try{fw.close();}catch(Throwable t){}
            }
        }
	}
	public String getRelativeCssDir()
	{
		if( chapFile == null ) return "";
		//                               t
		// C:/kindle/bookName/OEBPS/Text/SomeChapter.xhtml
		// C:/kindle/bookName/OEBPS/Text/SomeSubject/SomeChapter.xhtml
		int t = chapFile.getAbsolutePath().indexOf("Text")+5;
		String x = chapFile.getAbsolutePath().substring(t);
		
		String regFileSep = Pattern.quote(File.separator);//"[" + File.separator +"]";
		int numSubDirs = x.split(regFileSep).length-1;
		x = ".." + "/";
		for( int i=0; i< numSubDirs; i++)
		  x+="..." + "/";
		x+="Styles";
		return x;
	}
	public File getChapFile()
	{
		return chapFile;
	}
	public void addCheat(CheatDefinition cd)
	{
		if(cd != null )
			cheats.add(cd);
	}
	public static Chapter createChapter(Component parentComponent, CheatBook book, String longChapName)
	{
		
		File chapFile = buildChapterFile(parentComponent,book,longChapName);
		if( chapFile == null ) return null;
		
		Chapter cb = new Chapter();
		cb.chapTitle = longChapName;
		cb.chapFile = chapFile;
		cb.book = book;
		cb.id = Utils.makeSafeFileName(longChapName);
		return cb;
	}
	public static File buildChapterFile(Component parentComponent, CheatBook book, String longChapName)
	{
		try
		{
			String safeName = Utils.makeSafeFileName(longChapName) + ".xhtml";
			File chapDir = new File(book.dir + File.separator + "OEBPS" + File.separator + "Text");
			File chapFile = new File(chapDir,safeName);
		
			if(!chapFile.exists())
			{
				int i = JOptionPane.showConfirmDialog(parentComponent, 
															"OK to create \n" +
															safeName + "\n" +
						                                   "for chapter\n" +
						                                    "\""+longChapName + "\" ?\n",
			                                                "Build New Chapter File", 
			                                                JOptionPane.YES_NO_CANCEL_OPTION, 
			                                                JOptionPane.QUESTION_MESSAGE);
				if( i != JOptionPane.YES_OPTION ) return null;
				chapFile.createNewFile();
			}	
			return chapFile;
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
		return null;
	}

	
	public String getTitle() { return chapTitle; }
	/*
	public String getId()
	{
		StringBuffer sb = new StringBuffer("");
		for( int i= 0 ;i < title.length() && i <Utils.MAX_ID_LENGTH; i++)
		{
			String s = title.substring(i);
			if(  Utils.isSafe(s))
			{
				sb.append(s.toLowerCase());
			}
			else if( s.equals(" "))
				sb.append("_");
			
		}
		return sb.toString();
	}
	*/
	String getHtmlValue()
	{
		return CHT_CHAPTER_PREFIX + " " +
	           "class=\"eg\">" + 
				Utils.spreadString(chapTitle) +
				CHT_CHAPTER_SUFFIX;
				
	}
	boolean cancelHit;
	JDialog jd;JTextField chapNameTF;
	public String toString(){return this.chapTitle;}

	/*
	protected void chapterBTClicked(final Component parentComponent)
	{
		 jd = new JDialog();
		jd.setModal(true);
		
		JPanel jp = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2,2,2,2);
		
		gbc.anchor=gbc.WEST;
		gbc.gridx=0;gbc.gridy=0;
		jp.add(new JLabel("Chapter Title:"),gbc);
		
		gbc.anchor=gbc.EAST;
		gbc.gridx++;
		chapNameTF = new JTextField("Admin",20);
		jp.add(chapNameTF,gbc);
		
		gbc.anchor=gbc.WEST;
		gbc.gridx=0;gbc.gridy++;
		//subTopics.toArray(new String[subTopics.size()]);
		//java.util.Collections.sort(
		String[] s = subTopics.toArray(new String[subTopics.size()]);
		Arrays.sort(s);
		JComboBox jcb= new JComboBox(s);
		jp.add(jcb,gbc);
	
		gbc.gridx=0;gbc.gridy=0;
		JButton okJB = new JButton("OK");
		okJB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( chapNameTF.getText().length() == 0)
				{
					JOptionPane.showMessageDialog(parentComponent, "Please provide chapter name");
					return;
				}
				cancelHit = false;
				
				jd.setVisible(true);
			}
		});
		JButton canJB = new JButton("Cancel");
		canJB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cancelHit = true;
				jd.setVisible(false);
				
			}
		});
		jp.add(okJB);
		
		gbc.gridx++;
		jp.add(canJB);
		
		cancelHit = false;
		jd.getContentPane().add(jp);
		jd.getContentPane().setVisible(true);
		jd.dispose();
		
		if(cancelHit ) return;
		
		
		
		
		Chapter c = Chapter.createChapter(jf,  currentBook, longChapName);
		if( c == null ) return;
		currentChaper = c;
		chapterTF.setText(c.getTitle());
		currentBook.addChapter(currentChaper);
	}
*/
}
