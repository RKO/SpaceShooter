package menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import units.BaseUnit;

import net.miginfocom.swing.MigLayout;
import main.Board;
import main.CommunicationProtocol;

public class BoardSideMenu extends JPanel {
	private static final long serialVersionUID = 1L;
	private Board board;
	private JLabel laserLevelLabel;
	private JLabel missileLevelLabel;
	private JLabel hullLevelLabel;
	private JLabel shieldLevelLabel;
	private JLabel engineLevelLabel;

	public BoardSideMenu(Board board, int width, int height) {
		this.board = board;
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(width, height);
		MigLayout migLayout = new MigLayout("", "[][]10[]", "[]10[]");
		this.setLayout(migLayout);
		this.setBackground(Color.LIGHT_GRAY);


		JLabel laserLabel = new JLabel("Laser Level ");
		laserLevelLabel = new JLabel("1");
		this.add(laserLabel, "cell 0 0");
		this.add(laserLevelLabel, "cell 1 0");

		JLabel missileLabel = new JLabel("Missile Level ");
		missileLevelLabel = new JLabel("1");
		this.add(missileLabel, "cell 0 1");
		this.add(missileLevelLabel, "cell 1 1");

		JLabel hullLabel = new JLabel("Hull Level ");
		hullLevelLabel = new JLabel("1");
		this.add(hullLabel, "cell 0 2");
		this.add(hullLevelLabel, "cell 1 2");

		JLabel shieldLabel = new JLabel("Shield Level ");
		shieldLevelLabel = new JLabel("1");
		this.add(shieldLabel, "cell 0 3");
		this.add(shieldLevelLabel, "cell 1 3");

		JLabel engineLabel = new JLabel("Engine Level ");
		engineLevelLabel = new JLabel("1");
		this.add(engineLabel, "cell 0 4");
		this.add(engineLevelLabel, "cell 1 4");


		JButton laserUpgrade = new JButton("Upgrade Laser");
		laserUpgrade.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BoardSideMenu.this.board.upgrade(CommunicationProtocol.UPGRADE_LASER);
				BoardSideMenu.this.board.requestFocus();
			}
		});
		this.add(laserUpgrade, "cell 0 5");

		JButton missileUpgrade = new JButton("Upgrade Missiles");
		missileUpgrade.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BoardSideMenu.this.board.upgrade(CommunicationProtocol.UPGRADE_MISSILE);
				BoardSideMenu.this.board.requestFocus();
			}
		});
		this.add(missileUpgrade, "cell 0 6");

		JButton hullUpgrade = new JButton("Upgrade Hull");
		hullUpgrade.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BoardSideMenu.this.board.upgrade(CommunicationProtocol.UPGRADE_HULL);
				BoardSideMenu.this.board.requestFocus();
			}
		});
		this.add(hullUpgrade, "cell 0 7");

		JButton shieldUpgrade = new JButton("Upgrade Shield");
		shieldUpgrade.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BoardSideMenu.this.board.upgrade(CommunicationProtocol.UPGRADE_SHIELD);
				BoardSideMenu.this.board.requestFocus();
			}
		});
		this.add(shieldUpgrade, "cell 0 8");

		JButton engineUpgrade = new JButton("Upgrade Engine");
		engineUpgrade.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BoardSideMenu.this.board.upgrade(CommunicationProtocol.UPGRADE_SPEED);
				BoardSideMenu.this.board.requestFocus();
			}
		});
		this.add(engineUpgrade, "cell 0 9");
	}
	public void updateUpgrades(BaseUnit unit) {
		if(unit != null) {
			laserLevelLabel.setText(""+unit.getLaserLevel());
			missileLevelLabel.setText(""+unit.getMissileLevel());
			hullLevelLabel.setText(""+unit.getHullLevel());
			shieldLevelLabel.setText(""+unit.getShieldLevel());
			engineLevelLabel.setText(""+unit.getSpeedLevel());
		}
	}
}
