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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

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
public class UI  {

	public static final int POP_UP_ANSWER_DELAY = 750;

	//private static String dm.runMode;
	
	JFrame jf;
	JTextField fnameTF;// = new JTextField();
	JTextField letterValueTF = new JTextField(5);
	JTextField numericValueTF  = new JTextField(15);
	//JLabel      isMasterJL     = new JLabel("   ");
	JTextField answerTF = new JTextField(15);
	JButton    runJB    = new JButton("Start!");
	JButton    stopJB   = new JButton("Stop");
	//JButton    resetJB = new JButton("Reset to 00");
	JButton    submitJB = new JButton("Submit");
	//JButton    pauseJB  = new JButton("Pause");
	//JButton    cancelJB = new JButton("Stop");
	JButton    exitJB   = new JButton("Exit");
	JButton    peekJB   = new JButton("Hint");
	JButton    masterJB = new JButton("Make Master");
	JLabel     peekJL = new JLabel("               ");
	JLabel     imageJL = new JLabel();
	JMenu      modeMI = new JMenu("Mode");  
	JMenuItem  practiceShowNounsMI = new JMenuItem("Show NOUN Values");
	JMenuItem  practiceShowPeopleMI = new JMenuItem("Show PEOPLE Values");

	JMenuItem  practiceNounsMI = new JMenuItem("Quiz/Train with NOUNS");
	JMenuItem  practicePeopleMI = new JMenuItem("Quiz/Train with PEOPLE");
	JMenuItem  displayMI = new JMenuItem("Display...");
	JMenuItem  loadMI= new JMenuItem("Load...");
	JMenuItem  exitMI= new JMenuItem("Exit");   
	
	JLabel dummyNorth = new JLabel(" ");
	JLabel dummySouth = new JLabel(" " );
	String imagePrefixLocation = "C:/aaa/kindle/mnemonics/OEBPS/Images/sm";

	protected JLabel modeJL;
	
	//boolean running;
	//DomWord currentDw;
	//long start;
	//long stop;
	/**
	 * Utilitye class to force uppercase entry of values
	 * for text fields.
	 */
	DocumentFilter filter = new UppercaseDocumentFilter();
	//Random rand = new Random();
	//int min = 0;
	//int max = 9;
	/////////////////DomMap masterDominicHM;// = new HashMap<String, DomWord>();
	/**
	 * Major Mnemonic List (map) of numbers to letters, e.g.
	 * 0: C
	 * 1: T
	 * 2: N ...
	 */
	///////////////LetterMap majorLettersHM;
	String tab = "     ";
	//String masterFname = "master.txt";
	//String guessFname  = "guess.bin";
	//String guessFnameText = "guess.csv";
	//String majorFname = "major.txt";
	
	/**
	 * Dimensions for mnemonic cartoon image
	 * for given dominic word
	 */
	final static int hintImgWidth=100;
	final static int hintImgHeight=100;
	//boolean ezMode;
	
	DomMain dm;
	
	public UI(DomMain dm)
	{
		this.dm = dm;
	}
	public void setVisible(boolean v)
	{
		jf.setVisible(v);
	}
	 int peepShowIdx = 0;
	 int nounShowIdx = 0;

