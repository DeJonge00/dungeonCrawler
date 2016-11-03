package gameModel;

import java.awt.Color;
import java.awt.Point;

public class Boss extends Monster {

	private Game game;
	private int cooldown;

	protected Boss(Game g, Point location, Player target) {
		super(location, target, 100, 100, 500, Color.pink);
		this.game = game;
		this.cooldown = Game.rng.nextInt(100/Game.REFRESHINTERVAL) + 200/Game.REFRESHINTERVAL;
	}

	@Override
	public void nextStep() {
		if(cooldown <= 0) {
			int next = Game.rng.nextInt(100);
			if(next <= 100) this.attack1();
		} else {
			cooldown--;
		}
	}
	
	private void attack1() {
		double diffX = (this.locationX - this.target.locationX);
		double diffY = (this.locationY - this.target.locationY);
		double distance = Math.sqrt((diffX*diffX)+(diffY*diffY));
		double scale = 2*Game.REFRESHINTERVAL / distance;
		double velX = -(diffX*scale);
		double velY = -(diffY*scale);
		
		game.addAttack(new TurretBullet(null, new Point((int)locationX, (int)locationY), velX, velY));
		
		this.cooldown = Game.rng.nextInt(100/Game.REFRESHINTERVAL) + 200/Game.REFRESHINTERVAL;
	}
}
