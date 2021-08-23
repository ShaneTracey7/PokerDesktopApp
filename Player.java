
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;



////////////////////////////////////
// Poker Swing
////////////////////////////////////

public class Player implements Comparable <Player>
{
//attributes
	//Cards [] hand = new Cards[2];
private String name; 		// non-empty string for players name (1-15 characters
private int cash; 		//amount a player has to bid $500 to start	
public Cards [] hand; 	// 2 pocket cards
private double status;  //where their hand ranks among other players
/* new */ public String statusWords; // makes a sentence from status
private int bet;		//


//default constructor
public Player()
{
	name = "player1";
	cash = 55;
	hand = null;
	status = 0.0;
	bet = 0;
}

//overload constructor
public Player(String name, int cash, Cards [] hand, double status, int bet)
{
	this();
	setName(name);
	setCash(cash);
	setHand(hand);
	setStatus(status);
	setBet(bet);
}

//public access methods

public int GetCash()
{
	return cash;
	
}
public String GetName()
{
	return name;
}

public Cards [] GetCards()
{
	return hand;
}

public double  GetStatus()
{
	return status;	
}

public int GetBet()
{
	return bet;
}
// validity checks
public boolean isValidCash(int cash)
{
	return (cash >= 0 && cash <= 10000);
}
 
public boolean isValidName(String name)
{
	return( name.length() > 0 && name.length() <= 15); 
}
public boolean isValidHand(Cards [] hand)
{
	return(hand.length <= 5); //cuz regular poker
}

public boolean isValidStatus(double status)
{
	return(status >= 0 && status <= 1000); //10 levels of hands
}

public boolean isValidBet(int bet)
{
	return (bet >= 0 );
}

//setter methods

public int setCash(int cash)
{
	if(isValidCash(cash))
		this.cash = cash;
	return this.cash;
}

public String setName(String name)
{
	if(isValidName(name))
		this.name = name;
	return this.name;
}

public Cards [] setHand(Cards [] hand)
{
	if(isValidHand(hand)) //validity check for hand to still be made
		this.hand = hand;
	return this.hand;
}

public double  setStatus(double  status)
{
	if(isValidStatus(status)) //validity check for hand to still be made
		this.status = status;
	return this.status;
}
public int setBet(int bet)
{
	if(isValidBet(bet))
		this.bet = bet;
	return this.bet;
}






///////////////////    Texam hold'em Methods  ///////////////

//makes single player for texas holdem
public static Player make_a_player(int cash,ArrayList <Cards> deck, int cardCount, String name, Cards [] hand) 
{	
		int num =getPCards(deck,cardCount,hand);
		Player p1 = new Player(name, cash, hand,0,0);
	
	//sc.close();
	return p1;
}

//sets hand for texas holdem
static public int getPCards(ArrayList <Cards> deck, int cardCount, Cards [] hand) //removes cards from deck
{
	Cards c1 = deck.get(cardCount);
	hand [0] = c1;
	deck.remove(cardCount);
	Cards c2 = deck.get(cardCount); //used to be -1
	hand [1] = c2;
	deck.remove(cardCount);
	
	
	return cardCount;    //used to be -2
}

//accidentally deleted the OG so idk if this gon work
public static Player make_a_playerdif(int cash, Cards [] hand, String name) 
{	
		
		Player p1 = new Player(name, cash, hand,0, 0);
	
	return p1;
}


//////////  Universal Player methods  ////////////


//determines winner when win condition is met
public static Player getWinner(ArrayList <Player> players)
{
	
	for(int count = 0; count < players.size(); count++) 
	{
			
		if(players.get(0).GetCash() < players.get(count).GetCash())
		{
			players.set(0,players.get(count));
		}
	}
	//System.out.println(players.get(0));
	return players.get(0);
}

//displays players using linked list
public static void Display(ArrayList<Player> list)
{
	System.out.println("\n");
	for(Player p1: list)  
		System.out.println(p1); // printing players to screen
	System.out.println("\n");
}

//resets status and bets for next hand
public static void refreshBS(ArrayList<Player> players)
{
	int len = players.size();
	for(int count = 0; count < len; count++)
	{
		players.get(count).setStatus(0);
		players.get(count).setBet(0);
	}
}


//checks all players to see if they still have cash if they dont they are removed from the game
public static void hasCash(ArrayList <Player> players, ArrayList <Player> leadtracker)
{
	for(int count = 0; count < players.size(); count++)
	{
	 if(players.get(count).GetCash() == 0)  //changed from if to while
	 {
		 leadtracker.remove(players.get(count));
		 players.remove(players.get(count));
		 count--;
	 }
	}
}

//function to ensure that every player gets to lead on their time to 
public static void leadhandset(ArrayList <Player> players,ArrayList <Player> leadtracker, int hcount)
{
	
	//sets players
	if(hcount == 0)
	{
	for(int ltcount = 0; ltcount < players.size(); ltcount++)
	{
		if(ltcount == players.size() - 1)
		{
			players.set(players.size() - 1,leadtracker.get(0));	
		}
		else
		{
		players.set(ltcount,leadtracker.get(ltcount+1));
		}
	}
	}
	else // not first time around
	{
		for(int ltcount = 0; ltcount < players.size(); ltcount++)
		{
			
			players.set(ltcount,leadtracker.get(ltcount));
		}
	}
	//sets lead tracker for next time
	for(int ltscount = 0; ltscount < leadtracker.size(); ltscount++)
	{
		if(ltscount == leadtracker.size() - 1)
		{
			leadtracker.set(leadtracker.size() - 1,players.get(0));	
		}
		else
		{
		leadtracker.set(ltscount,players.get(ltscount + 1));
		}
		
	}
}


//finds maxbet  (used in betChoice)
public static int checkBets(ArrayList<Player> players)
{
	int max = 0;
	int len = players.size();
	for(int count = 0; count < len; count++)
	{
		int num =players.get(count).GetBet();
		if(max < num)
		{
			max = num;
		}
	}
	
	return max;
}

// everything is good except to make sure everyone is either zero cash or at maxbet
//make max raises 3
public int betChoice(ArrayList<Player> players,  Game game, ArrayList<Player> foldedplayers, int tor, Scanner sc)
{
int pot = game.GetPot();
//Scanner sc = new Scanner(System.in);
int len = players.size();
int bc = 0;
int maxbet = Player.checkBets(players);
int currentBet = this.GetBet();
int currentCash = this.GetCash();
int maxRaise = this.GetCash() - maxbet;
		if(pot == 0 )
		{
			if(tor == 1)
			{
			Cards.DisplayHandTH(this.hand);
			}
			else
			{
			Cards.DisplayHandR(this.hand);
			}
			while(bc < 1 || bc > 3)
			{
				
				System.out.println(this.GetName() + ": Check (1), Bet(2), or Fold(3):");
				bc = sc.nextInt();
				
			}
		}
		

		if(this.GetCash() == 0)
		{
			System.out.println(this.GetName() + " is all in");//player is all in 
			return 0;
		}
		if(currentBet + currentCash <= maxbet)
		{
			while(bc != 1 && bc != 3)
			{
				
				if(tor == 1)
				{
				Cards.DisplayHandTH(this.hand);
				}
				else
				{
				Cards.DisplayHandR(this.hand);
				}
				System.out.println(this.GetName() + ": Go all in (1) or Fold(3):");
				bc = sc.nextInt();
			}
		}
		// players that are all in dont make it into the while loop
		while(bc < 1 || bc > 3)
		{
			if(tor == 1)
			{
			Cards.DisplayHandTH(this.hand);
			}
			else
			{
			Cards.DisplayHandR(this.hand);
			}
			System.out.println(this.GetName() + ": Call (1), Raise(2), or Fold(3):");
			if(sc.hasNextInt())
			{
				bc = sc.nextInt();
			}
			
		}

		switch(bc)
		{
		case 1: // call/check
		{
			if(maxbet >= (currentBet + currentCash))
			{
				this.setBet(currentBet + currentCash); // player is now all in
				this.setCash(0);
				game.setPot(pot + currentCash);
			}
			else
			{
				this.setBet(maxbet);
				this.setCash(currentCash - (maxbet - currentBet));
				game.setPot((pot - currentBet) + maxbet);
			}
			//sc.close();
			return 1;						// call - match current bet
		}
		case 2: //raise
		{
		System.out.println("Enter your raise: ");
		int r = sc.nextInt();
		while(r > maxRaise || r < 0)
			{
			System.out.println("Enter your raise: ");
			r = sc.nextInt();
			}
		this.setBet(r + maxbet);
		this.setCash(currentCash - (r +(maxbet - currentBet)));
		game.setPot((pot - currentBet) + (r + maxbet));
		//sc.close();
		return 2;						// raise- add "bet" attribute to player
		}
		case 3: //fold
		{
			foldedplayers.add(this);
			players.remove(this);
			Display(players);
			//sc.close();
			return 3; // fold -removes this player
		}
		
	  }
		//sc.close();
		return 0;
}

/////





/////
/*


Need to fix issues rn it doesnt work since the method cannot wait for a button to be pressed 

fixes:
- scrap whole algorthm for betting and create it in the Game class 
- quick fix with wait() n notify() functions


*/
/////
/////
public int betChoicefix(ArrayList<Player> players, Game game, ArrayList<Player> foldedplayers, int tor)
{
int pot = game.GetPot();
Scanner sc = new Scanner(System.in);
int len = players.size();
int bc = 0;
int maxbet = Player.checkBets(players);
int currentBet = this.GetBet();
int currentCash = this.GetCash();
int maxRaise = this.GetCash() - maxbet;
Game.maxBR = maxRaise; //for bet text area

		if(pot == 0 || currentBet == maxbet)
		{
			if(tor == 1)
			{
			Cards.DisplayHandTH(this.hand);
			}
			else
			{
			Cards.DisplayHandR(this.hand);
			}
			while(bc < 1 || bc > 3)
			{
				Game.check.setVisible(true);
				Game.bet.setVisible(true);
				Game.fold.setVisible(true);
				System.out.println(this.GetName() + ": Check (1), Bet(2), or Fold(3):");
				
				try {
					synchronized(Game.send) {
				      
					Game.send.wait();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bc = Game.decider;
				Game.decider = 0;
				//bc = sc.nextInt();
				
			}
			//Game.check.setVisible(false);
			//Game.bet.setVisible(false);
			//Game.fold.setVisible(false);
		}
		

		if(this.GetCash() == 0)
		{
			System.out.println(this.GetName() + " is all in");//player is all in 
			return 0;
		}
		if(currentBet + currentCash <= maxbet)
		{
			while(bc != 1 && bc != 3)
			{
				
				if(tor == 1)
				{
				Cards.DisplayHandTH(this.hand);
				}
				else
				{
				Cards.DisplayHandR(this.hand);
				}
				Game.allIn.setVisible(true);
				Game.fold.setVisible(true);
				System.out.println(this.GetName() + ": Go all in (1) or Fold(3):");
				
				try {
					synchronized(Game.send) {
				      
					Game.send.wait();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bc = Game.decider;
				Game.decider = 0;
				
				//bc = sc.nextInt();
			}
			//Game.allIn.setVisible(false);
			//Game.fold.setVisible(false);
		}
		// players that are all in dont make it into the while loop
		while(bc < 1 || bc > 3)
		{
			if(tor == 1)
			{
			Cards.DisplayHandTH(this.hand);
			}
			else
			{
			Cards.DisplayHandR(this.hand);
			}
			Game.call.setVisible(true);
			Game.raise.setVisible(true);
			Game.fold.setVisible(true);
			System.out.println(this.GetName() + ": Call (1), Raise(2), or Fold(3):");
			
			
			try {
				synchronized(Game.send) {
			      
				Game.send.wait();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bc = Game.decider;
			Game.decider = 0;
			/*
			if(sc.hasNextInt())
			{
				bc = sc.nextInt();
			}
			*/
		//	Game.call.setVisible(false);
		//	Game.raise.setVisible(false);
		//	Game.fold.setVisible(false);
			
		}
			/*
			synchronized(Game.send)
			{
				while(Game.send == "nothing")
				{
					try {
						Game.send.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			 wait = 0;
		while(wait== 0)
		{
		//does nothing until button pressed
			Game.bchoice;
			
		
		}
			 */
		//switch(Game.bchoice)
		switch(bc)
		{
		//case 0:break; // does nothing
		case 1: // call/check/all in
		{
			if(maxbet >= (currentBet + currentCash))
			{
				Game.hbet.setText("Bet: $" + (currentBet + currentCash)); // Bet JLabel
				this.setBet(currentBet + currentCash); // player is now all in
				this.setCash(0);
				game.setPot(pot + currentCash);
				
			}
			else
			{
				Game.hbet.setText("Bet: $" + maxbet); // Bet JLabel
				this.setBet(maxbet);
				this.setCash(currentCash - (maxbet - currentBet));
				game.setPot((pot - currentBet) + maxbet);
				
			}
			//sc.close();
			Game.clearButtons();
			return 1;						// call - match current bet
		}
		case 2: //raise//bet
		{
			System.out.println("We is about to wait");
			try {
				synchronized(Game.send) {
			      
				Game.send.wait();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		int r = Game.enteredBet;
		System.out.println("We is made it thru with" + r );
		/*
		System.out.println("Enter your raise: ");
		int r = sc.nextInt();
		while(r > maxRaise || r < 0)
			{
			System.out.println("Enter your raise: ");
			r = sc.nextInt();
			}
		*/
		Game.hbet.setText("Bet: $" + (r + maxbet)); // Bet JLabel
		this.setBet(r + maxbet);
		this.setCash(currentCash - (r +(maxbet - currentBet)));
		game.setPot((pot - currentBet) + (r + maxbet));
		
		//sc.close();
		Game.clearButtons();
		return 2;						// raise- add "bet" attribute to player
		}
		case 3: //fold
		{
			foldedplayers.add(this);
			players.remove(this);
			Display(players);
			//sc.close();
			Game.clearButtons();
			return 3; // fold -removes this player
		}
		
	  }
		//sc.close();
		return 0;
}

//kinda dumb way to fix probs wont work
public int swinghelpbet(int maxbet,int currentBet, int currentCash, int maxRaise, int pot, int len, Scanner sc,ArrayList<Player> players, Game game, ArrayList<Player> foldedplayers)
{
	switch(Game.bchoice)
	//switch(bc)
	{
	case 1: // call/check/all in
	{
		if(maxbet >= (currentBet + currentCash))
		{
			this.setBet(currentBet + currentCash); // player is now all in
			this.setCash(0);
			game.setPot(pot + currentCash);
		}
		else
		{
			this.setBet(maxbet);
			this.setCash(currentCash - (maxbet - currentBet));
			game.setPot((pot - currentBet) + maxbet);
		}
		//sc.close();
		Game.clearButtons();
		return 1;						// call - match current bet
	}
	case 2: //raise//bet
	{
	System.out.println("Enter your raise: ");
	int r = sc.nextInt();
	while(r > maxRaise || r < 0)
		{
		System.out.println("Enter your raise: ");
		r = sc.nextInt();
		}
	this.setBet(r + maxbet);
	this.setCash(currentCash - (r +(maxbet - currentBet)));
	game.setPot((pot - currentBet) + (r + maxbet));
	//sc.close();
	Game.clearButtons();
	return 2;						// raise- add "bet" attribute to player
	}
	case 3: //fold
	{
		foldedplayers.add(this);
		players.remove(this);
		Display(players);
		//sc.close();
		Game.clearButtons();
		return 3; // fold -removes this player
	}
	
  }
	//sc.close();
	return 0;
}





//////////////// end of universal methods //////////

///////////  AI player methods ////////////

//makes player check, call, raise or fold according to the situation
//sets the  correct player cash n bet value and correct Game pot value
public int betValue( ArrayList <Player> aiplayers, Game game,ArrayList <Player> players, ArrayList <Player> foldedplayers )
{

Player hp = new Player();  //null pointer maybe
	
for(int countHr = 0; countHr < players.size(); countHr++)
{
	if(aiplayers.contains(players.get(countHr)) == false) //human player
	{
		hp = players.get(countHr);
	}
}

int maxbet = Player.checkBets(players);
int betLevel = 0;
int cashleft = this.GetCash();
int currentbet = this.GetBet();
int state = 10;

if(cashleft == 0) //already all in 
{
	return 0; 
}
double stat = this.GetStatus();
if(stat < 200)
{
	betLevel = 1;
}
else if(stat < 300)
{
	betLevel = 2;
}
else if(stat < 400)
{
	betLevel = 3;
}
else if(stat < 500)
{
	betLevel = 4;
}
else if(stat >= 500)
{
	betLevel = 5;
}

if(cashleft + currentbet <= maxbet) //go all in or fold
{
	state  = this.setAiBetA(betLevel,cashleft,currentbet,maxbet, aiplayers, game, foldedplayers, players, hp);    
	return state;
}
else if(currentbet < maxbet) //call or raise or fold
{
	state = this.setAiBetC(betLevel,maxbet, cashleft,currentbet, aiplayers, game, foldedplayers, players, hp); 
	return state;
}
else if(currentbet == maxbet)  // check or raise (never fold)
{
state = this.setAiBetR(betLevel, maxbet, cashleft, game);  
return state;
 
} 
else
{
	return state;
}
}


public int setAiBetR(int betLevel, int maxbet, int cashleft, Game game)
{
	
int set1 = 1; int set2 = 1; int set3 = 1; int set4 = 1; int set41 = 1; int set5 = 1;

int maxrand = cashleft/10;   //throws array remainder
if(maxrand >= 30 )
{
	set1 = 2; set2 = 5; set3 = 6; set4 = 8; set41 = 4; set5 = 15;
}

else if(maxrand < 30 && maxrand >= 20 )
{
	set1 = 2; set2 = 4; set3 = 5; set4 = 7; set41 = 3; set5 = 10;
}

else if(maxrand < 20 && maxrand >= 15 )
{
	set1 = 2; set2 = 3; set3 = 4; set4 = 6; set41 = 2; set5 = 8;
}

else if(maxrand < 15 && maxrand >= 10 )
{
	set1 = 2; set2 = 3; set3 = 4; set4 = 5; set41 = 2; set5 = 5;
}
else if(maxrand < 10 && maxrand >= 7)
{
	set1 = 1; set2 = 2; set3 = 3; set4 = 4; set41 = 1; set5 = 4;
}
else if(maxrand < 7 && maxrand >= 5)
{
	set1 = 1; set2 = 1; set3 = 2; set4 = 3; set41 = 1; set5 = 3;
}
else if(maxrand < 5 && maxrand >= 2)
{
	set1 = 1; set2 = 1; set3 = 1; set4 = 2; set41 = 1; set5 = 2;
}
else   // $10 or less
{
	// all variables are set to '1'
}
	
Random r = new Random();
int percent = r.nextInt(10); //0-9
if(percent >=7 && betLevel != 1) //30% of the time drops down a case
{
	betLevel --;
}
/* cant  work cuz bets bigger than player cash will be placed
if(percent == 0 && betLevel != 1) // 10% of the time drops down a case
{
	betLevel ++;
}
*/
switch(betLevel)
{
case 1:// 0 really bad hand
	{	
		if(maxbet > 50)
		{
		 return 1;//check  	// pair
		}
		else
		{
			int num =(r.nextInt(set1)) * 5; // 0 or 5
		this.setBet(this.GetBet() + num); 
		this.setCash(this.GetCash() - num);
		game.setPot(game.GetPot() + num);
		int aiplayernum = Game.playersp.indexOf(this);
		System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
		int bets = this.GetBet();
		
		if(aiplayernum == 0)
		{
			Game.bet1.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 1)
		{
			Game.bet2.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 2)
		{
			Game.bet3.setText("Bet: $" + bets);	
		}
		else
		{
			Game.bet4.setText("Bet: $" + bets);	
		}
			
		
			if(num > 0)
			{
				return 2;
			}
			else
			{
				return 1;
			}   
		}
		
	}
case 2:// pair
	{
		if(maxbet > 100)
		{
		 return 1;//check  	// pair
		}
		else
		{
		int num =(r.nextInt(set2)) * 5; // 0,5,10,15,20
		this.setBet(this.GetBet() + num); 
		this.setCash(this.GetCash() - num);	
		game.setPot(game.GetPot() + num);
		
		int aiplayernum = Game.playersp.indexOf(this);
		System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
		int bets = this.GetBet();
		
		if(aiplayernum == 0)
		{
			Game.bet1.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 1)
		{
			Game.bet2.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 2)
		{
			Game.bet3.setText("Bet: $" + bets);	
		}
		else
		{
			Game.bet4.setText("Bet: $" + bets);	
		}
		
			if(num > 0)
			{
				return 2;
			}
			else
			{
				return 1;
			}  	 
		}
	}
case 3:// high pair/two pair 
	{
		if(maxbet > 200)
		{
		int num =(r.nextInt(set1)) * 5 + (r.nextInt(set1) * 10); 
		this.setBet(this.GetBet() + num); 
		this.setCash(this.GetCash() - num);
		game.setPot(game.GetPot() + num);
		
		int aiplayernum = Game.playersp.indexOf(this);
		System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
		int bets = this.GetBet();
		
		if(aiplayernum == 0)
		{
			Game.bet1.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 1)
		{
			Game.bet2.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 2)
		{
			Game.bet3.setText("Bet: $" + bets);	
		}
		else
		{
			Game.bet4.setText("Bet: $" + bets);	
		}
		
			if(num > 0)
			{
				return 2;
			}
			else
			{
				return 1;
			}   	
		}
		else
		{
		int num =(r.nextInt(set1)) * 5 + (r.nextInt(set3) * 10); 
		this.setBet(this.GetBet() + num); 
		this.setCash(this.GetCash() - num);
		game.setPot(game.GetPot() + num);
		
		int aiplayernum = Game.playersp.indexOf(this);
		System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
		int bets = this.GetBet();
		
		if(aiplayernum == 0)
		{
			Game.bet1.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 1)
		{
			Game.bet2.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 2)
		{
			Game.bet3.setText("Bet: $" + bets);	
		}
		else
		{
			Game.bet4.setText("Bet: $" + bets);	
		}
		
			if(num > 0)
			{
				return 2;
			}
			else
			{
				return 1;
			}   
		}
	}
case 4: // 3 of kind
	{
		if(maxbet > 200)
		{
		int num =(r.nextInt(set1)) * 5 + (r.nextInt(set41) * 10); 
		this.setBet(this.GetBet() + num); 
		this.setCash(this.GetCash() - num);
		game.setPot(game.GetPot() + num);
		
		int aiplayernum = Game.playersp.indexOf(this);
		System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
		int bets = this.GetBet();
		
		if(aiplayernum == 0)
		{
			Game.bet1.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 1)
		{
			Game.bet2.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 2)
		{
			Game.bet3.setText("Bet: $" + bets);	
		}
		else
		{
			Game.bet4.setText("Bet: $" + bets);	
		}
		
			if(num > 0)
			{
				return 2;
			}
			else
			{
				return 1;
			}   	 	
		}
		else
		{
		int num =(r.nextInt(set1)) * 5 + (r.nextInt(set4) * 10); 
		this.setBet(this.GetBet() + num); 
		this.setCash(this.GetCash() - num);
		game.setPot(game.GetPot() + num);
		
		int aiplayernum = Game.playersp.indexOf(this);
		System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
		int bets = this.GetBet();
		
		if(aiplayernum == 0)
		{
			Game.bet1.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 1)
		{
			Game.bet2.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 2)
		{
			Game.bet3.setText("Bet: $" + bets);	
		}
		else
		{
			Game.bet4.setText("Bet: $" + bets);	
		}
		
			if(num > 0)
			{
				return 2;
			}
			else
			{
				return 1;
			}   	 
		}
	}
case 5:
	{
		if(maxbet > 300)
		{
		int num = (r.nextInt(set1)) * 5 + (r.nextInt(set4) * 10); 
		this.setBet(this.GetBet() + num); 
		this.setCash(this.GetCash() - num);
		game.setPot(game.GetPot() + num);
		
		
		int aiplayernum = Game.playersp.indexOf(this);
		System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
		int bets = this.GetBet();
		
		if(aiplayernum == 0)
		{
			Game.bet1.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 1)
		{
			Game.bet2.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 2)
		{
			Game.bet3.setText("Bet: $" + bets);	
		}
		else
		{
			Game.bet4.setText("Bet: $" + bets);	
		}
		
			if(num > 0)
			{
				return 2;
			}
			else
			{
				return 1;
			}   	 // best straight or higher
		}
		else
		{
		int num = (r.nextInt(set1)) * 5 + (r.nextInt(set5) * 10); 
		this.setBet(this.GetBet() + num); 
		this.setCash(this.GetCash() - num);
		game.setPot(game.GetPot() + num);
		
		int aiplayernum = Game.playersp.indexOf(this);
		System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
		int bets = this.GetBet();
		
		if(aiplayernum == 0)
		{
			Game.bet1.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 1)
		{
			Game.bet2.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 2)
		{
			Game.bet3.setText("Bet: $" + bets);	
		}
		else
		{
			Game.bet4.setText("Bet: $" + bets);	
		}
		
			if(num > 0)
			{
				return 2;
			}
			else
			{
				return 1;
			}   	 // best straight or higher
		}
	}
default: 
	{
		return 5;  // glitch
	}
}

}

public int setAiBetC(int betLevel, int maxbet, int cashleft, int currentbet, ArrayList <Player> aiplayers, Game game, ArrayList <Player> foldedplayers,ArrayList <Player> players, Player Hp )
{
	
int set1 = 1; int set2 = 1; int set3 = 1; int set4 = 1; int set5 = 1;
int cb1 = 0;  int cb2 = 0;  int cb3 = 0;  int cb4 = 0;  int cb5 = 0;


int maxrand = cashleft/10;   //throws array remainder
if(maxrand >= 30 )
{
	set1 = 3; set2 = 5; set3 = 6; set4 = 8; set5 = 15;
	cb1 = 5;  cb2 = 10; cb3 = 30; cb4 = 50; cb5 = 200;
	
}
else if(maxrand < 30 && maxrand >= 20 )
{
	set1 = 3; set2 = 5; set3 = 5; set4 = 7; set5 = 15;
	cb1 = 5;  cb2 = 10; cb3 = 30; cb4 = 50; cb5 = 200;
	
}
if(maxrand < 20 && maxrand >= 15 )
{
	set1 = 3; set2 = 4; set3 = 4; set4 = 6; set5 = 15;
	cb1 = 5;  cb2 = 10; cb3 = 30; cb4 = 50; cb5 = 120;
	
}
else if(maxrand < 15 && maxrand >= 10 )
{
	set1 = 2; set2 = 4; set3 = 4; set4 = 6; set5 = 10;
	cb1 = 5;  cb2 = 10; cb3 = 30; cb4 = 50; cb5 = 75;
	
}
else if(maxrand < 10 && maxrand >= 7)
{
	set1 = 2; set2 = 3; set3 = 3; set4 = 5; set5 = 7;
	cb1 = 5;  cb2 = 10; cb3 = 20; cb4 = 30; cb5 = 50;
}
else if(maxrand < 7 && maxrand >= 5)
{
	set1 = 2; set2 = 2; set3 = 3; set4 = 3; set5 = 5;
	cb1 = 5;  cb2 = 10; cb3 = 10; cb4 = 25; cb5 = 40;
}
else if(maxrand < 5 && maxrand >= 2)
{
	set1 = 1; set2 = 1; set3 = 1; set4 = 2; set5 = 2;
	cb1 = 0;  cb2 = 0;  cb3 = 5;  cb4 = 5;  cb5 = 15;
}
else   // $10 or less
{
	
}

Random r = new Random();
int percent = r.nextInt(10); //0-9
int safebet = 0;
int callbet = 0;
if(percent >=7 && betLevel != 1) //30% of the time drops down a case
{
	betLevel --;
}
/* cant  work cuz bets bigger than player cash will be placed
if(percent == 0 && betLevel != 1) // 10% of the time drops down a case
{
	betLevel --;
}
*/

//probs bluffing 
if(Hp.GetBet() == maxbet && maxbet > 200 && Hp.GetStatus() + 100 < this.GetStatus())
{
	if(percent < 3 && cashleft > maxbet) // %30 0-2
	{
		this.setBet(maxbet); // calling
		this.setCash(this.GetCash() - (maxbet - currentbet));
		game.setPot(game.GetPot() + (maxbet - currentbet));
		
		int aiplayernum = Game.playersp.indexOf(this);
		System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
		int bets = this.GetBet();
		
		if(aiplayernum == 0)
		{
			Game.bet1.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 1)
		{
			Game.bet2.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 2)
		{
			Game.bet3.setText("Bet: $" + bets);	
		}
		else
		{
			Game.bet4.setText("Bet: $" + bets);	
		}
		return 1;
	}

}


switch(betLevel)
{
case 1:   //really bad hand
	{	int num = (r.nextInt(set1)) * 5;
		safebet = this.GetBet() + num;// currentbet + 0 or 5
		callbet = this.GetBet() + cb1;
		if(maxbet > safebet && maxbet > callbet)
		{
			foldedplayers.add(this);
			//aiplayers.remove(this); // folding 
			players.remove(this);
			return 3;
		}
		else if(safebet >= maxbet)
		{
			this.setBet(safebet); // 0 or 5
			this.setCash(this.GetCash() - num);
			game.setPot(game.GetPot() + num);
			int aiplayernum = Game.playersp.indexOf(this);
			System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
			int bets = this.GetBet();
			
			if(aiplayernum == 0)
			{
				Game.bet1.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 1)
			{
				Game.bet2.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 2)
			{
				Game.bet3.setText("Bet: $" + bets);	
			}
			else
			{
				Game.bet4.setText("Bet: $" + bets);	
			}
			
			return 2; 
		}
		else // callbet >= maxbet
		{
			this.setBet(maxbet); // calling
			this.setCash(this.GetCash() - (maxbet - currentbet));
			game.setPot(game.GetPot() + (maxbet - currentbet));
			
			int aiplayernum = Game.playersp.indexOf(this);
			System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
			int bets = this.GetBet();
			
			if(aiplayernum == 0)
			{
				Game.bet1.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 1)
			{
				Game.bet2.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 2)
			{
				Game.bet3.setText("Bet: $" + bets);	
			}
			else
			{
				Game.bet4.setText("Bet: $" + bets);	
			}
			return 1;
			   
		}
		
	}
case 2:  //pair
	{
		int num = (r.nextInt(set2)) * 5;
		safebet = this.GetBet() + num; //currentbet + 0,5,10,15,20
		callbet = this.GetBet() + cb2;
		if(maxbet > safebet && maxbet > callbet)
		{
			foldedplayers.add(this);
			//aiplayers.remove(this); // folding 
			players.remove(this);
			return 3;
		}
		else if (safebet >= maxbet)
		{
			this.setBet(safebet);  //raising
			this.setCash(this.GetCash() - num);
			game.setPot(game.GetPot() + num);
			int aiplayernum = Game.playersp.indexOf(this);
			System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
			int bets = this.GetBet();
			
			if(aiplayernum == 0)
			{
				Game.bet1.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 1)
			{
				Game.bet2.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 2)
			{
				Game.bet3.setText("Bet: $" + bets);	
			}
			else
			{
				Game.bet4.setText("Bet: $" + bets);	
			}
			
			return 2;
		}
		else // callbet >= maxbet
		{
			this.setBet(maxbet); // calling
			this.setCash(this.GetCash() - (maxbet - currentbet));
			game.setPot(game.GetPot() + (maxbet - currentbet));
			
			int aiplayernum = Game.playersp.indexOf(this);
			System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
			int bets = this.GetBet();
			
			if(aiplayernum == 0)
			{
				Game.bet1.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 1)
			{
				Game.bet2.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 2)
			{
				Game.bet3.setText("Bet: $" + bets);	
			}
			else
			{
				Game.bet4.setText("Bet: $" + bets);	
			}
			
			return 1;
		}
		
	}
		
case 3:// high pair/two pair
	{
		int num = (r.nextInt(set1)) * 5 + (r.nextInt(set3) * 10);
		safebet = this.GetBet() + num; //currentbet + 0 - 55
		callbet = this.GetBet() + cb3;
		if(maxbet > safebet && maxbet > callbet)
		{
			foldedplayers.add(this);
			//aiplayers.remove(this); // folding 
			players.remove(this);
			return 3;
		}
		else if (safebet >= maxbet)
		{
			this.setBet(safebet);  //raising
			this.setCash(this.GetCash() - num);
			game.setPot(game.GetPot() + num);
			
			int aiplayernum = Game.playersp.indexOf(this);
			System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
			int bets = this.GetBet();
			
			if(aiplayernum == 0)
			{
				Game.bet1.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 1)
			{
				Game.bet2.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 2)
			{
				Game.bet3.setText("Bet: $" + bets);	
			}
			else
			{
				Game.bet4.setText("Bet: $" + bets);	
			}
			
			return 2;
		}
		else // callbet >= maxbet
		{
			this.setBet(maxbet); // calling
			this.setCash(this.GetCash() - (maxbet - currentbet));
			game.setPot(game.GetPot() + (maxbet - currentbet));
			
			int aiplayernum = Game.playersp.indexOf(this);
			System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
			int bets = this.GetBet();
			
			if(aiplayernum == 0)
			{
				Game.bet1.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 1)
			{
				Game.bet2.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 2)
			{
				Game.bet3.setText("Bet: $" + bets);	
			}
			else
			{
				Game.bet4.setText("Bet: $" + bets);	
			}
			
			return 1;
		}
	}
case 4: // 3 of a kind
	{
		int num = (r.nextInt(set1)) * 5 + (r.nextInt(set4) * 10);
		safebet = this.GetBet() + num; //currentbet + 0 - 75
		callbet = this.GetBet() + cb4;
		if(maxbet > safebet && maxbet > callbet)
		{
			foldedplayers.add(this);
			//aiplayers.remove(this); // folding 
			players.remove(this);
			return 3;
		}
		else if (safebet >= maxbet)
		{
			this.setBet(safebet);  //raising
			this.setCash(this.GetCash() - num);
			game.setPot(game.GetPot() + num);
			
			
			int aiplayernum = Game.playersp.indexOf(this);
			System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
			int bets = this.GetBet();
			
			if(aiplayernum == 0)
			{
				Game.bet1.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 1)
			{
				Game.bet2.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 2)
			{
				Game.bet3.setText("Bet: $" + bets);	
			}
			else
			{
				Game.bet4.setText("Bet: $" + bets);	
			}
			
			return 2;
		}
		else // callbet >= maxbet
		{
			this.setBet(maxbet); // calling
			this.setCash(this.GetCash() - (maxbet - currentbet));
			game.setPot(game.GetPot() + (maxbet - currentbet));
			
			int aiplayernum = Game.playersp.indexOf(this);
			System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
			int bets = this.GetBet();
			
			if(aiplayernum == 0)
			{
				Game.bet1.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 1)
			{
				Game.bet2.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 2)
			{
				Game.bet3.setText("Bet: $" + bets);	
			}
			else
			{
				Game.bet4.setText("Bet: $" + bets);	
			}
			
			return 1;
		}
	}
case 5:  // best - straight or higher
	{
		int num = (r.nextInt(set1)) * 5 + (r.nextInt(set5) * 10);
		safebet = this.GetBet() + num; 
		callbet = this.GetBet() + cb5;
		if(maxbet > safebet && maxbet > callbet)
		{
			foldedplayers.add(this);
			//aiplayers.remove(this); // folding 
			players.remove(this);
			return 3;
		}
		else if (safebet >= maxbet)
		{
			this.setBet(safebet);  //raising
			this.setCash(this.GetCash() - num);
			game.setPot(game.GetPot() + num);
			
			int aiplayernum = Game.playersp.indexOf(this);
			System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
			int bets = this.GetBet();
			
			if(aiplayernum == 0)
			{
				Game.bet1.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 1)
			{
				Game.bet2.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 2)
			{
				Game.bet3.setText("Bet: $" + bets);	
			}
			else
			{
				Game.bet4.setText("Bet: $" + bets);	
			}
			
			return 2;
		}
		else // callbet >= maxbet
		{
			this.setBet(maxbet); // calling
			this.setCash(this.GetCash() - (maxbet - currentbet));
			game.setPot(game.GetPot() + (maxbet - currentbet));
			
			int aiplayernum = Game.playersp.indexOf(this);
			System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
			int bets = this.GetBet();
			
			if(aiplayernum == 0)
			{
				Game.bet1.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 1)
			{
				Game.bet2.setText("Bet: $" + bets);
			}
			else if(aiplayernum == 2)
			{
				Game.bet3.setText("Bet: $" + bets);	
			}
			else
			{
				Game.bet4.setText("Bet: $" + bets);	
			}
			
			return 1;
		}
	}
default: 
	{
		return 5;
		  // glitch
	}
}

}

public int setAiBetA(int betLevel, int cashLeft,int currentbet,int maxbet, ArrayList <Player> aiplayers, Game game, ArrayList <Player> foldedplayers, ArrayList <Player> players , Player Hp)
{
Random r = new Random();
int percent = r.nextInt(10); //0-9
if(percent >=7 && betLevel != 1) //30% of the time drops down a case
{
	betLevel --;
}
/* cant  work cuz bets bigger than player cash will be placed
if(percent == 0 && betLevel != 1) // 10% of the time drops down a case
{
	betLevel ++;
}
*/

//probs bluffing 
if(Hp.GetBet() == maxbet && maxbet > 200 && Hp.GetStatus() + 100 < this.GetStatus())
{
	if(percent < 3 ) // %30 0-2
	{
		this.setBet(currentbet + cashLeft); // calling
		this.setCash(0);
		game.setPot(game.GetPot() + cashLeft);
		
		int aiplayernum = Game.playersp.indexOf(this);
		System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
		int bets = this.GetBet();
		
		if(aiplayernum == 0)
		{
			Game.bet1.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 1)
		{
			Game.bet2.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 2)
		{
			Game.bet3.setText("Bet: $" + bets);	
		}
		else
		{
			Game.bet4.setText("Bet: $" + bets);	
		}
		
		return 1;
	}

}

switch(betLevel)
{
case 1:// 0 really bad hand
	{	
		foldedplayers.add(this);
		//aiplayers.remove(this);
		players.remove(this);
		return 3;
	}
case 2:// pair
	{
		foldedplayers.add(this);
		//aiplayers.remove(this);
		players.remove(this);
		return 3;
	}
case 3:// high pair/two pair 
	{
		foldedplayers.add(this);
		//aiplayers.remove(this);
		players.remove(this);
		return 3;
	}
case 4: // 3 of kind
	{
		if(percent == 1) // 10% chance
		{
		this.setBet(currentbet + cashLeft); //all in
		this.setCash(0);
		game.setPot(game.GetPot() + cashLeft);
		
		int aiplayernum = Game.playersp.indexOf(this);
		System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
		int bets = this.GetBet();
		
		if(aiplayernum == 0)
		{
			Game.bet1.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 1)
		{
			Game.bet2.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 2)
		{
			Game.bet3.setText("Bet: $" + bets);	
		}
		else
		{
			Game.bet4.setText("Bet: $" + bets);	
		}
		
		return 1;
		}
		else
		{
		foldedplayers.add(this);
		//aiplayers.remove(this);
		players.remove(this);
		return 3;
		}
	}
case 5: //straight or higher
	{
		if(percent % 2 == 0) // 50% chance
		{
		this.setBet(currentbet + cashLeft); //all in
		this.setCash(0);
		game.setPot(game.GetPot() + cashLeft);
		
		int aiplayernum = Game.playersp.indexOf(this);
		System.out.println("\n\naiplayernum: " + aiplayernum + "\n\n");
		int bets = this.GetBet();
		
		if(aiplayernum == 0)
		{
			Game.bet1.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 1)
		{
			Game.bet2.setText("Bet: $" + bets);
		}
		else if(aiplayernum == 2)
		{
			Game.bet3.setText("Bet: $" + bets);	
		}
		else
		{
			Game.bet4.setText("Bet: $" + bets);	
		}
		
		return 1;
		}
		else
		{
		foldedplayers.add(this);
		//aiplayers.remove(this);
		players.remove(this);
		return 3;
		}
	}
default: 
	{
		
		return 5;  // glitch
	}
}

}




public static void betting(ArrayList <Player> players, ArrayList <Player> foldedplayers, Game game, Cards [] c, Scanner sc)
{
int bettingRound = 1;
//int pot = 0;
int raisecount = 1;

for(;bettingRound < 5; bettingRound++)
{

Cards.showCommunityCards(c, bettingRound);


do
	{
	raisecount--;
	for(int count2 = 0;count2 < players.size(); count2++)
		{
		if(players.size() == 1)
		{
			raisecount = 20;break; //only one player left in the hand
		}
		int cases2 = players.get(count2).betChoice(players,game,foldedplayers,1, sc);
		
		while(cases2 == 3 && count2 <= players.size() - 1) //if a player folds 
			{
			cases2 = players.get(count2 ).betChoice(players,game,foldedplayers,1,sc);
			}
		if(cases2 == 2 && count2 > 0) // raise
			{
			raisecount = 1;
			}
		if(cases2 == 3 && count2 >= players.size()-1)
			{
			// dont do anything
			}
		else 
			{
			System.out.println("Pot is " + game.GetPot());
			System.out.println("Bet is " + players.get(count2).GetBet());
			}
		}
	System.out.println("raise count: "+ raisecount);
	
	}while(raisecount == 1);
	
	if(raisecount == 20)
	{
		break;
	}
	
}

}


//bet choice method is set for texas holdem atm
public static void bettingAI(ArrayList <Player> players, ArrayList <Player> foldedplayers, ArrayList <Player> aiplayers, Game game, Cards [] c)
{
	int bettingRound = 1;
	//int pot = 0;
	int raisecount = 1;	
	
	
for(;bettingRound < 5; bettingRound++)
{

Cards.showCommunityCards(c, bettingRound);
Game.showCommunityCardsSwing(bettingRound, Game.c1,Game.c2,Game.c3,Game.c4,Game.c5,Game.c1label,Game.c2label,Game.c3label,Game.c4label,Game.c5label);


do
	{
	raisecount--;
	for(int count2 = 0;count2 < players.size(); count2++)
		{
		if(players.size() == 1)
		{
			break; //only one player left in the hand
		}
		int cases2 = 10;
		if(aiplayers.contains(players.get(count2))) //if aiplayer
		{
		cases2 = players.get(count2).betValue(aiplayers, game, players, foldedplayers); //need to add folded players to this function
		}
		else // if human player
		{
		cases2 = players.get(count2).betChoicefix(players,game,foldedplayers,1);
		}
		while(cases2 == 3 && count2 <= players.size() - 1) //if a player folds 
			{
			System.out.println("Folded");
			//
			String xcheck = foldedplayers.get(foldedplayers.size() - 1).GetName();
			if(xcheck == "SpiderMan")
			{
				Game.p1x.setVisible(true);
				//set visibile
			}
			else if(xcheck == "Hulk")
			{
				Game.p2x.setVisible(true);
			}
			else if(xcheck == "Thor")
			{
				Game.p3x.setVisible(true);
			}
			else if(xcheck == "IronMan")
			{
				Game.p4x.setVisible(true);
			}
			else //human player
			{
				// do nothing
			}
			
			//
			if(aiplayers.contains(players.get(count2)))
			{
			cases2 = players.get(count2).betValue(aiplayers, game, players, foldedplayers); //need to add folded players to this function
			}
			else
			{
			cases2 = players.get(count2).betChoicefix(players,game,foldedplayers,1);
			}
			}
		if(cases2 == 2 && count2 > 0) // raise
			{
			raisecount = 1;
			}
		if(cases2 == 3 && count2 >= players.size()-1)
			{
			System.out.println("Folded lp");//last player of hand folds
			//
			
			String xcheck = foldedplayers.get(foldedplayers.size() - 1).GetName();
			if(xcheck == "SpiderMan")
			{
				Game.p1x.setVisible(true);
				//set visibile
			}
			else if(xcheck == "Hulk")
			{
				Game.p2x.setVisible(true);
			}
			else if(xcheck == "Thor")
			{
				Game.p3x.setVisible(true);
			}
			else if(xcheck == "IronMan")
			{
				Game.p4x.setVisible(true);
			}
			else //human player
			{
				// do nothing
			}
			
			//
			continue;// dont do anything
			
			}
		else if(cases2 == 5)  ///new
		{
			System.out.println("default");
		}
		else if(players.size() == 1)
		{
			break; //only one player left in the hand
		}
		else 
			{
			System.out.println("Pot is " + game.GetPot());
			Game.potlabel.setText("$" + game.GetPot()); //for pot label
			System.out.println("Bet is " + players.get(count2).GetBet());
			}
		
		String labelcheck = players.get(count2).GetName();
		if(labelcheck == "SpiderMan")
		{
			Game.p1dot.setVisible(true);
			//set visibile
		}
		else if(labelcheck == "Hulk")
		{
			Game.p2dot.setVisible(true);
		}
		else if(labelcheck == "Thor")
		{
			Game.p3dot.setVisible(true);
		}
		else if(labelcheck == "IronMan")
		{
			Game.p4dot.setVisible(true);
		}
		else //human player
		{
			Game.hpdot.setVisible(true);
			// do nothing
		}
		
		
		
		try {
			Thread.sleep(5000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
			}
		
		Game.p1dot.setVisible(false);
		Game.p2dot.setVisible(false);
		Game.p3dot.setVisible(false);
		Game.p4dot.setVisible(false);
		Game.hpdot.setVisible(false);
		
		/*
		if(foldedplayers.size() > 0)
		{
			for(int ct = 0; ct < foldedplayers.size() - 1; ct++) 
			{
			String xcheck = players.get(ct).GetName();
			if(xcheck == "SpiderMan")
			{
				Game.p1x.setVisible(true);
				//set visibile
			}
			else if(xcheck == "Hulk")
			{
				Game.p2x.setVisible(true);
			}
			else if(xcheck == "Thor")
			{
				Game.p3x.setVisible(true);
			}
			else if(xcheck == "IronMan")
			{
				Game.p4x.setVisible(true);
			}
			else //human player
			{
				// do nothing
			}
		}
		}
		*/
		
		}
	System.out.println("raise count: "+ raisecount);
	
	}while(raisecount == 1);
	
}
}
///////////ai player method







public int compareTo(Player p)
{
if(this.status < p.status) 
{
	return 1;
}
if(this.status > p.status)
{
	return -1;
}
return 0;
}


//to string 
public String toString()
{
	if(this.hand.length == 5)
	{
		return "" + name + " $" + cash + hand[0] + " " + hand[1] + " " + hand[2]+ " " + hand[3]+ " " + hand[4] +" status: " + status + " bet " + bet;	
	}
	else
	{
		return "" + name + " $" + cash + hand[0] + " " + hand[1] +" status: " + status + " bet " + bet;	
	}
	  

}

}

