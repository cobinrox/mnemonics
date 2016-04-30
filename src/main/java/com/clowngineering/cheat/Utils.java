package com.clowngineering.cheat;

import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class Utils {
	public final static int MAX_ID_LENGTH = 20;
    public final static Font MONO_FONT = new Font( "Monospaced", Font.PLAIN, 12 );  

	//public static Font getMonoFont()
	//{
	//	Font mono = new Font(new JDialog(), "Monospaced", 10, SWT.NONE);
	//}
	public static String spreadString(String str)
	{
		StringBuffer sb = new StringBuffer("");
		for( int i=0; i< str.length(); i++ )
		{
			String s = str.substring(i,i+1);
			sb.append(s);
			if( s.equals(" "))
			{
				appendNBSP(sb,3);
			}
			else
				appendNBSP(sb,1);
		}
		return sb.toString();
	}
	public static String makeSafeFileName(String someFName)
	{
		StringBuffer sb = new StringBuffer("");
		for( int i= 0 ;i <someFName.length() && i <Utils.MAX_ID_LENGTH; i++)
		{
			String s = someFName.substring(i,i+1);
			if( i != 0 )
			{
				if( !Utils.isNumeric(s))
				{
			
					if(  Utils.isSafe(s))
					{
						sb.append(s);
					}
					else if( s.equals(" "))
						sb.append("_");
				}
				else
					sb.append(s); // allow numerics that aren't the first char
			}
			else
			{
				if(  Utils.isSafe(s))
				{
					sb.append(s);
				}
			}
			
		}
		return sb.toString();
	}
	public static void appendNBSP(StringBuffer sb, int numSpaces)
	{
		for( int i =0; i<numSpaces; i++)
		{
			sb.append("&nbsp;");
		}
	}
	public static boolean isNumeric(String s)
	{
		try
		{
			Integer.parseInt(s);
			return true;
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
	}
	public static boolean isSafe(String s)
	{
		try
		{
			Integer.parseInt(s);
			return false;
		}
		catch(NumberFormatException nfe)
		{
			if(     s.equals("!") || 
					s.equals("@") ||
					s.equals("#") ||
					s.equals("$") ||
					s.equals("%") ||
					s.equals("^") ||
					s.equals("&") ||
					s.equals("*") ||
					s.equals("(") ||
					s.equals(")") ||
					s.equals("_") ||
					s.equals("-") ||
					s.equals("+") ||
					s.equals("=") ||
					s.equals("{") ||
					s.equals("[")  ||
					s.equals("}")  ||
					s.equals("]")  ||
					s.equals("|")  ||
					s.equals("\\") ||
					s.equals(":")  ||
					s.equals(";")  ||
					s.equals("\"") ||
					s.equals("'")  ||
					s.equals("<") ||
					s.equals(",") ||
					s.equals(">") ||
					s.equals(".") ||
					s.equals("?") ||
					s.equals("/") ||
					s.equals(" ") ||
					s.equals("~") ||
					s.equals("`"))
				return false;
		}
		return true;
	}
	
	public static File getDir(Component parentFrame, JFileChooser fileChooser,String approveBtText)
	{
		File x = getFile(parentFrame, fileChooser,approveBtText);
		if( x == null ) return null;
		if( x.isFile() )
			x = x.getParentFile();
		return x;
	}
	
	public static File getFile(Component parentFrame, JFileChooser fileChooser, String approveBtText)
	{

		int userSelection = fileChooser.showDialog(parentFrame,approveBtText);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			return fileToSave;
		}
		return null;
	}
	
	public static void  getAllFilesAndDirs(String dir, ArrayList<File> retList)
	{

		File root = new File( dir );
		File[] list = root.listFiles();

		for ( File f : list ) {
			if ( f.isDirectory() ) {
				getAllFilesAndDirs( f.getAbsolutePath(), retList );
				//System.out.println( "Dir:" + f.getAbsoluteFile() );
				retList.add( f);
			}
			else {
				//System.out.println( "File:" + f.getAbsoluteFile() );
				retList.add(f);

			}
		}
	}

}
