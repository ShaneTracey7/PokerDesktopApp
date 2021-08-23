
import java.util.ArrayList;
import java.util.Random;

///////////////////////
// Poker Swing
//////////////////////



public class AIplayer extends Player
{
static String [] names = {"SpiderMan","Hulk","Thor","IronMan","HawkEye"};
//attributes


	

//////METHODS//////


//in use
public static Player make_a_playerdif(int cash,Cards [] hand, int count) 
{
Player p1 = new Player(names[count], cash, hand,0,0);

return p1;
}


public static Cards [] getPCardsdifR(ArrayList <Cards> deck) //removes cards from deck
{
Cards [] hand = new Cards [2];
Cards c1 = deck.get(0);
hand [0] = c1;
deck.remove(0);
Cards c2 = deck.get(0); //used to be -1
hand [1] = c2;
deck.remove(0);
	
	
return hand;    //used to be -2
}

//in use
public static Cards [] getPCardsdifRR(ArrayList <Cards> deck) //removes cards from deck
{
Cards [] hand = new Cards [5];
Cards c1 = deck.get(0);
hand [0] = c1;
deck.remove(0);
Cards c2 = deck.get(0); //used to be -1
hand [1] = c2;
deck.remove(0);
Cards c3 = deck.get(0);
hand [2] = c3;
deck.remove(0);
Cards c4 = deck.get(0); //used to be -1
hand [3] = c4;
deck.remove(0);
Cards c5 = deck.get(0);
hand [4] = c5;
deck.remove(0);

return hand;   
}





}
