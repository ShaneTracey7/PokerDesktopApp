import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;



import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


/*
 Ideas:
- create a odds of winning given the community cards n have an option to display that


// I SHOULDN'T BE MIXING AWT AND SWING COMPONENTS ////



||||||||||| Areas to work on ||||||||||||

says 'Too much' when betting when I had enough to bet 
sometimes after a 'Too much' warning, the next bet will not register



Algorithm
-leadhand isnt accurate when player is deleted
if player1 is deleted dont change maybe
-make side pots for people who go all in
- with good hand dont bet high first round
- big blind small blind
- i lost head to head when both players had a flush (should've been split pot)

Swing
-have all console outputs to swing
-fix issue with outputing cards
- show hands of players that go to the last round like when u have to compare cards

 
To Do's:
- change player's cash after each bet
- make aiplayer stop raising when all other players are either all in or folded (raise cap per hand)
- Make Window that pops up when Game ends ex. when human player is out of cash

create another AI for betting
create converter from float value to words for what hand someone has i.e. 229.12 into pair of jacks
display that after each hand.

weird stuff happening in raisecount, cards are being shown after a player raises. Might be checking, then betting.
 */



public class Game implements ActionListener,Styles1 

{
	public static Thread send;
	public static int  decider; // for call/bet/fold etc jbuttons
	public static int bchoice;
	public static int numplayers; // number of players
	public static String player_name; // the name of the human player
	public static int win_condition; // the win condition of the game
	
	public static JFrame frame; // main frame for application
	
	//Jmenubar attributues
	public static JFrame wf; // for hand winner window
	public static JTextArea textarea; // for hand winner window
	public static JFrame hf; //for previous hand results window
	public static JTextArea textarea2; //for previous hand results window
	public static JLabel htitle; //for previous hand results window
	public static JFrame rf; //for hand rankings window
	
	//Final Winner window(end of game) attributes
	public static JFrame fwf;
	
	
	//Display features
	public static JLabel wclabel;  //win condition label
	public static JLabel potlabel; // actual pot value
	public static JLabel potlabel2; // says "Pot"
	
	
	//community cards
	public static JLabel c1;
	public static JLabel c2;
	public static JLabel c3;
	public static JLabel c4;
	public static JLabel c5;
	public static JLabel c1label;
	public static JLabel c2label;
	public static JLabel c3label;
	public static JLabel c4label;
	public static JLabel c5label;
	
	//pocket cards
	public static JLabel h1;
	public static JLabel h2;
	public static JLabel h1label;
	public static JLabel h2label;
	
	//AIplayer names & cash labels
	public static JLabel p1name;
	public static JLabel p2name;
	public static JLabel p3name;
	public static JLabel p4name;
	public static JLabel p1cash;
	public static JLabel p2cash;
	public static JLabel p3cash;
	public static JLabel p4cash;
	public static JLabel hplayerlabel;
	public static JLabel hcash;
	//player bet displays
	public static JLabel hbet; //human players bet display
	public static JLabel bet1;
	public static JLabel bet2;
	public static JLabel bet3;
	public static JLabel bet4;
	
	// to display who's turn it is
	public static JLabel p1dot;
	public static JLabel p2dot;
	public static JLabel p3dot;
	public static JLabel p4dot;
	public static JLabel hpdot;
	// to display who folded
	public static JLabel p1x;
	public static JLabel p2x;
	public static JLabel p3x;
	public static JLabel p4x;
	
	//Buttons
	public static JButton call;
	public static JButton check;
	public static JButton fold;
	public static JButton allIn;
	
	public static JButton raise;
	public static JButton bet;
	public static JTextField betRaiseTA;
	public static JButton placeBR;
	public static JLabel brtext;
	public static int maxBR; // to connect maxRaise from betting algorithm to actionlister
	public static int enteredBet; // to send back to betting algorithm from actionlistener
	
	
	
	
	
	
	public static ArrayList <Player> players = new ArrayList <Player> ();
	public static ArrayList <Player> foldedplayers = new ArrayList <Player> (); //for folding
	public static ArrayList <Player> aiplayers = new ArrayList <Player> ();   //ai
	public static ArrayList <Player> leadtracker = new ArrayList <Player> (); //for dealing
	public static ArrayList <Player> hplayers = new ArrayList <Player> ();	// human players
	public static ArrayList <Player> tbplayers = new ArrayList <Player> (); //for tie breaks
	public static ArrayList <Player> playersp = new ArrayList <Player> (); //kepts correct order of players

	private int pot;

	public Game()
	{
		pot = 0;
	}

	public Game(int pot)
	{
		this();
		setPot(pot);
	}

	public int GetPot()
	{
		return pot;
	}

	public boolean isValidPot(int pot)
	{
		return(pot >= 0);
	}

	public int setPot(int pot)
	{
		if(isValidPot(pot))
			this.pot = pot;
		return this.pot;
		
	}
	

