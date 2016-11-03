package mainMenu;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gameGui.GameFrame;
import gameModel.Game;

public class MainMenuPanel extends JPanel {
	private static final long serialVersionUID = -3176198390190043523L;
	
	private JLabel title;
	private JButton startSPGame;
	private JButton exitGame;

	private JFrame frame;
	
	public MainMenuPanel(JFrame frame) {
		this.frame = frame;
		this.title = addLabel("Let the games begin!", 10, 10, 200, 50, 45);
		this.startSPGame = addButton("Start singleplayer game", 10, 100, 200, 50);
		this.exitGame = addButton("Exit game", 10, 100, 200, 50);
		
		// Button starts new single player game
		startSPGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new GameFrame();
				MainMenuPanel.this.frame.setVisible(false);
			}
		}
		);
		
		exitGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		);
	}

	private JButton addButton(String text, int x, int y, int width, int height) {
		JButton b = new JButton(text);
		b.setBounds(x, y, width, height);
		this.add(b);
		return b;
	}
	
	private JLabel addLabel(String text, int x, int y, int width, int height, int fontSize) {
	    JLabel l = new JLabel();
	    l.setText(text);
	    l.setBounds(x, y, width, height);
	    l.setFont(new Font("Arial", 0, fontSize));
	    this.add(l);
	    return l;
	}
	
	private JTextField addTextField(String text, int x, int y, int width, int height) {
	    JTextField l = new JTextField();
	    l.setText(text);
	    l.setBounds(x, y, width, height);
	    this.add(l);
	    return l;
	}
}