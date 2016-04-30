package com.clowngineering.cheat;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.clowngineering.cheat.model.Chapter;
import com.clowngineering.cheat.model.CheatBook;
import com.clowngineering.cheat.model.CheatDefinition;

public class CheatMain implements Serializable {

	 static JFrame jf;
		JTextField kindleFolderTF = new JTextField("   ",60);
		JButton browseRootDirBT      = new JButton("Browse...");

		JButton newBookBT    = new JButton("New Book");
		JTextField bookTF = new JTextField("     ",25);
		JButton browseBookBT      = new JButton("Browse...");

		JButton newChapterBT = new JButton("New Chapter");
		JComboBox chapterTF = new JComboBox();//("     ",25);
		JButton browseChapterBT      = new JButton("Browse...");

		JButton newCheatBT   = new JButton("New Cheat");
		JTextField cheatTF = new JTextField("     ",25);

		JButton makeBT = new JButton("Build XHTML Content");
		JPanel  buttonJP;
		static CheatMain me;
	public static void main(String[] args) {
		CheatMain cm = new CheatMain();
		cm.buildGui();
		CheatMain oldCM = (CheatMain)(PropHandler.getPreviousSettingsIfAny(CheatMain.defaultDir(),cm.getClass()));
		if( oldCM != null )
		{
			popGuiWith(cm,oldCM);
			if( cm.currentBook != null && cm.currentBook.getDir() != null && cm.currentBook.getDir().exists() )
			{
				oldCM =(CheatMain)(PropHandler.getPreviousSettingsIfAny(cm.currentBook.getDir(),cm.getClass()));
				popGuiWith(cm,oldCM);

			}
			//cm = oldCM;
		}
		
		System.out.println("***" + cm.chapterTF.getModel().getSize());
		System.out.println(cm.chapterTF.getModel().getSelectedItem());
		me = cm;
		jf.pack();
        jf.setVisible(true);

	}
	protected static void popGuiWith(CheatMain gui, CheatMain vals)
	{
		gui.kindleFolderTF.setText(vals.kindleFolderTF.getText());
		gui.bookTF.setText( vals.bookTF.getText());
		//gui.chapterTF.setText( vals.chapterTF.getText());
		System.out.println(vals.chapterTF.getModel());
		gui.chapterTF = vals.chapterTF;
		gui.chapterTF.repaint();
		System.out.println(gui.chapterTF.getModel());
		gui.cheatTF.setText(vals.cheatTF.getText());
		gui.currentBook = vals.currentBook;
		gui.currentChaper = vals.currentChaper;
		gui.currentCheat = vals.currentCheat;
	}
	protected void buildGui()
	{
		jf = new JFrame("T E S T");
		jf.addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				PropHandler.saveUserPreferences(CheatMain.defaultDir(),me);
				if( currentBook != null && currentBook.getDir() != null && currentBook.getDir().exists())
				{
					PropHandler.saveUserPreferences( currentBook.getDir(),me);
				}
                System.exit(0);				
			}
		});
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		buildButtonPN();
		jf.add(buttonJP,BorderLayout.CENTER);
		
		makeBT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				makeBtClicked();
				
			}
		});
		jf.add(makeBT,BorderLayout.SOUTH);
	}

	static File defaultDir()
	{
		File x = new File(
				System.getProperty("user.home") + File.separator + "Dropbox" + File.separator + "kindle");
		if( ! x.exists()) return null;
		return x;
	}
	protected void buildButtonPN()
	{
		buttonJP   = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets= new Insets(2,2,2,2);
		
		// Source file directory JLabel
		gbc.gridwidth=1;
		gbc.anchor=GridBagConstraints.EAST;
		gbc.gridx=0; gbc.gridy=0;
		JLabel jl = new JLabel("Soure file directory:");
		buttonJP.add(jl,gbc);
		
		// Folder JTextField
		gbc.gridwidth=2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx++;
		kindleFolderTF.setText(System.getProperty("user.home") + File.separator + "Dropbox" + File.separator + "kindle");
		buttonJP.add(kindleFolderTF,gbc);
		
		// Browse JButton
		gbc.gridwidth=1;
		browseRootDirBT.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				browseRootBTClicked();				
			}
		});
		gbc.gridx+=2;
		buttonJP.add(browseRootDirBT,gbc);
		
		// New book JButton
		gbc.gridwidth = 1;
		//gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx=1;gbc.gridy++;
		newBookBT.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				bookBTClicked();				
			}
		});
		buttonJP.add(newBookBT,gbc);
		
		// Book JTextField
		bookTF.setEditable(false);
		gbc.gridx++;
		buttonJP.add(bookTF,gbc);
		
		// New Chapter JButton
		gbc.gridx=1;gbc.gridy++;
		newChapterBT.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				chapterBTClicked();				
			}
		});
		buttonJP.add(newChapterBT,gbc);
		
		// Chapter JTextField
		DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
		chapterTF = new JComboBox(dcbm);
		chapterTF.setPrototypeDisplayValue("mmmmmmmmmmmmmmmmmmmmmmmmm");
		chapterTF.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
			          currentChaper = (Chapter)arg0.getItem();
			       }
				
			}
		});

		chapterTF.setEditable(false);
		gbc.gridx++;
		buttonJP.add(chapterTF,gbc);
	
		// New Cheat JButton
		gbc.gridx=1;gbc.gridy++;
		newCheatBT.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cheatBTClicked();				
			}
		});
		buttonJP.add(newCheatBT,gbc);
		
		// Cheat JTextField
		cheatTF.setEditable(false);
		gbc.gridx++;
		buttonJP.add(cheatTF,gbc);
		
		// dummy space
		gbc.gridx=0;gbc.gridy++;
		buttonJP.add( new JLabel(" "),gbc);
		
		// build XHTML button
		//gbc.gridx=0;
		//gbc.gridy++;
		//gbc.gridwidth=4;
		//gbc.weightx=1;
        //gbc.fill=GridBagConstraints.HORIZONTAL;
		
		//buttonJP.add(makeBT,gbc);

	}
	JFileChooser fileChooser = new JFileChooser();

	protected void browseRootBTClicked()
	{
		fileChooser.setDialogTitle("Specify Book Directory"); 

		File x = Utils.getDir(jf, fileChooser, "OK");
		if( x != null )
		{
			try
			{
				kindleFolderTF.setText(x.getCanonicalPath());
			}catch(Throwable t){} // required catch for getCanPath
		}
	}
	
	Chapter currentChaper;
	CheatBook currentBook;
	protected void bookBTClicked()
	{
		
		String bookName = JOptionPane.showInputDialog(jf, 
				                              "Enter a book title", 
				                              "Provide Book Name", 
				                              JOptionPane.QUESTION_MESSAGE);
		if(bookName == null ) return;
		
		String safeName = Utils.makeSafeFileName(bookName).toUpperCase();
		File f = new File(kindleFolderTF.getText(),safeName);
		if(!f.exists())
		{
			int i = JOptionPane.showConfirmDialog(jf,  "OK to build dir \n" +
														safeName + "\n" +
					                                   "for book\n" +
					                                    "\""+bookName + "\" ?\n",
		                                                "Build New Book Dir", 
		                                                JOptionPane.YES_NO_CANCEL_OPTION, 
		                                                JOptionPane.QUESTION_MESSAGE);
			if( i != JOptionPane.YES_OPTION ) return;
			
			CheatBook cb = CheatBook.createBook(jf,bookName,f);
			if( cb != null )
			{
				bookTF.setText(f.getName());
				currentBook = cb;
			}
		}
		
	}
	protected void chapterBTClicked()
	{
		if( currentBook == null)
		{
			JOptionPane.showMessageDialog(jf,  "Please create or pick a book for your chapter");
			return;
		}
		JDialog jd = new JDialog();
		jd.setModal(true);
		
		String longChapName = JOptionPane.showInputDialog(jf, 
				                              "Enter a chapter title", 
				                              "Provide Chapter Name", 
				                              JOptionPane.QUESTION_MESSAGE);
		if(longChapName == null ) return;
		
		Chapter c = Chapter.createChapter(jf,  currentBook, longChapName);
		if( c == null ) return;
		currentChaper = c;
		((DefaultComboBoxModel)chapterTF.getModel()).removeElement(currentChaper);
		((DefaultComboBoxModel)chapterTF.getModel()).addElement(currentChaper);
		
		chapterTF.setSelectedItem(currentChaper);
		currentBook.addChapter(currentChaper);
	}
	CheatDefinition currentCheat;
	protected void cheatBTClicked()
	{
		if( currentBook == null)
		{
			JOptionPane.showMessageDialog(jf,  "Please create or pick a book for your cheat");
			return;
		}
		if( currentChaper == null)
		{
			JOptionPane.showMessageDialog(jf,  "Please create or pick a chapter for your cheat");
			return;
		}
		String longCheatName = JOptionPane.showInputDialog(jf, 
				                              "Enter a cheat title", 
				                              "Provide Cheat Name", 
				                              JOptionPane.QUESTION_MESSAGE);
		if(longCheatName == null ) return;
		
		CheatDefinition c = CheatDefinition.createCheat(jf,  currentBook, currentChaper, longCheatName);
		if( c == null ) return;
		currentCheat = c;
		cheatTF.setText(c.getTitle().getTitle());
		currentChaper.addCheat(c);
		
	}
	protected void makeBtClicked()
	{
		currentBook.write();
	}
}
	
