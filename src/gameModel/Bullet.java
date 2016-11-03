package gameModel;

import java.awt.Color;
import java.awt.Point;

public class Bullet extends Attack {
	private static final long serialVersionUID = 7300264636688701463L;

	protected Bullet(Player shooter, Point location, double velocityX, double velocityY) {
		super(shooter, location, velocityX, velocityY, 5, 70 / Game.REFRESHINTERVAL, 30, Color.yellow);
	}
}
