package mainMenu;

import javax.swing.JFrame;

public class MainMenuFrame extends JFrame {
	private static final long serialVersionUID = 3479343261234571218L;
	private MainMenuPanel panel;

	public MainMenuFrame() {
		this.setTitle ("My Amazing Game");
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		this.setSize (600, 400);
		
		this.panel = new MainMenuPanel(this);
		this.add(panel);
		
		this.setVisible (true);
	}
}