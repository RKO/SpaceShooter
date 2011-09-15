package graphics;

import java.util.Date;

public class Animation {
	private String[] images;
	private int currentStep = 0;
	private Date lastTime;
	private boolean done = false;
	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;
	
	public Animation(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		lastTime = new Date();
	}
	
	public void setImages(String[] images) {
		this.images = images;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	public void tick() {
		Date now = new Date();
		if(now.getTime() - lastTime.getTime() > 100) {
			currentStep++;
			lastTime = now;
		}
		if(currentStep >= images.length-1) {
			done = true;
		}
	}
	
	public Sprite getSprite() {
		return ImageHandler.getSprite(this.getClass(), images[currentStep]);
	}
	
	public boolean isDone() {
		return done;
	}
}
