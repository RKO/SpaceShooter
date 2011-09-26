package units;

public class EnemyFighter extends EnemyCraft {
	protected boolean hasEntered = false;
	
	public EnemyFighter(String name, int x, int y) {
		super(Craft.STANDARD_WIDTH, Craft.STANDARD_HEIGHT, name, x, y, Craft.TYPE_ENEMY1);
		setMaxHp(10);
		//maxHp = 10;
		//hp = 10;
		setMaxShield(0);
		//shield = 0;
		setDamage(2);
		setSpeed(2);
		//damage = 2;
	}
}