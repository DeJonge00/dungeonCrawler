package gameModel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;

public abstract class GameObject implements Serializable {
	private static final long serialVersionUID = -3202387986473646380L;
	// Location
	protected double locationX;
	protected double locationY;

	// Velocity
	protected double velocityX;
	protected double velocityY;

	// Radius of the object
	protected double radius;
	
	// Color of the gameobject
	protected Color color;

	// Holds true if object collided with another object, false otherwise
	protected boolean destroyed;
	
	// Constructor
	protected GameObject (Point location, double velocityX, double velocityY, double radius, Color c)
	{
		this.locationX = location.x;
		this.locationY = location.y;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.radius = radius;
		this.color = c;
	}
	
	// Getters and Setters
	public Point getLocation() {
		return new Point((int)this.locationX, (int)this.locationY);
	}

	public double getRadius() {
		return radius;
	}

	public boolean isDestroyed() {
		return destroyed;
	}
	
	public Color getColor() {
		return this.color;
	}

	// Methods
	// Subclasses need to specify their own behaviour
	abstract public void nextStep ();
	abstract public void paint(Graphics2D g);
	
	// Destroys object
	public void destroy ()
	{
		this.destroyed = true;
	}
	
	public boolean collides (GameObject other) 
	{
		double distX = this.locationX - other.getLocation ().x;
		double distY = this.locationY - other.getLocation ().y;
		double distance = Math.sqrt(distX * distX + distY * distY);
		
		return distance < this.getRadius() + other.getRadius();
	}
}
