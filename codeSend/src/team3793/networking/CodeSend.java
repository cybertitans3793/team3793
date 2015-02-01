package team3793.networking;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class CodeSend extends JFrame
{
	public Socket socket;
	public ServerSocket sSocket;
	
	public JTextArea write = new JTextArea();
	public JTextArea read = new JTextArea();
	
	public CodeSend()
	{
		
		write.setPreferredSize(new Dimension(480, 450));
		write.setCaretColor(Color.GREEN);
		write.
		read.setPreferredSize(new Dimension(480, 450));
		read.setEditable(false);
		
		getContentPane().setBackground(Color.DARK_GRAY);
		getContentPane().setLayout(new FlowLayout());
		add(write);
		add(read);
		setTitle("Code Sender");
		setPreferredSize(new Dimension(1000,500));
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public static void main(String args[])
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				CodeSend cs = new CodeSend();
			}
		});
	}
}
