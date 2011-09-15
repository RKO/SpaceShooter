package main;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Server extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L;
	private static final int width = 200;
	private static final int height = 300;
	private JScrollPane scrollPane;
	private JLabel LPScounter;
	private JTextArea outputArea;
	private MultiThreadedServer threadServer;

	public Server() {
		setTitle("Multiplayer Server");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setResizable(true);
		addViews();
		pack();
		setVisible(true);

		threadServer = new MultiThreadedServer(this, 9090);
		new Thread(threadServer).start();
	}

	public static void main(String[] args) {
		new Server();
	}

	public void addViews() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		outputArea = new JTextArea(15,50);
		LPScounter = new JLabel("Loops per second: ");
		panel.add(LPScounter);
		scrollPane = new JScrollPane(outputArea);
		panel.add(scrollPane);
		add(panel);
	}
	public void appendMessage(String message) {
		outputArea.append(message+"\n");
		outputArea.setCaretPosition(outputArea.getDocument().getLength()-1);
	}

	public void showLPS(int lps){
		LPScounter.setText("Loops per second: "+lps);
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if(threadServer != null) {
			threadServer.broadcastMessage(CommunicationProtocol.CLOSE_MESSAGE);
			threadServer.stopServer();
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
