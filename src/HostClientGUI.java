import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.event.ActionEvent;

public class HostClientGUI extends JFrame {

	private JPanel contentPane;
	private boolean isReady = false;
	private JLabel lblNewLabel;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HostClientGUI frame = new HostClientGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HostClientGUI() {
		setTitle("Battleship");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 379, 223);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNewLabel = new JLabel("Waiting For Opponent...");
		lblNewLabel.setVisible(false);
		lblNewLabel.setBounds(115, 159, 190, 14);
		contentPane.add(lblNewLabel);

		JButton btnNewButton = new JButton("Host");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Server server = new Server(8080);
				btnNewButton.setEnabled(false);
				btnNewButton_1.setEnabled(false);
				lblNewLabel.setVisible(true);
				if (server.isInterrupted()) {
					dispose();
				}

			}
		});
		btnNewButton.setBounds(46, 87, 89, 23);
		contentPane.add(btnNewButton);

		btnNewButton_1 = new JButton("Client");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String input = JOptionPane.showInputDialog(null, "Baðlanmak için bir host adresi Girin!");

				if (!input.isEmpty()) {
					Client client = new Client(input, 8080);
					btnNewButton.setEnabled(false);
					btnNewButton_1.setEnabled(false);
					lblNewLabel.setVisible(true);

					if (client.isInterrupted()) {
						dispose();
					}
				}

			}
		});
		btnNewButton_1.setBounds(205, 87, 89, 23);
		contentPane.add(btnNewButton_1);

	}
}
