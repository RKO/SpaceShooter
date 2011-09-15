package units;

import graphics.ImageHandler;

public class Projectile extends BaseUnit {
	public static final String TYPE_LASER_RED = "laserR";
	public static final String TYPE_LASER_GREEN = "laserG";
	public static final String TYPE_MISSILE = "missile";

	public Projectile(String name, String type) {
		super(name, type, getTypeImage(type));
	}
	
	public static String[] getTypeImage(String type) {
		String[] image;
		if(type.equals(TYPE_LASER_RED)) {
			image = new String[]{ImageHandler.IMAGE_LASER_RED};
			//image[0] = IMAGE_RED;
		}
		else if(type.equals(TYPE_LASER_GREEN)) {
			image = new String[]{ImageHandler.IMAGE_LASER_GREEN};
			//image[0] = IMAGE_GREEN;
		}
		else {
			image = new String[]{ImageHandler.IMAGE_MISSILE};
			//image[0] = IMAGE_MISSILE;
		}
		return image;
	}
}
