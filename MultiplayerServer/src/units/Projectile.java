package units;

import java.awt.Point;
import java.awt.Rectangle;
import main.Game;

public class Projectile {
	public static final String TYPE_LASER_RED = "laserR";
	public static final String TYPE_LASER_GREEN = "laserG";
	public static final String TYPE_MISSILE = "missile";
	protected String name;
	protected String type;
	protected final int width = 4;
	protected final int height = 15;
	private int speed = 12;
	protected int x;
	protected int y;
	protected boolean friendly;
	protected boolean isAlive = true;
	private int damage = 1;
	
	public Projectile(String name, int x, int y, boolean friendly, int damage, String type) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.friendly = friendly;
		this.damage = damage;
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void move(Game game) {
		if(friendly) {
			y = y-speed;
		}
		else {
			y = y+speed;
		}
		if(game.getBounds().contains(new Point(x,y)) == false) {
			isAlive = false;
		}
	}
	
	public Boolean isAlive() {
		return isAlive;
	}
	
	public void kill() {
		isAlive = false;
	}

	public void setFriendly(boolean friendly) {
		this.friendly = friendly;
	}

	public boolean isFriendly() {
		return friendly;
	}


	public String getId() {
		return name;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public int getDamage() {
		return damage;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getSpeed() {
		return speed;
	}

	public String getType() {
		return type;
	}
}