	public static void main(String [] args)
	{
		send = Thread.currentThread();
		bchoice = 0;
		Game game = new Game(); // for algorithm
		frame = new JFrame("Poker");
		frame.setVisible(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 640);
		frame.setLocationRelativeTo(null); // puts frame in center of screen upon opening
		frame.setResizable(false); 
		
		JLayeredPane lpane = new JLayeredPane();

		URL url1 = Game.class.getResource("/poker_bg.png");
		ImagePanel panel = new ImagePanel(new ImageIcon(url1).getImage());
		//ImagePanel panel = new ImagePanel(new ImageIcon("poker_bg.png").getImage());
		
		panel.setLayout(null);  //absolute positioning
		Dimension dim = new Dimension(900,640);
		panel.setPreferredSize(dim);
		frame.add(panel);
		
		JMenuBar menubar = new JMenuBar();
		JMenu menuh = new JMenu("Help");
		menubar.add(menuh);
		
		
		JMenu menus = new JMenu("Stats");
		menubar.add(menus);
		
		JMenuItem itemh1 = new JMenuItem("Hand Rankings");
		JMenuItem items1 = new JMenuItem("Previous Hand Winners");
		JMenuItem items2 = new JMenuItem("Your Previous Hand Results");
		menuh.add(itemh1);
		menus.add(items1);
		menus.add(items2);
		
		
		itemh1.addActionListener(game);
		items1.addActionListener(game);
		items2.addActionListener(game);
		
		
		frame.setJMenuBar(menubar);
		
	// start up tasks
	Startup.gameSet(frame);

	player_name = Startup.pName;
	while (player_name == null)
	{
		player_name = Startup.pName;
	}
	
	String win_con = Startup.winCondition;
	while (win_con == null)
	{
		win_con = Startup.winCondition;
	}
	
	switch(win_con)
	{
	case "Most money after 5 hands": win_condition = 1;break;
	case "First to $1000": 			 win_condition = 2;break;
	case "Last man standing":		 win_condition = 3;break;
	}
	
	
	numplayers = Startup.playerNum;
	while (numplayers == 0)
	{
		numplayers = Startup.playerNum;
	}
	
	makeRuleWindow();
	makeWinnerWindow();
	makeHumanWindow();
	htitle.setText(player_name + "'s Hand Results");
	
	
	
	//sets AIlabels dependent upon number of players
	
	//display features
	wclabel = new JLabel(win_con);
	wclabel.setBounds(300,45,300,30);
	wclabel.setFont(font3);
	wclabel.setForeground(Color.white);
	wclabel.setHorizontalAlignment(JTextField.CENTER);
	
	potlabel = new JLabel("$" + game.pot);
	potlabel.setBounds(350,150,200,30);
	potlabel.setFont(font3);
	potlabel.setForeground(Color.white);
	potlabel.setHorizontalAlignment(JTextField.CENTER);
	
	potlabel2 = new JLabel("Pot");
	potlabel2.setBounds(400,125,100,30);
	potlabel2.setFont(font3);
	potlabel2.setForeground(Color.white);
	potlabel2.setHorizontalAlignment(JTextField.CENTER);
	
	//name and cash labels
	hplayerlabel = new JLabel(player_name);
	hplayerlabel.setBounds(350,400,200,30);
	hplayerlabel.setFont(font3);
	hplayerlabel.setBorder(border);
	hplayerlabel.setHorizontalAlignment(JTextField.CENTER);
	
	hcash = new JLabel();
	hcash.setBounds(390,360,100,30);
	hcash.setHorizontalAlignment(JTextField.CENTER);
	hcash.setFont(font3);
	hcash.setVisible(false);
	
	URL url2 = Game.class.getResource("/tIcon.png");
	ImageIcon ti = new ImageIcon(url2);
	//ImageIcon ti = new ImageIcon("pics/tIcon.png");
	hpdot = new JLabel(ti);
	hpdot.setBounds(415,335,50,30);
	hpdot.setVisible(false);
	
	p1name = new JLabel("SpiderMan");
	p1name.setBounds(25,75,150,35);
	p1name.setBorder(border3);
	p1name.setFont(font3);
	p1name.setHorizontalAlignment(JTextField.CENTER);
	p1name.setVisible(false);
	
	p1cash = new JLabel();
	p1cash.setBounds(40,35,100,30);
	p1cash.setHorizontalAlignment(JTextField.CENTER);
	p1cash.setFont(font3);
	p1cash.setVisible(false);
	
	p1dot = new JLabel(ti);
	p1dot.setBounds(65,15,50,30);
	p1dot.setVisible(false);
	
	URL url3 = Game.class.getResource("/x.png");
	ImageIcon x = new ImageIcon(url3);
	//ImageIcon x = new ImageIcon("pics/x.png");
	p1x = new JLabel(x);
	p1x.setBounds(65,110,50,40);
	p1x.setVisible(false);
	
	p2name = new JLabel("Hulk");
	p2name.setBounds(730,75,150,35);
	p2name.setBorder(border3);
	p2name.setFont(font3);
	p2name.setHorizontalAlignment(JTextField.CENTER);
	p2name.setVisible(false);
	
	p2cash = new JLabel();
	p2cash.setBounds(745,35,100,30);
	p2cash.setHorizontalAlignment(JTextField.CENTER);
	p2cash.setFont(font3);
	p2cash.setVisible(false);
	
	p2dot = new JLabel(ti);
	p2dot.setBounds(770,15,50,30);
	p2dot.setVisible(false);
	
	p2x = new JLabel(x);
	p2x.setBounds(780,110,50,40);
	p2x.setVisible(false);
	
	p3name = new JLabel("Thor");
	p3name.setBounds(0,380,150,35);
	p3name.setBorder(border3);
	p3name.setFont(font3);
	p3name.setHorizontalAlignment(JTextField.CENTER);
	p3name.setVisible(false);
	
	p3cash = new JLabel();
	p3cash.setBounds(15,340,100,30);
	p3cash.setHorizontalAlignment(JTextField.CENTER);
	p3cash.setFont(font3);
	p3cash.setVisible(false);
	
	p3dot = new JLabel(ti);
	p3dot.setBounds(40,310,50,30);
	p3dot.setVisible(false);
	
	p3x = new JLabel(x);
	p3x.setBounds(40,430,50,40);
	p3x.setVisible(false);

	p4name = new JLabel("IronMan");
	p4name.setBounds(750,380,150,35);
	p4name.setBorder(border3);
	p4name.setFont(font3);
	p4name.setHorizontalAlignment(JTextField.CENTER);
	p4name.setVisible(false);
	
	p4cash = new JLabel();
	p4cash.setBounds(770,340,100,30);
	p4cash.setHorizontalAlignment(JTextField.CENTER);
	p4cash.setFont(font3);
	p4cash.setVisible(false);
	
	p4dot = new JLabel(ti);
	p4dot.setBounds(795,310,50,30);
	p4dot.setVisible(false);
	
	p4x = new JLabel(x);
	p4x.setBounds(805,430,50,40);
	p4x.setVisible(false);
	
	//community cards
	c1 = new JLabel();
	c1.setLocation(170,200);
	c1.setVisible(false);
	c1label = new JLabel();
	c1label.setBounds(185,205,40,40);
	c1label.setFont(font3);
	c1label.setVisible(false);
	
	c2 = new JLabel();
	c2.setLocation(287,200);
	c2.setVisible(false);
	c2label = new JLabel();
	c2label.setBounds(310,205,40,40);
	c2label.setFont(font3);
	c2label.setVisible(false);
	
	c3 = new JLabel();
	c3.setLocation(405,200);
	c3.setVisible(false);
	c3label = new JLabel();
	c3label.setBounds(420,205,40,40);
	c3label.setFont(font3);
	c3label.setVisible(false);
	
	c4 = new JLabel();
	c4.setLocation(520,200);
	c4.setVisible(false);
	c4label = new JLabel();
	c4label.setBounds(540,205,40,40);
	c4label.setFont(font3);
	c4label.setVisible(false);
	
	c5 = new JLabel();
	c5.setLocation(637,200);
	c5.setVisible(false);
	c5label = new JLabel();
	c5label.setBounds(660,205,40,40);
	c5label.setFont(font3);
	c5label.setVisible(false);
	
	//pocket cards
	h1 = new JLabel();
	h1.setLocation(348,425);
	h1label = new JLabel();
	h1label.setBounds(365,430,40,40);
	h1label.setFont(font3);
	
	h2 = new JLabel();
	h2.setLocation(455,425);
	h2label = new JLabel();
	h2label.setBounds(470,430,40,40);
	h2label.setFont(font3);
	
	
	check = new JButton("Check");
	check.setBounds(600,395,90,50);
	check.setFont(font3);
	check.setBackground(Color.red);
	check.setOpaque(true);
	//check.setForeground(Color.red);
	check.addActionListener(game);
	check.setVisible(false);
	
	call = new JButton("Call");
	call.setBounds(600,395,90,50);
	call.setFont(font3);
	call.setBackground(Color.red);
	call.setOpaque(true);
	call.addActionListener(game);
	call.setVisible(false);
	
	bet = new JButton("Bet");
	bet.setBounds(600,450,90,50);
	bet.setFont(font3);
	bet.setBackground(Color.red);
	bet.setOpaque(true);
	bet.addActionListener(game);
	bet.setVisible(false);
	
	raise = new JButton("Raise");
	raise.setBounds(600,450,90,50);
	raise.setFont(font3);
	raise.setBackground(Color.red);
	raise.setOpaque(true);
	raise.addActionListener(game);
	raise.setVisible(false);
	
	allIn = new JButton("All in");
	allIn.setBounds(600,450,90,50);
	allIn.setFont(font3);
	allIn.setBackground(Color.red);
	allIn.setOpaque(true);
	allIn.addActionListener(game);
	allIn.setVisible(false);
	
	fold = new JButton("Fold");
	fold.setBounds(600,505,90,50);
	fold.setFont(font3);
	fold.setBackground(Color.red);
	fold.setOpaque(true);
	fold.addActionListener(game);
	fold.setVisible(false);
	
	brtext = new JLabel("");
	brtext.setBounds(600,420,150,30);
	brtext.setFont(font3);
	brtext.setHorizontalAlignment(JTextField.CENTER);
	brtext.setVisible(false);
	
	betRaiseTA = new JTextField("");
	betRaiseTA.setBounds(600,460,150,50);
	betRaiseTA.setForeground(Color.DARK_GRAY);
	betRaiseTA.setFont(font3);
	betRaiseTA.setHorizontalAlignment(JTextField.CENTER);
	betRaiseTA.setVisible(false);
	
	placeBR = new JButton("Place");
	placeBR.setBounds(640,520,70,35);
	placeBR.setFont(font3);
	placeBR.setBackground(Color.red);
	placeBR.setOpaque(true);
	placeBR.addActionListener(game);
	placeBR.setVisible(false);
	
	hbet = new JLabel("Bet: $");
	hbet.setBounds(350,560,200,30);
	hbet.setFont(font3);
	hbet.setHorizontalAlignment(JTextField.CENTER);
	hbet.setVisible(true);
	
	bet1 = new JLabel("Bet: $");
	bet1.setBounds(25,110,150,35);
	bet1.setFont(font3);
	bet1.setHorizontalAlignment(JTextField.CENTER);
	bet1.setVisible(false);
	
	bet2 = new JLabel("Bet: $");
	bet2.setBounds(730,110,150,35);
	bet2.setFont(font3);
	bet2.setHorizontalAlignment(JTextField.CENTER);
	bet2.setVisible(false);
	
	bet3 = new JLabel("Bet: $");
	bet3.setBounds(0,415,150,35);
	bet3.setFont(font3);
	bet3.setHorizontalAlignment(JTextField.CENTER);
	bet3.setVisible(false);
	
	bet4 = new JLabel("Bet: $");
	bet4.setBounds(750,415,150,35);
	bet4.setFont(font3);
	bet4.setHorizontalAlignment(JTextField.CENTER);
	bet4.setVisible(false);
	
	
	//components added to JPanel
	panel.add(wclabel);
	panel.add(potlabel);
	panel.add(potlabel2);
	panel.add(p1name);
	panel.add(p2name);
	panel.add(p3name);
	panel.add(p4name);
	panel.add(hplayerlabel);
	panel.add(p1cash);
	panel.add(p2cash);
	panel.add(p3cash);
	panel.add(p4cash);
	panel.add(hcash);
	//
	panel.add(hbet);
	panel.add(bet1);
	panel.add(bet2);
	panel.add(bet3);
	panel.add(bet4);
	//
	panel.add(p1dot);
	panel.add(p2dot);
	panel.add(p3dot);
	panel.add(p4dot);
	panel.add(hpdot);
	// 
	panel.add(p1x);
	panel.add(p2x);
	panel.add(p3x);
	panel.add(p4x);
	//
	panel.add(bet);
	panel.add(check);
	panel.add(fold);
	panel.add(allIn);
	panel.add(raise);
	panel.add(call);
	//
	panel.add(brtext);
	panel.add(placeBR);
	panel.add(betRaiseTA);


	//components being added to JLayeredPane (just cards)
	lpane.add(panel,JLayeredPane.DEFAULT_LAYER);
	lpane.add(c1label,JLayeredPane.PALETTE_LAYER);
	lpane.add(c2label,JLayeredPane.PALETTE_LAYER);
	lpane.add(c3label,JLayeredPane.PALETTE_LAYER);
	lpane.add(c4label,JLayeredPane.PALETTE_LAYER);
	lpane.add(c5label,JLayeredPane.PALETTE_LAYER);
	lpane.add(c1, JLayeredPane.PALETTE_LAYER);   // cards always have to be added last cuz components are added to the bottom of layer
	lpane.add(c2, JLayeredPane.PALETTE_LAYER);
	lpane.add(c3, JLayeredPane.PALETTE_LAYER);
	lpane.add(c4, JLayeredPane.PALETTE_LAYER);
	lpane.add(c5, JLayeredPane.PALETTE_LAYER);
	lpane.add(h1label,JLayeredPane.PALETTE_LAYER);
	lpane.add(h2label,JLayeredPane.PALETTE_LAYER);
	lpane.add(h1, JLayeredPane.PALETTE_LAYER);
	lpane.add(h2, JLayeredPane.PALETTE_LAYER);
	
	
	//for showing correct amount of players
	if(numplayers >=2)
	{
		p1name.setVisible(true);
		bet1.setVisible(true);
		if(numplayers >= 3)
		{
			p2name.setVisible(true);
			bet2.setVisible(true);
			if(numplayers >= 4)
			{
				p3name.setVisible(true);
				bet3.setVisible(true);
				if(numplayers == 5)
				{
					p4name.setVisible(true);
					bet4.setVisible(true);
				}
			}
		}
	}	
	
	System.out.println(win_condition);
	System.out.println(player_name);
	System.out.println(numplayers);
	
	// end of start up tasks
	lpane.setVisible(true);
	panel.setVisible(true);
	frame.add(lpane);
	frame.setVisible(true);
	
	
	ArrayList <Cards> deck = new ArrayList <Cards> ();

	int handcount = 4; //number of hands to play up to +1 so handcount = 4 that means 5 hands
	int deckSize = 52;
	int cash = 500;

	System.out.println("\n");

	Cards.stdDeck(deck, deckSize);
	System.out.println("\n");
	Cards.shuffle(deck);
	Cards.Display(deck);
	int cardCount = 0;  


	System.out.println("Lets begin\n");

	int startCash = 0;
	int endCash = 0;
	String oldtext2;


	// (1) texas holdem and (2) AI

		
		Cards.Display(deck);
		Cards [] hc = new Cards [2];
		 
		//creates all AI players
		for (int count = 0; count < (numplayers - 1); count++)
		{
		hc = AIplayer.getPCardsdifR(deck);
		Player p1= AIplayer.make_a_playerdif(cash,hc,count);
		players.add(p1);
		aiplayers.add(p1);
		leadtracker.add(p1);
		playersp.add(p1);
		}
		//Creates Human player
		hc = AIplayer.getPCardsdifR(deck);
		Player p = Player.make_a_playerdif(cash,hc,player_name); 
		players.add(p);
		leadtracker.add(p);
		hplayers.add(p);
		playersp.add(p);
		
		//Game.showPocketCardsS(p.GetCards(), h1, h2);
		
		int winnerWindowtracker = 0;
		int hcount = 0;
		while(true) //infinte loop
		//for(int hcount = 0; hcount < handcount; hcount++ ) //second time around ai betting is off
		{
		if(hcount != 0)
		{
		int varcount = players.size() * 2;
		int cardsper = varcount + 5;
		if(deck.size() < cardsper) // if not enough cards for another hand  get new deck
			{
			deck.clear(); //clears deck
			Cards.stdDeck(deck, deckSize);
			System.out.println("\n");
			Cards.shuffle(deck);
			}
		for(int scount = 0; scount < players.size(); scount++)
			{
			cardCount = Player.getPCards(deck,cardCount,players.get(scount).hand);  ///have to getpcards differently so i can set human player cards
			}
		}

		/////
		
		Player.Display(players);
		Player.Display(aiplayers);
		Game.showCash(playersp); // testing /////////////////////////////////////////////////
		Game.showPocketCardsS(playersp.get((playersp.size() - 1)).GetCards(), h1, h2);
		Game.showPocketCards(playersp.get((playersp.size() - 1)).GetCards(), h1label, h2label);
		
		Cards.Display(deck);
		
		//just a tester spot for hbet (human player's bet Jlabel)
		hbet.setText("Bet: $");
		bet1.setText("Bet: $");
		bet2.setText("Bet: $");
		bet3.setText("Bet: $");
		bet4.setText("Bet: $");
		
		Cards [] community = new Cards [5];
		Cards [] c =Cards.getCommunityCards(deck,cardCount,community);
		
		showComCards(c, c1label,c2label,c3label,c4label,c5label);
		showComCardsS(c,c1,c2,c3,c4,c5);
		
		//checks all hands for 'best 5 cards' if has flush or  pair etc and sets status
		Checks.fullCheck(players, community);
		
		
		startCash = playersp.get((playersp.size()-1)).GetCash();
		
		
		//all 4 betting rounds
		Player.bettingAI(players,foldedplayers,aiplayers,game,c); //gets wonky 2nd time around
		
			
		Player.Display(players);
		System.out.println("The pot is $" + game.GetPot());


		Cards.showCommunityCards(c, 4);
		
		//sort by status
		Collections.sort(players);

		
		//handles tiebreaks 
		System.out.println("player size " + players.size());
		
		tbplayers.add(players.get(0));
		if(players.size() > 1)
		{
		for(int tbcount = 1; tbcount < players.size(); tbcount++)
		{	
			if(tbcount == players.size() -1 )
			{
				if(players.get(0).GetStatus() == players.get(tbcount).GetStatus() )
				{
					tbplayers.add(players.get(tbcount));
				}
			}
			else
			{
				if(players.get(0).GetStatus() == players.get(tbcount).GetStatus())
				{
					tbplayers.add(players.get(tbcount));
				}
				else
				{
					break;
				}
			}
		}
		
		}
		System.out.println("tb size " + tbplayers.size());
		//try making a pop up window here with winning hands 
		//will crash if arraylist (tbplayers) contains more than 1 Player.
		//makeHandEndWinnerWindow(Player.getWinner(tbplayers), game); // calls winner pop up window
		
		//method for side pots
		/*
		int biggestbet = 0;
		if(tbplayers.size() > 1)
		{
			if(tbplayers.get(0).GetBet() < tbplayers.get(1).GetBet())
			{
				biggestbet = tbplayers.get(1).GetBet();
				tbplayers.get(0).setCash(tbplayers.get(0).GetBet() *2); //went all in
				Game.setPot(Game.GetPot() + tbplayers.get(0).GetBet() *2); //subtracting side pot from pot
			}
			else if(tbplayers.get(0).GetBet() > tbplayers.get(1).GetBet())// 0 equal or greater
			{
				biggestbet = tbplayers.get(0).GetBet();
				tbplayers.get(1).setCash(tbplayers.get(1).GetBet() *2); //went all in
				Game.setPot(Game.GetPot() + tbplayers.get(1).GetBet() *2); //subtracting side pot from pot
			}
			else
			{
				biggestbet = tbplayers.get(1).GetBet();// equal so do nothing 
			}
			
			
			for(int spcount = 2; spcount < tbplayers.size(); spcount++)
			{
				
				if(tbplayers.get(spcount).GetBet() < biggestbet )
				{
					biggestbet = tbplayers.get(spcount).GetBet();
					tbplayers.get(spcount).setCash(tbplayers.get(spcount).GetBet() *2); //went all in
					Game.setPot(Game.GetPot() + tbplayers.get(spcount).GetBet() *2); //subtracting side pot from pot
				}
				else if(tbplayers.get(spcount).GetBet() > biggestbet )
				{
					biggestbet = tbplayers.get(spcount).GetBet();
					tbplayers.get(spcount).setCash(tbplayers.get(spcount).GetBet() *2); //went all in
				}
				else
				{
					//do nothing since they equal the same
				}
				
			}
		}
		*/
		//maybe scratch this code n first get the biggest bet then once found compare all bets to biggest 
		//bet and deal with side pots from there
		ArrayList <Integer> ints = new ArrayList <Integer> ();
		for(Player p1: tbplayers)
		{
		ints.add((p1.GetCash() + p1.GetBet())); //should get total cash b4 hand started
		}
		
		
		

		int pot_division = game.GetPot()/tbplayers.size();  //size is at least 1
		System.out.println("tb portion " + pot_division);
		int cnt = 0;
		String oldtext;
			for(Player p1: tbplayers)
				{
				p1.setCash(p1.GetCash() + pot_division); //should set cash correctly
				//ints.set(cnt,p1.GetCash() - ints.indexOf(cnt));
				ints.set(cnt, (pot_division - p1.GetBet()));
				
				oldtext = textarea.getText();
				
				if(oldtext.length() == 0) // very first hand
				{
					textarea.append((hcount+1) + " " + p1.GetName()+ " +" + (pot_division - p1.GetBet()));
					textarea.setSelectionStart(1);
					textarea.setSelectionEnd(5);
					textarea.setSelectedTextColor(Color.green);
					
				}
				else if (winnerWindowtracker % 3 == 0)
				{
					if(cnt > 0)
					{
						textarea.setText(oldtext + "\n" + p1.GetName()+ " +" + (pot_division - p1.GetBet()));
					}
					else
					{
						textarea.setText(oldtext + "\n" + (hcount+1) + " " + p1.GetName()+ " +" + (pot_division - p1.GetBet()));
					}
					
				}
				else
				{
					if(cnt > 0)
					{
						textarea.setText(oldtext + "   " + p1.GetName()+ " +" + (pot_division - p1.GetBet())); 
					}
					
					else
					{
						textarea.setText(oldtext + "   " + (hcount+1) + " " +  p1.GetName()+ " +" + (pot_division - p1.GetBet()));
						
					}
				}
				winnerWindowtracker++;
				cnt++;
				}
				// need to find a way to format so each column is lined up correctly
			//also find a way to delete hand winners from top once winnerWindoetracker reaches a certain amount
				
			
		
		/*
		///winning player
		int winner = 0;
		int money = players.get(winner).GetCash();
		int money_gained = money + Game.GetPot();
		players.get(winner).setCash(money_gained);
		//highest status gets pot
		 */
			endCash = playersp.get((playersp.size()-1)).GetCash();
			oldtext2 = textarea2.getText();
			if(oldtext2.length() == 0)
			{
				if(endCash- startCash < 0)
				{
					textarea2.setText((hcount+1) + "  -$" + (Math.abs(endCash- startCash)) + "\n");	
				}
				else
				{
					textarea2.setText((hcount+1) + "  +$" + (Math.abs(endCash- startCash)) + "\n");
				}
					
			}
			else
			{
				if(endCash- startCash < 0)
				{
					textarea2.setText(oldtext2 + (hcount+1) +"  -$" + (Math.abs(endCash- startCash)) + "\n");
				}
				else
				{
					textarea2.setText(oldtext2 + (hcount+1) +"  +$" + (Math.abs(endCash- startCash)) + "\n");
				}
			
			}
		makeHandEndWinnerWindow(Player.getWinner(tbplayers), game); // calls winner pop up window
		Player.Display(players);
		
		players.addAll(foldedplayers); // adds all players removed from og arrlist cuz they folded
		foldedplayers.clear();   // clears folded player arraylist each rounc
		tbplayers.clear();
		
		p1x.setVisible(false);
		p2x.setVisible(false);
		p3x.setVisible(false);
		p4x.setVisible(false);
		
		
		Player.refreshBS(players); //clear bets and status
		Player.Display(players);

		Player.hasCash(players,leadtracker);  // if a player runs out of cash they will be booted

		//Player.leadhandset(players,leadtracker,hcount); //goes one to mant times on second time around

		//Player.Display(leadtracker);
		
		game.setPot(0);            //setting pot to 0

		cardCount = 0; //reseting 
		
		if(players.contains(hplayers.get(0)) == false)
		{
			System.out.println("Game...... Ova");
			break;
		}
		
		
		
		if(players.size() <= 1) // && wincond == 3)
		{
		break;
		} 
		
		if(hcount == handcount && win_condition == 1)
		{
		break;
		}
		Player pp =Player.getWinner(players); // sets current winner to index 0 
		if(pp.GetCash() >= 1000 && win_condition == 2)
		{
		break;
		}
		
		Player.leadhandset(players,leadtracker,hcount); //goes one to mant times on second time around

		Player.Display(leadtracker);
		hcount++;
		
		Game.clearComCards(c1,c2,c3,c4,c5,c1label,c2label,c3label,c4label,c5label);
		Game.clearPocketCards(h1,h2,h1label,h2label);
		potlabel.setText("$" + game.pot); //clearing pot
		
		}
		System.out.println(Player.getWinner(players) + "\n" +  Player.getWinner(players).GetName() + " is the winner!" ); // win condition has been met and game is over
		/* make pop up window announcing winner
		window can contain option to continue playing or exit.
		*/
		makeFinalWinnerWindow(Player.getWinner(players)); // calls winner pop up window
		
	
		
	}
	
