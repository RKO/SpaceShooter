package main;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.zip.GZIPOutputStream;

public class WorkerRunnable implements Runnable {
	private static final int KEY_DOWN = 40;
	private static final int KEY_RIGHT = 39;
	private static final int KEY_UP = 38;
	private static final int KEY_LEFT = 37;
	private static final int KEY_SPACE = 32;
	private static final int KEY_X = 88;
	private static final int KEY_CTRL = 17;
	protected Socket clientSocket = null;
	protected MultiThreadedServer server;
	private boolean isRunning = true;
	private PrintWriter out;
	private BufferedReader in;
	private PlayerConnection client;
	private int samples = 0;
	private String test = "";
	private boolean printTest = false;
	private boolean xReleased = true;

	public WorkerRunnable(Socket clientSocket, MultiThreadedServer server) {
		this.clientSocket = clientSocket;
		this.server = server;

		client = new PlayerConnection(clientSocket, Game.width/2, Game.height/2);
		client.setInetAddress(clientSocket.getInetAddress());
		//server.appendMessage("Processing request from "+clientSocket.getInetAddress());
		//System.out.println("Processing request from "+clientSocket.getInetAddress());
	}

	public void run() {
		try {
			CompressedBlockOutputStream compressed =
				new CompressedBlockOutputStream(
						clientSocket.getOutputStream(), 1024);
			out = new PrintWriter(new OutputStreamWriter(compressed));

			//out = new PrintWriter(clientSocket.getOutputStream(), true);
			//in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			in = new BufferedReader(
					new InputStreamReader(
							new CompressedBlockInputStream(
									clientSocket.getInputStream())));
			String inputLine, outputLine;

			CommunicationProtocol protocol = new CommunicationProtocol();
			outputLine = protocol.processInput(null);
			out.println(outputLine);

			while ((inputLine = in.readLine()) != null) {
				outputLine = protocol.processInput(inputLine);
				if (inputLine.equals(CommunicationProtocol.CLOSE_MESSAGE)){
					isRunning = false;
					client.kill();
					out.println(outputLine);
					break;
				}
				if (inputLine.startsWith(CommunicationProtocol.NAME_MESSAGE)){
					//out.println(inputLine);
					client.setName(inputLine.replace(CommunicationProtocol.NAME_MESSAGE, ""));
					out.println(outputLine);
					server.addToList(this);
				}
				else if(inputLine.startsWith(CommunicationProtocol.MOVE_REQUEST)) {
					Rectangle rect = server.getGame().getBounds();
					if(inputLine.contains(""+KEY_DOWN)) {
						client.moveDown(rect);
					}
					if(inputLine.contains(""+KEY_RIGHT)) {
						client.moveRight(rect);
					}
					if(inputLine.contains(""+KEY_UP)) {
						client.moveUp(rect);
					}
					if(inputLine.contains(""+KEY_LEFT)) {
						client.moveLeft(rect);
					}
					if(inputLine.contains(""+KEY_SPACE)) {
						client.fireLaser();
					}
					if(inputLine.contains(""+KEY_CTRL)) {
						client.fireSpecial();
					}
					if(inputLine.contains(""+KEY_X)) {
						if(xReleased) {
							xReleased = false;
							client.setFiremode();
						}
					}
					else if(xReleased == false) {
						xReleased = true;
					}
					String[] params = inputLine.split(":");

					for(String param : params) {
						if(param.startsWith("w")) {
							int width = Integer.parseInt(param.replace("w", ""));
							client.setWidth(width);
						}
						else if(param.startsWith("h")) {
							int height = Integer.parseInt(param.replace("h", ""));
							client.setWidth(height);
						}
					}

					//sendMessage(CommunicationProtocol.MOVE_ACCEPT+client.getX()+":"+client.getY());
				}
				else {
					//out.println(outputLine);
					server.appendMessage(client.getName()+": "+outputLine);
					server.broadcastMessage(CommunicationProtocol.CHAT_MESSAGE+client.getName()+": "+outputLine);
				}
				//server.appendMessage("From client "+client.getName()+": "+inputLine);
				//System.out.println("From client "+client.getName()+": "+inputLine);
			}
			out.close();
			in.close();
			clientSocket.close();
			server.appendMessage("Done processing for "+client.getName()+" at "+clientSocket.getInetAddress());
			//System.out.println("Done processing for "+client.getName()+" at "+clientSocket.getInetAddress());
			isRunning = false;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean isRunning() {
		return isRunning;
	}

	public void sendMessage(String message) {
		if(out != null && in != null) {
			out.println(message);
			out.flush();

			if(samples < 100 && samples >= 0 && printTest) {
				samples++;
				test = test + message;
			}
			else if(samples >= 0 && printTest) {
				samples = -1;
				try {
					FileWriter fstream = new FileWriter("file.txt");
					BufferedWriter out = new BufferedWriter(fstream);
					out.write(test);
					out.close();

					GZIPOutputStream outGzip1 = new GZIPOutputStream(new FileOutputStream("file1.txt"));
					PrintWriter out1 = new PrintWriter(new OutputStreamWriter(outGzip1));
					out1.println(test);
					out1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public String getName() {
		return client.getName();
	}

	public PlayerConnection getClient() {
		return client;
	}
}