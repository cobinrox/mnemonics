package com.clowngineering.dominic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.KeyStore.Builder;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

//import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
//import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


public class ImageCell extends JPanel {
	static String imagePrefixLocation = "C:/aaa/kindle/mnemonics/OEBPS/Images/sm";

	public static void main(String arg[] )
	{
		final JFrame jf = new JFrame();
		jf.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we)
			{
				System.exit(0);
			}
		});
		//ImageCell ic = new ImageCell();
		//ic.buildImgCell (new DomWord("07","CL","CINDI LAUPER",3,1,true,"fileName"));
		//jf.getContentPane().add(ImageCell.getnewJSP("07"),BorderLayout.CENTER);
		//JPanel ijp = new JPanel();
		//ijp.setBorder(new TitledBorder("Image JSP Panel"));
		//ijp.setSize(300,400);
		//DefaultTableModel m = getModel();
		
		
		//DefaultTableModel dfm = new DefaultTableModel();
		
		DomWord2 dw1 = new DomWord2("07","CL","COOL",0,0,false,null);
		DomWord2 dw2 = new DomWord2("07","CL","CINDI LAUPER",0,0,false,null);
		ArrayList<DomWord2>dws = new ArrayList<DomWord2>();
		dws.add(0,dw1);
		dws.add(1,dw2);
		final JPanel imageCellPn = new JPanel(new GridBagLayout());
		imageCellPn.setPreferredSize(new Dimension(100, 100));
		JPanel icpwPN = new JPanel();
		
		JPanel jpLeft = new JPanel();//new GridBagLayout());
		JPanel jpCtr  = new JPanel();
		JButton jb = new JButton("load");
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DomWord2 dw1 = new DomWord2("96","PG","PIG",0,0,false,null);
				ArrayList<DomWord2>v = new ArrayList<DomWord2>();
				v.add(0,dw1);
				imageCellPn.removeAll();
				ImageCell.addImgCells(v, imageCellPn,Color.ORANGE,Color.PINK);
				jf.validate();
				
			}
		});
		jpLeft.add(jb);
		
		JButton jbStart = new JButton("START");
		JButton jb2  = new JButton("STOP");
		JButton jb3 = new JButton("3");
		
		// get imagePanel
		jpCtr.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2,2,2,2);
		
		ImageCell.addImgCells(dws, imageCellPn,Color.ORANGE,Color.PINK);

		imageCellPn.setBorder(new TitledBorder("Image Hints"));
		imageCellPn.setPreferredSize(new Dimension(600,600));
		JScrollPane jsp = new JScrollPane(imageCellPn);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		//icpwPN.add(jsp);
		
		gbc.gridx=0;gbc.gridy=0;
		gbc.gridheight=3;
		gbc.fill = gbc.VERTICAL;
		jpCtr.add(jsp, gbc);
		gbc.fill=gbc.NONE;
		gbc.gridheight=1;
		
		gbc.gridx++;gbc.gridy=0;
		jpCtr.add(jbStart,gbc);
		
		gbc.gridy++;
		jpCtr.add(jb2,gbc);
		
		gbc.gridy++;
		jpCtr.add(jb3,gbc);
		
		jf.getContentPane().add( jpCtr,BorderLayout.CENTER);
		jf.getContentPane().add(jpLeft,BorderLayout.WEST);
		jf.setSize(500,500);
		jf.setVisible(true);
		
	}
	public ImageCell()
	{
		setLayout(new GridBagLayout());
	}
	public static void addImgCells(List<DomWord2> dws, JPanel jp, Color bk, Color starsColor)
	{
		jp.removeAll();
		 GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2,2,2,2);
		gbc.gridx=0;
		gbc.gridy=0;
		int c = 1;
		int y = 0;
		for( DomWord2 dw:dws )
		{
			
			JLabel     numLetterValsJL = new JLabel(dw.numVal + " (" + dw.letVal + ")");numLetterValsJL.setForeground(Color.WHITE);
			JLabel     wordValJL = new JLabel(dw.word);wordValJL.setForeground(Color.WHITE);

			JCheckBox  jbM = new JCheckBox(""); jbM.setHorizontalTextPosition(SwingConstants.LEFT);
			jbM.setSelected(dw.isMaster);jbM.setBackground(bk);
			jbM.setEnabled(false);

			//
			// W O R D   T E X T   F I E L D s
			//
			JPanel jsumPan = new JPanel();
			gbc.gridx=0;gbc.gridy=y+1;
			gbc.gridwidth=2;
			gbc.fill=gbc.HORIZONTAL;
			gbc.anchor=gbc.EAST;
			jsumPan.add(numLetterValsJL);
			//gbc.gridx++;
			jsumPan.add(wordValJL);
			jp.add(jsumPan,gbc);
			
			 			
			//
			//  I S   M A S T E R   C H E C K   B O X
			//
			JPanel mjp = new JPanel();
			gbc.anchor=gbc.EAST;
			gbc.gridx=0; gbc.gridy=y+2;
			gbc.gridwidth=1;
			gbc.fill=gbc.NONE;
			JLabel m = new JLabel("Is Master Word");m.setForeground(Color.white);
			mjp.add(m);
			//gbc.gridx++;
			gbc.anchor=gbc.EAST;
			gbc.gridwidth=2;gbc.fill=gbc.HORIZONTAL;
			mjp.add(jbM);
			jp.add(mjp,gbc);
			
			
			//
			//  I M A G E   B O X 
			//
			gbc.anchor=gbc.CENTER;
			gbc.gridwidth=2;gbc.gridheight=4;
			gbc.fill=gbc.VERTICAL;
			gbc.gridx=2;gbc.gridy=y+1;
			
			JLabel jli = new JLabel("");
			jli.setPreferredSize(new Dimension(100,100));
			jli.setSize(jli.getPreferredSize());
			Utils.setImage(imagePrefixLocation, jli, dw.numVal, dw.word);
			//jpI.add(new JLabel("this is this here"));
			jp.add(jli,gbc);
			gbc.gridheight=1;
			gbc.fill = gbc.NONE;
			
			
			//
			//  S P A C E R 
			//
			 if( c < dws.size() )
			{
				gbc.fill=gbc.NONE;
				gbc.gridy=y+5;
				gbc.gridx=2;
				gbc.gridwidth=4;gbc.fill=gbc.HORIZONTAL;
				jp.add(new JLabel("-------------------------"),gbc);
				gbc.gridy++;
				jp.add(new JLabel("   "),gbc);
				gbc.gridy++;
				jp.add(new JLabel("   "),gbc);
				gbc.gridwidth=1;gbc.fill=gbc.NONE;
				c++;
			}
			 
			y=gbc.gridy++;
			gbc.gridx=0;
			
		}
		//setBorder(new TitledBorder("Words as Images"));
		//this.setSize(200,200);this.setPreferredSize(this.getSize());
		//return jList;
		//return gbc;
	}
	
	public static JScrollPane getJSP(JPanel jp)
	{
		//DefaultListModel model = new DefaultListModel();
		//JList jl = new JList(model);
		//jl.setVisibleRowCount(4);
		JScrollPane jsp = new JScrollPane(jp);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return jsp;
	}

}