	//swing method
	//input community cards
	
		public static void setComCards(int num, JLabel a)
		{
			if(num == 1 )
				a.setText("A");
			else if(num == 11 )
				a.setText("J");
			else if(num == 12 )
				a.setText("Q");
			else if(num == 13 )
				a.setText("K"); 
			else
				a.setText(Integer.toString(num)); //any non face card
		}
		
		public static void setComCardsS(String suit, JLabel a)
		{
			URL url4 = Game.class.getResource("/heart.png");
			URL url5 = Game.class.getResource("/club.png");
			URL url6 = Game.class.getResource("/diamond.png");
			URL url7 = Game.class.getResource("/spades.png");
			ImageIcon h = new ImageIcon(url4);
			ImageIcon c = new ImageIcon(url5);
			ImageIcon d = new ImageIcon(url6);
			ImageIcon s = new ImageIcon(url7);
			/*
			ImageIcon h = new ImageIcon("pics/heart.png");
			ImageIcon c = new ImageIcon("pics/club.png");
			ImageIcon d = new ImageIcon("pics/diamond.png");
			ImageIcon s = new ImageIcon("pics/spades.png"); //get pic for spades
			*/
			
			if(suit == "Diamond" )
			{
				a.setIcon(d);
				a.setSize(d.getIconWidth(), d.getIconHeight());
			}
			else if(suit == "Club" )
			{
				a.setIcon(c);
				a.setSize(c.getIconWidth(), c.getIconHeight());
			}
				
			else if(suit == "Heart" )
			{
				a.setIcon(h);
				a.setSize(h.getIconWidth(), h.getIconHeight());
			}
				
			else if(suit == "Spade" )
			{
				a.setIcon(s);
				a.setSize(s.getIconWidth(), s.getIconHeight());
			}
				
		}
	
	
		public static void showComCardsS(Cards [] cards, JLabel a,JLabel b,JLabel c,JLabel d,JLabel e)
		{
			Game.setComCardsS(cards[0].GetSuit(), a);
			Game.setComCardsS(cards[1].GetSuit(), b);
			Game.setComCardsS(cards[2].GetSuit(), c);
			Game.setComCardsS(cards[3].GetSuit(), d);
			Game.setComCardsS(cards[4].GetSuit(), e);
		}
		
		
		
