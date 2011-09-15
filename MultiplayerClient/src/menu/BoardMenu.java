package menu;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.Board;
import main.ClientView;
import units.BaseUnit;

public class BoardMenu extends JPanel {
	private static final long serialVersionUID = 1L;
	private ClientView clientView;
	private Board board;
	private JLabel fpsLabel;
	private JLabel hpLabel;
	private JLabel shieldLabel;
	private JTextArea messageArea;
	private JTextField inputField;
	private JLabel firemode;
	private JLabel firemodeTip;
	private JLabel chatTip;

	public BoardMenu(ClientView clientView, Board board, int width, int height) {
		this.clientView = clientView;
		this.board = board;
		this.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		this.setPreferredSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
		this.setSize(width, height);

		this.setBorder(new EmptyBorder(5, 10, 5, 10));
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//-------------------------------\\
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.15;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));

		firemode = new JLabel();
		leftPanel.add(firemode);

		hpLabel = new JLabel();
		leftPanel.add(hpLabel);

		shieldLabel = new JLabel();
		leftPanel.add(shieldLabel);

		this.add(leftPanel, c);

		//-------------------------------\\
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.70;
		c.weighty = 1;
		c.gridx = 1;
		c.gridy = 0;
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));

		messageArea = new JTextArea();
		messageArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(messageArea);
		centerPanel.add(scrollPane);

		inputField = new JTextField();
		inputField.addKeyListener
		(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					enterAction();
				}
			}
		}
		);
		centerPanel.add(inputField);

		this.add(centerPanel, c);
		//-------------------------------\\
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.15;
		c.weighty = 1;
		c.gridx = 2;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_END;
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		rightPanel.setBorder(new EmptyBorder(0, 5, 0, 5));
		
		fpsLabel = new JLabel();
		rightPanel.add(fpsLabel);
		
		firemodeTip = new JLabel("Press X to change firemode");
		rightPanel.add(firemodeTip);
		
		chatTip = new JLabel("Press Enter to open and close chat");
		rightPanel.add(chatTip);
		
		this.add(rightPanel,c);
		//-------------------------------\\
	}

	public void displayStatus(BaseUnit moveable) {
		if(moveable != null) {
			hpLabel.setText("Hull: "+moveable.getHp()+"/"+moveable.getMaxHp());
			int sheildPercentage = (int) moveable.getShieldStrength();
			shieldLabel.setText("Shield: "+sheildPercentage+"%");
		}
	}

	public void displayFPS(String fps){
		fpsLabel.setText("FPS: "+fps);
	}

	public void displayFiremode(String fireMode) {
		firemode.setText("Firemode: "+fireMode);
	}

	public void printMessage(String message) {
		if(messageArea != null){
			messageArea.append(message+"\n");
			messageArea.setCaretPosition(messageArea.getDocument().getLength()-1);
		}
	}
	
	public void enterAction() {
		clientView.sendChatMessage(inputField.getText());
		inputField.setText("");
		board.requestFocus();
	}
	
	public void openChat() {
		inputField.requestFocus();
	}
}
