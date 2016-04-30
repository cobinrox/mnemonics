package com.clowngineering.dominic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

public class DrainPrefs {

	/**
	 * Program preferences info
	 */
	static File prefFile;
	static String prefFileName = "clowngineering.properties";
	static Properties props;
	
	/**
	 * Properties that clients can get/set and which are saved/read
	 * to props file for convenience upon re-starting program
	 */
	public static final String PROP_DOM_FILE_NAME       = "domFileName";
	public static final String PROP_LETTER_FILE_NAME    = "letterFileName";
	public static final String PROP_GUESSES_FILE_NAME   = "guessesFileName";
	public static final String PROP_RUN_MODE            = "practiceMode";
	
	/**
	 * Constants
	 */
	public static final String RUN_MODE_SHOW_NOUNS = "SHOW_NOUNS";
	public static final String RUN_MODE_SHOW_PEOPLE = "SHOW_PEOPLE";

	public static final String RUN_MODE_QUIZ_NOUNS = "QUIZ_NOUNS";
	public static final String RUN_MODE_QUIZ_PEOPLE = "QUIZ_PEOPLE";
	/**
	 * Default property values
	 */
	public static final String DEFAULT_DOM_NOUNS_FILE_NAME     = "clowngineering_nouns.dom";
	public static final String DEFAULT_DOM_PEOPLE_FILE_NAME    = "clowngineering_peeps.dom";
	public static final String DEFAULT_GUESSES_NOUNS_FILE_NAME = "guesses_nouns.csv";
	public static final String DEFAULT_GUESSES_PEOPLE_FILE_NAME= "guesses_peeps.csv";
	public static final String DEFAULT_LETTER_FILE_NAME        = "clowngineering.let";

	public static final String DEFAULT_PRACTICE_MODE    = RUN_MODE_SHOW_NOUNS;
	
	/**
	 * Main for testing
	 * @param args
	 */
	public static void main(String args[])
	{
		boolean useDefault = !new File(prefFileName).exists();
		props = DrainPrefs.getPropsFromFile();
		assert(props != null );
		assert(props.size() == 2);
		
		List<String> keys= new ArrayList(props.keySet());
		Collections.sort(keys);
		for(String k:keys)
		{
			System.out.println(k + ":" + props.get(k));
		}
		 
	}
	public static Properties getProps()
	{
		return getPropsFromFile();
	}
	protected static Properties getPropsFromFile()
	{
		props = new Properties();

		prefFile = new File(prefFileName);
		if( !prefFile.exists() )
		{
			/* no initial properties were found, so we need to make some up from scratch
			 * since we have no idea what user may want to use as Dom words and
			 * major letters
			 */
			
			/* look to see if there is already a default master list of DOM words */
			File x = new File(DEFAULT_DOM_NOUNS_FILE_NAME);
			if( !x.exists())
			{
				/* no default dom list, let's make default DOM word list file */
				buildDomFile(DEFAULT_DOM_NOUNS_FILE_NAME, DomMap.DEFAULT_CLOWN_DOM_NOUNS_MAP);
			}
			x = new File(DEFAULT_DOM_PEOPLE_FILE_NAME);
			if( !x.exists())
			{
				/* no default dom list, let's make default DOM word list file */
				buildDomFile(DEFAULT_DOM_PEOPLE_FILE_NAME, DomMap.DEFAULT_CLOWN_DOM_PEOPLE_MAP);
			}
			/* same with default master letter list */
			x = new File(DEFAULT_LETTER_FILE_NAME);
			if( !x.exists())
			{
				buildLetterFile(DEFAULT_LETTER_FILE_NAME, LetterMap.DEFAULT_CLOWN_LETTERS_MAP);
			}
			
			/* similar with guesses results file */
			x = new File(DEFAULT_GUESSES_NOUNS_FILE_NAME);
			if(!x.exists())
			{
				try
				{
					x.createNewFile();
					props.put(PROP_GUESSES_FILE_NAME, DEFAULT_GUESSES_NOUNS_FILE_NAME);	

				}
				catch(Throwable t)
				{
					System.out.println("ERROR creating empty guesses file [" + DEFAULT_GUESSES_NOUNS_FILE_NAME +"] " + t.getMessage());
					props.put(PROP_GUESSES_FILE_NAME, null);
				}
			}
			else
			{
				props.put(PROP_GUESSES_FILE_NAME, DEFAULT_GUESSES_NOUNS_FILE_NAME);	
			}
			x = new File(DEFAULT_GUESSES_PEOPLE_FILE_NAME);
			if(!x.exists())
			{
				try
				{
					x.createNewFile();

				}
				catch(Throwable t)
				{
					System.out.println("ERROR creating empty guesses file [" + DEFAULT_GUESSES_PEOPLE_FILE_NAME +"] " + t.getMessage());
				}
			}
			props.put(PROP_DOM_FILE_NAME,   DEFAULT_DOM_NOUNS_FILE_NAME);
			props.put(PROP_LETTER_FILE_NAME,DEFAULT_LETTER_FILE_NAME);
			props.put(PROP_RUN_MODE,   DEFAULT_PRACTICE_MODE);
			props.put(PROP_GUESSES_FILE_NAME, DEFAULT_GUESSES_NOUNS_FILE_NAME);
			savePropsToFile();
			return props;
		}
		else
		{
			Reader rdr = null;
			try
			{
				rdr = new FileReader(prefFile);
				props.load(rdr);
				File x;
				if( ! (x = new File(props.getProperty(PROP_DOM_FILE_NAME))).exists())
				{
					JOptionPane.showMessageDialog(null, "Missing " + props.getProperty(PROP_DOM_FILE_NAME), 
							"Logic Error, Missing File", JOptionPane.ERROR_MESSAGE);
				}
				if( ! (x = new File(props.getProperty(PROP_GUESSES_FILE_NAME))).exists())
				{
					JOptionPane.showMessageDialog(null, "Missing " + props.getProperty(PROP_GUESSES_FILE_NAME), 
							"Logic Error, Missing File", JOptionPane.ERROR_MESSAGE);
				}
				if( ! (x = new File(props.getProperty(PROP_LETTER_FILE_NAME))).exists())
				{
					JOptionPane.showMessageDialog(null, "Missing " + props.getProperty(PROP_LETTER_FILE_NAME), 
							"Logic Error, Missing File", JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
			catch(Throwable t)
			{
				System.err.println("Error reading from pref file [" + prefFileName + "]");
				t.printStackTrace();
			}
			finally
			{
				try{rdr.close();}catch(Throwable t){}
			}
		}
		return props;
	}
	protected static void buildLetterFile(String fileName, LetterMap map)
	{
		LetterMap.saveMajorLettersToTextFile(fileName,map);
	}
	protected static void buildDomFile(String fileName, DomMap map)
	{
		DomMap.saveDomMapToTextFile(fileName, map);
	}
	public static void savePropsToFile()
	{
		BufferedWriter bw = null;
		try
		{
			bw = new BufferedWriter(new FileWriter(prefFile));
			props.store(bw, ""+new Date());
		}
		catch(Throwable t)
		{
			System.err.println("Error saving props to [" + prefFileName + "]");
			t.printStackTrace();
		}
		finally
		{
			try{bw.close();}catch(Throwable t){}
		}
	}
	
}
