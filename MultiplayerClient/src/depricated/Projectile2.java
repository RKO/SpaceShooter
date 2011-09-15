package depricated;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;



public class Projectile2 implements UnitInterface {
	public static final String TYPE_LASER = "laser";
	public static final String TYPE_MISSILE = "missile";
	public static final String IMAGE_RED = "/sprites/laser_red.png";
	public static final String IMAGE_GREEN = "/sprites/laser_green.png";
	public static final String IMAGE_MISSILE = "/sprites/missile.png";
	private String name;
	private String type;
	private int x;
	private int y;
	private int width = 4;
	private int height = 15;
	private boolean friendly;
	private boolean alive;
	private static Image image_red;
	private static Image image_green;
	private static Image image_missile;

	public Projectile2(String name, String type, int x, int y, boolean friendly, boolean alive) {
		this.name = name;
		this.type = type;
		this.x = x;
		this.y = y;
		this.friendly = friendly;
		this.alive = alive;

		if(type.equals(TYPE_LASER)) {
			if(image_red == null) {
				ImageIcon ii = new ImageIcon(this.getClass().getResource(IMAGE_RED));
				//width = ii.getIconWidth();
				//height = ii.getIconHeight();
				image_red = ii.getImage();
				ii = null;
			}
			if(image_green == null) {
				ImageIcon ii = new ImageIcon(this.getClass().getResource(IMAGE_GREEN));
				//width = ii.getIconWidth();
				//height = ii.getIconHeight();
				image_green = ii.getImage();
				ii = null;
			}
		}
		else if(image_missile == null && type.equals(TYPE_MISSILE)) {
			ImageIcon ii = new ImageIcon(this.getClass().getResource(IMAGE_MISSILE));
			width = ii.getIconWidth();
			height = ii.getIconHeight();
			image_missile = ii.getImage();
			ii = null;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setFriendly(boolean friendly) {
		this.friendly = friendly;
	}

	public boolean isFriendly() {
		return friendly;
	}

	public String getName() {
		return name;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Image getImage() {
		if(type.equals(TYPE_LASER)) {
			if(friendly) {
				return image_red;
			}
			else {
				return image_green;
			}
		}
		else {
			return image_missile;
		}
	}

	public boolean isAlive() {
		return alive;
	}

	@Override
	public int getHp() {
		return 0;
	}

	@Override
	public int getShield() {
		return 0;
	}

	@Override
	public double getShieldStrength() {
		return 0;
	}

	@Override
	public void setIsFriendly(boolean friendly) {
		this.friendly = friendly;
	}
}
