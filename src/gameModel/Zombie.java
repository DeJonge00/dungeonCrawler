package gameModel;

import java.awt.Color;
import java.awt.Point;

public class Zombie extends Monster {
	private static final long serialVersionUID = -4681510136089117903L;

	protected Zombie(Point location, Player target) {
		super(location, target, Color.green);
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
			if(this.locationX < 0 || this.locationX > 1280 || this.locationY < 0 || this.locationY > 800) this.destroy();
		}
	}
}
