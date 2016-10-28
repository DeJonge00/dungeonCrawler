package gameModel;

import java.awt.Point;

import java.awt.Color;

public class LightningBolt extends Attack {
	private static final long serialVersionUID = 5628077465545068736L;

	protected LightningBolt(Point location, double velocityX, double velocityY, int radius, int level) {
		super(location, velocityX, velocityY, radius, 150 / Game.REFRESHINTERVAL, Color.blue);
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
}
