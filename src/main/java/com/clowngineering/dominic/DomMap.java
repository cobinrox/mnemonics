package com.clowngineering.dominic;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.imageio.ImageIO;


public class DomMap extends HashMap<String,List<DomWord2>>
{
	private static final long serialVersionUID = 9175798112079928384L;
	
	public static final DomMap DEFAULT_CLOWN_DOM_PEOPLE_MAP = new DomMap() {
		private static final long serialVersionUID = -828265124196401878L;

	{
	    put("00",   new ArrayList<DomWord2>() {{ add(new DomWord2("00","CC","Charlie Chan",true));add(new DomWord2("00","CC","Charle Chaplin"));}});
	    put("01",   new ArrayList<DomWord2>() {{ add(new DomWord2("01","CT","Quentin Tarantino",true));}});
	    put("02",   new ArrayList<DomWord2>() {{ add(new DomWord2("02","CN","Chuck Norris",true));}});
	    put("03",   new ArrayList<DomWord2>() {{ add(new DomWord2("03","CM", "Charles Manson",true));}});
	    put("04",   new ArrayList<DomWord2>() {{ add(new DomWord2("04","CR", "Chris Rock",true));}});
	    put("05",   new ArrayList<DomWord2>() {{ add(new DomWord2("05","CS", "Charlie Sheen",true));}});
	    put("06",   new ArrayList<DomWord2>() {{ add(new DomWord2("06","CG", "Carey Grant",true));}});
	    put("07",   new ArrayList<DomWord2>() {{ add(new DomWord2("07","CL", "Cindy Lauper",true));add(new DomWord2("07","CL", "Cool (Joe)",false));}});
	    put("08",   new ArrayList<DomWord2>() {{ add(new DomWord2("08","CB", "Charlie Brown",true));}});
	    put("09",   new ArrayList<DomWord2>() {{ add(new DomWord2("09","CP", "Chris Paul",true));}});
	    
	    put("10",   new ArrayList<DomWord2>() {{ add(new DomWord2("10","TC","Tom Cruise",true));}});
	    put("11",   new ArrayList<DomWord2>() {{ add(new DomWord2("11","TT","Tina Turner",true));}});
	    put("12",   new ArrayList<DomWord2>() {{ add(new DomWord2("12","TN","Tom Nix",true));}});
	    put("13",   new ArrayList<DomWord2>() {{ add(new DomWord2("13","TM", "Tracey Morgan",true));}});
	    put("14",   new ArrayList<DomWord2>() {{ add(new DomWord2("14","TR", "Tony Romo",true));}});
	    put("15",   new ArrayList<DomWord2>() {{ add(new DomWord2("15","TS", "Tom Selleck",true));}});
	    put("16",   new ArrayList<DomWord2>() {{ add(new DomWord2("16","TG", "Thomas Gibson",true));}});
	    put("17",   new ArrayList<DomWord2>() {{ add(new DomWord2("17","TL", "Tommy Lee",true));}});
	    put("18",   new ArrayList<DomWord2>() {{ add(new DomWord2("18","TB", "Tyra Banks",true));}});
	    put("19",   new ArrayList<DomWord2>() {{ add(new DomWord2("19","TP", "Tom Petty",true));}});
	    
	    put("20",   new ArrayList<DomWord2>() {{ add(new DomWord2("20","NC","Nicholas Cage",true));}});
	    put("21",   new ArrayList<DomWord2>() {{ add(new DomWord2("21","NT","Niki Taylor",true));}});
	    put("22",   new ArrayList<DomWord2>() {{ add(new DomWord2("22","NN","Nick Nolte",true));}});
	    put("23",   new ArrayList<DomWord2>() {{ add(new DomWord2("23","NM", "Natalie Merchant",true));}});
	    put("24",   new ArrayList<DomWord2>() {{ add(new DomWord2("24","NR", "Nicole Richie",true));}});
	    put("25",   new ArrayList<DomWord2>() {{ add(new DomWord2("25","NS", "Nancy Sinatra",true));}});
	    put("26",   new ArrayList<DomWord2>() {{ add(new DomWord2("26","NG", "Newt Gingrich",true));}});
	    put("27",   new ArrayList<DomWord2>() {{ add(new DomWord2("27","NL", "Nathan Lane",true));}});
	    put("28",   new ArrayList<DomWord2>() {{ add(new DomWord2("28","NB", "Norman Bates",true));}});
	    put("29",   new ArrayList<DomWord2>() {{ add(new DomWord2("29","NP", "Nancy Pelosi",true));}});
	    /*
	    put("30",   new DomWord2("30","MC","Michael Caine"));
	    put("31",   new DomWord2("31","MT","Margaret Thatcher"));
	    put("32",   new DomWord2("32","MN","Martina Navratilova"));
	    put("33",   new DomWord2("33","MM", "Mickey Mouse"));
	    put("34",   new DomWord2("34","MR", "Mickey Rooney"));
	    put("35",   new DomWord2("35","MS", "Martin Sheen"));
	    put("36",   new DomWord2("36","MG", "Mel Gibson"));
	    put("37",   new DomWord2("37","ML", "Michael Landon"));
	    put("38",   new DomWord2("38","MB", "Mel Brooks"));
	    put("39",   new DomWord2("39","MP", "Michelle Pfeiffer"));
	    
	    put("40",   new DomWord2("40","RC","Richard Chamberlain"));
	    put("41",   new DomWord2("41","RT","Richard Thomas"));
	    put("42",   new DomWord2("42","RN","Richard Nixon"));
	    put("43",   new DomWord2("43","RM", "Roger Moore"));
	    put("44",   new DomWord2("44","RR", "Robert Redford"));
	    put("45",   new DomWord2("45","RS", "Red Skelton"));
	    put("46",   new DomWord2("46","RG", "Rudy Guliani"));
	    put("47",   new DomWord2("47","RL", "Rush Limbaugh"));
	    put("48",   new DomWord2("48","RB", "Richard Burton"));
	    put("49",   new DomWord2("49","RP", "Robert Plant"));
	    
	    put("50",   new DomWord2("50","SC","Santa Claus"));
	    put("51",   new DomWord2("51","ST","Steven Tyler"));
	    put("52",   new DomWord2("52","SN","Steve Nash"));
	    put("53",   new DomWord2("53","SM", "SuperMan"));
	    put("54",   new DomWord2("54","SR", "Smokey Robinson"));
	    put("55",   new DomWord2("55","SS", "Steven Seagal"));
	    put("56",   new DomWord2("56","SG", "Selena Gomez"));
	    put("57",   new DomWord2("57","SL", "Spike Lee"));
	    put("58",   new DomWord2("58","SB", "Sandra Bullock"));
	    put("59",   new DomWord2("59","SP", "Scotty Pippen"));
	    
	    put("60",   new DomWord2("60","GC","George Clooney"));
	    put("61",   new DomWord2("61","GT","George Thorogood"));
	    put("62",   new DomWord2("62","GN","Gary Neville"));
	    put("63",   new DomWord2("63","GM", "George Michael"));
	    put("64",   new DomWord2("64","GR", "Gilda Radner"));
	    put("65",   new DomWord2("65","GS", "George Strait"));
	    put("66",   new DomWord2("66","GG", "Greta Garbo"));
	    put("67",   new DomWord2("67","GL", "George Lucas"));
	    put("68",   new DomWord2("68","GB", "George Bush"));
	    put("69",   new DomWord2("69","GP", "Gwyneth Paltrow"));
	    
	    put("70",   new DomWord2("70","LC","Lon Chaney"));
	    put("71",   new DomWord2("71","LT","Liz Taylor"));
	    put("72",   new DomWord2("72","LN","Liam Neeson"));
	    put("73",   new DomWord2("73","LM", "Liza Minelli"));
	    put("74",   new DomWord2("74","LR", "Lone Ranger"));
	    put("75",   new DomWord2("75","LS", "Luke Skywalker"));
	    put("76",   new DomWord2("76","LG", "Lady GaGa"));
	    put("77",   new DomWord2("77","LL", "Lindsey Lohan"));
	    put("78",   new DomWord2("78","LB", "Larry Bird"));
	    put("79",   new DomWord2("79","LP", "Luke Perry"));
	    
	    put("80",   new DomWord2("80","BC","Bill Cosby"));
	    put("81",   new DomWord2("81","BT","Billy Thornton"));
	    put("82",   new DomWord2("82","BN","Bob Newhart"));
	    put("83",   new DomWord2("83","BM", "BatMan"));
	    put("84",   new DomWord2("84","BR", "Br'er Rabbit"));
	    put("85",   new DomWord2("85","BS", "Bruce Springsteen"));
	    put("86",   new DomWord2("86","BG", "Bill Gates"));
	    put("87",   new DomWord2("87","BL", "Bruce Lee"));
	    put("88",   new DomWord2("88","BB", "Bilboe Baggins"));
	    put("89",   new DomWord2("89","BP", "Brad Pitt"));
	    
	    put("90",   new DomWord2("90","PC","Prince Charles"));
	    put("91",   new DomWord2("91","PT","Pete Townshend"));
	    put("92",   new DomWord2("92","PN","Paul Newman"));
	    put("93",   new DomWord2("93","PM", "Paul McCartney"));
	    put("94",   new DomWord2("94","PR", "Portia de Rossi"));
	    put("95",   new DomWord2("95","PS", "Pat Sajak"));
	    put("96",   new DomWord2("96","PG", "Peter Gabriel"));
	    put("97",   new DomWord2("97","PL", "Pippy Longstocking"));
	    put("98",   new DomWord2("98","PB", "Paul Bunyan"));
	    put("99",   new DomWord2("99","PP", "Peter Parker"));
	   */
	}};
	
