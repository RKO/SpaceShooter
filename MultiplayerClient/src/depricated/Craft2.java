package depricated;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;



public class Craft2 implements UnitInterface {
	public static final String TYPE_FRIENDLY = "PL";
	public static final String TYPE_ENEMY = "EC";
	public static final String IMAGE = "/sprites/ship.png";
	public static final String IMAGE_ENEMY = "/sprites/ship_enemy.png";
	private String name;
	private String type;
	public static int width = 0;
	public static int height = 0;
	private int x;
	private int y;
	private static Image image;
	private static Image image_enemy;
	private boolean alive = true;;
	private int hp = 0;
	private int shield = 0;
	private int maxShield = 0;

	public Craft2(String name, String type) {
		this.name = name;
		this.type = type;
		x = 0;
		y = 0;

		if(image == null) {
			ImageIcon ii = new ImageIcon(this.getClass().getResource(IMAGE));
			if(type.equals(TYPE_FRIENDLY)) {
				width = ii.getIconWidth();
				height = ii.getIconHeight();
			}
			image = ii.getImage();
			ii = null;
		}
		if(image_enemy == null) {
			ImageIcon ii = new ImageIcon(this.getClass().getResource(IMAGE_ENEMY));
			if(type.equals(TYPE_ENEMY)) {
				width = ii.getIconWidth();
				height = ii.getIconHeight();
			}
			image_enemy = ii.getImage();
			ii = null;
		}
	}

	/* (non-Javadoc)
	 * @see Unit#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see Unit#getWidth()
	 */
	@Override
	public int getWidth() {
		return width;
	}
	/* (non-Javadoc)
	 * @see Unit#getHeight()
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/* (non-Javadoc)
	 * @see Unit#setX(int)
	 */
	@Override
	public void setX(int x) {
		this.x = x;
	}
	/* (non-Javadoc)
	 * @see Unit#getX()
	 */
	@Override
	public int getX() {
		return x;
	}
	/* (non-Javadoc)
	 * @see Unit#setY(int)
	 */
	@Override
	public void setY(int y) {
		this.y = y;
	}
	/* (non-Javadoc)
	 * @see Unit#getY()
	 */
	@Override
	public int getY() {
		return y;
	}

	/* (non-Javadoc)
	 * @see Unit#getImage()
	 */
	@Override
	public Image getImage() {
		if(type.equals(TYPE_FRIENDLY)) {
			return image;
		}
		else {
			return image_enemy;
		}
	}

	/* (non-Javadoc)
	 * @see Unit#getBounds()
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	public String getType() {
		return type;
	}
	
	public void setAliveState(boolean alive) {
		this.alive = alive;
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

	public void setHp(int hp) {
		this.hp = hp;
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

	@Override
	public boolean isFriendly() {
		return type.equals(TYPE_FRIENDLY);
	}

	@Override
	public void setIsFriendly(boolean friendly) {
		
	}
}
