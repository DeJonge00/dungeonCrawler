package gameModel;

import java.awt.Color;
import java.awt.Point;

public abstract class Attack extends GameObject {
	private static final long serialVersionUID = -2894854206285788391L;
	protected int lifetime;

	protected Attack(Point location, double velocityX, double velocityY, int radius, int lifetime, Color color) {
		super(location, velocityX, velocityY, radius, color);
		this.lifetime = lifetime;
	}

}
