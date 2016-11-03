package gameModel;

import java.awt.Color;
import java.awt.Point;

import gameGui.GameFrame;

public class Zombie extends Monster {
	private static final long serialVersionUID = -4681510136089117903L;

	protected Zombie(Point location, Player target) {
		super(location, target, 100, 30, Color.green);
	}

	@Override
	public void nextStep() {
		if(target == null) {
			System.out.println("No target found");
		} else {
			// Calculate direction
			double diffX = (this.locationX - this.target.locationX);
			double diffY = (this.locationY - this.target.locationY);
			double distance = Math.sqrt((diffX*diffX)+(diffY*diffY));
			double scale = 0.8*Game.REFRESHINTERVAL / distance;
			this.velocityX = -(diffX*scale);
			this.velocityY = -(diffY*scale);

			// Update location.
			this.locationX = (this.locationX + this.velocityX);
			this.locationY = (this.locationY + this.velocityY);
			if(this.locationX < 0 || this.locationX > GameFrame.FRAME_WIDTH || this.locationY < 0 || this.locationY > GameFrame.FRAME_HEIGHT) this.destroy();
		}
	}
}
