package gameModel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

import gameGui.GameFrame;

public class Player extends GameObject {

	private Game game;
	
	// Indicates whether a walk button is pressed
	private boolean up;
	private boolean right;
	private boolean left;
	private boolean down;
	
	// Indicates if/where the player is attacking
	private String attackDirection;
	private boolean isAttacking;
	
	// Player info
	private int score;
	private String name;

	// Player stats
	private double acceleration;
	private double decelleration;
	private double maxSpeed;
	private String weapon;
	private int health;
	private int attackStrength;
	private int attackCooldown;
	
	// Constructor
	public Player (Point point, String name) {
		this (point, 0, 0, 22, name);
	}

	private Player (Point location, double velocityX, double velocityY, int radius, String name)
	{
		super (location, velocityX, velocityY, radius, Color.red);
		this.init();
		this.name = name;
	}
	
	public void init() {
		this.resurrect();
		this.up = false;
		this.left = false;
		this.right = false;
		this.down = false;
		this.attackDirection = "";
		
		this.isAttacking = false;
		this.attackCooldown = 0;
		this.attackStrength = 0;
		this.weapon = "lightningbolt";
		this.score = 0;
		this.health = 100;
		
		this.acceleration = 1.0*Game.REFRESHINTERVAL;			// Multiplier
		this.decelleration = 0.5 / Game.REFRESHINTERVAL;		// Multiplier
		this.maxSpeed = 11 * Game.REFRESHINTERVAL;	
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
	public void resurrect() {
		this.destroyed = false;
	}
	public void addScore(int s) {
		this.score += s;
	}
	public void addHealth(int h) {
		health += h;
		if(health <= 0) {
			health = 0;
			this.destroy();
		}
	}
	// Getters HUD elements
	public int getScore() {
		return this.score;
	}
	public String getName() {
		return this.name;
	}
	public int getCooldown() {
		return this.attackCooldown;
	}
	public int getHealth() {
		return this.health;
	}
	
	// Methods
	@Override 
	public void nextStep () 
	{	
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
		this.locationX = Math.max(30, Math.min(GameFrame.FRAME_WIDTH-45, this.locationX + this.velocityX));
		this.locationY = Math.max(30, Math.min(GameFrame.FRAME_HEIGHT-90, this.locationY + this.velocityY));

		// Decrease speed due to traction.
		this.velocityX *= decelleration;
		this.velocityY *= decelleration;

		// -- ATTACK ----------------
		// Decrease firing cooldown counter.
		if (this.attackCooldown > 0) {
			this.attackCooldown--;
		}
		
		// Execute attack
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
		if(this.destroyed == true) return;
		g.setColor(this.color);
		Ellipse2D.Double e = new Ellipse2D.Double ();
		e.setFrame (this.locationX - this.radius, this.locationY - this.radius, 2 * this.radius, 2 * this.radius);
		g.fill (e);
	}
	
	private void attack() {
		double velX, velY;
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
					game.addAttack(new LightningBolt(this, new Point((int)this.locationX, (int) this.locationY), velX, velY, 0));
					this.attackStrength = 0;
					this.attackDirection = "";
					this.attackCooldown = 100/Game.REFRESHINTERVAL;
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
					game.addAttack(new Fireball(this, new Point((int)this.locationX, (int)this.locationY), velX, velY, 0));
					this.attackStrength = 0;
					this.attackDirection = "";
					this.attackCooldown = 5*Game.REFRESHINTERVAL;
					break;
				}
				break;
			case "shotgun":
				double velX1, velY1, velX2, velY2;
				double spread = 1.5;
				switch(this.attackDirection) {
				case "up":
					velX = 0;
					velY = -3 * Game.REFRESHINTERVAL;
					velX1 = spread;
					velY1 = 0.9*velY;
					velX2 = -spread;
					velY2 = 0.9*velY;
					break;
				case "down":
					velX = 0;
					velY = 3 * Game.REFRESHINTERVAL;
					velX1 = spread;
					velY1 = 0.9*velY;
					velX2 = -spread;
					velY2 = 0.9*velY;
					break;
				case "right":
					velX = 3 * Game.REFRESHINTERVAL;
					velY = 0;
					velX1 = 0.9*velX;
					velY1 = spread;
					velX2 = 0.9*velX;
					velY2 = -spread;
					break;
				case "left":
					velX = -3 * Game.REFRESHINTERVAL;
					velY = 0;
					velX1 = 0.9*velX;
					velY1 = spread;
					velX2 = 0.9*velX;
					velY2 = -spread;
					break;
				default:
					velX = 0;
					velY = 0;
					velX1 = 0;
					velY1 = 0;
					velX2 = 0;
					velY2 = 0;
				}
				if(!(velX == 0 && velY == 0)) {
					game.addAttack(new Bullet(this, new Point((int)this.locationX, (int)this.locationY), velX, velY));
					game.addAttack(new Bullet(this, new Point((int)this.locationX, (int)this.locationY), velX1, velY1));
					game.addAttack(new Bullet(this, new Point((int)this.locationX, (int)this.locationY), velX2, velY2));
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
