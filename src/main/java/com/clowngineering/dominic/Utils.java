package com.clowngineering.dominic;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;



public class Utils {

	public static void setImage( String dir, JLabel jl, String nv, String word)
	{
		Image img = null;
		try
		{
			word = word.replace(" ","_");
			word = word.replace("&","");
			word = word.replace("'","");

			File f = dir==null?new File(nv+"_"+word+".gif"):new File(dir,nv + "_" +  word + ".gif");
			if( f.exists())
			{
				img = ImageIO.read(f);
					//Image img = ImageIO.read(new File("clown_60.gif"));
			 
				Image resizedImage = img.getScaledInstance(jl.getWidth(),jl.getHeight(),0);
				jl.setIcon(new ImageIcon(resizedImage));
			}
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		    System.err.println("Error getting image "+ t.getMessage());
		    jl.setIcon(null);
		}
		
	}
}
