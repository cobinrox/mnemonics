package com.clowngineering.cheat.model;

import java.awt.Component;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.clowngineering.cheat.Utils;

public class CheatBook implements Serializable{
	
	String title;
	File   dir;
	List<Chapter> chapters = new ArrayList<Chapter>();
	public void write()
	{
		
		for( Chapter chapter: chapters )
		{
			chapter.write();
		}
	}
	public File getDir()
	{
		return dir;
	}
	public void addChapter(Chapter t)
	{
		if(t != null )
		{
			chapters.add(t);
		}
	}
	public List<Chapter>getChapters(){return chapters;}
	
	public static CheatBook createBook(Component parentComponent, String longBookName, File bookDir)
	{
		boolean b = buildBookDir(parentComponent,longBookName,bookDir);
		if( !b ) return null;
		
		CheatBook cb = new CheatBook();
		cb.title = longBookName;
		cb.dir = bookDir;
		return cb;
	}
	public static boolean buildBookDir(Component parentComponent, String longBookName, File bookDir)
	{
		try
		{
		// CREATE BOOK DIRECTORY
		int res = JOptionPane.showConfirmDialog(parentComponent, "(1) Preparing directory\n\n"+
				                                                  bookDir + "\n\n", 
				                                                    "Preparing Book " + longBookName, 
				                                                    JOptionPane.OK_CANCEL_OPTION);
		
		if( res != JOptionPane.OK_OPTION ) return false;
		bookDir.mkdirs();

		// CREATE META-INF DIR
		File f = new File(bookDir, "META-INF");
		res = JOptionPane.showConfirmDialog(parentComponent, "(2) Creating META-INF directory\n\n"+
				                                                  f.getAbsolutePath() + "\n\n", 
				                                                    "Preparing Book " + longBookName, 
				                                                    JOptionPane.OK_CANCEL_OPTION);
		
		if( res != JOptionPane.OK_OPTION ) return false;
		f.mkdirs();
		
		// CREATE OEPBS DIR
		f = new File(bookDir, "OEBPS");
		res = JOptionPane.showConfirmDialog(parentComponent, "(3) Creating OEBPS directory\n\n"+
																f.getAbsolutePath()  + "\n\n", 
				                                                    "Preparing Book " + longBookName, 
				                                                    JOptionPane.OK_CANCEL_OPTION);
		
		if( res != JOptionPane.OK_OPTION ) return false;

		// CREATE Images/, Styles, Text folders
		f = new File(bookDir, "OEBPS" + File.separator + "Images");
		res = JOptionPane.showConfirmDialog(parentComponent, "(4) Creating Images, Styles, Text directories\n\n"+
				f.getAbsolutePath() + "\n\n", 
				"Preparing Book " + longBookName, 
				JOptionPane.OK_CANCEL_OPTION);

		if( res != JOptionPane.OK_OPTION ) return false;
		f.mkdirs();
		f = new File(bookDir, "OEBPS" + File.separator +"Styles");
		f.mkdirs();
		f = new File(bookDir,"OEBPS" + File.separator +"Text");
		f.mkdirs();
		
		ArrayList<File> files = new ArrayList<File>();
		Utils.getAllFilesAndDirs(bookDir.getAbsolutePath(), files);
		StringBuffer sb = new StringBuffer("Created the following files/dirs:\n" );
		sb.append("============================\n");
		sb.append("[");
		sb.append(bookDir.getAbsolutePath());
		sb.append("]\n");
		for( File fl : files)
		{
			if( fl.isDirectory() )
			   sb.append("[" + fl.getParent() + File.separator + fl.getName() + "]");
			else
			   sb.append(fl.getParent() + File.separator +fl);
			sb.append("\n");
		}
		JOptionPane.showMessageDialog(parentComponent, sb.toString());
		//JFileChooser jf = new JFileChooser(bookDir);
		//jf.setApproveButtonText("OK");
		//res = jf.showDialog(parentComponent, "OK??????????");
		
		return true;
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
		return false;
	}

}
