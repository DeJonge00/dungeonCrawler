package gameModel;

import java.awt.Color;
import java.awt.Point;

public abstract class Attack extends GameObject {
	private static final long serialVersionUID = -2894854206285788391L;
	protected int lifetime;
	protected int damage;
	protected boolean hostile;
	
	protected Attack(Point location, double velocityX, double velocityY, double d, int lifetime, int damage, boolean hostile, Color color) {
		super(location, velocityX, velocityY, d, color);
		this.damage = damage;
		this.lifetime = lifetime;
		this.hostile = hostile;
	}
	
	public boolean isHostile() {
		return hostile;
	}
}
