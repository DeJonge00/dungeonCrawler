package gameModel;

import java.awt.Point;
import java.io.Serializable;

public abstract class GameObject implements Serializable {
	// Location
	protected double locationX;
	protected double locationY;

	// Velocity
	protected double velocityX;
	protected double velocityY;

	// Radius of the object
	protected int radius;

	// Holds true if object collided with another object, false otherwise
	protected boolean destroyed;
	
	// Counts the amount of game ticks left, until this object is allowed to collide
	protected int stepsTilCollide;
	
	// Constructor
	protected GameObject (Point location, double velocityX, double velocityY, int radius)
	{
		this.locationX = location.x;
		this.locationY = location.y;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.radius = radius;
		this.stepsTilCollide = 3;
	}
	
	// Getters and Setters
	public Point getLocation() {
		return new Point((int)this.locationX, (int)this.locationY);
	}

	public int getRadius() {
		return radius;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	// Methods
	// Subclasses need to specify their own behaviour
	abstract public void nextStep ();
	
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
		
		return distance < this.getRadius() + other.getRadius() && this.stepsTilCollide () == 0 && other.stepsTilCollide () == 0;
	}
	
	public int stepsTilCollide ()
	{
		return this.stepsTilCollide;
	}
}
