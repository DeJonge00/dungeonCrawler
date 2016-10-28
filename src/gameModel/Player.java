package gameModel;

import java.awt.Color;
import java.awt.Point;

public class Player extends GameObject {

	// Amount of game ticks left, until the player can attack again
	private int attackCooldown;

	// Indicates whether the fire button is pressed
	private boolean isAttacking;

	// Indicates whether a walk button is pressed
	private boolean up;
	private boolean right;
	private boolean left;
	private boolean down;
	
	// Player info
	private Color color;
	private int score;

	// Player stats
	private double acceleration;
	private double decelleration;
	private double maxSpeed;
	
	// Constructor
	public Player (Point point, int radius)
	{
		this (point, 0, 0, 15);
	}

	private Player (Point location, double velocityX, double velocityY, int radius)
	{
		super (location, velocityX, velocityY, radius);
		this.up = false;
		this.left = false;
		this.right = false;
		this.down = false;
		
		this.isAttacking = false;
		this.attackCooldown = 0;
		this.score = 0;
		this.color = Color.red;
		
		this.acceleration = Game.REFRESHINTERVAL;			// Multiplier
		this.decelleration = 0.5 / Game.REFRESHINTERVAL;		// Multiplier
		this.maxSpeed = 10 * Game.REFRESHINTERVAL;			
	}

	// Getters and Setters
	public void setUp(boolean b) {
		this.up = b;
	}
	public void setDown(boolean b) {
		this.down = b;
	}
	public void setLeft(boolean b) {
		this.left = b;
	}
	public void setRight(boolean b) {
		this.right = b;
	}
	public void setAttacking(boolean b) {
		this.isAttacking = b;
	}
	public Color getColor() {
		return this.color;
	}
	
	// Methods
	@Override 
	public void nextStep () 
	{
		this.stepsTilCollide = Math.max (0, this.stepsTilCollide - 1);
		
		if (this.up)
		{ // Update speed if accelerating, but constrain values.
			this.velocityY = Math.max ((-maxSpeed), Math.min (maxSpeed, this.velocityY - acceleration));
		}
		if (this.down)
		{ // Update speed if accelerating, but constrain values.
			this.velocityY = Math.max ((-maxSpeed), Math.min (maxSpeed, this.velocityY + acceleration));
		}
		if (this.left)
		{ // Update speed if accelerating, but constrain values.
			this.velocityX = Math.max ((-maxSpeed), Math.min (maxSpeed, this.velocityX - acceleration));
		}
		if (this.right)
		{ // Update speed if accelerating, but constrain values.
			this.velocityX = Math.max ((-maxSpeed), Math.min (maxSpeed, this.velocityX + acceleration));
		}

		// Update location.
		this.locationX = Math.max(30, Math.min(1235, this.locationX + this.velocityX));
		this.locationY = Math.max(30, Math.min(710, this.locationY + this.velocityY));

		// Decrease speed due to traction.
		this.velocityX *= decelleration;
		this.velocityY *= decelleration;

		// Decrease firing step counter.
		if (this.attackCooldown > 0)
			this.attackCooldown--;
	}
}
