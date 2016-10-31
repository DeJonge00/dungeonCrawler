package gameModel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

public class Player extends GameObject {

	private Game game;
	// Amount of game ticks left, until the player can attack again
	private int attackCooldown;
	
	// Time attackbutton has been pressed
	private int attackStrength;

	// Indicates whether the fire button is pressed
	private boolean isAttacking;

	// Indicates whether a walk button is pressed
	private boolean up;
	private boolean right;
	private boolean left;
	private boolean down;
	
	// Indicates where the player is attacking
	private String attackDirection;
	
	// Player info
	private int score;

	// Player stats
	private int health;
	private double acceleration;
	private double decelleration;
	private double maxSpeed;
	private String weapon;
	
	// Constructor
	public Player (Point point, int radius)
	{
		this (point, 0, 0, 25);
	}

	private Player (Point location, double velocityX, double velocityY, int radius)
	{
		super (location, velocityX, velocityY, radius, Color.red);
		this.up = false;
		this.left = false;
		this.right = false;
		this.down = false;
		this.attackDirection = "";
		
		this.health = 100;
		this.isAttacking = false;
		this.attackCooldown = 0;
		this.attackStrength = 0;
		this.weapon = "lightningbolt";
		this.score = 0;
		
		this.acceleration = Game.REFRESHINTERVAL;			// Multiplier
		this.decelleration = 0.5 / Game.REFRESHINTERVAL;		// Multiplier
		this.maxSpeed = 10 * Game.REFRESHINTERVAL;			
	}

	// Getters and Setters
	public void setGame(Game g) {
		this.game = g;
	}
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
	public void setNoAttack() {
		this.setAttacking(false);
	}
	public void setUpAttack() {
		this.setAttacking(true);
		this.attackDirection = "up";
	}
	public void setDownAttack() {
		this.setAttacking(true);
		this.attackDirection = "down";
	}
	public void setLeftAttack() {
		this.setAttacking(true);
		this.attackDirection = "left";
	}
	public void setRightAttack() {
		this.setAttacking(true);
		this.attackDirection = "right";
	}
	private void setAttacking(boolean b) {
		this.isAttacking = b;
	}
	public void setWeapon(String s) {
		this.weapon = s;
	}
	
	// Methods
	@Override 
	public void nextStep () 
	{
		this.stepsTilCollide = Math.max (0, this.stepsTilCollide - 1);
		
		// -- MOVEMENT --------------
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

		// -- ATTACK ----------------
		// Decrease firing cooldown counter.
		if (this.attackCooldown > 0) {
			this.attackCooldown--;
		}
		
		if(this.isAttacking) {
			this.attackStrength++;
		} else {
			if(this.attackStrength > 0 && this.attackCooldown <= 0) {
				this.attack();
			} else {
				this.attackStrength = 0;
				this.attackDirection = "";
			}
		}
	}
	
	public void paint(Graphics2D g) {
		g.setColor(this.color);
		Ellipse2D.Double e = new Ellipse2D.Double ();
		e.setFrame (this.locationX - this.radius, this.locationY - this.radius, 2 * this.radius, 2 * this.radius);
		g.fill (e);
	}
	
	private void attack() {
		int velX, velY;
		switch (this.weapon) {
			case "lightningbolt":
				switch(this.attackDirection) {
				case "up":
					velX = 0;
					velY = -5 * Game.REFRESHINTERVAL;
					break;
				case "down":
					velX = 0;
					velY = 5 * Game.REFRESHINTERVAL;
					break;
				case "right":
					velX = 5 * Game.REFRESHINTERVAL;
					velY = 0;
					break;
				case "left":
					velX = -5 * Game.REFRESHINTERVAL;
					velY = 0;
					break;
				default:
					velX = 0;
					velY = 0;
				}
				if(!(velX == 0 && velY == 0)) {
					game.addAttack(new LightningBolt(new Point((int)this.locationX, (int) this.locationY), velX, velY, 0, false));
					this.attackStrength = 0;
					this.attackDirection = "";
					this.attackCooldown = 5*Game.REFRESHINTERVAL;
					break;
				}
				break;
			case "fireball":
				switch(this.attackDirection) {
				case "up":
					velX = 0;
					velY = -3 * Game.REFRESHINTERVAL;
					break;
				case "down":
					velX = 0;
					velY = 3 * Game.REFRESHINTERVAL;
					break;
				case "right":
					velX = 3 * Game.REFRESHINTERVAL;
					velY = 0;
					break;
				case "left":
					velX = -3 * Game.REFRESHINTERVAL;
					velY = 0;
					break;
				default:
					velX = 0;
					velY = 0;
				}
				if(!(velX == 0 && velY == 0)) {
					game.addAttack(new Fireball(new Point((int)this.locationX, (int)this.locationY), velX, velY, 0, false));
					this.attackStrength = 0;
					this.attackDirection = "";
					this.attackCooldown = 5*Game.REFRESHINTERVAL;
					break;
				}
				break;
			default:
				System.out.println("No weapon equipped");
				this.attackStrength = 0;
				this.attackDirection = "";
		}
	}
}
