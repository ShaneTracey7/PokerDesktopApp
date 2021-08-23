import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class picture extends JPanel{

	    private ImageIcon icon;

	    public picture(String s) {

	        loadImage(s);
	        initPanel();
	    }

	    private void loadImage(String s) {

	        icon = new ImageIcon(s);
	    }
	    
	    private void initPanel() {

	        int w = icon.getIconWidth();
	        int h = icon.getIconHeight();
	        setPreferredSize(new Dimension(w, h));
	    }    

	    @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);

	        icon.paintIcon(this, g, 0, 0);
	    }
	    
	    public void toJL(JLabel j)
	    {
	    	j = new JLabel(icon);
	    }
	    
	}