		public static void showComCards(Cards [] cards, JLabel a,JLabel b,JLabel c,JLabel d,JLabel e)
		{
			if(cards[0].GetColour().equalsIgnoreCase("Red"))
			{
			a.setForeground(Color.red);
			}
			else
			{
			a.setForeground(Color.black);	
			}
			if(cards[1].GetColour().equalsIgnoreCase("Red"))
			{
				b.setForeground(Color.red);
			}
			else
			{
				b.setForeground(Color.black);	
			}
			
			if(cards[2].GetColour().equalsIgnoreCase("Red"))
			{
			c.setForeground(Color.red);
			}
			else
			{
			c.setForeground(Color.black);	
			}
			if(cards[3].GetColour().equalsIgnoreCase("Red"))
			{
				d.setForeground(Color.red);
			}
			else
			{
				d.setForeground(Color.black);	
			}
			if(cards[4].GetColour().equalsIgnoreCase("Red"))
			{
				e.setForeground(Color.red);
			}
			else
			{
				e.setForeground(Color.black);	
			}
			
			Game.setComCards(cards[0].GetNum(), a);
			Game.setComCards(cards[1].GetNum(), b);
			Game.setComCards(cards[2].GetNum(), c);
			Game.setComCards(cards[3].GetNum(), d);
			Game.setComCards(cards[4].GetNum(), e);
			
		}
		
