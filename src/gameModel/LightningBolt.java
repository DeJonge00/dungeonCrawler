package gameModel;

import java.awt.Point;
import java.awt.Color;

public class LightningBolt extends Attack {
	private static final long serialVersionUID = 5628077465545068736L;

	protected LightningBolt(Player player, Point location, double velocityX, double velocityY, int level) {
		super(player, location, velocityX, velocityY, 10*(Math.pow(1.1, level)), 150 / Game.REFRESHINTERVAL, 40,  Color.blue);
	}
}
