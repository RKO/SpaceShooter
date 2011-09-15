package main;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import units.Craft;
import units.EnemyCraft;
import units.Projectile;

public class Game implements Runnable {
	private static final int GAME_TIMER = 10;
	private static final int SERVER_TIMRE = 5;
	public static final int width = 1025;//800 // 1024
	public static final int height = 693;//600 // 693 //Always -75
	public static ArrayList<Projectile> projectiles;
	private ArrayList<EnemyCraft> enemies;
	private MultiThreadedServer server;
	private Date timer;
	private ArrayList<Wave> waves;
	private int waveNumber = 0;

	public Game(MultiThreadedServer server) {
		this.server = server;
		projectiles = new ArrayList<Projectile>();
		enemies = new ArrayList<EnemyCraft>();
		waves = new ArrayList<Wave>();
		timer = new Date();
		setupWaves();
	}
	
	public void setupWaves() {
		Wave wave1 = new Wave();
		wave1.addEnemy(Craft.TYPE_ENEMY1);
		wave1.addEnemy(Craft.TYPE_ENEMY1);
		waves.add(wave1);
		
		Wave wave2 = new Wave();
		wave2.addEnemy(Craft.TYPE_ENEMY1);
		wave2.addEnemy(Craft.TYPE_ENEMY2);
		wave2.addEnemy(Craft.TYPE_ENEMY1);
		waves.add(wave2);
		
		Wave wave3 = new Wave();
		wave3.addEnemy(Craft.TYPE_ENEMY_WASP);
		wave3.addEnemy(Craft.TYPE_ENEMY2);
		wave3.addEnemy(Craft.TYPE_ENEMY_WASP);
		wave3.addEnemy(Craft.TYPE_ENEMY2);
		wave3.addEnemy(Craft.TYPE_ENEMY_WASP);
		waves.add(wave3);
		
		Wave wave4 = new Wave();
		wave4.addEnemy(Craft.TYPE_ENEMY_WASP);
		wave4.addEnemy(Craft.TYPE_ENEMY_WASP);
		wave4.addEnemy(Craft.TYPE_ENEMY_WASP);
		wave4.addEnemy(Craft.TYPE_ENEMY_WASP);
		wave4.addEnemy(Craft.TYPE_ENEMY_WASP);
		wave4.addEnemy(Craft.TYPE_ENEMY_WASP);
		waves.add(wave4);
	}

	@Override
	public void run() {
		while(server.isStopped == false) {
			FPSCounter.tick();
			if(server.getConnections().size() > 0) {
				//System.out.println("Rounds per second: "+PackageCounter.getPPS());
				//System.out.println("Rounds per second per player: "+(PackageCounter.getPPS()/server.getConnections().size()));
				Date now = new Date();
				Boolean timeGone = false;
				if(now.getTime() - timer.getTime() > GAME_TIMER) {
					timeGone = true;
					timer = now;
				}

				String positions = "";
				String projectileString = "";
				for(WorkerRunnable w : server.getConnections()) {
					PlayerConnection c = w.getClient();
					if(w.isRunning()) {
						if(c.hasChanged()) {
							positions = positions + CommunicationProtocol.POSITION_MESSAGE;
							positions = positions+c.getPackageInfo();
						}
						w.getClient().tick();
						c.resetChanged();
					}
				}
				Object[] enemyList = enemies.toArray();
				for(int i=0; i<enemyList.length;i++) {
					EnemyCraft enemy = (EnemyCraft) enemyList[i];
					positions = positions + CommunicationProtocol.POSITION_MESSAGE;
					positions = positions + enemy.getPackageInfo();
					if(timeGone && (enemy.isAlive() || enemy.hasEntered() == false)) {
						enemy.move();
						enemy.tick();
					}
					else if(enemy.isAlive() == false) {
						enemies.remove(enemy);
					}
				}

				if(projectiles != null) {
					for(int i=0; i<projectiles.size();i++) {
						Projectile m = projectiles.get(i);
						if(timeGone && m.isAlive()) {
							m.move(this);
						}
						else if(m.isAlive() == false){
							projectiles.remove(i);
						}
						projectileString = projectileString + CommunicationProtocol.MISSILE_MESSAGE;
						projectileString = projectileString +m.getId()+":"+m.getType()+":"+m.getX()+":"+m.getY()+":"+m.isFriendly()+":"+m.isAlive();
						if(m.isFriendly()) {
							for(EnemyCraft enemy : enemies) {
								if(m.isAlive() && m.isFriendly() && enemy.isAlive() && m.getBounds().intersects(enemy.getBounds())) {
									enemy.dealDamage(m.getDamage());
									m.kill();
									break;
								}
							}
						}
						else {
							for(int j=0; j<server.getConnections().size();j++) {
								WorkerRunnable w = server.getConnections().get(j);
								PlayerConnection client = w.getClient();
								if(m.isAlive() && m.isFriendly() == false && client.isAlive() && m.getBounds().intersects(client.getBounds())) {
									client.dealDamage(m.getDamage());
									m.kill();
									break;
								}
							}
						}
					}
				}
				String message = CommunicationProtocol.MESSSAGE_SEPERATOR+positions;
				if(projectileString.replace(CommunicationProtocol.MISSILE_MESSAGE, "").length() > 0) {
					message = message +CommunicationProtocol.MESSSAGE_SEPERATOR+projectileString;
				}
				String testMessage = message.replace(CommunicationProtocol.MESSSAGE_SEPERATOR, "");
				if(testMessage.trim().length() > 0) {
					server.broadcastMessage(message);
				}
				int players = server.getConnections().size();
				if(enemies.size() < players) {
					for(int i=0; i<players;i++) {
						enemies.addAll(waves.get(waveNumber).getEnemies());
					}
					waveNumber++;
					if(waveNumber == waves.size()) {
						waveNumber = 0;
					}
				}
			}
			server.showLPS((int) FPSCounter.getFPS());
			try {
				Thread.sleep(SERVER_TIMRE);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			PackageCounter.tick();
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(0, 0, width, height);
	}
	
	public Craft findEnemy(Rectangle rect) {
		Craft firstResult = null;
		for(EnemyCraft e : enemies) {
			Rectangle targetRect = e.getBounds();
			if(rect.intersects(targetRect)) {
				firstResult = e;
				break;
			}
		}
		return firstResult;
	}
}