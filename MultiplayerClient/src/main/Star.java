package main;

import java.awt.Rectangle;

public class Star implements BackgroundObejct {
	private int speed = 3;
	private int x;
	private int y;
	
	public Star(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/* (non-Javadoc)
	 * @see BackgroundObejct#getX()
	 */
	@Override
	public int getX() {
		return x;
	}

	/* (non-Javadoc)
	 * @see BackgroundObejct#getY()
	 */
	@Override
	public int getY() {
		return y;
	}
	
	/* (non-Javadoc)
	 * @see BackgroundObejct#move()
	 */
	@Override
	public void move() {
		y = y + speed;
	}
	
	/* (non-Javadoc)
	 * @see BackgroundObejct#getBounds()
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, (int)(5*ClientView.widthFactor), (int)(5*ClientView.widthFactor));
	}
	
	/* (non-Javadoc)
	 * @see BackgroundObejct#setSpeed(int)
	 */
	@Override
	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
