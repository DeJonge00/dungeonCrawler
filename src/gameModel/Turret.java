package gameModel;

import java.awt.Color;
import java.awt.Point;

public class Turret extends Monster {

	private int cooldown;
	private Game game;
	protected Turret(Game g, Player target, Point location) {
		super(location, target, Color.yellow);
		this.game = g;
		cooldown = 500/Game.REFRESHINTERVAL;
	}

	@Override
	public void nextStep() {
		if(cooldown <= 0) {
			
		} else {
			cooldown--;
		}
	}
}
