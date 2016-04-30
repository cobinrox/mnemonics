package com.clowngineering.dominic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * http://letterword.com/index.php/four-letter-words-starting-with-b-and-ending-in-t/
 * 
 * To run through Excel, feed in the guess.csv,
 * highlight cells b,c,d, for a particular set of numerics,
 * e.g. for 00-09, select graph, clustered column, Finish.
 * Wash, rinse, repeat.
 * 
 * @author robin.cox
 *
 */
public class DomMain implements ActionListener {


	public static String runMode;
	
	UI ui;
	
	
	boolean running;
	DomWord2 currentDw;
	long start;
	long stop;
	
	Random rand = new Random();
	int min = 0;
	int max = 9;
	DomMap masterDominicHM;// = new HashMap<String, DomWord2>();

	GuessesMap guessesHM;
	//HashMap<String, ArrayList<DomGuess>> guessesHM = new HashMap<String, ArrayList<DomGuess>>();
	//Map<String,String> majorLettersHM = new HashMap<String,String>();
	/**
	 * Major Mnemonic List (map) of numbers to letters, e.g.
	 * 0: C
	 * 1: T
	 * 2: N ...
	 */
	LetterMap majorLettersHM;
	
	
	public static void main(String[] args) {
		DomMain dm = new DomMain();
		DrainPrefs.getProps();

		// set up major letter substitution map, eg 0='C", 1='T', etc
		String x = DrainPrefs.props.getProperty(DrainPrefs.PROP_LETTER_FILE_NAME);
		dm.majorLettersHM =  LetterMap.loadMajorLettersFromTextFile(x);
		
		// set up dom number pair word map, eg 77=Lolli, 19=Tipi, etc
		x = DrainPrefs.props.getProperty(DrainPrefs.PROP_DOM_FILE_NAME);
		dm.masterDominicHM = DomMap.loadDomMapFromTextFile(x);
		
		// set up guesses map
		x = DrainPrefs.props.getProperty(DrainPrefs.PROP_GUESSES_FILE_NAME);
		dm.guessesHM = GuessesMap.loadGuessesMapFromTextFile(x);
		
		// get preferred run mode
		runMode = DrainPrefs.props.getProperty(DrainPrefs.PROP_RUN_MODE);
		
		dm.buildUI();

		dm.ui.setVisible(true);

	}
	public void buildUI()
	{
		ui = new UI(this);
		ui.buildUI(this);
	}
	protected void makeThisAMasterWord(String nv, String lv, String w)
	{
			List<DomWord2> dws = masterDominicHM.get(nv);
			boolean found = false;
			for(DomWord2 d:dws)
			{
				if(d.word.equalsIgnoreCase(w))
				{
					d.isMaster=true;
					found = true;
				}
				else
					d.isMaster=false;
			}
			if( !found )
			{
				DomWord2 d = new DomWord2(nv,lv,w,true);
				dws.add(d);
			}
			JOptionPane.showMessageDialog( null, 
					                       (found?"Updated ":"Added ") + "\n" +
			                                w + "\n" +
					                       "as master for\n" +
			                               nv + " " + lv
					                     );
	}
	/**
	 * indexes that keep track of which person or noun
	 * word in the list of default persons/nouns that
	 * the demo mode uses
	 */
	int peepShowIdx = 0;
	int nounShowIdx = 0;
	
	/**
	 * Keeps track of running number of good guesses and
	 * the guesses (both nouns and people) that were wrong
	 */
	int score=0;
	HashMap<String,Integer> missedNouns = new HashMap<String,Integer>();
	HashMap<String,Integer> missedPeople = new HashMap<String, Integer>();
	
