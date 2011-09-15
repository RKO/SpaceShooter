package depricated;
import java.awt.Image;
import java.awt.Rectangle;

public interface UnitInterface {

	public abstract String getName();
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract void setX(int x);
	public abstract int getX();
	public abstract void setY(int y);
	public abstract int getY();
	public abstract Image getImage();
	public abstract Rectangle getBounds();
	public abstract boolean isAlive();
	public abstract int getHp();
	public abstract int getShield();
	public abstract double getShieldStrength();
	public abstract boolean isFriendly();
	public abstract void setIsFriendly(boolean friendly);
}