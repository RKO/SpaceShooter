package units;

public class EnemyFreighter extends EnemyCraft {
	protected boolean hasEntered = false;
	
	public EnemyFreighter(String name, int x, int y) {
		super(name, x, y, Craft.TYPE_ENEMY2);
		setMaxHp(10);
		//maxHp = 10;
		//hp = 10;
		setMaxShield(10);
		//maxShield = 10;
		//shield = maxShield;
		setDamage(3);
		//damage = 3;
	}
}