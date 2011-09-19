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
	private int laserLevel = 1;
	private int missileLevel = 1;
	private int hullLevel = 1;
	private int shieldLevel = 1;
	private int speedLevel = 1;
	private int credits = 0;
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
		setMaxShield(15);
		setDamage(1);
		setSpeed(BASE_SPEED);
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
		int tempY = y+getSpeed();
		if(rect.contains(x, tempY+height) && alive) {
			y = tempY;
			hasChanged = true;
		}
	}
	public void moveRight(Rectangle rect) {
		int tempX = x+getSpeed();
		if(rect.contains(tempX+width, y) && alive) {
			x = tempX;
			hasChanged = true;
		}
	}
	public void moveUp(Rectangle rect) {
		int tempY = y-getSpeed();
		if(rect.contains(x, tempY) && alive) {
			y = tempY;
			hasChanged = true;
		}
	}
	public void moveLeft(Rectangle rect) {
		int tempX = x-getSpeed();
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
			Rectangle missileTarget = new Rectangle(x, 0, width, Game.height);
			//if(fireLeft) {
			Missile missile = new Missile("missile"+random.nextInt(), x, y+WEAPON_OFFSET_Y, true, true, missileTarget, missileLevel);
			Game.projectiles.add(missile);
			//fireLeft = false;
			//}
			//else {
			Missile missile1 = new Missile("missile"+random.nextInt(), x+width, y+WEAPON_OFFSET_Y, true, false, missileTarget, missileLevel);
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

	public int getCredits() {
		return credits;
	}

	@Override
	public String getPackageInfo() {
		System.out.println("GetPackageInfo!");
		return super.getPackageInfo()+":"+getCredits()+":"+laserLevel+":"+missileLevel+":"+hullLevel+":"+shieldLevel+":"+speedLevel;
	}

	public void upgrade(int upgrade) {
		System.out.println("Upgrade "+upgrade);
		switch (upgrade) {
		case CommunicationProtocol.UPGRADE_LASER:
			if(laserLevel < 10) {
				laserLevel++;
				double newDamage = (double)((double)laserLevel/(double)2);
				setDamage((int)(1+newDamage));
				hasChanged = true;
			}
			break;
		case CommunicationProtocol.UPGRADE_MISSILE:
			if(missileLevel < 10) {
				missileLevel++;
				hasChanged = true;
			}
			break;
		case CommunicationProtocol.UPGRADE_HULL:
			if(hullLevel < 10) {
				hullLevel++;
				increaseMaxHp(1);
				hasChanged = true;
			}
			break;
		case CommunicationProtocol.UPGRADE_SHIELD:
			if(shieldLevel < 10) {
				shieldLevel++;
				increaseMaxShield(1);
				hasChanged = true;
			}
			break;
		case CommunicationProtocol.UPGRADE_SPEED:
			if(speedLevel < 10) {
				speedLevel++;
				double newSpeed = (double)BASE_SPEED + (double)((double)speedLevel/3.0);
				setSpeed((int)newSpeed);
				hasChanged = true;
			}
			break;
		}
	}

	@Override
	public int compareTo(CraftInterface o) {
		Integer thisY = getY();
		return thisY.compareTo(o.getY());
	}
}
