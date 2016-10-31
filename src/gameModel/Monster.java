package gameModel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

public class Monster extends GameObject {

	private Point target;
	
	protected Monster(Point location) {
		super(location, 0, 0, 30, Color.pink);
	}

	@Override
	public void nextStep() {
		if(target == null) {
			System.out.println("No target found");
		} else {
			// Calculate direction
			if(this.target.getLocation().x > this.locationX) {
				this.velocityX = (Game.rng.nextInt(10)/10)+2;
			} else {
				this.velocityX = -((Game.rng.nextInt(10)/10)+2);
			}
			if(this.target.getLocation().y > this.locationY) {
				this.velocityY = (Game.rng.nextInt(10)/10)+2;
			} else {
				this.velocityY = -((Game.rng.nextInt(10)/10)+2);
			}
						
			this.stepsTilCollide = Math.max (0, this.stepsTilCollide - 1);

			// Update location.
			this.locationX = (this.locationX + this.velocityX);
			this.locationY = (this.locationY + this.velocityY);
			System.out.println("Monster location: " + this.locationX + ", " + this.locationY);
			if(this.locationX < 0 || this.locationX > 1280 || this.locationY < 0 || this.locationY > 800) this.destroy();
		}
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(this.color);
		Ellipse2D.Double e = new Ellipse2D.Double ();
		e.setFrame (this.locationX - this.radius, this.locationY - this.radius, 2 * this.radius, 2 * this.radius);
		g.fill (e);
	}

	public void setTarget(Point p) {
		this.target = p;
	}
}
