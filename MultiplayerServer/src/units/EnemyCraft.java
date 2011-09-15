package units;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Date;
import java.util.Random;

import main.Game;

public class EnemyCraft extends Craft {
	private Random random;
	protected boolean hasEntered = false;
	
	public EnemyCraft(String name, int x, int y, String type) {
		super(x, y, type);
		this.name = name;
		random = new Random();
		setMaxHp(10);
		//maxHp = 10;
		//hp = 10;
		setMaxShield(0);
		//shield = 0;
		setDamage(2);
		//damage = 2;
	}
	
	public void move() {
		if(destination.x == x && destination.y == y) {
			int newX = random.nextInt(Game.width-width);
			int newY = random.nextInt(Game.height/2);
			destination = new Point(newX, newY);
		}
		else {
			for(int i=0; i<getSpeed();i++) {
				if(x >= destination.x) {
					x = x - 1;
				}
				if(x < destination.x) {
					x = x + 1;
				}
				if(y >= destination.y) {
					y = y - 1;
				}
				if(y < destination.y) {
					y = y + 1;
				}
			}
		}
		
		if(hasEntered == false) {
			Rectangle gameRect = new Rectangle(Game.width, Game.height);
			if(gameRect.contains(this.getBounds())) {
				hasEntered = true;
			}
		}

		Date now = new Date();
		if(now.getTime() - lastFired.getTime() > fireDelayTime) {
			int chance = random.nextInt(100);
			if(chance > 75) {
				fire();
				lastFired = now;
			}
		}
	}
	
	public void fire() {
		Laser left = new Laser("el"+random.nextInt(), x+(width/2)-1, y+height, false, getDamage(), Projectile.TYPE_LASER_GREEN);
		Game.projectiles.add(left);
	}
	
	public void setHasEntered(boolean hasEntered) {
		this.hasEntered = hasEntered;
	}

	public boolean hasEntered() {
		return hasEntered;
	}
}