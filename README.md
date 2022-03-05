# PokerDesktopApp

This is a single-player texas hold'em poker desktop app, written in Java, where you can play against up to 4 AI players. You can play with a selection of 3 win conditions: first to $1000, most money after 5 hands, or last man standing. The algorthim comes from a previous poker program which only outputs to the console. For Graphics, this program uses the Java Swing library (JFrame, JLabel, JButton, and etc.). All windows use abosolute positioning for their layout and cannot be resized. 

Images:

All images were created in Google Draw (except images for "Hand Rankings" and Game "Settings" windows) and sized using online png resizer.

Menu Bar:

Under 'Help', you can find "Hand Rankings". This creates a new window in the top right corner of your display, which displays the rank of each poker hand in order from best (#1) to worst (#10).

Under 'Stats', you can find "Previous Hand Winners". This creates a new window in the top right corner of your display, which displays all hand winners of the game, including their profit. Also, you can find "Your Previous Hand Results". This creates a new window in the top right corner of your display, which displays money gained or lost for each hand.

To Begin:

To begin game, properly populate the fields of the Game Settings window: Name, Number of Players (including yourself), and Game Style (win condition). The Game will automatically begin once you press the 'Enter' button.

Gameplay:

The rules are the same as the standard rules for Texas Hold'em poker with a couple exceptions. 

Key conditions : 

- 4 rounds of betting: Preflop, Flop, Turn, River
- No Big or Small Blinds (contrary to standard rules)
- no-limit (player can bet/raise to any amount they desire)

To End:

The game ends when the win condition has been fulfilled, you lose all your money, or you exit out of the main window.
