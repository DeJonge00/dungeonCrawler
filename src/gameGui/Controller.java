package gameGui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gameModel.Player;

public class Controller implements KeyListener {
	private Player player;

	public Controller(Player p) {
		this.player = p;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode ()) 
		{
			case KeyEvent.VK_UP:
				this.player.setUp (true);
				break;
			case KeyEvent.VK_DOWN:
				this.player.setDown (true);
				break;
			case KeyEvent.VK_LEFT:
				this.player.setLeft (true);
				break;
			case KeyEvent.VK_RIGHT:
				this.player.setRight (true);
				break;
			case KeyEvent.VK_P:
				System.out.println("PAUSE");
				break;
			case KeyEvent.VK_ESCAPE:
				System.out.println("Main Menu?");
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode ()) 
		{
			case KeyEvent.VK_UP:
				this.player.setUp (false);
				break;
			case KeyEvent.VK_DOWN:
				this.player.setDown (false);
				break;
			case KeyEvent.VK_LEFT:
				this.player.setLeft (false);
				break;
			case KeyEvent.VK_RIGHT:
				this.player.setRight (false);
				break;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
