package gameModel;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class Game extends Observable implements Runnable {

	public static int REFRESHINTERVAL = 3;
	
	private static Random rng;
	private ArrayList<Player> players;
	private boolean aborted;
	private int tickCounter;
	private boolean gameOver;
	private boolean paused;
	
	// Constructors
	public Game() {
		this.rng = new Random ();
		this.players = new ArrayList<>();
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
				System.out.println("Time: " + (-executionTime));
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
		for(Player p : players) {
			p.nextStep();
		}
		this.setChanged ();
		this.notifyObservers ();
	}

	private void addPlayer(Player p1) {
		players.add(p1);
	}
	
	// Getters and Setters
	public ArrayList<Player> getPlayers() {
		return this.players;
	}
}