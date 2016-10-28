package gameGui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import gameModel.Game;
import gameModel.Player;
import mainMenu.MainMenuFrame;
import mainMenu.MainMenuPanel;

public class GameFrame extends JFrame {
	private GamePanel panel;
	private AbstractAction quitAction;
	private AbstractAction toMainMenu;
	private Game game;

	public GameFrame() {
		// Init frame
		this.initActions ();
		this.setTitle ("My Amazing Game");
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		this.setSize (1280, 800);
		addMenuBar();
		
		// Init player and keylistener
		Player p1 = new Player(new Point(this.getWidth()/2,this.getHeight()/2), this.getHeight()/40);
		this.game = new Game(p1);
		Controller c1 = new Controller(game, p1);
		this.addKeyListener(c1);
		this.game.addObserver (new Observer ()
		{
			@Override
			public void update(Observable o, Object arg) {
				GameFrame.this.repaint ();
			}
		});
		System.out.println("Start new game thread");
		
		this.panel = new GamePanel(this, game);
		this.add(panel);
		
		Thread t = new Thread(game);
		t.start();
		
		// Enable frame
		this.setFocusable(true);
		this.requestFocusInWindow();
		
		this.setVisible (true);
	}
	
	private void addMenuBar() {
		JMenuBar mb = new JMenuBar ();
		JMenu m = new JMenu ("Game");
		mb.add (m);
		m.add (this.quitAction);
		//m.add (this.newSPGameAction);
		m.add (this.toMainMenu);
		this.setJMenuBar (mb);
	}
	
	private void initActions() 
	{
		// Quits the application
		this.quitAction = new AbstractAction ("Quit") 
		{
			public static final long serialVersionUID = 2L;

			@Override
			public void actionPerformed (ActionEvent ae) 
			{
				System.exit(0);
			}
		};
		
		// Quits the application
		this.toMainMenu = new AbstractAction ("Main Menu") 
		{
			public static final long serialVersionUID = 2L;

			@Override
			public void actionPerformed (ActionEvent ae) 
			{
				
				//Custom button text
				Object[] options = {"GET ME OUTTA HERE", "No, thanks"};
				int n = JOptionPane.showOptionDialog(GameFrame.this,
				    "Exit to Main Menu?","I WANT ATTENTION", JOptionPane.YES_NO_CANCEL_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null, options, options[1]);
				System.out.println(n);
				
				if(n == 0) {
					new MainMenuFrame();
					GameFrame.this.setVisible(false);
				}
			}
		};
	}
}
