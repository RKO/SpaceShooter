package main;

import graphics.Animation;
import graphics.Explotion;
import graphics.ImageHandler;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;
import menu.BoardMenu;
import menu.BoardSideMenu;
import units.Craft;
import units.BaseUnit;
import units.Projectile;

public class Board extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	public static final int FIREMODE_SPRAY = 0;
	public static final int FIREMODE_BURST = 1;
	private final String FIREMODE_SPRAY_NAME = "Spray";
	private final String FIREMODE_BURST_NAME = "Burst";
	private final int LOOP_DELAY = 10;
	private ClientView clientView;
	private Timer timer;
	private Hashtable<String, BaseUnit> unitTable;
	private ArrayList<BackgroundObejct> backgroundObejcts;
	private ArrayList<Animation> animations;
	private Random random;
	private int starRowCount = 0;
	private String keyDown = "";
	private BoardMenu menu;
	private BoardSideMenu sideMenu;
	private int fireMode = 0;

	public Board(ClientView callback, int width, int height, JPanel container) {	
		this.clientView = callback;

		this.addKeyListener(new TAdapter());
		this.setFocusable(true);
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(width, height);

		menu = new BoardMenu(callback, this, width, ClientView.menuHight);
		sideMenu = new BoardSideMenu(this, ClientView.menuWidth, height);
		container.add(this);
		container.add(menu, "dock south");
		container.add(sideMenu, "east");

		addKeyListener (new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					Board.this.requestFocus();
					//Board.this.requestFocusInWindow();
					menu.openChat();
					System.out.println("Test");
				}
			}
		}
		);

		unitTable = new Hashtable<String, BaseUnit>();

		backgroundObejcts = new ArrayList<BackgroundObejct>();

		animations = new ArrayList<Animation>();

		random = new Random();

		int starRows = ClientView.width/15;
		for(int i=0; i<starRows;i++) {
			populateStars(i*20);
		}

		timer = new Timer(LOOP_DELAY, this);
		timer.start();
		/*Thread thread = new Thread(this);
		thread.start();*/
	}


	public void paint(Graphics g) {
		super.paint(g);

		String fireModeString = FIREMODE_SPRAY_NAME;
		if(fireMode == FIREMODE_BURST) {
			fireModeString = FIREMODE_BURST_NAME;
		}

		menu.displayFPS(""+FPSCounter.getFPS());
		menu.displayStatus(unitTable.get(clientView.getPlayerName()));
		menu.displayFiremode(fireModeString);
		
		sideMenu.updateUpgrades(unitTable.get(clientView.getPlayerName()));

		Graphics2D g2d = (Graphics2D)g;

		for(int i=0; i< backgroundObejcts.size();i++) {
			BackgroundObejct s = backgroundObejcts.get(i);
			if(s.getY() < ClientView.height) {
				g2d.fill(s.getBounds());
				s.move();
			}
			else {
				backgroundObejcts.remove(i);
			}
		}
		if(starRowCount >= 15) {
			populateStars(0);
			starRowCount = 0;
		}
		else {
			starRowCount++;
		}

		for(int i=0; i<animations.size(); i++) {
			Animation ani = animations.get(i);
			if(ani.isDone() == false) {
				g2d.drawImage(ani.getSprite().getImage(), ani.getX(), ani.getY(), ani.getWidth(), ani.getHeight(), null);
				ani.tick();
			}
			else {
				animations.remove(i);
			}
		}

		Enumeration<BaseUnit> e = unitTable.elements();
		while(e.hasMoreElements()) {
			BaseUnit u = e.nextElement();
			if(u != null && u.isAlive()) {
				if(u.getShield() > 0) {
					int factor = (int) u.getShieldStrength();
					int alpha = (255*factor)/100;
					if(alpha > 255) {
						alpha = 255;
					}
					if(alpha < 0) {
						alpha = 0;
					}
					g2d.setColor(new Color(0, 0, 200, alpha));
					g2d.fillOval(u.getX(), u.getY(), u.getWidth(), u.getHeight());
				}
				g2d.drawImage(u.getImage(), u.getX(), u.getY(), u.getWidth(), u.getHeight(), null);
				if(u.getType().equals(Craft.TYPE_FRIENDLY)) {
					g2d.setColor(Color.RED);
					g2d.drawString(u.getName(), u.getX(), u.getY());
					if(u.getHpStrength() <= 50) {
						g2d.drawImage(ImageHandler.getSprite(this.getClass(), ImageHandler.IMAGE_DAMAGE2).getImage(), 
								u.getX(), u.getY(), u.getWidth(), u.getHeight(), null);
					}
					else if(u.getHpStrength() <= 75) {
						g2d.drawImage(ImageHandler.getSprite(this.getClass(), ImageHandler.IMAGE_DAMAGE1).getImage(), 
								u.getX(), u.getY(), u.getWidth(), u.getHeight(), null);
					}
				}
				else {
					if(u.getHpStrength() <= 50) {
						g2d.drawImage(ImageHandler.getSprite(this.getClass(), ImageHandler.IMAGE_DAMAGE_Enemy2).getImage(), 
								u.getX(), u.getY(), u.getWidth(), u.getHeight(), null);
					}
					else if(u.getHpStrength() <= 75) {
						g2d.drawImage(ImageHandler.getSprite(this.getClass(), ImageHandler.IMAGE_DAMAGE_Enemy1).getImage(), 
								u.getX(), u.getY(), u.getWidth(), u.getHeight(), null);
					}
				}

			}
			else if(u != null) {
				unitTable.remove(u.getName());
				animations.add(new Explotion(u.getX(), u.getY(), u.getWidth(), u.getHeight()));
			}
		}
		if(keyDown.length() > 0) {
			clientView.sendMoveEvent(keyDown);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();

		FPSCounter.tick();

		/*try {
			//Thread.sleep(LOOP_DELAY);
			//repaint();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}*/
	}

	private class TAdapter extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			keyDown = keyDown.replace(e.getKeyCode()+",", "");
		}

		public void keyPressed(KeyEvent e) {
			if(keyDown.contains(e.getKeyCode()+",") == false) {
				keyDown = keyDown+e.getKeyCode()+",";
				//System.out.println("Keycode: "+e.getKeyCode());
			}
			if(e.getKeyCode() == 88) {
				if(fireMode == FIREMODE_BURST) {
					fireMode = FIREMODE_SPRAY;
				}
				else {
					fireMode = FIREMODE_BURST;
				}
			}
		}
	}

	public void addUnit(BaseUnit u) {
		if(u.getType().equals(Projectile.TYPE_LASER_RED) && unitTable.get(u.getName()) == null) {
			Sound.laser.play();
		}
		BaseUnit unit = unitTable.get(u.getName());
		if(unit != null) {
			unit.setX(u.getX());
			unit.setY(u.getY());
			unit.setMaxHp(u.getMaxHp());
			unit.setHp(u.getHp());
			unit.setAliveState(u.isAlive());
			unit.setShield(u.getShield());
			unit.setLaserLevel(u.getLaserLevel());
			unit.setMissileLevel(u.getMissileLevel());
			unit.setHullLevel(u.getHullLevel());
			unit.setShieldLevel(u.getShieldLevel());
			unit.setSpeedLevel(u.getSpeedLevel());
		}
		else {
			unitTable.put(u.getName(), u);
		}
	}

	public void removeUnit(BaseUnit m) {
		if(unitTable.get(m.getName()) != null) {
			unitTable.remove(m.getName());
		}
	}

	public void removePlayer(String player) {
		unitTable.remove(player);
	}

	public BaseUnit getUnit(String name) {
		return unitTable.get(name);
	}

	public void populateStars(int y) {
		int number = random.nextInt(5);
		for(int i=0; i<5+number; i++) {
			int x = random.nextInt(ClientView.width);
			backgroundObejcts.add(new Star(x, y));
		}
	}

	/*@Override
	public void run() {
		repaint();
	}*/

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	public void printMessage(String message) {
		menu.printMessage(message);
	}
	
	
	public void upgrade(int upgrade) {
		clientView.sendUpgradeMessage(upgrade);
	}
}