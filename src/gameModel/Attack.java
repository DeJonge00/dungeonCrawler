package gameModel;

import java.awt.Color;
import java.awt.Point;

public abstract class Attack extends GameObject {
	private static final long serialVersionUID = -2894854206285788391L;
	protected int lifetime;
	protected int damage;
	protected boolean hostile;
	private Player shooter;
	
	protected Attack(Player shooter, Point location, double velocityX, double velocityY, double d, int lifetime, int damage, Color color) {
		super(location, velocityX, velocityY, d, color);
		this.damage = damage;
		this.lifetime = lifetime;
		this.shooter = shooter;
	}
	
	public boolean isHostile() {
		return this.shooter == null;
	}
	
	public Player getShooter() {
		return this.shooter;
	}
}
