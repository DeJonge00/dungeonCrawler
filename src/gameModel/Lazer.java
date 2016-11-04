package gameModel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

import gameGui.GameFrame;

public class Lazer extends Attack {
	private static final long serialVersionUID = 969857174724560046L;
	private GameObject shooter;

	protected Lazer(Player shooter, int radius, int lifetime, int damage) {
		super(shooter, shooter.getLocation(), 0, 0, radius, lifetime / Game.REFRESHINTERVAL, damage, 100000, Color.red);
		this.shooter = shooter;
	}
	
	protected Lazer(Monster shooter, int radius, int lifetime, int damage) {
		super(null, shooter.getLocation(), 0, 0, radius, lifetime / Game.REFRESHINTERVAL, damage, 100000, Color.red);
		this.shooter = shooter;
	}

	@Override 
	public void nextStep () {
		this.lifetime--;
		if(lifetime <= 0) this.destroy();
	}
	
	public void paint(Graphics2D g) {
		g.setColor(this.color);
		Rectangle2D.Double e = new Rectangle2D.Double();
		e.setFrame (this.shooter.locationX-this.radius, 0, 2*this.radius, GameFrame.FRAME_HEIGHT);
		g.fill (e);
		e.setFrame (0, this.shooter.locationY-this.radius, GameFrame.FRAME_WIDTH, 2*this.radius);
		g.fill (e);
	}
	
	@Override
	public boolean collides (GameObject other) {
		double distance = Math.min(Math.abs(this.shooter.locationX - other.locationX), Math.abs((this.shooter.locationY - other.locationY)));
		return distance < this.getRadius() + other.getRadius();
	}
}
