package gameModel;

import java.awt.Color;
import java.awt.Point;

public class TurretBullet extends Attack {
	private static final long serialVersionUID = -3646230673278017436L;
	protected TurretBullet(Player shooter, Point location, double velocityX, double velocityY) {
		super(shooter, location, velocityX, velocityY, 10, 500 / Game.REFRESHINTERVAL, 50, 1, Color.orange);
	}
}