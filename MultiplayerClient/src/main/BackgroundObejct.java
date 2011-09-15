package main;

import java.awt.Shape;

public interface BackgroundObejct {
	public abstract int getX();
	public abstract int getY();
	public abstract void move();
	public abstract Shape getBounds();
	public abstract void setSpeed(int speed);

}