	protected void clearUI()
	{
		setColor();
		numericValueTF.setText("");
		answerTF.setText("");
	    letterValueTF.setText("     ");
	    imageJL.setIcon(null);
		
		peekJL.setText("               ");
		// prolly dont need this since we have a full master list now
		/*if( masterDominicHM.containsKey(currentDw.numericValue))
			isMasterJL.setText("*");
		else
			isMasterJL.setText(" ");
		*/
	}
	protected void primeUIWithThisDomWord(DomWord2 dw)
	{
		numericValueTF.setText(""+dw.numVal);
		if(dm.runMode.equals(DrainPrefs.RUN_MODE_SHOW_NOUNS) ||
				dm.runMode.equals(DrainPrefs.RUN_MODE_SHOW_PEOPLE))
		{
			peekJL.setText(dw==null?"unknown":dw.word);
			letterValueTF.setText(
								   dm.majorLettersHM.getLettersFor2ByteNumber(numericValueTF.getText())
			                     );
			this.answerTF.setText(dw.word);
			imageJL.setIcon(null);
			setImagesForThisDomWord(imageJP,dw);
		}
		else
		{
			letterValueTF.setText("     ");
			imageJL.setIcon(null);
		}
		answerTF.requestFocusInWindow();
		peekJL.setText("               ");
	}

	
	Color demoColor = Color.green.darker().darker();
	Color quizColor = Color.red.darker();
	protected void popupTheAnswerBriefly(DomWord2 dwCorrectAnswer, 
			                             boolean choseCorrectAnswer,
			                             int numRight,
			                             int numWrong)
	{

		final JDialog jd = new JDialog();
		jd.setLayout(new GridBagLayout());
		
		JLabel jl1 = new JLabel(dwCorrectAnswer.numVal);
		Font curFont = jl1.getFont();
		jl1.setFont(new Font(curFont.getFontName(), curFont.getStyle(), 16));
		
		JLabel jl2 = new JLabel(dwCorrectAnswer.letVal);
		jl2.setFont(new Font(curFont.getFontName(), curFont.getStyle(), 16));

		JLabel jl3 = new JLabel(dwCorrectAnswer.word);
		jl3.setFont(new Font(curFont.getFontName(), curFont.getStyle(), 16));
		
		JPanel imageJP = new JPanel();
		JLabel jl4  = new JLabel();
		jl4.setPreferredSize(new Dimension(hintImgWidth,hintImgHeight));
		jl4.setSize(new Dimension(hintImgWidth,hintImgHeight));
		
		String scoreMsg =""+numRight + " right, ";
		scoreMsg+= numWrong + " wrong";
		JLabel jl5 = new JLabel(scoreMsg);

		imageJP.add(jl4);
		setImageForThisDomWord(imageJP,dwCorrectAnswer);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2,2,2,2);
		gbc.gridx=0;gbc.gridy=0;
		jd.getContentPane().add(jl1,gbc);gbc.gridx=0;gbc.gridy++;
		jd.getContentPane().add(jl2,gbc);gbc.gridx=0;gbc.gridy++;
		jd.getContentPane().add(jl3,gbc);gbc.gridx=0;gbc.gridy++;
		jd.getContentPane().add(imageJP,gbc);gbc.gridx=0;gbc.gridy++;
		jd.getContentPane().add(jl5,gbc);
		
		//jd.setPreferredSize(new Dimension(200,100));
		jd.setTitle("ANSWER");
		jd.pack();
		jd.setLocationRelativeTo(null);

		if( choseCorrectAnswer ) jd.getContentPane().setBackground(demoColor);
		else jd.getContentPane().setBackground(quizColor);

		jd.setVisible(true);
		