		public static void showPocketCardsS(Cards [] cards, JLabel a,JLabel b)
		{
			Game.setComCardsS(cards[0].GetSuit(), a);
			Game.setComCardsS(cards[1].GetSuit(), b);
			a.setVisible(true);
			b.setVisible(true);
			
		}
		
		public static void showPocketCards(Cards [] cards, JLabel a,JLabel b)
		{
	
			Game.setComCards(cards[0].GetNum(), a);
			Game.setComCards(cards[1].GetNum(), b);
			
			if(cards[0].GetColour().equalsIgnoreCase("Red"))
			{
			a.setForeground(Color.red);
			}
			else
			{
			a.setForeground(Color.black);	
			}
			if(cards[1].GetColour().equalsIgnoreCase("Red"))
			{
				b.setForeground(Color.red);
			}
			else
			{
				b.setForeground(Color.black);	
			}
			a.setVisible(true);
			b.setVisible(true);
			System.out.println("The card color is" + cards[0].GetColour());
			System.out.println("The card color is" + cards[1].GetColour());
			
		}
		
		public static void clearPocketCards(JLabel a,JLabel b,JLabel a1,JLabel a2)
		{
			a.setVisible(false);
			b.setVisible(false);
			
			a1.setVisible(false);
			a2.setVisible(false);

		}
		