	protected DomWord2 getRandomDominicWord2()
	{
		DomWord2 dw = null;
		String skey =null;
		if( runMode.equals(DrainPrefs.RUN_MODE_SHOW_NOUNS) )
		{
			// convert index to two character string, e.g. "00", "01",...
			skey = ((""+nounShowIdx).length() ==1?"0":"")+nounShowIdx;
			List<DomWord2>dws = masterDominicHM.get(skey) ;
			for( DomWord2 d:dws)
			{
				if( d.isMaster ) dw=d;
			}
			
			nounShowIdx++;
			if( nounShowIdx >= masterDominicHM.size() )
			{
				// restart from top of word list
				nounShowIdx=0;
			}
		}
		else if(runMode.equals(DrainPrefs.RUN_MODE_SHOW_PEOPLE))
		{
			skey = ((""+peepShowIdx).length() ==1?"0":"")+peepShowIdx;
			List<DomWord2>dws = masterDominicHM.get(skey) ;
			for( DomWord2 d:dws)
			{
				if( d.isMaster ) dw=d;
			}
			
			peepShowIdx++;
			if( peepShowIdx >= masterDominicHM.size())
			{
				// restart from top of people list
				peepShowIdx = 0;
			}
		}
		else
		{
			// else we're in "game" mode,, get random numbers
			boolean goodNum = false;
			int upperByte = 0;
			int lowerByte = 0;
			while(!goodNum)
			{
				upperByte = rand.nextInt(max-min+1)+min;
				lowerByte = rand.nextInt(max-min+1)+min;
				assert(upperByte >= 0 && upperByte <=9 && lowerByte >=0 && lowerByte <=9);
				int tmp = Integer.parseInt(""+upperByte+""+lowerByte);
				if( tmp < masterDominicHM.size() )
					goodNum=true;
			}
			dw = new DomWord2(""+upperByte+lowerByte,
			 		                 majorLettersHM.get(""+upperByte)+majorLettersHM.get(""+lowerByte));
			dw.word = masterDominicHM.getValForNum(dw.numVal).word;
		}
		// sanity check
		if(dw == null ) dw = masterDominicHM.get("00").get(0);
		return dw;
	}
	
	protected DomWord2 primeUIWithNextDomWord2()
	{
		currentDw = getRandomDominicWord2();
		ui.primeUIWithThisDomWord(currentDw);
		/*
		ui.numericValueTF.setText(""+currentDw.numVal);
		if(runMode.equals(DrainPrefs.RUN_MODE_SHOW_NOUNS) ||
				runMode.equals(DrainPrefs.RUN_MODE_SHOW_PEOPLE))
		{
			ui.peekJL.setText(currentDw==null?"unknown":currentDw.word);
			ui.letterValueTF.setText(
								   majorLettersHM.getLettersFor2ByteNumber(ui.numericValueTF.getText())
			                     );
			ui.answerTF.setText(currentDw.word);
			ui.imageJL.setIcon(null);

			ui.setImagesForThisDomWord(ui.imageJP,currentDw);
		}
		else
		{
			ui.letterValueTF.setText("     ");
			ui.imageJL.setIcon(null);

		}
		*/
		start = System.currentTimeMillis();
		//ui.answerTF.requestFocusInWindow();
		//ui.peekJL.setText("               ");
		return currentDw;
		
	}
	protected void submitGuess( )
	{
		if( runMode.equals(DrainPrefs.RUN_MODE_SHOW_NOUNS)
				|| runMode.equals(DrainPrefs.RUN_MODE_SHOW_PEOPLE))
		{
			//popupTheAnswerBriefly(true);

		}
		else
		{  
			// QUIZ MODE
			if(ui.answerTF.getText().trim().equals(""))
				ui.answerTF.setText("PASS");
			String usersWord = ui.answerTF.getText().trim();
			stop = System.currentTimeMillis();
			long totaltime = stop-start;
			//System.out.println("totaltime: [" + totaltime + "] msecs");
			//currentDw.word = answerTF.getText().trim();
			boolean found = false;

			if( guessesHM.containsKey(currentDw.numVal))
			{
				// this numeric has been guessed at before...
				ArrayList<DomGuess> dgs = guessesHM.get(currentDw.numVal);
				for(DomGuess dg:dgs)
				{
					// so see if the user's guess has been used before...
					if(dg.word.equalsIgnoreCase(usersWord))
					{
						// user has used this word before so bump up its count
						dg.numGuesses++;dg.addTTG(totaltime);dg.startTime=start;dg.stopTime=stop;found=true;break;
					}
				}
				if( !found )
				{
					// user used word that has not been guessed at before
					DomGuess dg = new DomGuess(currentDw.numVal,
							majorLettersHM.getLettersFor2ByteNumber(ui.numericValueTF.getText()),
	                    usersWord,totaltime,1);
					dg.startTime = start;
					dg.stopTime = stop;
					dgs.add(dg );
				}
			}
			else
			{
				ArrayList<DomGuess> al = new ArrayList<DomGuess>();
				DomGuess dg =new DomGuess(currentDw.numVal,
						majorLettersHM.getLettersFor2ByteNumber(ui.numericValueTF.getText()),
	                usersWord,totaltime,1);
				dg.startTime = start;
				dg.stopTime = stop;
				al.add(dg );
				guessesHM.put(currentDw.numVal,al);
			}
			boolean scored = usersWord.equalsIgnoreCase(currentDw.word);
			if(!scored)
			{
				HashMap<String,Integer>ptr =null;
				if( runMode.equals(DrainPrefs.RUN_MODE_QUIZ_NOUNS))
				{
					ptr = missedNouns;
				}
				else
				{
					ptr = missedPeople;
				}
				Integer i = ptr.get(currentDw.word);
				if( i==null ) ptr.put(currentDw.word,1);
				else ptr.put(currentDw.word, ptr.get(currentDw.word).intValue()+1);
			}
			else
				score++;
			ui.popupTheAnswerBriefly(currentDw, scored,missedNouns.size(),missedPeople.size());

		}
		ui.answerTF.setText("");
		primeUIWithNextDomWord2();
		
	}
	

