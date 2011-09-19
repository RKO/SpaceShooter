package main;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import menu.LoginMenu;
import units.BaseUnit;

public class ClientView extends JFrame implements WindowListener{
	private static final long serialVersionUID = 1L;
	public static final int width = 1024;//800 //1024
	public static final int height = 733;//640 // 733// -35
	public static int menuHight = 100;
	public static final int menuWidth = 175;
	private Client client;
	private String name;
	private Board board;
	private JPanel boardPanel;

	public ClientView() {
		setTitle("Login");
		addWindowListener(this);

		setSize(width, height);
		setLocationRelativeTo(null);
		setResizable(true);

		setupLoginView();
		setVisible(true);
		boardPanel = new JPanel();
		boardPanel.setBackground(Color.BLACK);
		MigLayout migLayout = new MigLayout("", "[][]0[]", "[]0[]");
		boardPanel.setLayout(migLayout);
		board = new Board(this, width, height, boardPanel);
	}

	public static void main(String args[]) {
		new ClientView();
	}

	public void setupLoginView() {
		this.getContentPane().removeAll();
		add(new LoginMenu(this));
		pack();
	}

	public void loginAction(String serverName, String username) {
		if(serverName.length() == 0) {
			serverName = Client.DEFAULT_ADDRESS;
		}
		if(username.length() > 0) {
			name = username;
			setTitle("Logged in to "+serverName+" as "+name);
			login(serverName);
			setupGameView();
		}
	}

	public void setupGameView() {
		this.getContentPane().removeAll();
		this.add(boardPanel);
		setSize(width+menuWidth, height+menuHight);
		board.requestFocus();
	}

	/*public void enterAction() {
		client.sendMessage(inputField.getText());
		inputField.setText("");
	}*/
	
	public void sendChatMessage(String message) {
		client.sendMessage(message);
	}
	
	public void sendUpgradeMessage(int upgrade) {
		client.sendCode(CommunicationProtocol.UPGRADE_EVENT+upgrade);
	}

	public void login(String serverName) {
		client = new Client(serverName, name, board);
		Thread clientThread = new Thread(client);
		clientThread.start();
	}

	public void sendMoveEvent(String direction) {
		if(client != null) {
			BaseUnit ownerCraft = board.getUnit(name);
			if(ownerCraft != null) {
				client.sendCode(CommunicationProtocol.MOVE_REQUEST+direction+":w"+ownerCraft.getWidth()+":h"+ownerCraft.getHeight());
			}
			else {
				client.sendCode(CommunicationProtocol.MOVE_REQUEST+direction+":w"+0+":h"+0);
			}
		}
	}
	
	public void addUnit(BaseUnit u) {
		board.addUnit(u);
	}
	
	public void removeUnit(BaseUnit m) {
		board.removeUnit(m);
	}
	
	public void removePlayer(String player) {
		board.removePlayer(player);
	}
	
	public String getPlayerName() {
		return name;
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if(client != null) {
			try {
				client.sendCode(CommunicationProtocol.CLOSE_MESSAGE);
			}
			catch(Exception exc) {
				System.exit(0);
			}
		}
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}
}