	public static final DomMap DEFAULT_CLOWN_DOM_NOUNS_MAP = new DomMap() {
		private static final long serialVersionUID = -828265124196401878L;

	{
	    put("00",   new ArrayList<DomWord2>() {{ add(new DomWord2("00","CC","COO COO",true));add(new DomWord2("00","CC","Charlie Chan",false));}});
	    put("01",   new ArrayList<DomWord2>() {{ add(new DomWord2("01","CT","CAT",true));}});
	    put("02",   new ArrayList<DomWord2>() {{ add(new DomWord2("02","CN","COON",true));}});
	    put("03",   new ArrayList<DomWord2>() {{ add(new DomWord2("03","CM", "CHARLES MANSON",true));}});
	    put("04",   new ArrayList<DomWord2>() {{ add(new DomWord2("04","CR", "CAR",true));}});
	    put("05",   new ArrayList<DomWord2>() {{ add(new DomWord2("05","CS", "CASE",true));}});
	    put("06",   new ArrayList<DomWord2>() {{ add(new DomWord2("06","CG", "CAGE",true));}});
	    put("07",   new ArrayList<DomWord2>() {{ add(new DomWord2("07","CL", "COLA",true));add(new DomWord2("07","CL", "Cindi Lauper",false));add(new DomWord2("07","CL", "COOL",false));}});
	    put("08",   new ArrayList<DomWord2>() {{ add(new DomWord2("08","CB", "CAB",true));}});
	    put("09",   new ArrayList<DomWord2>() {{ add(new DomWord2("09","CP", "COP",true));}});
	    
	    put("10",   new ArrayList<DomWord2>() {{ add(new DomWord2("10","TC","TACK",true));}});
	    put("11",   new ArrayList<DomWord2>() {{ add(new DomWord2("11","TT","TUT",true));}});
	    put("12",   new ArrayList<DomWord2>() {{ add(new DomWord2("12","TN","TIN",true));}});
	    put("13",   new ArrayList<DomWord2>() {{ add(new DomWord2("13","TM", "TOMMY",true));}});
	    put("14",   new ArrayList<DomWord2>() {{ add(new DomWord2("14","TR", "TIRE",true));}});
	    put("15",   new ArrayList<DomWord2>() {{ add(new DomWord2("15","TS", "TOES",true));}});
	    put("16",   new ArrayList<DomWord2>() {{ add(new DomWord2("16","TG", "TUG",true));}});
	    put("17",   new ArrayList<DomWord2>() {{ add(new DomWord2("17","TL", "TAIL",true));}});
	    put("18",   new ArrayList<DomWord2>() {{ add(new DomWord2("18","TB", "TUB",true));}});
	    put("19",   new ArrayList<DomWord2>() {{ add(new DomWord2("19","TP", "TIPI",true));}});
	    
	    put("20",   new ArrayList<DomWord2>() {{ add(new DomWord2("20","NC","KNOCK",true));}});
	    put("21",   new ArrayList<DomWord2>() {{ add(new DomWord2("21","NT","NUT",true));}});
	    put("22",   new ArrayList<DomWord2>() {{ add(new DomWord2("22","NN","NUN",true));}});
	    put("23",   new ArrayList<DomWord2>() {{ add(new DomWord2("23","NM", "ENEMY",true));}});
	    put("24",   new ArrayList<DomWord2>() {{ add(new DomWord2("24","NR", "NERO",true));}});
	    put("25",   new ArrayList<DomWord2>() {{ add(new DomWord2("25","NS", "NOSE",true));}});
	    put("26",   new ArrayList<DomWord2>() {{ add(new DomWord2("26","NG", "NAG",true));}});
	    put("27",   new ArrayList<DomWord2>() {{ add(new DomWord2("27","NL", "NAIL",true));}});
	    put("28",   new ArrayList<DomWord2>() {{ add(new DomWord2("28","NB", "KNOB",true));}});
	    put("29",   new ArrayList<DomWord2>() {{ add(new DomWord2("29","NP", "NAP",true));}});
	    /*
	    put("30",   new DomWord2("30","MC","MACK"));
	    put("31",   new DomWord2("31","MT","MAT"));
	    put("32",   new DomWord2("32","MN","MOON"));
	    put("33",   new DomWord2("33","MM", "M&M"));
	    put("34",   new DomWord2("34","MR", "MIRROR"));
	    put("35",   new DomWord2("35","MS", "MASS"));
	    put("36",   new DomWord2("36","MG", "MUG"));
	    put("37",   new DomWord2("37","ML", "MOLE"));
	    put("38",   new DomWord2("38","MB", "MOB"));
	    put("39",   new DomWord2("39","MP", "MOP"));
	    
	    put("40",   new DomWord2("40","RC","ROCK"));
	    put("41",   new DomWord2("41","RT","RAT"));
	    put("42",   new DomWord2("42","RN","RHINO"));
	    put("43",   new DomWord2("43","RM", "ARM"));
	    put("44",   new DomWord2("44","RR", "ROAD RUNNER"));
	    put("45",   new DomWord2("45","RS", "ROSE"));
	    put("46",   new DomWord2("46","RG", "RUG"));
	    put("47",   new DomWord2("47","RL", "REEL"));
	    put("48",   new DomWord2("48","RB", "RIB"));
	    put("49",   new DomWord2("49","RP", "ROPE"));
	    
	    put("50",   new DomWord2("50","SC","SOCK"));
	    put("51",   new DomWord2("51","ST","SOOT"));
	    put("52",   new DomWord2("52","SN","SUN"));
	    put("53",   new DomWord2("53","SM", "SUMO"));
	    put("54",   new DomWord2("54","SR", "SORE"));
	    put("55",   new DomWord2("55","SS", "SNAKE"));
	    put("56",   new DomWord2("56","SG", "SEWAGE"));
	    put("57",   new DomWord2("57","SL", "SALE"));
	    put("58",   new DomWord2("58","SB", "SUB"));
	    put("59",   new DomWord2("59","SP", "SOAP"));
	    
	    put("60",   new DomWord2("60","GC","GECKO"));
	    put("61",   new DomWord2("61","GT","GOAT"));
	    put("62",   new DomWord2("62","GN","GUN"));
	    put("63",   new DomWord2("63","GM", "GUM"));
	    put("64",   new DomWord2("64","GR", "GRIZZLY"));
	    put("65",   new DomWord2("65","GS", "GOOSE"));
	    put("66",   new DomWord2("66","GG", "GAG"));
	    put("67",   new DomWord2("67","GL", "GHOUL"));
	    put("68",   new DomWord2("68","GB", "GAB"));
	    put("69",   new DomWord2("69","GP", "GOOP"));
	    
	    put("70",   new DomWord2("70","LC","LOCK"));
	    put("71",   new DomWord2("71","LT","LATTE"));
	    put("72",   new DomWord2("72","LN","LION"));
	    put("73",   new DomWord2("73","LM", "LOOM"));
	    put("74",   new DomWord2("74","LR", "LURE"));
	    put("75",   new DomWord2("75","LS", "LASSO"));
	    put("76",   new DomWord2("76","LG", "LEG"));
	    put("77",   new DomWord2("77","LL", "LOLLI"));
	    put("78",   new DomWord2("78","LB", "LAB"));
	    put("79",   new DomWord2("79","LP", "LIP"));
	    
	    put("80",   new DomWord2("80","BC","BC"));
	    put("81",   new DomWord2("81","BT","BAT"));
	    put("82",   new DomWord2("82","BN","BOONE"));
	    put("83",   new DomWord2("83","BM", "BUM"));
	    put("84",   new DomWord2("84","BR", "BEER"));
	    put("85",   new DomWord2("85","BS", "BUS"));
	    put("86",   new DomWord2("86","BG", "BUG"));
	    put("87",   new DomWord2("87","BL", "BALL"));
	    put("88",   new DomWord2("88","BB", "BABY"));
	    put("89",   new DomWord2("89","BP", "BOOP"));
	    
	    put("90",   new DomWord2("90","PC","PUCK"));
	    put("91",   new DomWord2("91","PT","PIT"));
	    put("92",   new DomWord2("92","PN","PEN"));
	    put("93",   new DomWord2("93","PM", "PUMA"));
	    put("94",   new DomWord2("94","PR", "PEAR"));
	    put("95",   new DomWord2("95","PS", "PUS"));
	    put("96",   new DomWord2("96","PG", "PIG"));
	    put("97",   new DomWord2("97","PL", "POOL"));
	    put("98",   new DomWord2("98","PB", "PUB"));
	    put("99",   new DomWord2("99","PP", "POOP"));
	   */
	    
	}};
	public DomMap( )
	{
		super();
	}
	public DomMap( String nv, DomWord2 dw)
	{
		
	}
	public DomWord2 getValForNum(String num)
	{
		List<DomWord2> dws = get(num);
		for( DomWord2 dw: dws)
		{
			if( dw.isMaster ) return dw;
		}
		return get(num).get(0);
	}
	