		public static void clearComCards(JLabel a,JLabel b,JLabel c,JLabel d,JLabel e,JLabel a1,JLabel a2,JLabel a3,JLabel a4,JLabel a5)
		{
			a.setVisible(false);
			b.setVisible(false);
			c.setVisible(false);
			d.setVisible(false);
			e.setVisible(false);
			
			a1.setVisible(false);
			a2.setVisible(false);
			a3.setVisible(false);
			a4.setVisible(false);
			a5.setVisible(false);
		}

		public static void showCommunityCardsSwing(int bettingRound,JLabel a,JLabel b,JLabel c,JLabel d,JLabel e,JLabel a1,JLabel a2,JLabel a3,JLabel a4,JLabel a5)
		{
			if(bettingRound >= 2 )
			{
				a.setVisible(true);
				a1.setVisible(true);
				b.setVisible(true);
				a2.setVisible(true);
				c.setVisible(true);
				a3.setVisible(true);
				if(bettingRound >= 3)
				{
					d.setVisible(true);
					a4.setVisible(true);
					if(bettingRound == 4)
					{
						e.setVisible(true);
						a5.setVisible(true);
					}
				}
			}
			else
			{
				//do nothing
			}
			  
		}
		public static void showCash(ArrayList<Player> aiplayers)
		{
			int len = aiplayers.size() -1; //-1  to account for human player 
			if(len >= 1)
			{
			//Game.p1cash.setText(Integer.toString(aiplayers.get(0).GetCash()));
			Game.p1cash.setText(" $" +(aiplayers.get(0).GetCash()));
			p1cash.setVisible(true);
			len--;
			}
			if(len >= 1)
			{
			//Game.p2cash.setText(Integer.toString(aiplayers.get(1).GetCash()));
			Game.p2cash.setText(" $" +(aiplayers.get(1).GetCash()));
			p2cash.setVisible(true);
			len--;
			}
			if(len >= 1)
			{
			//Game.p3cash.setText(Integer.toString(aiplayers.get(2).GetCash()));
			Game.p3cash.setText(" $" +(aiplayers.get(2).GetCash()));
			p3cash.setVisible(true);
			len--;
			}
			if(len >= 1)
			{
			//Game.p4cash.setText(Integer.toString(aiplayers.get(3).GetCash()));
			Game.p4cash.setText(" $" +(aiplayers.get(3).GetCash()));
			p4cash.setVisible(true);
			len--;
			}	
			//Game.hcash.setText(Integer.toString(aiplayers.get((aiplayers.size()-1)).GetCash()));
			Game.hcash.setText(" $" +(aiplayers.get((aiplayers.size()-1)).GetCash()));
			hcash.setVisible(true);
			
		}
		