		final Runnable closerRunner = new Runnable()
        {
            public void run()
            {
                jd.setVisible(false);
                jd.dispose();
            }
        };
        Runnable waitRunner = new Runnable()
        {
            public void run()
            {
                try
                    {
                        Thread.sleep(POP_UP_ANSWER_DELAY);
                        SwingUtilities.invokeAndWait(closerRunner);
                    }
                catch(Exception e)
                    {
                        e.printStackTrace();
                    }
            }
        };
    jd.setVisible(true);
    Thread splashThread = new Thread(waitRunner, "SplashThread");
    splashThread.start();		
	}
	protected String displayResults(int score, int missedNouns, int missedPeople, GuessesMap guesses)
	{
		String results = "Your score:\n"+
				         "[" + score + "] correct\n"+
	                     "[" + missedNouns  + "] incorrect nouns\n" +
				         "[" + missedPeople + "] incorrect people\n";
		System.out.println("Total of [" + guesses.size() + "] guesses are in the guess file");
		List<DomGuess> badGuesses = new ArrayList<DomGuess>();
		List<String> keys=new ArrayList(guesses.keySet());
		Collections.sort(keys);
		DomGuess topBadGuesses[] = null;
		int top = 10;
		HashMap<String,Integer> baddies = new HashMap<String,Integer>();
		for( String keyOfGuessedWord:keys)
		{
			String masterNv=keyOfGuessedWord ;
			String masterLv=(guesses.get(keyOfGuessedWord)).get(0).numVal;
			String masterW = "unknown";
			if( dm.masterDominicHM.containsKey(masterNv))
			{
				
				List<DomWord2> dws = dm.masterDominicHM.get(masterNv);
				for(DomWord2 d:dws)
				{
					if(d.isMaster)masterW=d.word;
				}
				//masterW = dw.word;
			}
			
			int i=0;
			System.out.println(masterNv+"/"+masterLv+"/"+ masterW);
			ArrayList<DomGuess> al = guesses.get(keyOfGuessedWord);
			int badguesses=0;
		    for(DomGuess dg: al)
			{
		    	if(!dg.word.equalsIgnoreCase(masterW) || dg.word.equalsIgnoreCase("PASS"))
		    	{
		    		//badGuesses.add(dg);
		    		badguesses+=dg.numGuesses;
		    		//updateBaddiesIfBadder(masterW,badguesses);
		    		baddies.put(masterW,badguesses);
		    		
		    	}
				// annotate output if really bad or really good
		    	 String decoration = getDecoration(masterNv,dg);
				System.out.print(decoration);
				System.out.println(tab + dg.numVal + "/" + dg.letVal + "/" + dg.word + ": " + dg.numGuesses + " @ avg " +
			      (dg.sumOfTimesToGuess/(float)dg.numGuesses)/1000 + " secs");
				i++;
			}
		    System.out.println("TOTAL BAD GUESSES FOR [" + masterW + "]: " + badguesses);
			System.out.println("===============================================");
			
		}
		LinkedHashMap<String,Integer> tops = sortHashMapByValuesD(baddies);
		if(tops !=null )
		{
			System.out.println("Finding Top " + top + " bad guesses:");
			List<String> words=new ArrayList(tops.keySet());
			results+="\nTop [" + (tops.size()>top?top:tops.size()) + "] missed words:\n";
			
			int t=0;
			for(int i = (words.size()-1);i>-1;i--) 
			{
				String w = words.get(i);
				Integer numBad = (tops.get(w));
				results += w + ": " + numBad + " bad guesses\n";
				t++;
				if( t== top) break;
			}
		}
		JOptionPane.showMessageDialog(jf, results);

		return results ;
	}
	public LinkedHashMap sortHashMapByValuesD(HashMap passedMap) {
	    List mapKeys = new ArrayList(passedMap.keySet());
	    List mapValues = new ArrayList(passedMap.values());
	    Collections.sort(mapValues);
	    Collections.sort(mapKeys);
	        
	    LinkedHashMap sortedMap = 
	        new LinkedHashMap();
	    
	    Iterator valueIt = mapValues.iterator();
	    while (valueIt.hasNext()) {
	        Object val = valueIt.next();
	        Iterator keyIt = mapKeys.iterator();
	        
	        while (keyIt.hasNext()) {
	            Object key = keyIt.next();
	            String comp1 = passedMap.get(key).toString();
	            String comp2 = val.toString();
	            
	            if (comp1.equalsIgnoreCase(comp2)){
	                passedMap.remove(key);
	                mapKeys.remove(key);
	                sortedMap.put((String)key, (Integer)val);
	                break;
	            }
	        }
	    }
	    return sortedMap;
	}
	protected void setColor( )
	{
		for (Component c : getAllComponents(jf)) {
		    if( c instanceof JPanel)
		    {
		    	if( dm.runMode.equals(DrainPrefs.RUN_MODE_QUIZ_NOUNS) ||
		    			dm.runMode.equals(DrainPrefs.RUN_MODE_QUIZ_PEOPLE))
		    		c.setBackground(quizColor);
		    	else
		    		c.setBackground(demoColor);
		    }
		    else
		    {
		    	//System.out.println( c.getClass().getName());
		    }
		}
	}
	public static List<Component> getAllComponents(final Container c) {
	    Component[] comps = c.getComponents();
	    List<Component> compList = new ArrayList<Component>();
	    for (Component comp : comps) {
	        compList.add(comp);
	        if (comp instanceof Container)
	            compList.addAll(getAllComponents((Container) comp));
	    }
	    return compList;
	}
	/**
	 * Return +/- sign characters to help highlight (decorate) output
	 * @param masterKey
	 * @param guess
	 * @return
	 */
	protected String getDecoration(String masterKey, DomGuess guess)
	{
		if( true ) return "";
		if( guess.word.equalsIgnoreCase("PASS")) return "-";
		String masterWord=null;
		List<DomWord2> dws = dm.masterDominicHM.get(masterKey);
		for( DomWord2 d:dws)
		{
			if( d.isMaster) masterWord = d.word;
		}
		// = dm.masterDominicHM.get(masterKey)!=null?dm.masterDominicHM.get(masterKey).word:null;
		if( masterWord!= null &&
				masterWord.equalsIgnoreCase(guess.word) && !masterWord.equalsIgnoreCase("PASS") )
		{
			return "+";
		}
		if( (guess.sumOfTimesToGuess/(float)guess.numGuesses)/1000 > 5)
			return "-";
		if( guess.numGuesses > 1) return "+";
		return "";
	}
	protected void buildMenu(ActionListener al)
	{
		JMenu toolsMenu = new JMenu("Options");
		modeMI.add(practiceShowNounsMI);
		modeMI.add(practiceShowPeopleMI);
		modeMI.add(practiceNounsMI);
		modeMI.add(practicePeopleMI);
		
		if( dm.runMode.equals(DrainPrefs.RUN_MODE_QUIZ_PEOPLE))
			practicePeopleMI.setEnabled(false);
		else if(dm.runMode.equals(DrainPrefs.RUN_MODE_QUIZ_NOUNS))
			practiceNounsMI.setEnabled(false);
		else if(dm.runMode.equals(DrainPrefs.RUN_MODE_SHOW_NOUNS))
			practiceShowNounsMI.setEnabled(false);
		else practiceShowPeopleMI.setEnabled(false);
		
		modeMI.addActionListener(al);
		practiceShowNounsMI.addActionListener(al);
		practiceShowPeopleMI.addActionListener(al);
		practiceNounsMI.addActionListener(al);
		practicePeopleMI.addActionListener(al);
		
		displayMI.addActionListener(al);
		
		exitMI.addActionListener(al);
		loadMI.addActionListener(al);

		toolsMenu.add(modeMI);
		toolsMenu.add(displayMI);
		toolsMenu.add(exitMI);
		JMenuBar mb = new JMenuBar();
		mb.add(toolsMenu);
		jf.setJMenuBar(mb);
	}
	JPanel imageJP;
	public void buildUI(final ActionListener al)
	{
		jf = new JFrame("Dominic System Quiz");
		jf.setSize(new Dimension(750, 550));
		jf.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent e) {
	        al.actionPerformed(new ActionEvent(exitJB,0,null));//exit();
	        }
	    });
		buildMenu(al);
		
		JPanel inputJP = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(2,2,2,2);
		gbc.gridx = gbc.gridy = 0;
		
		imageJP = new JPanel(new GridBagLayout());
		//imageJP.setBorder(new TitledBorder("Image Hint"));
		imageJP.setAutoscrolls(true);
		JScrollPane imgScroll = new JScrollPane( imageJP);
		imgScroll.setPreferredSize(new Dimension(375,300));
		
		gbc.gridheight=8;
		gbc.fill=gbc.VERTICAL;
		inputJP.add(imgScroll,gbc);
		gbc.gridheight=1;
		gbc.fill=gbc.NONE;
		
		JPanel topJP = new JPanel();
		gbc.gridy++;
		gbc.gridx=1;
		gbc.gridwidth=3;
		gbc.fill= gbc.HORIZONTAL;
		topJP.add(runJB );
		runJB.addActionListener(al);
		
		topJP.add(stopJB );
		stopJB.addActionListener(al);stopJB.setEnabled(false);
		
		inputJP.add(topJP,gbc);
		gbc.gridwidth=1;
		gbc.fill=gbc.NONE;
		
		gbc.gridx=0;gbc.gridy++;
		JLabel jl = new JLabel("Letter value hint:");
		gbc.gridx++;inputJP.add(jl,gbc);
		
		gbc.gridx++;
		jl = new JLabel("Numeric value:");
		gbc.gridx++;inputJP.add(jl,gbc);
		
		gbc.gridx=0;gbc.gridy++;
		letterValueTF.setEditable(false);
		gbc.gridx++;inputJP.add(letterValueTF,gbc);
		gbc.gridx++;
		numericValueTF.setEditable(false);
		gbc.gridx++;inputJP.add(numericValueTF,gbc);
		
		gbc.gridx=0; gbc.gridy++;
		jl = new JLabel("Dominic Word:");
		gbc.gridx++;inputJP.add(jl,gbc);
		
		gbc.gridx++;
		gbc.gridx++;inputJP.add(answerTF,gbc);
		((AbstractDocument) answerTF.getDocument()).setDocumentFilter(filter);
		answerTF.setEnabled(false);
		answerTF.requestFocusInWindow();
		
		JPanel btJP = new JPanel();
		btJP.add(submitJB);submitJB.addActionListener(al); submitJB.setEnabled(false);
		JRootPane jrp = jf.getRootPane(); jrp.setDefaultButton(submitJB);
		btJP.add(exitJB);exitJB.addActionListener(al);
		
		gbc.gridx=0;
		gbc.gridy++;
		gbc.fill=gbc.HORIZONTAL;
		gbc.gridwidth=3;
		gbc.gridx++;inputJP.add(btJP,gbc);
		
		JPanel btJP2 = new JPanel();
		btJP2.add(peekJB);peekJB.addActionListener(al);peekJB.setEnabled(false);
		btJP2.add(masterJB);masterJB.addActionListener(al);masterJB.setEnabled(false);
		
		gbc.gridx=0;
		gbc.gridy++;
		gbc.gridx++;inputJP.add(btJP2,gbc);
		
		gbc.gridx=0;
		gbc.gridy++;
		JPanel jp3 = new JPanel();
		jp3.add(peekJL);
		gbc.gridx++;inputJP.add(jp3,gbc);
		
		jf.getContentPane().add(inputJP,BorderLayout.CENTER);
		
		JPanel jp = new JPanel();
		jp.add(new JLabel("Practice Mode:"));
		modeJL = new JLabel(dm.runMode);
		jp.add(modeJL);
		jf.getContentPane().add(jp,BorderLayout.NORTH);
		
		//imageJP.setBorder(new TitledBorder("Image Hint"));
		  jf.setLocationRelativeTo(null);
		setColor();
	}
	protected void changePracticeModesUI(String newMode, GuessesMap guessesHM)
	{
		dm.runMode = newMode;
		practiceNounsMI.setEnabled(true);
		practicePeopleMI.setEnabled(true);
		practiceShowNounsMI.setEnabled(true);
		practiceShowPeopleMI.setEnabled(true);
		
		if(newMode.equals(DrainPrefs.RUN_MODE_QUIZ_NOUNS))
		{
			practiceNounsMI.setEnabled(false);
		}
		else if( dm.runMode.equals(DrainPrefs.RUN_MODE_QUIZ_PEOPLE))
		{
			practicePeopleMI.setEnabled(false);
		}
		else if( dm.runMode.equals(DrainPrefs.RUN_MODE_SHOW_NOUNS))
		{
			practiceShowNounsMI.setEnabled(false);
		}
		else
		{
			practiceShowPeopleMI.setEnabled(false);
		}
		modeJL.setText(dm.runMode);
		clearUI();
		////////////////running=false;
		runJB.setText("Start!");
		enablebts(false);
	}
	public void actionPeek(DomWord2 dw)
	{
			peekJL.setText(dw==null?"unknown":dw.word);
			setImagesForThisDomWord(imageJP,dw);
	}
	public void actionShowInfo()
	{
		String x = "Your files are located at:\n" +
				new File(DrainPrefs.prefFileName).getAbsolutePath();
		x+= "\nYour Working dir:\n" + System.getProperty("user.dir");

		x += "\nYour Classpath:\n";
		String cp = System.getProperty("java.class.path");
		int maxLineSize = 50;
		int lineSize = 0;
		for(String p:cp.split(";"))
		{
			x+=p; lineSize += p.length();
			if( lineSize >= maxLineSize )
			{
				x+= "\n";
				lineSize = 0;
			}
		}
		JOptionPane.showMessageDialog(jf, x);
	}

	protected void setImageForThisDomWord( JPanel jp, DomWord2 domWord)
	{
		if( domWord.imageFile == null )
		{			String fn = domWord.buildFileName();
			domWord.imageFile = fn;
		}
		List<DomWord2> dws = new ArrayList<DomWord2>();
		dws.add(domWord);
		ImageCell.addImgCells(dws,jp,(dm.runMode.equals(DrainPrefs.RUN_MODE_SHOW_PEOPLE)||
				                      dm.runMode.equals(DrainPrefs.RUN_MODE_SHOW_NOUNS)?demoColor:quizColor),
				                     (dm.runMode.equals(DrainPrefs.RUN_MODE_QUIZ_PEOPLE)||
						              dm.runMode.equals(DrainPrefs.RUN_MODE_QUIZ_NOUNS)?quizColor:Color.white)
				                     );
		setColor();
	}
	protected void setImagesForThisDomWord( JPanel jp, DomWord2 domWord)
	{
 		try
		{
			//DomWord master = masterDominicHM.get(numericValueTF.getText());
 			List<DomWord2> dws = dm.masterDominicHM.get(domWord.numVal);
 			for( DomWord2 d:dws)
 			{
 				d.imageFile = d.buildFileName();
 			}
			ImageCell.addImgCells(dws,jp,(dm.runMode.equals(DrainPrefs.RUN_MODE_SHOW_PEOPLE)||
                    dm.runMode.equals(DrainPrefs.RUN_MODE_SHOW_NOUNS)?demoColor:quizColor),
                   (dm.runMode.equals(DrainPrefs.RUN_MODE_QUIZ_PEOPLE)||
		              dm.runMode.equals(DrainPrefs.RUN_MODE_QUIZ_NOUNS)?Color.white:quizColor)
                   );
			setColor();
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		    System.err.println("Error showing image "+ t.getMessage());
		    //jl.setIcon(null);
		}
	}
	protected void enablebts(boolean x)
	{
		runJB.setEnabled(!x);
		
		stopJB.setEnabled(x);
		//resetJB.setEnabled(x);
		submitJB.setEnabled(x);
		//cancelJB.setEnabled(x);
		//pauseJB.setEnabled(x);
		peekJB.setEnabled(x);
		masterJB.setEnabled(x);
		answerTF.setEnabled(x);
		if(dm.runMode.equals(DrainPrefs.RUN_MODE_SHOW_NOUNS) ||
				dm.runMode.equals(DrainPrefs.RUN_MODE_SHOW_PEOPLE) )
		{
			peekJB.setEnabled(false);
		}
	}
}

/**
 * Utility class to help force uppercase on text boxes
 * @author name
 *
 */
class UppercaseDocumentFilter extends DocumentFilter {
    public void insertString(DocumentFilter.FilterBypass fb, int offset,
            String text, AttributeSet attr) throws BadLocationException {

        fb.insertString(offset, text.toUpperCase(), attr);
    }

    public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
            String text, AttributeSet attrs) throws BadLocationException {

        fb.replace(offset, length, text.toUpperCase(), attrs);
    }
}
