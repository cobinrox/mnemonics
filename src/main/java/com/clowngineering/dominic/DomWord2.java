package com.clowngineering.dominic;

import java.io.Serializable;

public class DomWord2 implements Serializable{
	public String numVal;
	public String letVal;
	public String word;
	public int numStars;
	public int numGuesses;
	public boolean isMaster;
	String imageFile;
	public DomWord2()
	{}
	public DomWord2(String nv, String lv)
	{
		this.numVal = nv;
		this.letVal = lv;
	}
	public DomWord2(String nv, String lv, String w)
	{
		this.numVal = nv;
		this.letVal = lv;
		this.word = w;
	}
	public DomWord2(String nv, String lv, String w, boolean isM)
	{
		this.numVal = nv;
		this.letVal = lv;
		this.word = w;
		this.isMaster = isM;
	}
	public DomWord2(String nv, String lv, String w, boolean isM,String img)
	{
		this.numVal = nv;
		this.letVal = lv;
		this.word = w;
		this.isMaster = isM;
		this.imageFile=img;
	}
	public DomWord2(String nv, String lv, String w, int numS, int numG, boolean isM, String img)
	{
		this.numVal = nv;
		this.letVal = lv;
		this.word = w;
		this.numStars = numS;
		this.numGuesses = numG;
		this.isMaster = isM;
		this.imageFile = img;
	}
	public String buildFileName()
	{
		String w  = word.replace(" ","_");
		w  = w .replace("&","");
		w  = w .toUpperCase();
		return numVal + "_" + w  + ".gif";
	}
}
