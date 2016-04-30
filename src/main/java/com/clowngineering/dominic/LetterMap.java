package com.clowngineering.dominic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LetterMap extends HashMap<String,String>
{
	private static final long serialVersionUID = 9175798112079928384L;
	public static final LetterMap DEFAULT_CLOWN_LETTERS_MAP = new LetterMap() {
		private static final long serialVersionUID = -828265124196401878L;

	{
	    put("0",    "C");
	    put("1",    "T");
	    put("2",   "N");
	    put("3",   "M");
	    put("4",   "R");
	    put("5",   "S");
	    put("6",   "G");
	    put("7",   "L");
	    put("8",   "B");
	    put("9",   "P");
	}};
	public String getValForNum(String num)
	{
		return get(num);
	}
	public String getUpperLetterFor2ByteNumber(String twoByteNumVal)
	{
	    return get(twoByteNumVal.substring(0,1));

	}
	public String getLowerLetterFor2ByteNumber(String twoByteNumVal)
	{
		return get(twoByteNumVal.substring(1));
	}
	public String getLettersFor2ByteNumber(String twoByteNumVal)
	{
		return getUpperLetterFor2ByteNumber(twoByteNumVal)+
				getLowerLetterFor2ByteNumber(twoByteNumVal);
	}
	
	public static void saveMajorLettersToTextFile(String majorFname, LetterMap map)
	    {
	        FileWriter fw = null;
	        try
	        {
	            fw = new FileWriter(majorFname);
	            
	            List<String> keys=new ArrayList<String>(map.keySet());
	            Collections.sort(keys);
	            for(String key:keys)
	            {
	                fw.write(key);
	                fw.write(",");
	                fw.write(map.get(key));
	                fw.write(System.getProperty("line.separator"));
	            }
	        }
	        catch(Throwable t)
	        {
	        	System.err.println("Error saving Letter Map to file [" + majorFname + "]");
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
	public static LetterMap loadMajorLettersFromTextFile(String majorFname)
	{
	        File f = new File(majorFname);
	        BufferedReader br =null;
	        LetterMap map = new LetterMap();
	        if( !f.exists() )
	        {
	            return null;
	        }
	        try
	        {
	            br =  new BufferedReader(new FileReader(majorFname));
	            String line=null;
	            while( (line=br.readLine()) != null )
	            {
	                try
	                {
	                    String[] s = line.split(",");
	                    map.put(s[0],s[1]);
	                }
	                catch(Throwable t)
	                {
	                    System.out.println("Warning, skipping bad major value [" + line + "] " + t.getMessage());
	                }
	            }
	            return map;

	        }
	        catch(Throwable t)
	        {
	        	System.err.println("Error reading letter file [" + majorFname + "]");
	            t.printStackTrace();
	            return map;

	        }
	        finally
	        {
	            if(br != null )try{br.close();}catch(Throwable t){}
	        }
	    }
	
}