		public static void clearButtons()
		{
			call.setVisible(false);
			check.setVisible(false);
			bet.setVisible(false);
			allIn.setVisible(false);
			fold.setVisible(false);
			raise.setVisible(false);
			bchoice = 0;
		}
		
		public static void makeRuleWindow()
		{
			rf = new JFrame("Hand Order");
			rf.setSize(450,600);
			rf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			rf.setResizable(false);
			
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			double spot = d.getWidth() - 450;
			
			rf.setLocation((int) spot, 0);
			
			JLayeredPane pane = new JLayeredPane();
			
			
			JPanel p = new JPanel();
			p.setLayout(null);
			p.setSize(450,600);
			p.setBackground(Color.DARK_GRAY);
			p.setVisible(true);
			rf.add(p);
			
			URL url8 = Game.class.getResource("/hand_rankings.png");
			ImageIcon handr = new ImageIcon(url8);
			//ImageIcon handr = new ImageIcon("pics/hand_rankings.png");
			JLabel hr = new JLabel(handr);
			hr.setBounds(25,100,400,450);
			p.add(hr);
			
			URL url9 = Game.class.getResource("/hrg.png");
			ImageIcon text = new ImageIcon(url9);
			//ImageIcon text = new ImageIcon("pics/hrg.png");
			JLabel t = new JLabel(text);
			t.setBounds(125,-20,200,200);
			pane.add(t,JLayeredPane.DEFAULT_LAYER );
			pane.add(p, JLayeredPane.DEFAULT_LAYER);
			
			rf.add(pane);
			pane.setVisible(true);
			rf.setVisible(false);
		}
		
		public static void makeWinnerWindow()
		{
			wf = new JFrame("Hand Winners");
			wf.setSize(450,600);
			wf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			wf.setResizable(false);
			
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			double spot = d.getWidth() - 450;
			
			wf.setLocation((int) spot, 0);
			
			
			
			
			JPanel p = new JPanel();
			p.setLayout(null);
			p.setSize(450,600);
			p.setBackground(Color.DARK_GRAY);
			p.setVisible(true);
			wf.add(p);
			
			textarea = new JTextArea();
			textarea.setBounds(25,100,400,400);
			textarea.setEditable(false);
			p.add(textarea);
			
			URL url10 = Game.class.getResource("/hrg.png");
			ImageIcon text = new ImageIcon(url10);
			//ImageIcon text = new ImageIcon("pics/hrg.png");
			JLabel t = new JLabel(text);
			t.setBounds(125,-20,200,200);
			//p.add(t);
			
			
			
			wf.setVisible(false);
		}
		
