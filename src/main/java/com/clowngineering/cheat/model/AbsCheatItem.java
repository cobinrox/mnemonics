package com.clowngineering.cheat.model;

import com.clowngineering.cheat.Utils;

public abstract class AbsCheatItem {

	abstract String getHtmlTitle();

	abstract String getTitle();
	String getId()
	{
		StringBuffer sb = new StringBuffer("");
		for( int i= 0 ;i < getTitle().length() && i <Utils.MAX_ID_LENGTH; i++)
		{
			String s = getTitle().substring(i);
			if(  Utils.isSafe(s))
			{
				sb.append(s.toLowerCase());
			}
			else if( s.equals(" "))
				sb.append("_");
			
		}
		return sb.toString();
	}

}
