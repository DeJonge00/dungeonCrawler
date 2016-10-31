package gameModel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

public abstract class Monster extends GameObject {
	private static final long serialVersionUID = 7294683746940898656L;
	protected Player target;
	
	protected Monster(Point location, Player target, Color c) {
		super(location, 0, 0, 30, c);
		this.target = target;
	}
	
	@Override
	public void paint(Graphics2D g) {
		g.setColor(this.color);
		Ellipse2D.Double e = new Ellipse2D.Double ();
		e.setFrame (this.locationX - this.radius, this.locationY - this.radius, 2 * this.radius, 2 * this.radius);
		g.fill (e);
	}

	public void setTarget(Player p) {
		this.target = p;
	}
}
