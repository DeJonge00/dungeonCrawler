package gameModel;

import java.awt.Color;
import java.awt.Point;

public class Turret extends Monster {

	private int cooldown;
	private Game game;
	protected Turret(Game g, Point location, Player target) {
		super(location, target, 20, 20, Color.yellow);
		this.game = g;
		this.resetCooldown();
	}

	@Override
	public void nextStep() {
		if(cooldown <= 0) {
			double diffX = (this.locationX - this.target.locationX);
			double diffY = (this.locationY - this.target.locationY);
			double distance = Math.sqrt((diffX*diffX)+(diffY*diffY));
			double scale = 2*Game.REFRESHINTERVAL / distance;
			double velX = -(diffX*scale);
			double velY = -(diffY*scale);
			
			game.addAttack(new TurretBullet(null, new Point((int)locationX, (int)locationY), velX, velY));
			this.resetCooldown();
		} else {
			cooldown--;
		}
	}
	
	private void resetCooldown() {
		this.cooldown = 300/Game.REFRESHINTERVAL;
	}
}
