package main;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import units.Craft;
import units.BaseUnit;
import units.Projectile;

public class Client implements Runnable {
	public static final String DEFAULT_ADDRESS = "localhost";
	private final int client_wait = 1;
	private int port = 9090;
	private String serverAddress;
	private String name;
	private Socket requestSocket;
	private static boolean shutdown = false;
	private PrintWriter out = null;
	private static String fromServer = null;
	private Board board;
	private BufferedReader in;

	public Client(String serverName, String name, Board board){
		this.serverAddress = serverName;
		this.name = name;
		this.board = board;
	}

	@Override
	public void run() {
		try {
			try {
				requestSocket = new Socket(serverAddress, port);
				
				CompressedBlockOutputStream compressed = new CompressedBlockOutputStream(requestSocket.getOutputStream(), 1024);
				out = new PrintWriter(new OutputStreamWriter(compressed));
				
				in = new BufferedReader(new InputStreamReader(new CompressedBlockInputStream(requestSocket.getInputStream())));
				
			} catch (UnknownHostException e) {
				showMessage("Don't know about host: "+serverAddress);
				Toolkit.getDefaultToolkit().beep();
				System.err.println("Don't know about host: "+serverAddress);
				//System.exit(1);
			} catch (IOException e) {
				showMessage("Couldn't get I/O for the connection to: "+serverAddress+" at "+port);
				Toolkit.getDefaultToolkit().beep();
				System.err.println("Couldn't get I/O for the connection to: "+serverAddress+" at "+port);
				//System.exit(1);
			}

			out.println(CommunicationProtocol.NAME_MESSAGE+name);
			//sendMessage(CommunicationProtocol.NAME_MESSAGE+name);
			String lastFromServer = "";
			while (!shutdown && (fromServer = in.readLine()) != null) {
				String tempFromServer = fromServer;
				if(tempFromServer != null && tempFromServer.equals(lastFromServer) == false) {
					if(handleMessage(tempFromServer) == false) {
						break;
					}
					lastFromServer = tempFromServer;
				}
				Thread.sleep(client_wait);
			}

			out.close();
			in.close();
			requestSocket.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			showMessage("Error from client: "+e.getMessage());
		}
	}


	public boolean handleMessage(String message) {
		//System.out.println("from server: "+message);
		String[] messages;
		if(message.contains(CommunicationProtocol.MESSSAGE_SEPERATOR)) {
			messages = message.split(CommunicationProtocol.MESSSAGE_SEPERATOR);
		}
		else {
			messages = new String[]{message};
		}
		for(String subMessage : messages) {
			if(subMessage.startsWith(CommunicationProtocol.POSITION_MESSAGE)) {
				String[] positions = subMessage.split(CommunicationProtocol.POSITION_MESSAGE);
				for(String s : positions) {
					if(s.length() > 0) {
						String[] information = s.split(":");
						String name = information[0];
						String type = information[1];
						int x = Integer.parseInt(information[2]);
						int y = Integer.parseInt(information[3]);
						Boolean alive = Boolean.parseBoolean(information[4]);
						int hp = Integer.parseInt(information[5]);
						int maxHp = Integer.parseInt(information[6]);
						int shield = Integer.parseInt(information[7]);
						int maxShield = Integer.parseInt(information[8]);
						
						BaseUnit c = new Craft(name, type);
						c.setX(x);
						c.setY(y);
						c.setAliveState(alive);
						c.setHp(hp);
						c.setMaxHp(maxHp);
						c.setShield(shield);
						c.setMaxShield(maxShield);
						//clientView.addUnit(c);
						board.addUnit(c);
					}
				}
			}
			if(subMessage.startsWith(CommunicationProtocol.PROJECTILE_MESSAGE)) {
				String[] positions = subMessage.split(CommunicationProtocol.PROJECTILE_MESSAGE);
				for(String s : positions) {
					if(s.length() > 0) {
						String[] information = s.split(":");
						String id = information[0];
						String type = information[1];
						int x = Integer.parseInt(information[2]);
						int y = Integer.parseInt(information[3]);
						Boolean friendly = Boolean.parseBoolean(information[4]);
						Boolean alive = Boolean.parseBoolean(information[5]);
						
						BaseUnit m = new Projectile(id, type);
						m.setX(x);
						m.setY(y);
						m.setAliveState(alive);
						m.setIsFriendly(friendly);
						//Projectile m = new Projectile(id, type, x, y, friendly, alive);
						if(m.isAlive()) {
							//clientView.addUnit(m);
							board.addUnit(m);
						}
						else {
							//clientView.removeUnit(m);
							board.removeUnit(m);
						}
					}
				}
			}

			if(subMessage.startsWith(CommunicationProtocol.PLAYER_LEFT)) {
				String player = subMessage.replace(CommunicationProtocol.PLAYER_LEFT, "");
				//clientView.removePlayer(player);
				board.removePlayer(player);
				board.printMessage(player+" has left.");
			}
			if(subMessage.startsWith(CommunicationProtocol.CHAT_MESSAGE)) {
				String chatMessage = subMessage.replace(CommunicationProtocol.CHAT_MESSAGE, "");
				board.printMessage(chatMessage);
			}
			if (subMessage.equals(CommunicationProtocol.CLOSE_MESSAGE)){
				shutdown = true;
				return false;
			}
		}
		return true;
	}

	public void sendMessage(String message) {
		out.println(CommunicationProtocol.CHAT_MESSAGE+message);
		out.flush();
	}

	public void sendCode(String message) {
		out.println(message);
		out.flush();
	}

	private void showMessage(String message) {
		board.printMessage(message);
	}
}