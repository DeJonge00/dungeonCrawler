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

		this.paintAttacks(g2);
		this.paintPlayers(g2);

		g2.setColor (Color.WHITE);
	}
	
	private void paintPlayers (Graphics2D g)
	{
		g.setColor (Color.GRAY);

		for (Player p : this.game.getPlayers ())
		{
			g.setColor(p.getColor());
			Ellipse2D.Double e = new Ellipse2D.Double ();
			e.setFrame (p.getLocation ().x - p.getRadius (), p.getLocation ().y - p.getRadius (), 2 * p.getRadius (), 2 * p.getRadius ());
			g.fill (e);
		}
	}
	private void paintAttacks (Graphics2D g) {
		for (Attack a : this.game.getAttacks())
		{
			g.setColor(a.getColor());
			Ellipse2D.Double e = new Ellipse2D.Double ();
			e.setFrame (a.getLocation ().x - a.getRadius (), a.getLocation ().y - a.getRadius (), 2 * a.getRadius (), 2 * a.getRadius ());
			g.fill (e);
		}
	}
}
