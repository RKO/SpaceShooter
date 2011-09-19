package units;

import graphics.ImageHandler;
import graphics.Sprite;
import java.awt.Image;
import java.awt.Rectangle;

public class BaseUnit {
	private boolean movingUp = false;
	private boolean movingDown = false;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private String name;
	private String type;
	private int x, y;
	private int lastX, lastY = 0;
	private int width = 0;
	private int height = 0;
	private String[] images;
	private String baseImage;
	private boolean alive = true;
	private boolean friendly = false;;
	private int maxHp = 0;
	private int hp = 0;
	private int maxShield = 0;
	private int shield = maxShield;
	private int credits = 0;
	private int laserLevel = 1;
	private int missileLevel = 1;
	private int hullLevel = 1;
	private int shieldLevel = 1;
	private int speedLevel = 1;

	public BaseUnit(String name, String type, String[] imageNames) {
		this.name = name;
		this.type = type;
		images = imageNames;
		x = 0;
		y = 0;
		
		baseImage = imageNames[0];
		Sprite sprite = ImageHandler.getSprite(this.getClass(), baseImage);
		width = sprite.getWidth();
		height = sprite.getHeight();
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		if(x > lastX) {
			movingRight = true;
			movingLeft = false;
		}
		else if(x < lastX) {
			movingLeft = true;
			movingRight = false;
		}
		else {
			movingRight = false;
			movingLeft = false;
		}
		this.x = x;
		lastX = x;
	}

	public int getY() {
		return y;
	}
	public void setY(int y) {
		if(y < lastY) {
			movingUp = true;
			movingDown = false;
		}
		else if(y > lastY) {
			movingDown = true;
			movingUp = false;
		}
		else {
			movingUp = false;
			movingDown = false;
		}
		this.y = y;
		lastY = y;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAliveState(boolean alive) {
		this.alive = alive;
	}
	
	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}
	
	public int getMaxHp() {
		return maxHp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	
	public double getHpStrength() {
		double result = (double)((double)hp / (double)maxHp) * 100;
		return result;
	}

	public int getHp() {
		return hp;
	}

	public void setShield(int shield) {
		this.shield = shield;
	}

	public int getShield() {
		return shield;
	}

	public double getShieldStrength() {
		double result = (double)((double)shield / (double)maxShield) * 100;
		return result;
	}

	public void setMaxShield(int maxShield) {
		this.maxShield = maxShield;
	}

	public Image getImage() {
		if(images.length == 1) {
			return ImageHandler.getSprite(this.getClass(), baseImage).getImage();
		}
		else {
			if(movingLeft && !movingUp) {
				movingLeft = false;
				return ImageHandler.getSprite(this.getClass(), images[1]).getImage();
			}
			if(movingRight && !movingUp) {
				movingRight = false;
				return ImageHandler.getSprite(this.getClass(), images[2]).getImage();
			}
			if(movingUp && !movingRight && !movingLeft) {
				movingUp = false;
				return ImageHandler.getSprite(this.getClass(), images[3]).getImage();
			}
			if(movingLeft && movingUp) {
				movingLeft = false;
				return ImageHandler.getSprite(this.getClass(), images[4]).getImage();
			}
			if(movingRight && movingUp) {
				movingRight = false;
				return ImageHandler.getSprite(this.getClass(), images[5]).getImage();
			}
			if(movingDown) {
				movingDown = false;
				return ImageHandler.getSprite(this.getClass(), images[0]).getImage();
			}
			return ImageHandler.getSprite(this.getClass(), images[0]).getImage();
		}
	}

	public boolean isFriendly() {
		return friendly;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public void setIsFriendly(boolean friendly) {
		this.friendly = friendly;
	}

	public void setLaserLevel(int laserLevel) {
		this.laserLevel = laserLevel;
	}

	public int getLaserLevel() {
		return laserLevel;
	}

	public void setMissileLevel(int missileLevel) {
		this.missileLevel = missileLevel;
	}

	public int getMissileLevel() {
		return missileLevel;
	}

	public void setHullLevel(int hullLevel) {
		this.hullLevel = hullLevel;
	}

	public int getHullLevel() {
		return hullLevel;
	}

	public void setShieldLevel(int shieldLevel) {
		this.shieldLevel = shieldLevel;
	}

	public int getShieldLevel() {
		return shieldLevel;
	}

	public void setSpeedLevel(int speedLevel) {
		this.speedLevel = speedLevel;
	}

	public int getSpeedLevel() {
		return speedLevel;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public int getCredits() {
		return credits;
	}
}
