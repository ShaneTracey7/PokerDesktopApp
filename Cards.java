import java.util.Random;

import java.util.ArrayList;
import java.util.Collections;


//////////////////////////////
//Poker Swing Class
/////////////////////////////




public class Cards implements Comparable <Cards>
{
//attributes
private int num; //card number 1-13 (A,2-10,J,Q,K)
private String suit; // diamond,heart, club, spade
private String colour; //red or black
private int value; // value only for black jack 2-11 and ACE 1 or 11
private int order; // order 1-13 so 2-A
int deckSize = 52;

//default constructor
public Cards()
{
	num = 2;
	suit = "diamond";
	colour = "red";
	value = 2;
	order =  1;
}

//overloaded constructor
public Cards(int num, String suit, String colour, int value, int order)
{
	this();
	setNum(num); 
	setSuit(suit); 
	setColour(colour);
	setValue(value);
	setOrder(order);
}


//public access methods
public int GetNum()
{
	return num;
}
	
public String GetSuit()
{
	return suit;
}	

public String GetColour()
{
	return colour;
}

public int GetValue()
{
	return value;
}

public int GetOrder()
{
	return order;
}

//validity checks

public boolean isValidNum(int num)
{
	return (num > 0 && num <= 13); // only 13 numbers/face cards in a deck
}

public boolean isValidSuit(String suit)
{
	if(suit.equalsIgnoreCase("diamond") || suit.equalsIgnoreCase("spade") || suit.equalsIgnoreCase("heart") || suit.equalsIgnoreCase("club"))
	{
		return true;
	}
	else
		return false;
}

public boolean isValidColour(String colour)
{
	if(colour.equalsIgnoreCase("Red") || colour.equalsIgnoreCase("Black"))
	{
		return true;
	}
	else
		return false;
}

public boolean isValidValue(int value)
{
	return (value > 0 && value <= 11); // only for black jack
}

public boolean isValidOrder(int order)
{
	return (order > 0 && order <= 13); // for poker & for black jack(for splits and doubles) 
}
//setter methods

public int setNum(int num)
{
	if(isValidNum(num))
		this.num = num;
	return this.num;
}

public String setSuit(String suit)
{
	if(isValidSuit(suit))
		this.suit = suit;
	return this.suit;
}

public String setColour(String colour)
{
	if(isValidColour(colour))
		this.colour = colour;
	return this.colour;
}

public int setValue(int value)
{
	if(isValidValue(value))
		this.value = value;
	return this.value;
}

public int setOrder(int order)
{
	if(isValidOrder(order))
		this.order = order;
	return this.order;
}

//METHODS:

//creates deck of cards in order
public static void stdDeck(ArrayList <Cards> deck, int deckSize) {
	
  for(int count = 0; count < deckSize; ++count)
  {
	if(count < 13)  //all good for value & order
	{
		if(count > 0 && count < 10) //for 2-10
		{
		Cards c1 = new Cards(count+1, "Spade", "Black",count+1 , count);
		deck.add(c1);
		}
		else if(count == 0) //for ace
		{
		Cards c1 = new Cards(count+1, "Spade", "Black",11 , 13);
		deck.add(c1);	
		}
		else // j,q,k
		{
		Cards c1 = new Cards(count+1, "Spade", "Black",10 , count);
		deck.add(c1);
		}
		}
	
		else if(count >= 13 && count < 26) //all good for value & order
		{	
		if(count > 13 && count < 23) //for 2-10
		{
		Cards c1 = new Cards(count-12, "Diamond", "Red",count-12 , count-13);
		deck.add(c1);
		}
		else if(count == 13) //for ace
		{
		Cards c1 = new Cards(count-12, "Diamond", "Red",11 , 13);
		deck.add(c1);	
		}
		else // j,q,k
		{
		Cards c1 = new Cards(count-12, "Diamond", "Red",10 , count-13);
		deck.add(c1);
		}
		
		}
		else if(count >= 26 && count < 39) //NOT good for order but good for value
		{
			if(count > 28 && count < 38) //for 2-10
			{
			Cards c1 = new Cards(13 - (count - 26), "Club", "Black",13 - (count - 26), 12 - (count - 26));
			deck.add(c1);
			}
			else if(count == 38) //for ace
			{
			Cards c1 = new Cards(13 - (count - 26), "Club", "Black",11 , 13);
			deck.add(c1);	
			}
			else // j,q,k
			{
			Cards c1 = new Cards(13 - (count - 26), "Club", "Black",10 , 12 - (count - 26));
			deck.add(c1);
			}
		}
	
		else //NOT good for value & order
		{
			if(count > 41 && count < 51) //for 2-10
			{
			Cards c1 = new Cards(13 - (count - 39), "Heart", "Red",13 - (count - 39), 12 - (count - 39));
			deck.add(c1);
			}
			else if(count == 51) //for ace
			{
			Cards c1 = new Cards(13 - (count - 39), "Heart", "Red",11 , 13);
			deck.add(c1);	
			}
			else // j,q,k
			{
			Cards c1 = new Cards(13 - (count - 39), "Heart", "Red",10 , 12 - (count - 39));
			deck.add(c1);
			}
	}
  }
}







//shuffle deck of cards
public static void shuffle(ArrayList <Cards> deck)
{
	Collections.shuffle(deck);
}

public static void Display(ArrayList<Cards> list)
{
	System.out.println("\n\n");
	for(Cards card: list)  
		System.out.println(card); // printing cards to screen
}

public static void DisplayHandTH( Cards [] hand)
{
	ArrayList<Cards> list = new ArrayList<Cards> ();
	
	list.add(hand[0]);
	list.add(hand[1]);
	System.out.println(list);
}

public static void DisplayHandR( Cards [] hand)
{
	ArrayList<Cards> list = new ArrayList<Cards> ();
	
	list.add(hand[0]);
	list.add(hand[1]);
	list.add(hand[2]);
	list.add(hand[3]);
	list.add(hand[4]);
	System.out.println(list);
}

public static Cards [] getCommunityCards(ArrayList <Cards> deck, int cardCount,Cards []community) //removes cards from deck
{
	
	Cards c1 = deck.get(cardCount);
	community [0] = c1;
	deck.remove(cardCount);
	Cards c2 = deck.get(cardCount);
	community[1] = c2;
	deck.remove(cardCount);
	Cards c3 = deck.get(cardCount);
	community[2] = c3;
	deck.remove(cardCount);
	Cards c4 = deck.get(cardCount);
	community[3] = c4;
	deck.remove(cardCount);
	Cards c5 = deck.get(cardCount);
	community[4] = c5;
	deck.remove(cardCount);
	
	return community;    
}

public static void showCommunityCards(Cards []community, int bettingRound)
{
	if(bettingRound >= 2 )
	{
		System.out.println(community [0]);
		System.out.println(community [1]);
		System.out.println(community [2]);
		if(bettingRound >= 3)
		{
			System.out.println(community [3]);
			if(bettingRound == 4)
			{
				System.out.println(community [4]);
			}
		}
	}
	else
	{
		//do nothing
	}
	  
}

public int compareTo(Cards c)
{
if(this.order < c.order) 
{
	return -1;
}
if(this.order > c.order)
{
	return 1;
}
return 0;
}

//to String method
	public String toString()
	{	
		if(num == 1 )
		return " Ace" + " of " + suit + "s"; // Val: " + value +" Ord: "+ order;
	else if(num == 11 )
		return " Jack" + " of " + suit + "s"; // Val: " + value +" Ord: "+ order;
	else if(num == 12 )
		return " Queen" + " of " + suit + "s"; // Val: " + value +" Ord: "+ order;
	else if(num == 13 )
		return " King" + " of " + suit + "s"; // Val: " + value +" Ord: "+ order;
	else
		return " " + num + " of " + suit + "s"; // Val: " + value +" Ord: "+ order;
	}

}
