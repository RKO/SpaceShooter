package depricated;

import java.awt.Rectangle;

import main.Game;

public interface ProjectileInterface {
	public abstract int getX();
	public abstract int getY();
	public abstract void move(Game game);
	public abstract Boolean isAlive();
	public abstract void kill();
	public abstract void setFriendly(boolean friendly);
	public abstract boolean isFriendly();
	public abstract String getId();
	public abstract Rectangle getBounds();
	public abstract int getDamage();
	public abstract void setSpeed(int speed);
	public String getType();

}