package graphics;

import java.awt.Image;
import java.util.Hashtable;

import javax.swing.ImageIcon;

public class ImageHandler {
	public static final String IMAGE_PLAYER = "/sprites/ship.png";
	public static final String IMAGE_PLAYER_LEFT = "/sprites/ship_left.png";
	public static final String IMAGE_PLAYER_RIGHT = "/sprites/ship_right.png";
	public static final String IMAGE_PLAYER_FORWARD = "/sprites/ship_forward.png";
	public static final String IMAGE_PLAYER_FORWARD_LEFT = "/sprites/ship_forward_left.png";
	public static final String IMAGE_PLAYER_FORWARD_RIGHT = "/sprites/ship_forward_right.png";
	
	public static final String IMAGE_ENEMY1 = "/sprites/ship_enemy.png";
	public static final String IMAGE_ENEMY2 = "/sprites/ship_enemy2.png";
	public static final String IMAGE_ENEMY_WASP = "/sprites/ship_wasp.png";
	
	public static final String IMAGE_DAMAGE1 = "/sprites/damaged1.png";
	public static final String IMAGE_DAMAGE2 = "/sprites/damaged2.png";
	public static final String IMAGE_DAMAGE_Enemy1 = "/sprites/damaged_enemy1.png";
	public static final String IMAGE_DAMAGE_Enemy2 = "/sprites/damaged_enemy2.png";
	
	public static final String IMAGE_LASER_RED = "/sprites/laser_red.png";
	public static final String IMAGE_LASER_GREEN = "/sprites/laser_green.png";
	public static final String IMAGE_MISSILE = "/sprites/missile.png";
	
	public static final String EXPLOTION1 = "/sprites/exp1.png";
	public static final String EXPLOTION2 = "/sprites/exp2.png";
	public static final String EXPLOTION3 = "/sprites/exp3.png";
	public static final String EXPLOTION4 = "/sprites/exp4.png";
	
	//private static Hashtable<String, Image> imageTable;
	private static Hashtable<String, Sprite> spriteTable;
	
	/*public static Image getImage(Class<?> caller, String imageName) {
		if(imageTable == null) {
			imageTable = new Hashtable<String, Image>();
		}
		if(imageTable.get(imageName) == null) {
			ImageIcon ii = new ImageIcon(caller.getClass().getResource(imageName));
			//int width = ii.getIconWidth();
			//int height = ii.getIconHeight();
			Image image = ii.getImage();
			
			ii = null;
			imageTable.put(imageName, image);
		}
		return imageTable.get(imageName);
	}*/
	
	public static Sprite getSprite(Class<?> caller, String imageName) {
		if(spriteTable == null) {
			spriteTable = new Hashtable<String, Sprite>();
		}
		
		if(spriteTable.get(imageName) == null) {
			ImageIcon ii = new ImageIcon(caller.getClass().getResource(imageName));
			int width = ii.getIconWidth();
			int height = ii.getIconHeight();
			Image image = ii.getImage();
			ii = null;
			spriteTable.put(imageName, new Sprite(width, height, image));
		}
		
		return spriteTable.get(imageName);
	}
}