	protected void changePracticeModes(String newMode)
	{
		saveOffAll();
		runMode = newMode;
		DrainPrefs.props.put(DrainPrefs.PROP_RUN_MODE,newMode);
		//practiceNounsMI.setEnabled(true);
		//practicePeopleMI.setEnabled(true);
		//practiceShowNounsMI.setEnabled(true);
		//practiceShowPeopleMI.setEnabled(true);
		
		if(runMode.equals(DrainPrefs.RUN_MODE_QUIZ_NOUNS))
		{
			masterDominicHM = DomMap.loadDomMapFromTextFile(DrainPrefs.DEFAULT_DOM_NOUNS_FILE_NAME);
			guessesHM = GuessesMap.loadGuessesMapFromTextFile(DrainPrefs.DEFAULT_GUESSES_NOUNS_FILE_NAME);
			DrainPrefs.props.put(DrainPrefs.PROP_DOM_FILE_NAME,DrainPrefs.DEFAULT_DOM_NOUNS_FILE_NAME);
			DrainPrefs.props.put(DrainPrefs.PROP_GUESSES_FILE_NAME,DrainPrefs.DEFAULT_GUESSES_NOUNS_FILE_NAME);
			//practiceNounsMI.setEnabled(false);

		}
		else if( runMode.equals(DrainPrefs.RUN_MODE_QUIZ_PEOPLE))
		{
			masterDominicHM=DomMap.loadDomMapFromTextFile(DrainPrefs.DEFAULT_DOM_PEOPLE_FILE_NAME);
			guessesHM =GuessesMap.loadGuessesMapFromTextFile(DrainPrefs.DEFAULT_GUESSES_PEOPLE_FILE_NAME);
			DrainPrefs.props.put(DrainPrefs.PROP_DOM_FILE_NAME,DrainPrefs.DEFAULT_DOM_PEOPLE_FILE_NAME);
			DrainPrefs.props.put(DrainPrefs.PROP_GUESSES_FILE_NAME,DrainPrefs.DEFAULT_GUESSES_PEOPLE_FILE_NAME);
			//practicePeopleMI.setEnabled(false);

		}
		else if( runMode.equals(DrainPrefs.RUN_MODE_SHOW_NOUNS))
		{
			masterDominicHM = DomMap.loadDomMapFromTextFile(DrainPrefs.DEFAULT_DOM_NOUNS_FILE_NAME);
			guessesHM = GuessesMap.loadGuessesMapFromTextFile(DrainPrefs.DEFAULT_GUESSES_NOUNS_FILE_NAME);
			DrainPrefs.props.put(DrainPrefs.PROP_DOM_FILE_NAME,DrainPrefs.DEFAULT_DOM_NOUNS_FILE_NAME);
			DrainPrefs.props.put(DrainPrefs.PROP_GUESSES_FILE_NAME,DrainPrefs.DEFAULT_GUESSES_NOUNS_FILE_NAME);
			//practiceShowNounsMI.setEnabled(false);

		}
		else
		{
			masterDominicHM=DomMap.loadDomMapFromTextFile(DrainPrefs.DEFAULT_DOM_PEOPLE_FILE_NAME);
			guessesHM =GuessesMap.loadGuessesMapFromTextFile(DrainPrefs.DEFAULT_GUESSES_PEOPLE_FILE_NAME);
			DrainPrefs.props.put(DrainPrefs.PROP_DOM_FILE_NAME,DrainPrefs.DEFAULT_DOM_PEOPLE_FILE_NAME);
			DrainPrefs.props.put(DrainPrefs.PROP_GUESSES_FILE_NAME,DrainPrefs.DEFAULT_GUESSES_PEOPLE_FILE_NAME);
			//practiceShowPeopleMI.setEnabled(false);

		}
		//modeJL.setText(runMode);
		//clearUI();
		//this.runJB.setText("Start");
		running=false;
		//runJB.setText("Start!");
		//enablebts(false);
		 ui.changePracticeModesUI(runMode, guessesHM);

	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if( arg0.getSource().equals(ui.exitJB) || arg0.getSource().equals(ui.exitMI)) exit();
		if( arg0.getSource().equals(ui.practiceNounsMI))
		{
			 changePracticeModes(DrainPrefs.RUN_MODE_QUIZ_NOUNS);
		}
		else if(arg0.getSource().equals(ui.practicePeopleMI))
		{
			changePracticeModes(DrainPrefs.RUN_MODE_QUIZ_PEOPLE);
		}
		else if(arg0.getSource().equals(ui.practiceShowNounsMI))
		{
			changePracticeModes(DrainPrefs.RUN_MODE_SHOW_NOUNS);
		}
		else if(arg0.getSource().equals(ui.practiceShowPeopleMI))
		{
			changePracticeModes(DrainPrefs.RUN_MODE_SHOW_PEOPLE);
		}
		else if(arg0.getSource().equals(ui.displayMI))
		{
			ui.actionShowInfo();
		}
		else if( arg0.getSource().equals(ui.runJB))  
		{
				ui.enablebts(true);
				running = true;
				currentDw = getRandomDominicWord2();
				ui.primeUIWithThisDomWord(currentDw);
				start = System.currentTimeMillis();

		}
		else if(arg0.getSource().equals(ui.stopJB))
		{
				running=false;
				ui.clearUI();
				//runJB.setText("Start!");
				ui.enablebts(false);
				ui.displayResults(score,missedNouns.size(),missedPeople.size(),guessesHM);
				return;
		}
		
		else if( arg0.getSource().equals(ui.submitJB))
		{
			submitGuess();
		}
		else if( arg0.getSource().equals(ui.peekJB))
		{
			DomWord2 dw = null;
			List<DomWord2> dws  = masterDominicHM.get(ui.numericValueTF.getText());
			for(DomWord2 d:dws)
			{
				if( d.isMaster) dw = d;
			}
			ui.letterValueTF.setText(
								   //??majorLettersHM.getLettersFor2ByteNumber(ui.numericValueTF.getText())
									dw.letVal 
			                     );
			ui.actionPeek(dw);
		}
		else if( arg0.getSource().equals(ui.masterJB))
		{
			makeThisAMasterWord(ui.numericValueTF.getText(), ui.letterValueTF.getText(),ui.answerTF.getText());
		}
		
	}
	
	protected void saveOffAll()
	{
		LetterMap.saveMajorLettersToTextFile(DrainPrefs.props.getProperty(DrainPrefs.PROP_LETTER_FILE_NAME),majorLettersHM);
		GuessesMap.saveGuessesMapToTextFile(DrainPrefs.props.getProperty(DrainPrefs.PROP_GUESSES_FILE_NAME), guessesHM);
		DomMap.saveDomMapToTextFile(DrainPrefs.props.getProperty(DrainPrefs.PROP_DOM_FILE_NAME),masterDominicHM);
		DrainPrefs.savePropsToFile();
	}
	protected void exit()
	{
		int ans = JOptionPane.showConfirmDialog(null, "Exit?");
		if( ans != JOptionPane.YES_OPTION ){ return; }
		saveOffAll();
		if( running )
			//ui.displayResults(score,);
		System.exit(0);
	}

}

