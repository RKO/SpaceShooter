package units;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Date;

import depricated.CraftInterface;

public class Craft {
	public static final String TYPE_ENEMY1 = "EC1";
	public static final String TYPE_ENEMY2 = "EC2";
	public static final String TYPE_ENEMY_WASP = "EC3";
	public static final String TYPE_FRIENDLY = "PL1";
	public static int STANDARD_WIDTH = 64;
	public static int STANDARD_Height = 64;
	protected int width = 64;
	protected int height = 64;
	protected String type;
	protected final int SHIELD_DELAY = 300;
	private int speed = 1;
	protected String name;
	protected int x;
	protected int y;
	protected Date lastFired;
	protected Point destination;
	protected Boolean alive = true;
	private int maxShield = 0;
	private int maxHp = 1;
	private int hp;
	private double shield = maxShield;
	protected int fireDelayTime = 300;
	private int shieldDownTime = 0;
	private int damage = 1;

	public Craft(int x, int y, String type) {
		this.x = x;
		this.y = y;
		this.type = type;
		lastFired = new Date();
		destination = new Point(x,y);
		hp = maxHp;
	}
	
	public void tick() {
		if(shield < maxShield && shieldDownTime == 0) {
			shield = shield + 0.01;
		}
		else if(shieldDownTime > 0) {
			shieldDownTime--;
		}
	}

	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}

	public String getType() {
		return type;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public void kill() {
		this.alive = false;
	}

	public boolean isAlive() {
		return alive;
	}
	
	public void dealDamage(int damage) {
		int restDamage = damage;
		if(shield > 0) {
			restDamage = (int) (damage - shield);
			shield = shield - damage;
		}
		if(restDamage > 0) {
			hp = hp - restDamage;
			shield = 0;
		}
		if(hp <= 0) {
			kill();
		}
		else if(shield == 0) {
			shieldDownTime = SHIELD_DELAY;
		}
	}
	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
		hp = maxHp;
	}
	
	public int getMaxHp() {
		return maxHp;
	}
	public void increaseMaxHp(int inc) {
		maxHp = maxHp + inc;
		hp = hp + inc;
	}
	
	public int getHp() {
		return hp;
	}
	
	public void setMaxShield(int maxShield) {
		this.maxShield = maxShield;
		shield = maxShield;
	}
	
	public void increaseMaxShield(int inc) {
		maxShield = maxShield + inc;
	}
	
	public int getShield() {
		return (int) shield;
	}
	
	public int getMaxShield() {
		return maxShield;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public String getPackageInfo() {
		return getName()+":"+getType()+":"+getX()+":"+getY()+":"+isAlive()+":"+getHp()+":"+getMaxHp()+":"+getShield()+":"+getMaxShield();
	}

	public int compareTo(CraftInterface o) {
		Integer thisY = getY();
		return thisY.compareTo(o.getY());
	}
}
