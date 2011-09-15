package menu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import main.ClientView;

public class LoginMenu extends JPanel {
	private static final long serialVersionUID = -415903010867653821L;
	
	public LoginMenu(final ClientView clientView) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


		final JTextField serverField = new JTextField("localhost");
		final JTextField usernameField = new JTextField();
		KeyAdapter keyListener = (new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					clientView.loginAction(serverField.getText(), usernameField.getText());
				}
			}
		}
		);

		JLabel serverLabel = new JLabel("Server:");
		serverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(serverLabel);

		serverField.addKeyListener(keyListener);
		this.add(serverField);

		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(usernameLabel);

		usernameField.addKeyListener(keyListener);
		this.add(usernameField);

		JButton connectButton = new JButton("Login");
		connectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clientView.loginAction(serverField.getText(), usernameField.getText());
			}
		});
		this.add(connectButton);
	}

}
