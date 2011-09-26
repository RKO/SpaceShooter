package units;

public class EnemyWasp extends EnemyCraft {
	protected boolean hasEntered = false;
	
	public EnemyWasp(String name, int x, int y) {
		super(16, 16, name, x, y, Craft.TYPE_ENEMY_WASP);
		setMaxHp(4);
		//maxHp = 10;
		//hp = 10;
		setMaxShield(0);
		//shield = 0;
		setDamage(1);
		setSpeed(3);
		//damage = 2;
	}
}