class PropHandler
{
	static void saveUserPreferences(File path, Object o)
    {
        if(o == null ) return;
        ObjectOutputStream oos  = null;
        try
        {
        	oos = null;
        	if( path != null )
               oos = new ObjectOutputStream(new FileOutputStream(path.getAbsolutePath() + File.separator + 
            		                         "temp_"+o.getClass().getName() +".bin"));
        	else
        		oos = new ObjectOutputStream(new FileOutputStream("temp_"+o.getClass().getName() +".bin"));
            oos.writeObject(o);
        }
        catch(Throwable t)
        {
           t.printStackTrace();
        }
        finally
        {
            if(oos != null )
            {
                try
                {
                    oos.close();    
                }
                catch(IOException ie){}
            }
         }
    }

	static File getPreviousSettingsFileIfAny(File path, Class klass)
    {
		File x = null;
		if( path != null )
			// gets from specified dir
            x = new File(path,"temp_"+klass.getName()+ ".bin");
		else
			// gets from run-time dir
			x = new File("temp_"+klass.getName()+ ".bin");
        return x;
    }
	static Object getPreviousSettingsIfAny(File path, Class klass )
    {
        File x = getPreviousSettingsFileIfAny(path,klass);
        return getPreviousSettingsIfAny(x);
    }
	static Object getPreviousSettingsIfAny(File previousPrefsFile)
    {
        Object ret = null;
        ObjectInputStream ois = null;
        try
        {
            if(previousPrefsFile == null ) return null;
            
            
            if( !previousPrefsFile.exists() )
                return null;
            
            ois = new ObjectInputStream(new FileInputStream(previousPrefsFile));
            ret = ois.readObject();
            System.out.println("Obj " + ret.getClass().getName());
            ois.close();
        }
        catch(Throwable t )
        {
            if( ois != null )
            {
                try
                {
                    ois.close();
                }catch(IOException e){}
            }
            // usually this means an old version is no longer compatible
            System.out.println("Reading previous settings " + t.getMessage());
            System.out.println("Old prefs deleted? " + previousPrefsFile.delete());
        }
        return ret;
    }     

}