		//Need to customize more.
		// only takes first player of tiebreak players if tbplayers size is more than one
		public static void makeHandEndWinnerWindow(Player player, Game game)
		{
			fwf = new JFrame("Hand result");
			fwf.setVisible(true);
			fwf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //change 
			fwf.setSize(400, 400);
			fwf.setLocationRelativeTo(null); // puts frame in center of screen upon opening
			fwf.setResizable(false);
			
			JPanel p = new JPanel();
			p.setLayout(null);
			p.setSize(400,400);
			p.setBackground(Color.DARK_GRAY);
			p.setVisible(true);
			fwf.add(p);
			
			
			JLabel name = new JLabel(player.GetName());  //name of winner
			name.setBounds(50,50,300,50);
			name.setForeground(Color.yellow);
			name.setFont(font5);
			name.setHorizontalAlignment(JTextField.CENTER);
			
			JLabel Wcash = new JLabel( "Won $" + (game.pot - player.GetBet()));  //how much hand winner won
			Wcash.setBounds(50,150,300,50);
			Wcash.setForeground(Color.white);
			Wcash.setFont(font4);
			Wcash.setHorizontalAlignment(JTextField.CENTER);
			
			JLabel Whand = new JLabel(player.statusWords);  //winners hand
			Whand.setBounds(50,225,300,50);
			Whand.setForeground(Color.white);
			Whand.setFont(font1);
			Whand.setHorizontalAlignment(JTextField.CENTER);
			
			/*
			JLabel intro = new JLabel("The Winner is");  //intro
			intro.setBounds(50,300,300,50);
			intro.setFont(font4);
			intro.setForeground(Color.white);
			intro.setHorizontalAlignment(JTextField.CENTER);
			*/
			
			//p.add(intro);
			p.add(name);
			p.add(Wcash);
			p.add(Whand);
			
		}
		
		
		public static void makeFinalWinnerWindow(Player player)
		{
			fwf = new JFrame("Winner");
			fwf.setVisible(true);
			fwf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //change 
			fwf.setSize(400, 400);
			fwf.setLocationRelativeTo(null); // puts frame in center of screen upon opening
			fwf.setResizable(false);
			
			JPanel p = new JPanel();
			p.setLayout(null);
			p.setSize(400,400);
			p.setBackground(Color.DARK_GRAY);
			p.setVisible(true);
			fwf.add(p);
			
			JLabel intro = new JLabel("The Winner is");  //intro
			intro.setBounds(50,50,300,50);
			intro.setFont(font4);
			intro.setForeground(Color.white);
			intro.setHorizontalAlignment(JTextField.CENTER);
			
			JLabel name = new JLabel(player.GetName());  //name of winner
			name.setBounds(50,150,300,50);
			name.setForeground(Color.yellow);
			name.setFont(font5);
			name.setHorizontalAlignment(JTextField.CENTER);
			
			JLabel Wcash = new JLabel( "$" + player.GetCash());  //winners cash
			Wcash.setBounds(50,225,300,50);
			Wcash.setForeground(Color.white);
			Wcash.setFont(font4);
			Wcash.setHorizontalAlignment(JTextField.CENTER);
			
			JLabel Whand = new JLabel(player.statusWords);  //winners hand
			Whand.setBounds(10,300,380,50);
			Whand.setForeground(Color.white);
			Whand.setFont(font1);
			Whand.setHorizontalAlignment(JTextField.CENTER);
			
			p.add(intro);
			p.add(name);
			p.add(Wcash);
			p.add(Whand);
			
			
		}
		
		
		public static void makeHumanWindow()
		{
			hf = new JFrame("Your Previous Hand Results");
			hf.setSize(450,600);
			hf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			hf.setResizable(false);
			
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			double spot = d.getWidth() - 450;
			
			hf.setLocation((int) spot, 0);
			
			JPanel p = new JPanel();
			p.setLayout(null);
			p.setSize(450,600);
			p.setBackground(Color.black);
			p.setVisible(true);
			hf.add(p);
			
			textarea2 = new JTextArea();
			textarea2.setBounds(25,100,400,400);
			textarea2.setEditable(false);
			textarea2.setBackground(Color.DARK_GRAY);
			textarea2.setForeground(Color.green);
			p.add(textarea2);
			
			htitle = new JLabel();
			htitle.setBounds(75,15,300,20);
			htitle.setHorizontalAlignment(JTextField.CENTER);
			htitle.setFont(font3);
			htitle.setForeground(Color.white);
			p.add(htitle);
			//p.add(t);
			
			hf.setVisible(false);
		}

		
		public void actionPerformed(ActionEvent e) 
		{
			String s = e.getActionCommand();
			
			if(s.equals("Hand Rankings"))
			{
				rf.setVisible(true);
				//makeRuleWindow();
			}
			if(s.equals("Your Previous Hand Results"))
			{
				hf.setVisible(true);
				//makeHumanWindow();
			}
			
			
			if(s.equals("Previous Hand Winners"))
			{
				wf.setVisible(true);
				//makeWinnerWindow();
			}
			//hplayerlabel.setText(s); // JUST A TEST
		
			if(s.equals("Call") || s.equals("Check") || s.equals("All in"))
			{
				
				bchoice = 1;
				synchronized(send)
				{
				//send = "cca";
				send.notify();
				}
				decider = 1;
				
			}
			if(s.equals("Bet"))
			{
				
				bchoice = 2;
				synchronized(send)
				{
					//send = "br";
				send.notify();
				}
				decider = 2;
				
				bet.setVisible(false);
				call.setVisible(false);
				check.setVisible(false);
				fold.setVisible(false);
				
				placeBR.setVisible(true); 
				betRaiseTA.setVisible(true);
				brtext.setVisible(true);
				brtext.setText("Bet");
			}
			if(s.equals("Raise"))
			{
				bchoice = 4;
				synchronized(send)
				{
					//send = "br";
				send.notify();
				}
				decider = 2;
				
				raise.setVisible(false);
				call.setVisible(false);
				check.setVisible(false);
				fold.setVisible(false);
				
				placeBR.setVisible(true); 
				betRaiseTA.setVisible(true);
				brtext.setVisible(true);
				brtext.setText("Raise");
			}
			
			if(s.equals("Fold"))
			{
				
				bchoice = 3;
				synchronized(send)
				{
				//send = "f";
				send.notify();
				}
				decider = 3;
			}
			if(s.equals("Place"))
			{
				
				if(betRaiseTA.getText().length() == 0)
				{
					brtext.setText("Enter a value"); //text area is empty
				}
				
				if(numCheck(betRaiseTA.getText())) // if number is entered
				{
				enteredBet =Integer.parseInt(betRaiseTA.getText());
					
				if( enteredBet > maxBR)
				{
				brtext.setText("Too much");
				}
				else if (enteredBet < 0)
				{
				brtext.setText("Not enough");	
				}
				else
				{
					synchronized(send)
					{
					//send = "f";
					send.notify();
					}
					placeBR.setVisible(false); 
					betRaiseTA.setVisible(false);
					brtext.setVisible(false);
					betRaiseTA.setText(""); // clears text area
				
				}
				}
				else // if a number isnt entered
				{
					brtext.setText("numbers only");
				}
			
			
			
		}
	

		
		
	}
		
	public static boolean numCheck(String value)
	{
		int checker = 0;
		int len = value.length();
	//	char [] arr = value.toCharArray();
 		for(int count =0; count<len; count++)
		{
 			/*
 			if(!Character.isDigit(arr[count]));
			{
				System.out.println((arr[count]));
				return false;
			}
 			*/
 			
			//if(Character.isDigit('a'));
 			if(!Character.isDigit((value.charAt(count))))
			{
				System.out.println(value.charAt(count));
				checker++;
			}
			
			
		}
 		//System.out.println(Character.isDigit('a'));
 		//System.out.println(Character.isDigit('2'));
 		
 		System.out.println(checker);
 		if(checker > 0)
 		{
 			return false;
 		}
 		else
 		{
 			return true;	
 		}
		
	}
		
		
		
		
		
}




