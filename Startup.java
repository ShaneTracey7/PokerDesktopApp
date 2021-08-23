import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Startup 
{
	
// attributes
public static String pName;   //  name of human player
public static String winCondition; // win condition for game
public static int playerNum;  // number of players in game



//getter methods

	
//makes 
public static void gameSet(JFrame mframe) {
JFrame frame = new JFrame("Game Settings");
frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
frame.setSize(300, 300);
frame.setLocationRelativeTo(null); // puts frame in center of screen upon opening
frame.setResizable(false); 

JPanel panel = new JPanel();
panel.setLayout(null);  //absolute positioning
panel.setBackground(Color.DARK_GRAY);
frame.add(panel);

URL url11 = Startup.class.getResource("/th_logo.png");
ImageIcon icon = new ImageIcon(url11);
//ImageIcon icon = new ImageIcon("th_logo.png");
JLabel logo = new JLabel(icon);
logo.setBounds(45,0,200,120);


JLabel namelabel = new JLabel("Enter name:");
namelabel.setForeground(Color.green);
namelabel.setBounds(20,130,85,25);

JTextField nameInput = new JTextField("");
nameInput.setBounds(110,130,150,25);
nameInput.setForeground(Color.DARK_GRAY);



JLabel numlabel = new JLabel("# of players:");
numlabel.setForeground(Color.green);
numlabel.setBounds(20,160,85,25);

String [] nums = {" ","2", "3", "4", "5"};
JComboBox nump = new JComboBox(nums);
nump.setBounds(110,160,150,25);
nump.setForeground(Color.DARK_GRAY);
nump.setVisible(true);



JLabel winlabel = new JLabel("Game style:");
winlabel.setForeground(Color.green);
winlabel.setBounds(20,190,85,25);

String [] winc = {" ","First to $1000", "Most money after 5 hands", "Last man standing"};
JComboBox winS = new JComboBox(winc);
winS.setBounds(110,190,150,25);
winS.setForeground(Color.DARK_GRAY);
winS.setVisible(true);


JButton button = new JButton("Enter");
button.setBounds(110,225,80,25);
button.setForeground(Color.DARK_GRAY);
/*
button.setBackground(Color.green);
button.setOpaque(true);
button.setBorderPainted(false);
*/

panel.add(logo);

panel.add(namelabel);
panel.add(nameInput);

panel.add(numlabel);
panel.add(nump);

panel.add(winlabel);
panel.add(winS);

panel.add(button);

panel.setVisible(true);
frame.setVisible(true);


button.addActionListener(new ActionListener() {
	
	public void actionPerformed(ActionEvent e)
	{
		String name = null;
		
		String s = e.getActionCommand();
		if(s =="Enter")
		{
		name = nameInput.getText();
		
		}
		System.out.println("entered actionlistenr");
		Object cb1 = nump.getSelectedItem();
		Object cb2 = winS.getSelectedItem();
		String cb1s = cb1.toString();
		String cb2s = cb2.toString();
		if(cb1s != " " && cb2s != " " && name.length() > 0 )
		{
		winCondition = cb2s;
		/*
		switch(cb2s)
		{
		case "Most money after 5 hands": winCondition = 1;break;
		case "First to $1000": 			 winCondition = 2;break;
		case "Last man standing":		 winCondition = 3;break;
		}
		*/
		switch(cb1s)
		{
		case "2": playerNum = 2;break;
		case "3": playerNum = 3;break;
		case "4": playerNum = 4;break;
		case "5": playerNum = 5;break;
		}
		
		pName = name;
		mframe.setVisible(true);
		frame.dispatchEvent(new WindowEvent(mframe, WindowEvent.WINDOW_CLOSING));
		
		}
		else
		{
			//one field is empty so do nothing
		}
			
	}
});
}



}


/* 
 Startup window tasks
 -Get numbers of players
 -name of human player
 -win condition
 
 */



