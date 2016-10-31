package gameGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import gameModel.Attack;
import gameModel.Game;
import gameModel.Monster;
import gameModel.Player;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 3113035915036079980L;
	private GameFrame frame;
	private Game game;
	
	public GamePanel(GameFrame frame, Game game) {
		this.frame = frame;
		this.game = game;
	}
	
	@Override
	public void paintComponent (Graphics g)
	{
		super.paintComponent (g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.setBackground (Color.black);

		for (Attack a : this.game.getAttacks())
		{
			a.paint(g2);
		}
		for (Monster m : this.game.getMonsters())
		{
			m.paint(g2);
		}
		for (Player p : this.game.getPlayers())
		{
			p.paint(g2);
		}

		g2.setColor (Color.WHITE);
	}
}
