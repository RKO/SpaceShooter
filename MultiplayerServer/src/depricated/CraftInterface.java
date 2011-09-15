package depricated;

import java.awt.Rectangle;

public interface CraftInterface extends Comparable<CraftInterface> {

	public String getType();
	public String getName();
	public int getX();
	public int getY();
	public void tick();
	public Rectangle getBounds();
	public void kill();
	public boolean isAlive();
	public void dealDamage(int damage);
	public int getHp();
	public int getShield();
	public int getMaxShield();
	public String getPackageInfo();
}
