import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;


public class Test
{
	public static void main(String args[])
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				frame.setTitle("Sample Box");
				JTextField text = new JTextField("It Worked");
				text.setForeground(Color.RED);
				text.setEditable(false);
				frame.add(text);
				frame.setBackground(Color.CYAN);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
}
