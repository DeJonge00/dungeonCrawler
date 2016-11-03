package gameModel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

import gameGui.GameFrame;

public class Lazer extends Attack {
	private static final long serialVersionUID = 969857174724560046L;

	protected Lazer(Point location) {
		super(null, location, 0, 0, 40, 150 / Game.REFRESHINTERVAL, 100, 100, Color.red);
	}

	@Override 
	public void nextStep () 
	{
		this.lifetime--;
		if(lifetime <= 0) this.destroy();
		
	}
	
	public void paint(Graphics2D g) {
		g.setColor(this.color);
		Rectangle2D.Double e = new Rectangle2D.Double();
		e.setFrame (this.locationX-this.radius, 0, 2*this.radius, GameFrame.FRAME_HEIGHT);
		g.fill (e);
		e.setFrame (0, this.locationY-this.radius, GameFrame.FRAME_WIDTH, 2*this.radius);
		g.fill (e);
	}
	
	@Override
	public boolean collides (GameObject other) {
		double distance = Math.min(Math.abs(this.locationX - other.locationX), Math.abs((this.locationY - other.locationY)));
		System.out.println("Distance: " + distance);
		return distance < this.getRadius() + other.getRadius();
	}
}
