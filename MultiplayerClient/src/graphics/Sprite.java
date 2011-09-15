package graphics;

import java.awt.Image;

public class Sprite {
	private int width;
	private int height;
	private Image image;
	
	public Sprite(int width, int height, Image image) {
		this.width = width;
		this.height = height;
		this.image = image;
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public Image getImage() {
		return image;
	}
}