	public static void main(String arg[])
	{
		DomMap.saveDomMapToTextFile("deleteme.txt",DomMap.DEFAULT_CLOWN_DOM_NOUNS_MAP);
		DomMap dm = DomMap.loadDomMapFromTextFile("deleteme.txt");
		System.out.println(dm);
	}
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("");
	   //Set<String> keys = this.keySet();
	   List<String> keys=new ArrayList(this.keySet());
		Collections.sort(keys);
	   for(String k: keys)
	   {
		   List<DomWord2> dws = get(k);
		   sb.append(k);sb.append("\n");
		   for(DomWord2 dw: dws)
		   {
			   sb.append("   ");
			   sb.append(dw.letVal);
			   sb.append(",");
			   sb.append(dw.word);
			   sb.append(",");
			   sb.append(dw.isMaster);
			   String fn = dw.buildFileName();
			   try
			   {
			   javax.swing.ImageIcon ii = new javax.swing.ImageIcon(getClass().getResource("/"+fn));
			   if( ii != null )
			   {
				
				   sb.append(",");
			       sb.append(fn);
		       }
			   }catch(Throwable t){}
			   
			   sb.append("\n");
		   }
		   sb.append("===============================\n");
		   
	   }
	   return sb.toString();
	}
	public static void saveDomMapToTextFile(String domMapFname, DomMap map)
	    {
	        FileWriter fw = null;
	        try
	        {
	            fw = new FileWriter(domMapFname);
	            
	            List<String> keys=new ArrayList<String>(map.keySet());
	            Collections.sort(keys);
	            for(String key:keys)
	            {
	            	List<DomWord2> dws = map.get(key);
	            	for( DomWord2 dw:dws )
	            	{
		                fw.write(key);
		                fw.write(",");
		                fw.write(dw.numVal);
		                fw.write(",");
		                fw.write(dw.letVal);
		                fw.write(",");
		                fw.write(dw.word);
		                fw.write(",");
		                fw.write(""+dw.isMaster);
		                fw.write(System.getProperty("line.separator"));

	            	}
	            }
	        }
	        catch(Throwable t)
	        {
	        	System.err.println("Error saving Dominic Map to file [" + domMapFname + "]");
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
	public static DomMap loadDomMapFromTextFile(String domFname)
	{
	        File f = new File(domFname);
	        BufferedReader br =null;
	        DomMap map = new DomMap();
	        if( !f.exists() )
	        {
	            return null;
	        }
	        try
	        {
	            br =  new BufferedReader(new FileReader(domFname));
	            String line=null;
	            while( (line=br.readLine()) != null )
	            {
	            	
	                try
	                {
	                    String[] s = line.split(",");
	                    DomWord2 dw = new DomWord2(s[1],s[2],s[3],Boolean.parseBoolean(s[4]));
	                    String key = s[0];
	                    if( map.containsKey(key))
	                    {
	                       List<DomWord2> dws = map.get(key);
	                       dws.add(dw);
	                    }
	                    else
	                    {
	                    	List<DomWord2> dws = new ArrayList<DomWord2>();
	                    	dws.add(dw);
	                    	map.put(s[0],dws );
	                    }
	                }
	                catch(Throwable t)
	                {
	                    System.out.println("Warning, skipping bad dom value [" + line + "] " + t.getMessage());
	                }
	            }
	            return map;
	        }
	        catch(Throwable t)
	        {
	        	System.err.println("Error reading letter file [" + domFname + "]");
	            t.printStackTrace();
	            return map;
	        }
	        finally
	        {
	            if(br != null )try{br.close();}catch(Throwable t){}
	        }
	    }
}
