
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java.util.List;

//////////////////////////
// Poker Swing class
////////////////////////



public class Checks 
{
/*	
Class that contains all the checks for what hands r dealt	
*/	
	
	
//////// METHODS //////
	//checks if player has a flush
	public static boolean isflush( Cards [] community, Cards [] pocketCards )
	{
		ArrayList<Cards> allCards = setCards(community,pocketCards);
		
		int hcount = 0;
		int dcount = 0;
		int ccount = 0;
		int scount = 0;
		
	//string allCards  = append pocket cards to community cards 
	//int len = allCards.length;
	for(int count = 0; count < 7; count++)
	{
	String suit = allCards.get(count).GetSuit();
	switch(suit)
	{
	case "Heart": 
				{
				hcount++;
				if(hcount == 5)
				{
				return true;
				}
				break;
				}
	case "Diamond": 
				{
				dcount++;
				if(dcount == 5)
				{
				return true;
				}
				break;
				}

	case "Club": 
				{
				ccount++;
				if(ccount == 5)
				{
				return true;
				}
				break;
				}

	case "Spade": 
				{
				scount++;
				if(scount == 5)
				{
				return true;
				}
				break;
				}
	
	}

	
	}
	return false;
}
	
	
	
	
	public static Cards [] aListToArr(ArrayList<Cards> cards)
	{
		Cards [] c = new Cards [5];
		for(int count = 0; count < 5; count++)
		{
			c[count] = cards.get(count);
		}
		return c;
	}
	
	
	public static boolean isStraightFlush( Cards [] community, Cards [] pocketCards )
	{
		ArrayList<Cards> allCards = setCards(community,pocketCards);
		ArrayList<Cards> flushCardsH = new ArrayList<Cards> ();
		ArrayList<Cards> flushCardsD = new ArrayList<Cards> ();
		ArrayList<Cards> flushCardsS = new ArrayList<Cards> ();
		ArrayList<Cards> flushCardsC = new ArrayList<Cards> ();
		
		Cards [] c = new Cards [5];
		
		int hcount = 0;
		int dcount = 0;
		int ccount = 0;
		int scount = 0;
		
	//string allCards  = append pocket cards to community cards 
	//int len = allCards.length;
	for(int count = 0; count < 7; count++)
	{
	String suit = allCards.get(count).GetSuit();
	switch(suit)
	{
	case "Heart": 
				{
				hcount++;
				flushCardsH.add(allCards.get(count));
				if(hcount == 5)
				{
					c = Checks.aListToArr(flushCardsH);
					if(Checks.isStraightR(c))
					{
						return true;
					}
				}
				break;
				}
	case "Diamond": 
				{
				dcount++;
				flushCardsD.add(allCards.get(count));
				if(dcount == 5)
				{
					c = Checks.aListToArr(flushCardsD);
					if(Checks.isStraightR(c))
					{
						return true;
					}	
				}
				break;
				}

	case "Club": 
				{
				ccount++;
				flushCardsC.add(allCards.get(count));
				if(ccount == 5)
				{
					c = Checks.aListToArr(flushCardsC);
					if(Checks.isStraightR(c))
					{
						return true;
					}
				}
				break;
				}

	case "Spade": 
				{
				scount++;
				flushCardsS.add(allCards.get(count));
				if(scount == 5)
				{
					c = Checks.aListToArr(flushCardsS);
					if(Checks.isStraightR(c))
					{
						return true;
					}
				}
				break;
				}
	
	}

	
	}
	return false;
}	
	
	
	
	
	
	
/*pair/ two pair/3 of kind /full house/ 4 of kind/ 
 need to figure out how to check the value of pair like heirarchy
 */
public static double ofKind( Cards [] community, Cards [] pocketCards)
{
	ArrayList<Cards> cards = setCards(community,pocketCards);
	
	
	double count1 = 1.00;
	double count2 = 2.00;
	double count3 = 3.00;
	double count4 = 4.00;
	double count5 = 5.00;
	double count6 = 6.00;
	double count7 = 7.00;
	double count8 = 8.00;
	double count9 = 9.00;
	double count10 = 10.00;
	double count11 = 11.00;
	double count12 = 12.00;
	double count13 = 13.00;
	
	double add = 20.00;
	
	for(int count = 0; count < 7; count++)
	{
	int num = cards.get(count).GetOrder();
	switch(num)
	{
	case 1: count1+=add;break;       //2       
	case 2: count2+=add;break;		//3 	
	case 3: count3+=add;break;		//4 	
	case 4: count4+=add;break;		//5 	
	case 5: count5+=add;break;		//6 	
	case 6: count6+=add;break;		//7 	
	case 7: count7+=add;break;		//8 	
	case 8: count8+=add;break;		//9 	
	case 9: count9+=add;break;		//10 	
	case 10: count10+=add;break;		//J
	case 11: count11+=add;break;		//Q 
	case 12: count12+=add;break;		//K 
	case 13: count13+=add;break;		//A
	}
	
	}
	double [] nums = {count1,count2,count3,count4,count5,count6,count7,count8,count9,count10,count11,count12,count13};
	Arrays.sort(nums);
	
	double max = nums[12];
	double max2 = nums[11];
	
	System.out.println("\n" + max + " " + max2 +"\n" ); //for debugging 
	
	if(max >=81 && max <= 93) // 4 of a kind      
	{
	return max + 100;//181-193 ---1
	}
else if(max >=61 &&  max2 >= 41 ) //full house  
	{
	double rep = max2/100;
	return  max + rep + 100; // 161.41 - 173.53  ---2  
	}
else if(max >=41 && max2 >= 41 ) //two pair 
	{
	double rep = max2/100;
	return max + rep; // 41.42 - 53.52   ---4
	}
else if(max >=61 ) //3 of kind 
	{
	return max; // 61-73 ---3
	}
else if(max >=41 ) //pair 
	{
	return max -30; // 21-33 ---5
	}
else // nothing
	{
	return 0;
	}
	
}

//need to fix rn my function to remove from array list isnt working

static public boolean isStraight( Cards [] community, Cards [] pocketCards)
{
	
	int[] num = new int [7]; 
	
	ArrayList<Cards> c = setCards(community,pocketCards);
	ArrayList<Cards> cst = new ArrayList<Cards> ();
	Collections.sort(c);
	
	// removes duplicates of same number bc it mess up orders
			for(int n = 0; n < 6; n++)
			{
				Cards c1 = c.get(n);
				int n1 = c1.GetOrder();
				Cards c2 = c.get(n+1);
				int n2 = c2.GetOrder();
				if(n == 5)
				{
					cst.add(c1);
					cst.add(c2);
				}
				else if(n1 != n2)
				{
				cst.add(c1);
				}
				else
				{
					System.out.println("Double of: " + (n1+1));
				}
			}
	//creates int [] of order of cards
	int len = cst.size();
	for(int n = 0; n < len ; n++)
	{
		num[n] = cst.get(n).GetOrder();
	}
	//prints int[]
	for(int n = 0; n < len ; n++)
	{
		System.out.println((num[n]+1));
	}
	
for(int n = 0; n < 3; n++)
{
	System.out.println("round "+ n);
	int a = 1;
	int b = 0;
	int check = 1;
	while (num[(n+a)] - num [(n + b)] == 1)
	{
		System.out.println(check);
		a +=1;
		b +=1;
		check += 1;
		if(check == 5)
		{
			System.out.println("str8");
			return true;
		}
	}
}
return false;
		
}

//combines commmunity cards and pokcet cards of a player(for texas holdem only)
static public ArrayList<Cards> setCards( Cards [] community, Cards [] pocketCards)
{
	
	ArrayList<Cards> allCards = new ArrayList<Cards> (); 
	allCards.add(community[0]);
	allCards.add(community[1]);
	allCards.add(community[2]);
	allCards.add(community[3]);
	allCards.add(community[4]);
	allCards.add(pocketCards[0]);
	allCards.add(pocketCards[1]);
	
return allCards;
	
}

//sets community cards (for texas holdem only)
static public ArrayList<Cards> setComCards( Cards [] community)
{
	
	ArrayList<Cards> comCards = new ArrayList<Cards> (); 
	comCards.add(community[0]);
	comCards.add(community[1]);
	comCards.add(community[2]);
	comCards.add(community[3]);
	comCards.add(community[4]);
	
	
return comCards;
	
}

//return high card
static public int getHigh( Cards [] community, Cards [] pocketCards)
{
	ArrayList<Cards> allCards = setCards(community,pocketCards);
	int[] num = new int [7];
	for(int count = 0; count < 7; count++) 
	{
	int ord = allCards.get(count).GetOrder();
	num[count] = ord;
	}
	Arrays.sort(num);
	return num[6];
}

static public void highestCard( Cards [] community, ArrayList<Player> players)
{
 
	int len = players.size();
	int [] arr = new int [len];
	
	for(int count = 0; count < len; count++ )
	{
	int high = getHigh(community,players.get(count).hand);
	arr[count] = high;
	
	
	}
	Arrays.sort(arr);
	System.out.println("Highest card is " + arr[len-1]);
	
}

public static boolean isRoyal(Cards [] community, Cards [] pocketCards) // havent tested if works
{
	
	ArrayList<Cards> c = setCards(community,pocketCards);
	int [] arr = new int [7];
	
	for(int count = 0; count < 7; count++)
	{
		arr[count] = c.get(count).GetOrder();
	}
	if(Arrays.asList(arr).contains(8))
		{
		if(Arrays.asList(arr).contains(9))
			{
			if(Arrays.asList(arr).contains(10))
				{
				if(Arrays.asList(arr).contains(11))
					{
					if(Arrays.asList(arr).contains(12))
						{
						if(Arrays.asList(arr).contains(13))
							{
							return true;
							}
						}
					}
				}
			}
		
		}
	return false;
}


//used in texas holdem straight flush check
static public ArrayList<Cards> setRegCards( Cards [] hand)
{
	
	ArrayList<Cards> regCards = new ArrayList<Cards> (); 
	regCards.add(hand[0]);
	regCards.add(hand[1]);
	regCards.add(hand[2]);
	regCards.add(hand[3]);
	regCards.add(hand[4]);
	
	
return regCards;
	
}

//used in texas holdem straight flush check 
static public boolean isStraightR( Cards [] hand)
{
	
	int[] num = new int [5]; 
	
	ArrayList<Cards> c = setRegCards(hand);
	Collections.sort(c);
	
	//creates int [] of order of cards
	int len = c.size();
	for(int n = 0; n < len ; n++)
	{
		num[n] = c.get(n).GetOrder();
	}
	//prints int[]
	for(int n = 0; n < len ; n++)
	{
		System.out.println((num[n]+1));
	}

	int n = 0;
	int a = 1;
	int b = 0;
	int check = 1;
	while (num[(n+a)] - num [(n + b)] == 1)
	{
		System.out.println(check);
		a +=1;
		b +=1;
		check += 1;
		if(check == 5)
		{
			System.out.println("str8");
			return true;
		}
	}

return false;
		
}

//checks if player has a flush
	public static boolean isflushR( Cards [] hand)
	{
		ArrayList<Cards> allCards = setRegCards(hand);
		
		int hcount = 0;
		int dcount = 0;
		int ccount = 0;
		int scount = 0;
		
	//string allCards  = append pocket cards to community cards 
	//int len = allCards.length;
	for(int count = 0; count < 5; count++)
	{
	String suit = allCards.get(count).GetSuit();
	switch(suit)
	{
	case "Heart": 
				{
				hcount++;
				if(hcount == 5)
				{
				return true;
				}
				break;
				}
	case "Diamond": 
				{
				dcount++;
				if(dcount == 5)
				{
				return true;
				}
				break;
				}

	case "Club": 
				{
				ccount++;
				if(ccount == 5)
				{
				return true;
				}
				break;
				}

	case "Spade": 
				{
				scount++;
				if(scount == 5)
				{
				return true;
				}
				break;
				}
	
	}

	
	}
	return false;
}

/* 
Sometimes for Two Pair and full house, value off by 1 ( num gets rounded from .41 to .4099999)
*/	
public static String faceOrder(int startOrder, double currentOrder)
{
int co = (int)currentOrder; //type casting (rounds down to nearest whole number)

int o =	co - startOrder;
String ow = "broke";

switch(o)
{
case 1: ow = "Two"; break;
case 2: ow = "Three"; break;
case 3: ow = "Four"; break;
case 4: ow = "Five"; break;
case 5: ow = "Six"; break;
case 6: ow = "Seven"; break;
case 7: ow = "Eight"; break;
case 8: ow = "Nine"; break;
case 9: ow = "Ten"; break;
case 10: ow = "Jack"; break;
case 11: ow = "Queen"; break;
case 12: ow = "King"; break;
case 13: ow = "Ace"; break;
}
System.out.println("\n\n\n\n o = " + o + "\n\n\n\n");

return ow;
}

	
public static void fullCheck(ArrayList<Player> players,Cards [] community)
{
	for(int count = 0;count < players.size(); count++)
	{
	double alter = 0.00;

	double num = Checks.ofKind(community,players.get(count).hand);
	System.out.println("\n\n" + num + "\n\n");

	if(Checks.isflush(community,players.get(count).hand) && Checks.isRoyal(community,players.get(count).hand))
	{
		alter = 1000;
		System.out.println(players.get(count).GetName() + " has a royal flush");
		players.get(count).setStatus(alter);
		/* new */ players.get(count).statusWords = "Royal flush";
	}
	if(Checks.isStraightFlush(community,players.get(count).hand)) //updated straight flush method
	{
		alter = 900;
		System.out.println(players.get(count).GetName() + " has a straight flush");
		players.get(count).setStatus(alter);
		/* new */ players.get(count).statusWords = "Straight flush";
	}

	if (num >= 181 && num <= 193)
	{
		alter = 800 + (num-180);
		System.out.println(players.get(count).GetName() + " has a 4 of-a-kind");
		players.get(count).setStatus(alter);
		/* new */ players.get(count).statusWords = "4 of-a-kind of " + faceOrder(180, num) + "s";
	}
	else if(num >= 161 && num < 174)
	{
		alter = 700 + (num-160);
		System.out.println(players.get(count).GetName() + " has full house");
		players.get(count).setStatus(alter);
		/* new */ int helper = (int)num; 
		/* new */ players.get(count).statusWords = "Full house 3 " + faceOrder(160, num) + "s & 2 " + faceOrder(40, ((num - helper)*100));
		
	}
	else if(Checks.isflush(community,players.get(count).hand))
	{
		alter = 600;
		System.out.println(players.get(count).GetName() + " has a flush");
		players.get(count).setStatus(alter);
		/* new */ players.get(count).statusWords = "Flush";
	}
	else if(Checks.isStraight(community,players.get(count).hand))
	{
		alter = 500;
		System.out.println(players.get(count).GetName() + " has a straight");
		players.get(count).setStatus(alter);
		/* new */ players.get(count).statusWords = "Straight";
	}
	else if(num >= 61 && num <= 73)
	{
		alter = 400 + (num-60);
		System.out.println(players.get(count).GetName() + " has a 3 of-a-kind");
		players.get(count).setStatus(alter);
		/* new */ players.get(count).statusWords = "3 of-a-kind of " + faceOrder(60, num) + "s";
	}
	else if(num > 41 && num < 54)
	{
		
		alter = 300 + (num-40);
		System.out.println(players.get(count).GetName() + " has a two pair");
		players.get(count).setStatus(alter);
		/* new */ int helper = (int)num;
		/* new */ players.get(count).statusWords = "Two pair of "+ faceOrder(40, num) + "s high & " + faceOrder(40, ((num - helper)*100));
	}
	else if(num >= 11 && num <= 23)
	{
		alter = 200 + 30 + (num-21);
		
		System.out.println(players.get(count).GetName() + " has a pair");
		players.get(count).setStatus(alter);
		/* new */ players.get(count).statusWords = "Pair of " + faceOrder(10, num) + "s";
	}
	else  //num =0
	{
		int gh = Checks.getHigh(community,players.get(count).hand);
		alter = gh + 100;
		System.out.println(players.get(count).GetName() + " high card: " + gh);
		players.get(count).setStatus(alter);
		/* new */ players.get(count).statusWords = "High card";
	}

	}	
	
}



}

/* Things to do
 
 isflush method       DONE
 is ofKind method-    DONE  
 isStraight method     
 
 make things more efficient 
 ex. if no suit has 3 community cards then no isflush call
 ex. if no cards r close together then no isStraight call
 
 
 add "status" class attribute to a player for easy win declarations
 
 Royal flush    10
 Straight flush 9
 4 of kind		8.00-8.13
 full house		7.00 and refer to 3 of kind
 flush			6
 straight		5
 3 of kind	  4.00-4.13
 two pair     3.00 and     refer to pair highest pair takes it
 pair		  2.00-2.13
 high card    1.00-1.13
 
 
 */
	
	
	
	

