package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiThreadedServer implements Runnable {
	private Server server;
	protected int serverPort   = 9090;
	protected ServerSocket serverSocket = null;
	protected boolean isStopped    = false;
	protected Thread runningThread= null;
	private ArrayList<WorkerRunnable> connections;
	private Game game;

	public MultiThreadedServer(Server server, int port){
		this.server = server;
		this.serverPort = port;
		connections = new ArrayList<WorkerRunnable>();
		server.appendMessage("Starting server");
		System.out.println("Starting server");

		game = new Game(this);
		Thread gameThread = new Thread(game);
		gameThread.start();


	}
	public void run(){
		synchronized(this){
			this.runningThread = Thread.currentThread();
		}
		openServerSocket();
		server.appendMessage("Ready");
		System.out.println("Ready");
		while(! isStopped()) {
			//broadcastMessage("New user connected");
			Socket clientSocket = null;
			try {
				clientSocket = this.serverSocket.accept();
			} catch (IOException e) {
				if(isStopped()) {
					server.appendMessage("Server Stopped.");
					System.out.println("Server Stopped.") ;
					return;
				}
				throw new RuntimeException("Error accepting client connection", e);
			}

			WorkerRunnable worker = new WorkerRunnable(clientSocket, this);
			//connections.add(worker);
			new Thread(worker).start();
		}
		server.appendMessage("Server Stopped.");
		System.out.println("Server Stopped.") ;
	}


	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	public void stopServer() {
		isStopped = true;
	}

	public synchronized void stop(){
		this.isStopped = true;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			throw new RuntimeException("Error closing server", e);
		}
	}

	private void openServerSocket() {
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
		} catch (IOException e) {
			throw new RuntimeException("Cannot open port "+this.serverPort, e);
		}
	}
	public void broadcastMessage(String message){
		Object[] copy = connections.toArray();
		for(int i=0;i<copy.length;i++) {
			WorkerRunnable worker = (WorkerRunnable) copy[i];
			if(worker.isRunning() == false) {
				connections.remove(worker);
				broadcastMessage(CommunicationProtocol.PLAYER_LEFT+worker.getName());
				server.appendMessage("User "+worker.getName() + " disconnected from "+worker.getClient().getInetAddress());
			}
			else {
				worker.sendMessage(message);
			}
		}
	}

	public void addToList(WorkerRunnable connection) {
		broadcastMessage(CommunicationProtocol.CHAT_MESSAGE+connection.getName()+" connected.");
		connections.add(connection);
		server.appendMessage("User "+connection.getName() + " connected succesfully at "+connection.getClient().getInetAddress());
	}
	public void appendMessage(String message) {
		server.appendMessage(message);
	}
	
	public void showLPS(int lps){
		server.showLPS(lps);
	}

	public ArrayList<WorkerRunnable> getConnections() {
		return connections;
	}
	public Game getGame() {
		return game;
	}
}
