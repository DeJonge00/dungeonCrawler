package gameModel;

import java.awt.Color;
import java.awt.Point;

public class Fireball extends Attack {
	private static final long serialVersionUID = -8055718093573040994L;

	protected Fireball(Player player, Point location, double velocityX, double velocityY, int level) {
		super(player, location, velocityX, velocityY, 20*(Math.pow(1.1, level)), 90 / Game.REFRESHINTERVAL, 100, Color.red);
	}
}