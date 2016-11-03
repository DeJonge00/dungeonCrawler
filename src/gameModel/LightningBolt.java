package gameModel;

import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.Color;
import java.awt.Graphics2D;

public class LightningBolt extends Attack {
	private static final long serialVersionUID = 5628077465545068736L;

	protected LightningBolt(Player player, Point location, double velocityX, double velocityY, int level) {
		super(player, location, velocityX, velocityY, 10*(Math.pow(1.1, level)), 150 / Game.REFRESHINTERVAL, 40,  Color.blue);
	}

	// Methods
	@Override 
	public void nextStep () 
	{
		this.stepsTilCollide = Math.max (0, this.stepsTilCollide - 1);
		this.lifetime--;
		if(lifetime <= 0) this.destroy();

		// Update location.
		this.locationX = (this.locationX + this.velocityX);
		this.locationY = (this.locationY + this.velocityY);
		if(this.locationX < 0 || this.locationX > 1280 || this.locationY < 0 || this.locationY > 800) this.destroy();
	}
	
	public void paint(Graphics2D g) {
		g.setColor(this.color);
		Ellipse2D.Double e = new Ellipse2D.Double ();
		e.setFrame (this.locationX - this.radius, this.locationY- this.radius, 2 * this.radius, 2 * this.radius);
		g.fill (e);
	}
}
