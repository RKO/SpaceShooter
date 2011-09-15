package main;

import java.util.ArrayList;
import java.util.Random;
import units.Craft;
import units.EnemyCraft;
import units.EnemyFighter;
import units.EnemyFreighter;
import units.EnemyWasp;


public class Wave {
	private ArrayList<String> enemies;
	
	public Wave() {
		enemies = new ArrayList<String>();
	}
	
	public void addEnemy(String enemy) {
		enemies.add(enemy);
	}
	
	public ArrayList<EnemyCraft> getEnemies() {
		Random r = new Random();
		ArrayList<EnemyCraft> results = new ArrayList<EnemyCraft>();
		
		for(int i=0; i < enemies.size();i++) {
			if(enemies.get(i).equals(Craft.TYPE_ENEMY2)) {
				int x = r.nextInt(Game.width);
				int y = 0 - Craft.STANDARD_Height* 2;
				String name = "ec"+r.nextInt();
				results.add(new EnemyFreighter(name, x, y));
			}
			else if(enemies.get(i).equals(Craft.TYPE_ENEMY_WASP)) {
				int x = r.nextInt(Game.width);
				int y = 0 - Craft.STANDARD_Height* 2;
				String name = "ec"+r.nextInt();
				results.add(new EnemyWasp(name, x, y));
			}
			else {
				int x = r.nextInt(Game.width);
				int y = 0 - Craft.STANDARD_Height* 2;
				String name = "ec"+r.nextInt();
				results.add(new EnemyFighter(name, x, y));
			}
		}
		return results;
	}
}
