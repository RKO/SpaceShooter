package units;

import graphics.ImageHandler;

public class Craft extends BaseUnit {
	public static final String TYPE_FRIENDLY = "PL1";
	public static final String TYPE_ENEMY1 = "EC1";
	public static final String TYPE_ENEMY2 = "EC2";
	public static final String TYPE_ENEMY_WASP = "EC3";


	public Craft(String name, String type) {
		super(name, type, getTypeImage(type));
	}

	public static String[] getTypeImage(String type) {
		String[] image;
		if(type.equals(Craft.TYPE_FRIENDLY)) {
			image = new String[]{ImageHandler.IMAGE_PLAYER, ImageHandler.IMAGE_PLAYER_LEFT, 
								 ImageHandler.IMAGE_PLAYER_RIGHT, ImageHandler.IMAGE_PLAYER_FORWARD, 
								 ImageHandler.IMAGE_PLAYER_FORWARD_LEFT, ImageHandler.IMAGE_PLAYER_FORWARD_RIGHT};
		}
		else if(type.equals(Craft.TYPE_ENEMY2)) {
			image = new String[]{ImageHandler.IMAGE_ENEMY2};
		}
		else if(type.equals(Craft.TYPE_ENEMY_WASP)) {
			image = new String[]{ImageHandler.IMAGE_ENEMY_WASP};
		}
		else {
			image = new String[]{ImageHandler.IMAGE_ENEMY1};
		}
		return image;
	}
}
