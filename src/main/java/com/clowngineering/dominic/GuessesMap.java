package com.clowngineering.dominic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class GuessesMap extends HashMap<String, ArrayList<DomGuess>> 
{
	private static final long serialVersionUID = 9175798112079928384L;

	public GuessesMap( )
	{
		super();
	}
	
	public static void saveGuessesMapToTextFile(String domMapFname, GuessesMap map)
	    {
		FileWriter fw = null;
		try
		{
			fw = new FileWriter(DrainPrefs.props.getProperty(DrainPrefs.PROP_GUESSES_FILE_NAME));
			
			List<String> keys=new ArrayList(map.keySet());
			Collections.sort(keys);
			for(String key:keys)
			{
				ArrayList<DomGuess> dgs = map.get(key);
				for( DomGuess dg: dgs)
				{
					
					fw.write(dg.numVal);
					fw.write(",");
					fw.write(dg.letVal);
					fw.write(",");
					fw.write(dg.word);
					fw.write(",");
					fw.write(""+dg.numGuesses);
					fw.write(",");

					fw.write(""+dg.startTime);
					fw.write(",");
					fw.write(""+dg.stopTime);
					fw.write(",");
					fw.write(""+dg.sumOfTimesToGuess);
					fw.write(System.getProperty("line.separator"));
				}
			}
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
	public static GuessesMap loadGuessesMapFromTextFile(String guessesFname)
	{
	       File f = new File(guessesFname);
	        BufferedReader br =null;
	        if( !f.exists() )
	        {
	            return null;
	        }
	        GuessesMap map = new GuessesMap();
	        try
	        {
	            br =  new BufferedReader(new FileReader(guessesFname));
	            String line=null;
	            while( (line=br.readLine()) != null )
	            {
	                try
	                {
	                    String[] s = line.split(",");
	                    String key = s[0];
	                    DomGuess dg = new DomGuess(key,s[1],s[2],(long)0,Integer.parseInt(s[3]));
	                    dg.startTime = Long.parseLong(s[4]);
	                    dg.stopTime = Long.parseLong(s[5]);
	                    dg.sumOfTimesToGuess = Float.parseFloat(s[6]);
	                    ArrayList<DomGuess> al = null;
	                    if( map.containsKey(key))
	                    {
	                        al = map.get(key);
	                    }
	                    else
	                    {
	                        al = new ArrayList<DomGuess>();
	                    }
	                    al.add(dg);
	                    map.put(key,al);
	                }
	                catch(Throwable t)
	                {
	                    System.out.println("Warning, skipping bad guess value [" + line + "] " + t.getMessage());
	                }
	            }
	            return map;
	        }
	        catch(Throwable t)
	        {
	        	System.err.println("ERROR loading guesses file data " + t.getMessage() + "]");
	            t.printStackTrace();
	            return map;
	        }
	        finally
	        {
	            if(br != null )try{br.close();}catch(Throwable t){}
	        }
	    }
}
class DomGuess extends DomWord2 implements Serializable
{
	/**
	 * 
	 */
	//private static final long serialVersionUID = -7030599377589876632;
	int numGuesses;
	long startTime;
	long stopTime;
	float sumOfTimesToGuess;
	public DomGuess(String nv, String lv, String w, long timeToGuess) {
		this(nv,lv,w,timeToGuess,0);
	}
	public DomGuess(String nv, String lv, String w, long timeToGuess, int numGuess) {
		super(nv,lv,w);
		sumOfTimesToGuess = timeToGuess;
		numGuesses = numGuess;
	}
	public void addTTG(long newTimeToGuess)
	{
		sumOfTimesToGuess = (sumOfTimesToGuess+(float)newTimeToGuess);
	}
	
}