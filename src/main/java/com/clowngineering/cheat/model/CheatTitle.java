package com.clowngineering.cheat.model;

import java.io.Serializable;

import com.clowngineering.cheat.Utils;

public class CheatTitle implements Serializable{

	
	String title;
	String html;
	String id;
	
	final static String EXAMPLE = "CHAPTER: Topic, sub-topic (Admin: Design classes)";

	final static String CHT_TITLE_PREFIX = "<p id=\"";
	final static String CHT_TITLE_SUFFIX = "</p>";
	
	public String getTitle() { return title; }
	public String getHtmlValue()
	{
		return CHT_TITLE_PREFIX + " " +
		           "class=\"eg\">" + 
				title +
				CHT_TITLE_SUFFIX;
				
	}

}
