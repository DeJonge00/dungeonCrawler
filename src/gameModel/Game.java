package gameModel;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gameGui.GameFrame;
import mainMenu.MainMenuFrame;

public class Game extends Observable implements Runnable {

	public static int REFRESHINTERVAL = 4;
	
	// Gamestates
	private JFrame frame;
	public static Random rng;
	private boolean aborted;
	private int tickCounter;
	private boolean gameOver;
	private boolean paused;
	private int level;
	
	// Gameobjects
	private ArrayList<Player> allPlayers;
	private ArrayList<Player> players;
	private Collection<Attack> attacks;
	private Collection<Monster> monsters;
	
	// Constructors
	public Game(JFrame frame) {
		this.frame = frame;
		this.allPlayers = new ArrayList<Player>();
		this.initGameData ();
	}

	public Game(JFrame frame, Player p1) {
		this(frame);
		this.allPlayers.add(p1);
		p1.setGame(this);
	}
	
	// Methods
	public void initGameData() {
		this.rng = new Random ();
		this.resetPlayerStatus();
		this.players = this.allPlayers;
		this.attacks = new ArrayList<>();
		this.monsters = new ArrayList<>();

		this.aborted = false;
		this.gameOver = false;
		this.tickCounter = 0;
		this.gameOver = false;
		this.level = 0;
	}
	
	private void resetPlayerStatus() {
		for(Player p : this.allPlayers) {
			p.init();
		}
	}
	
	private void initLevel() {
		this.level++;
		System.out.println("Level " + this.level + " stated!");
		try {
			Thread.sleep (40);
		} catch (InterruptedException e) {
			System.out.println("Could not wait before starting new level");
		}
		int x, y, temp, dist;
		for(int i = 0; i < level; i++) {
			do {
				dist = 300;
				x = rng.nextInt(GameFrame.FRAME_WIDTH-45) + 20;
				y = rng.nextInt(GameFrame.FRAME_HEIGHT-90) + 10;
				for(Player p : players) {
					temp = Math.abs(p.getLocation().x - x) + Math.abs(p.getLocation().y - y);
					if(temp > dist) dist = temp;
				}
			} while (dist <= 400);
			
			// Decide on monster
			int next = rng.nextInt(100);
			if(i%5 == 0) {
				this.monsters.add(new Boss(this, new Point(x, y), players.get(rng.nextInt(players.size()))));
				i += 2;
			} else {
				if(next < 70) this.monsters.add(new Zombie(new Point(x, y), players.get(rng.nextInt(players.size()))));
				else this.monsters.add(new Turret(this, new Point(x, y), players.get(rng.nextInt(players.size()))));
			}
		}
	}
	
	@Override
	public void run ()
	{ // Update -> sleep -> update -> sleep -> etc...
		long executionTime, sleepTime;
		while (true) {
			if (!(this.gameOver() || this.aborted || this.paused)) {
				executionTime = System.currentTimeMillis ();
				this.update ();
				executionTime -= System.currentTimeMillis ();
				//System.out.println("Time: " + (-executionTime));
				sleepTime = (long) Math.max (0, REFRESHINTERVAL / 0.1 + executionTime);
			}
			else sleepTime = 100;
			if(this.gameOver()) {
				//Custom button text
				Object[] options = {"Again, again!", "No, I'm done"};
				int n = JOptionPane.showOptionDialog(frame,
				    "Restart game?","I WANT ATTENTION", JOptionPane.YES_NO_CANCEL_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null, options, options[1]);
				this.gameOver = false;
				if(n == 0) {
					this.initGameData();
				} else {
					this.abort();
					new MainMenuFrame();
					this.frame.setVisible(false);
					return;
				}
			}
			try {
				Thread.sleep (sleepTime);
			}
			catch (InterruptedException e) {
				System.err.println ("Could not sleep in the game class");
				e.printStackTrace ();
			}
		}
	}
	
	private void removeDestroyedObjects () {
		Collection <Attack> newa = new ArrayList <> ();
		for (Attack a : this.attacks) {
			if (!a.isDestroyed ()) {
				newa.add (a);
			}
		}
		this.attacks = newa;
		
		Collection <Monster> newm = new ArrayList <> ();
		for (Monster m : this.monsters) {
			if (!m.isDestroyed ()) {
				newm.add (m);
			}
		}
		this.monsters = newm;
		
		ArrayList <Player> newp = new ArrayList <> ();
		for (Player p : this.players) {
			if (!p.isDestroyed ()) {
				newp.add (p);
			}
		}
		this.players = newp;
	}
	
	private void checkCollisions () {
		for (Player p : this.players) {
			for (Monster m : this.monsters) {
				if (p.collides(m)) {
					m.destroy();
					p.addHealth(-m.getDamage());
				}
			}
			for(Attack a : this.attacks) {
				if (a.collides(p) && a.isHostile()) {
					a.addHealth(-1);
					p.addHealth(-a.getDamage());
				}
			}
		}
		for(Monster m : this.monsters) {
			for(Attack a : this.attacks) {
				if(a.collides(m) && !a.isHostile()) {
					m.addHealth(a);
					a.addHealth(-1);
				}
			}
		}
		for(Attack a : attacks) {
			for(Attack a2 : attacks) {
				if(!a.isHostile() && a2.isHostile() && a.collides(a2)) {
					a.addHealth(-1);
					((Player) a.getShooter()).addScore(1);
					a2.destroy();
				}
			}
		}
	}
	
	private boolean gameOver() {
		return gameOver;
	}
	
	public void pause(boolean b) {
		this.paused = b;
	}
	
	public boolean isPaused() {
		return this.paused;
	}

	private void update() {
		if(this.monsters.size() <= 0) this.initLevel();
		this.checkCollisions();
		this.removeDestroyedObjects();
		for(Player p : players) {
			p.nextStep();
		}
		if(players.size() <= 0) {
			this.gameOver = true;
			return;
		}
		for(Attack a : attacks) {
			a.nextStep();
		}
		for(Monster m : monsters) {
			int t = rng.nextInt(this.players.size());
			m.setTarget(this.players.get(t));
			m.nextStep();
		}
		this.setChanged ();
		this.notifyObservers ();
	}

	public void addPlayer(Player p1) {
		players.add(p1);
		p1.setGame(this);
	}
	
	public void addAttack(Attack a) {
		this.attacks.add(a);
	}
	
	// Getters and Setters
	public ArrayList<Player> getAllPlayers() {
		return this.allPlayers;
	}
	public ArrayList<Player> getPlayers() {
		return this.players;
	}
	public Collection<Attack> getAttacks() {
		return this.attacks;
	}
	public Collection<Monster> getMonsters() {
		return this.monsters;
	}
	public void abort() {
		this.aborted = true;
	}
	public String getLevel() {
		//if(level%10 == 0) return "Boss level: " + level/10;
		return "Level: " + String.valueOf(this.level);
	}
}