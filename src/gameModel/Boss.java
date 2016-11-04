package gameModel;

import java.awt.Color;
import java.awt.Point;

import gameGui.GameFrame;

public class Boss extends Monster {
	private static final long serialVersionUID = -1734064509930023742L;
	private Game game;
	private int cooldown;

	protected Boss(Game game, Point location, Player target) {
		super(location, target, 100, 100, 500, Color.pink);
		this.game = game;
		this.cooldown = Game.rng.nextInt(100/Game.REFRESHINTERVAL) + 200/Game.REFRESHINTERVAL;
	}

	@Override
	public void nextStep() {
		if(target == null) {
			System.out.println("No target found");
			return;
		}
		// Calculate direction
		double diffX = (this.locationX - this.target.locationX);
		double diffY = (this.locationY - this.target.locationY);
		double distance = Math.sqrt((diffX*diffX)+(diffY*diffY));
		double scale = 0.1*Game.REFRESHINTERVAL / distance;
		this.velocityX = -(diffX*scale);
		this.velocityY = -(diffY*scale);

		// Update location.
		this.locationX = (this.locationX + this.velocityX);
		this.locationY = (this.locationY + this.velocityY);
		if(this.locationX < 0 || this.locationX > GameFrame.FRAME_WIDTH || this.locationY < 0 || this.locationY > GameFrame.FRAME_HEIGHT) this.destroy();
	
		if(cooldown <= 0) {
			int next = Game.rng.nextInt(100);
			if(next <= 50) this.attack1();
			else this.attack2();
		} else {
			cooldown--;
		}
		if(cooldown <= 50/Game.REFRESHINTERVAL) this.color = Color.red;
	}
	
	// Shoot bullets
	private void attack1() {
		double diffX = (this.locationX - this.target.locationX);
		double diffY = (this.locationY - this.target.locationY);
		double distance = Math.sqrt((diffX*diffX)+(diffY*diffY));
		double scale = 2*Game.REFRESHINTERVAL / distance;
		double velX = -(diffX*scale);
		double velY = -(diffY*scale);
		
		game.addAttack(new TurretBullet(null, new Point((int)locationX, (int)locationY), velX, velY));
		this.cooldown = Game.rng.nextInt(100/Game.REFRESHINTERVAL) + 50/Game.REFRESHINTERVAL;
	}
	
	private void attack2() {
		game.addAttack(new Lazer(this, 40, 150, 100));
		this.cooldown = Game.rng.nextInt(100/Game.REFRESHINTERVAL) + 200/Game.REFRESHINTERVAL;
	}
}
