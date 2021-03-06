package gameGui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gameModel.Game;
import gameModel.Player;

public class Controller implements KeyListener {
	private Player player;
	private Game game;

	public Controller(Game game, Player p) {
		this.player = p;
		this.game = game;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode ()) 
		{
		// Movement
		case KeyEvent.VK_UP:
			this.player.setUpAttack();
			break;
		case KeyEvent.VK_DOWN:
			this.player.setDownAttack();
			break;
		case KeyEvent.VK_LEFT:
			this.player.setLeftAttack();
			break;
		case KeyEvent.VK_RIGHT:
			this.player.setRightAttack();
			break;
		// Attack
		case KeyEvent.VK_W:
			this.player.setUp(true);
			break;
		case KeyEvent.VK_S:
			this.player.setDown(true);
			break;
		case KeyEvent.VK_A:
			this.player.setLeft(true);
			break;
		case KeyEvent.VK_D:
			this.player.setRight(true);
			break;
		// Pause
		case KeyEvent.VK_P:
			
			if(game.isPaused()) {
				System.out.println("UNPAUSE");
				game.pause(false);
			} else {
				System.out.println("PAUSE");
				game.pause(true);
			}
			break;
		// Switch weapons
		case KeyEvent.VK_1:
			System.out.println("Equip: Lightningbolt");
			this.player.setWeapon("lightningbolt");
			break;
		case KeyEvent.VK_2:
			System.out.println("Equip: Fireball");
			this.player.setWeapon("fireball");
			break;
		case KeyEvent.VK_3:
			System.out.println("Equip: Shotgun");
			this.player.setWeapon("shotgun");
			break;
		case KeyEvent.VK_4:
			System.out.println("Equip: Lazer");
			this.player.setWeapon("lazer");
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode ()) 
		{
		case KeyEvent.VK_UP:
			this.player.setNoAttack();
			break;
		case KeyEvent.VK_DOWN:
			this.player.setNoAttack();
			break;
		case KeyEvent.VK_LEFT:
			this.player.setNoAttack();
			break;
		case KeyEvent.VK_RIGHT:
			this.player.setNoAttack();
			break;
		case KeyEvent.VK_W:
			this.player.setUp(false);
			break;
		case KeyEvent.VK_S:
			this.player.setDown(false);
			break;
		case KeyEvent.VK_A:
			this.player.setLeft(false);
			break;
		case KeyEvent.VK_D:
			this.player.setRight(false);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
