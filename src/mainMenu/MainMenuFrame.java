package mainMenu;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import gameGui.GameFrame;

public class MainMenuFrame extends JFrame {
	private static final long serialVersionUID = 3479343261234571218L;
	private MainMenuPanel panel;

	public MainMenuFrame() {
		this.setTitle ("Dungeon Crawler");
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		this.setSize (500, 500);
		
		this.panel = new MainMenuPanel(this);
		this.add(panel);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2)-100;
	    this.setLocation(x, y);
		
		this.setVisible (true);
	}
}