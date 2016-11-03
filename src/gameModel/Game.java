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
		System.out.println(allPlayers.size());
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
		this.level = 1;
	}
	
	private void resetPlayerStatus() {
		for(Player p : this.allPlayers) {
			p.init();
		}
	}
	
	private void initLevel() {
		System.out.println("Level " + this.level + " stated!");
		int x, y, temp, dist;
		for(int i = 0; i < level; i++) {
			do {
				dist = 300;
				x = rng.nextInt(GameFrame.FRAME_WIDTH-45) + 30;
				y = rng.nextInt(GameFrame.FRAME_HEIGHT-90) + 10;
				for(Player p : players) {
					temp = Math.abs(p.getLocation().x - x) + Math.abs(p.getLocation().y);
					if(temp > dist) dist = temp;
				}
			} while (dist <= 450);
			this.monsters.add(new Zombie(new Point(x, y), players.get(rng.nextInt(players.size()))));
		}
		this.level++;
	}
	
	@Override
	public void run ()
	{ // Update -> sleep -> update -> sleep -> etc...
		long executionTime, sleepTime;
		while (true)
		{
			
			if (!(this.gameOver() || this.aborted || this.paused))
			{
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
				System.out.println(n);
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

			try
			{
				Thread.sleep (sleepTime);
			}
			catch (InterruptedException e)
			{
				System.err.println ("Could not sleep in the game class");
				e.printStackTrace ();
			}
		}
	}
	
	private void removeDestroyedObjects ()
	{
		Collection <Attack> newa = new ArrayList <> ();
		for (Attack a : this.attacks)
		{
			if (!a.isDestroyed ())
			{
				newa.add (a);
			}
		}
		this.attacks = newa;
		
		Collection <Monster> newm = new ArrayList <> ();
		for (Monster m : this.monsters)
		{
			if (!m.isDestroyed ())
			{
				newm.add (m);
			}
		}
		this.monsters = newm;
		
		ArrayList <Player> newp = new ArrayList <> ();
		for (Player p : this.players)
		{
			if (!p.isDestroyed ())
			{
				newp.add (p);
			}
		}
		this.players = newp;
	}
	
	private void checkCollisions () {
		for (Player p : this.players) {
			for (Monster m : this.monsters) {
				if (p.collides (m)) {
					System.out.println("Player x monster");
					p.destroy();
				}
			}
			for(Attack a : this.attacks) {
				if (p.collides (a) && a.isHostile()) {
					p.destroy ();
				}
			}
		}
		for(Monster m : this.monsters) {
			for(Attack a : this.attacks) {
				if(m.collides(a) && !a.isHostile()) {
					m.destroy();
					a.destroy();
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
			System.out.println("205");
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
}