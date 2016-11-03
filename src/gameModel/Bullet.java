package gameModel;

import java.awt.Color;
import java.awt.Point;

public class Bullet extends Attack {

	protected Bullet(Player shooter, Point location, double velocityX, double velocityY) {
		super(shooter, location, velocityX, velocityY, 5, 70 / Game.REFRESHINTERVAL, 30, Color.yellow);
	}
}
