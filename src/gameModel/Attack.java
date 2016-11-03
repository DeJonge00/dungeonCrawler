package gameModel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

import gameGui.GameFrame;

public abstract class Attack extends GameObject {
	private static final long serialVersionUID = -2894854206285788391L;
	protected int lifetime;
	protected int damage;
	protected boolean hostile;
	private Player shooter;
	private int health;
	
	protected Attack(Player shooter, Point location, double velocityX, double velocityY, double radius, int lifetime, int damage, int health, Color color) {
		super(location, velocityX, velocityY, radius, color);
		this.damage = damage;
		this.lifetime = lifetime;
		this.shooter = shooter;
		this.health = health;
	}
	
	public boolean isHostile() {
		return this.shooter == null;
	}
	
	public Player getShooter() {
		return this.shooter;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public void addHealth(int h) {
		this.health += h;
		if(this.health <= 0) {
			this.destroy();
		}
	}
	
	@Override 
	public void nextStep () 
	{
		this.lifetime--;
		if(lifetime <= 0) this.destroy();

		// Update location.
		this.locationX = (this.locationX + this.velocityX);
		this.locationY = (this.locationY + this.velocityY);
		if(this.locationX < 0 || this.locationX > GameFrame.FRAME_WIDTH || this.locationY < 0 || this.locationY > GameFrame.FRAME_HEIGHT) this.destroy();
	}
	
	public void paint(Graphics2D g) {
		g.setColor(this.color);
		Ellipse2D.Double e = new Ellipse2D.Double ();
		e.setFrame (this.locationX - this.radius, this.locationY- this.radius, 2 * this.radius, 2 * this.radius);
		g.fill (e);
	}
}
