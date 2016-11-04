package mainMenu;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gameGui.GameFrame;

public class MainMenuPanel extends JPanel {
	private static final long serialVersionUID = -3176198390190043523L;
	
	private JButton startSPGame;
	private JButton showControls;
	private JButton exitGame;
	private JTextField name;

	private JFrame frame;
	
	public MainMenuPanel(JFrame frame) {
		this.frame = frame;
		this.setLayout(null);
		addLabel("Welcome to:", 175, 50, 400, 50, 25);
		addLabel("Dungeon Crawler", 105, 100, 400, 50, 40);
		this.startSPGame = addButton("Start singleplayer game", 150, 180, 200, 40);
		this.name = addTextField("<Insert name here>", 150, 230, 200, 40);
		this.showControls = addButton("Show controls", 150, 280, 200, 40);
		this.exitGame = addButton("Exit game", 150, 330, 200, 40);
		name.setHorizontalAlignment(JTextField.CENTER);
		
		// Button starts new single player game
		startSPGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new GameFrame(name.getText());
				MainMenuPanel.this.frame.setVisible(false);
			}
		}
		);
		
		showControls.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, 
						"Walking:                   W/A/S/D\n"
						+ "Attacking:                Arrow-keys\n"
						+ "(Un)Pause                P\n"
						+ "Equip weapon:\n"
						+ "  Lightningbolt:        1\n"
						+ "  Fireball:                   2\n"
						+ "  Shotgun:                 3\n"
						+ "  Lazer:                      4",
						"Control scheme", JOptionPane.PLAIN_MESSAGE);
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