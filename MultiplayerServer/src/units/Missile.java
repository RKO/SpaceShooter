package units;

import java.awt.Point;
import java.awt.Rectangle;
import main.Game;

public class Missile extends Projectile {
	private final int SAFETY_DISTANCE = 10;
	private static int damage = 10;
	private boolean left = true;
	private int distanceTraveled = 0;
	private Rectangle targetArea;
	private int level = 1;
	private Craft enemyTarget;

	public Missile(String name, int x, int y, boolean friendly, boolean left, Rectangle source, int level) {
		super(name, x, y, friendly, damage, Projectile.TYPE_MISSILE);
		this.left = left;
		this.level = level;
		if(this.level == 1) {
			this.targetArea = source;
		}
		else {
			targetArea = new Rectangle(x-(source.width), source.y, source.width, source.height);
		}
	}

	public void move(Game game) {
		int turnSpeed = 1;
		Point target;
		if(enemyTarget == null && level >= 2) {
			enemyTarget = game.findEnemy(targetArea);
		}
		
		if(level >= 2 && enemyTarget != null) {
			target = new Point(enemyTarget.getX(), enemyTarget.getY());
			turnSpeed = 2;
		}
		else {
			target = new Point(targetArea.x + (targetArea.width/2), 0);
		}
		if(distanceTraveled < SAFETY_DISTANCE) {
			if(left) {
				x = x-2;
				y = y + 1;
			}
			else {
				x = x+2;
				y = y + 1;
			}
		}
		else {
			if(target.x > (x+(width/2))) {
				x = x + turnSpeed;
			}
			else if(target.x < (x+(width/2))) {
				x = x - turnSpeed;
			}
			
			y = y-getSpeed();
		}
		distanceTraveled++;
		if(game.getBounds().contains(new Point(x,y)) == false) {
			kill();
		}
	}
}
