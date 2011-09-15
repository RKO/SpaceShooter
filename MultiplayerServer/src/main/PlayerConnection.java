package main;

import java.awt.Rectangle;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Random;

import depricated.CraftInterface;

import units.Craft;
import units.Laser;
import units.Missile;
import units.Projectile;

public class PlayerConnection extends Craft {
	private final int WEAPON_OFFSET_X_LEFT = 7;
	private final int WEAPON_OFFSET_X_RIGHT = 11;
	private final int WEAPON_OFFSET_Y = 30;
	private final int FIREMODE_SPRAY = 0;
	private static final int FIREMODE_BURST = 1;
	private InetAddress inetAddress;
	private Socket socket;
	private Random random;
	private Date lastFire;
	private Date lastFireSpecial;
	private boolean hasChanged = true;
	private int loops = 0;
	private int specialFireLoops = 0;
	private boolean fireLeft = true;
	private int fireMode = 0;
	private final int BASE_SPEED = 3;
	private int fireDelayLoops = 10;
	private int fireDelayTime = fireDelayLoops*10;
	private int specialFireDelayLoops = 20;
	private int specialFireDelayTime = 3000;
	
	public PlayerConnection(Socket socket, int x, int y) {
		super(x, y, Craft.TYPE_FRIENDLY);
		this.socket = socket;
		random = new Random();
		lastFire = new Date();
		lastFireSpecial = new Date();
		setMaxHp(10);
		//maxHp = 10;
		//hp = 10;
		setMaxShield(15);
		//maxShield = 15;
		//shield = maxShield;
		setDamage(1);
		//damage = 1;
	}

	public void tick() {
		super.tick();
		loops++;
		specialFireLoops++;
	}

	public void setInetAddress(InetAddress netAddress) {
		this.inetAddress = netAddress;
	}

	public InetAddress getInetAddress() {
		return inetAddress;
	}

	public void setName(String name) {
		this.name = name;
		hasChanged = true;
	}

	public Socket getSocket(){
		return socket;
	}
	
	public void moveDown(Rectangle rect) {
		int tempY = y+BASE_SPEED;
		if(rect.contains(x, tempY+height) && alive) {
			y = tempY;
			hasChanged = true;
		}
	}
	public void moveRight(Rectangle rect) {
		int tempX = x+BASE_SPEED;
		if(rect.contains(tempX+width, y) && alive) {
			x = tempX;
			hasChanged = true;
		}
	}
	public void moveUp(Rectangle rect) {
		int tempY = y-BASE_SPEED;
		if(rect.contains(x, tempY) && alive) {
			y = tempY;
			hasChanged = true;
		}
	}
	public void moveLeft(Rectangle rect) {
		int tempX = x-BASE_SPEED;
		if(rect.contains(tempX, y) && alive) {
			x = tempX;
			hasChanged = true;
		}
	}

	public void fireLaser() {
		if(fireMode == FIREMODE_SPRAY) {
			fireSpray();
		}
		else if(fireMode == FIREMODE_BURST) {
			fireBurst();
		}
	}

	public void fireSpray() {
		Date now = new Date();
		long difference = now.getTime() - lastFire.getTime();
		if(difference > fireDelayTime && alive && loops >= fireDelayLoops) {
			if(fireLeft) {
				Laser right = new Laser("laser_right"+random.nextInt(), x+width-WEAPON_OFFSET_X_RIGHT, y+WEAPON_OFFSET_Y, true, getDamage(), Projectile.TYPE_LASER_RED);
				Game.projectiles.add(right);
				fireLeft = false;
			}
			else {
				Laser left = new Laser("laser_left"+random.nextInt(), x+WEAPON_OFFSET_X_LEFT, y+WEAPON_OFFSET_Y, true, getDamage(), Projectile.TYPE_LASER_RED);
				Game.projectiles.add(left);
				fireLeft = true;
			}
			lastFire = now;
			hasChanged = true;
			//System.out.println("Loops: "+loops);
			loops = 0;
		}
	}

	public void fireBurst() {
		Date now = new Date();
		long difference = now.getTime() - lastFire.getTime();
		if(difference > (fireDelayTime*4) && alive && loops >= (fireDelayLoops*4)) {
			Laser right1 = new Laser("laser_right"+random.nextInt(), x+width-WEAPON_OFFSET_X_RIGHT, y+WEAPON_OFFSET_Y, true, getDamage(), Projectile.TYPE_LASER_RED);
			right1.setSpeed(15);
			Game.projectiles.add(right1);

			Laser left1 = new Laser("laser_left"+random.nextInt(), x+WEAPON_OFFSET_X_LEFT, y+WEAPON_OFFSET_Y, true, getDamage(), Projectile.TYPE_LASER_RED);
			left1.setSpeed(15);
			Game.projectiles.add(left1);

			Laser right2 = new Laser("laser_right"+random.nextInt(), x+width-WEAPON_OFFSET_X_RIGHT, y+WEAPON_OFFSET_Y+10, true, getDamage(), Projectile.TYPE_LASER_RED);
			right2.setSpeed(15);
			Game.projectiles.add(right2);

			Laser left2 = new Laser("laser_left"+random.nextInt(), x+WEAPON_OFFSET_X_LEFT, y+WEAPON_OFFSET_Y+10, true, getDamage(), Projectile.TYPE_LASER_RED);
			left2.setSpeed(15);
			Game.projectiles.add(left2);

			lastFire = now;
			hasChanged = true;
			System.out.println("Loops: "+loops);
			loops = 0;
		}
	}

	public void setFiremode() {
		if(fireMode == FIREMODE_BURST) {
			fireMode = FIREMODE_SPRAY;
		}
		else {
			this.fireMode = FIREMODE_BURST;
		}
	}

	public void fireSpecial() {
		Date now = new Date();
		long difference = now.getTime() - lastFireSpecial.getTime();
		if(difference > specialFireDelayTime && alive && specialFireLoops >= specialFireDelayLoops) {
			/*Missile missile = new Missile("missile"+random.nextInt(), x+(width/2), y+WEAPON_OFFSET_Y, true);
			Game.projectiles.add(missile);*/
			System.out.println("x"+x);
			System.out.println("y"+y);
			System.out.println("width"+width);
			System.out.println("Game.height"+Game.height);
			Rectangle missileTarget = new Rectangle(x, 0, width, Game.height);
			//if(fireLeft) {
				Missile missile = new Missile("missile"+random.nextInt(), x, y+WEAPON_OFFSET_Y, true, true, missileTarget);
				Game.projectiles.add(missile);
				//fireLeft = false;
			//}
			//else {
				Missile missile1 = new Missile("missile"+random.nextInt(), x+width, y+WEAPON_OFFSET_Y, true, false, missileTarget);
				Game.projectiles.add(missile1);
				//fireLeft = true;
			//}
			lastFireSpecial = now;
			specialFireLoops = 0;
			
		}
	}

	public void dealDamage(int damage) {
		super.dealDamage(damage);
		hasChanged = true;
	}

	public boolean hasChanged() {
		return hasChanged;
	}

	public void resetChanged() {
		hasChanged = false;
	}

	@Override
	public int compareTo(CraftInterface o) {
		Integer thisY = getY();
		return thisY.compareTo(o.getY());
	}
}
