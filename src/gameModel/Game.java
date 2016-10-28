package gameModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Random;

public class Game extends Observable implements Runnable {

	public static int REFRESHINTERVAL = 4;
	
	// Gamestates
	private static Random rng;
	private boolean aborted;
	private int tickCounter;
	private boolean gameOver;
	private boolean paused;
	
	// Gameobjects
	private ArrayList<Player> players;
	private Collection<Attack> attacks;
	
	// Constructors
	public Game() {
		this.rng = new Random ();
		this.players = new ArrayList<>();
		this.attacks = new ArrayList<>();
		this.initGameData ();
	}

	public Game(Player p1) {
		this();
		this.addPlayer(p1);
	}
	
	// Methods
	private void initGameData() {
		this.aborted = false;
		this.tickCounter = 0;
		this.gameOver = false;
	}
	
	@Override
	public void run ()
	{ // Update -> sleep -> update -> sleep -> etc...
		long executionTime, sleepTime;
		while (true)
		{
			if (!(this.gameOver () || this.aborted || this.paused))
			{
				executionTime = System.currentTimeMillis ();
				this.update ();
				executionTime -= System.currentTimeMillis ();
				//System.out.println("Time: " + (-executionTime));
				sleepTime = (long) Math.max (0, REFRESHINTERVAL / 0.1 + executionTime);
			}
			else sleepTime = 100;

			try
			{
				Thread.sleep (sleepTime);
			}
			catch (InterruptedException e)
			{
				System.err.println ("Could not perfrom action: Thread.sleep(...) in the game class");
				e.printStackTrace ();
			}
		}
	}
	
	private void removeDestroyedObjects ()
	{
		Collection <Attack> newarr = new ArrayList <> ();
		for (Attack a : this.attacks)
		{
			if (!a.isDestroyed ())
			{
				newarr.add (a);
			}
		}
		this.attacks = newarr;
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
		this.removeDestroyedObjects();
		for(Player p : players) {
			p.nextStep();
		}
		for(Attack a : attacks) {
			a.nextStep();
		}
		this.setChanged ();
		this.notifyObservers ();
	}

	private void addPlayer(Player p1) {
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
}