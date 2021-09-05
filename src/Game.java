import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;


import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.MatteBorder;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Game extends JFrame {

	private JFrame gameFrame;
	private JLayeredPane layeredPane;
	Dimension boardSize;
	JPanel panel;
	JLabel myTahta_lbl;
	JLabel enemyTahta_lbl;
	int countShip;
	JPanel ship1;
	JPanel ship2;
	JPanel ship3;
	JPanel ship4;
	JPanel ship5;
	int[][] table;
	int[][] enemyTable;
	int table_i;
	int table_j;
	static String title = "";
	static Socket socket = null;
	boolean am_i_ready = false;
	boolean is_it_ready = false;
	JTextPane textPane;
	JPanel square;
	JButton btn;
	JButton btnNewButton_1;
	boolean myTurn = false;
	boolean itTurn = false;
	JLabel txt_sira;

	private int shipLocY;
	private int shipLocX;

	ArrayList<String> charList;

	JPanel rakipPanel;
	private JTextField textField;
	private DataInputStream input;
	DataOutputStream out;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game frame = new Game(title, socket);
					frame.setVisible(true);
					frame.repaint();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Game(String title, Socket socket) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 1000);
		//setBackground(new Color(83,202,131));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		//setUndecorated(true);
		setVisible(true);

		this.socket = socket;

		layeredPane = new JLayeredPane();
		layeredPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		layeredPane.setBounds(0, 0, 1000, 1000);
		setContentPane(layeredPane);
		layeredPane.setLayout(null);

		boardSize = new Dimension(400, 400);
		table = new int[10][10];
		enemyTable = new int[10][10];
		this.title = title;
		setTitle(title);

		ship1 = new JPanel();
		ship2 = new JPanel();
		ship3 = new JPanel();
		ship4 = new JPanel();
		ship5 = new JPanel();

		panel = new JPanel();
		panel.setRequestFocusEnabled(false);
		panel.setFocusable(false);
		panel.setBorder(new LineBorder(Color.BLACK, 2));
		panel.setPreferredSize(boardSize);
		panel.setBounds(40, 40, boardSize.width - 4, boardSize.height - 4);
		layeredPane.add(panel, new Integer(0));
		panel.setLayout(new GridLayout(11, 11));
		// panel.add(leftPanel);

		rakipPanel = new JPanel();
		rakipPanel.setFocusable(false);
		rakipPanel.setBorder(new LineBorder(Color.BLACK, 2));
		rakipPanel.setPreferredSize(boardSize);
		rakipPanel.setName("rakipPanel");
		rakipPanel.setBounds(920, 40, boardSize.width - 4, boardSize.height - 4);
		layeredPane.add(rakipPanel, new Integer(0));
		rakipPanel.setLayout(new GridLayout(11, 11));

		myTahta_lbl = new JLabel();
		myTahta_lbl.setText("Senin Tahtan");
		myTahta_lbl.setBounds(panel.getX() + (panel.getWidth() / 2) - 50, panel.getY() - 50, 100, 50);
		layeredPane.add(myTahta_lbl);

		enemyTahta_lbl = new JLabel();
		enemyTahta_lbl.setText("Rakip Tahta");
		enemyTahta_lbl.setBounds(rakipPanel.getX() + (rakipPanel.getWidth() / 2) - 50, rakipPanel.getY() - 50, 100, 50);
		layeredPane.add(enemyTahta_lbl);

		charList = new ArrayList<String>();
		charList.add("A");
		charList.add("B");
		charList.add("C");
		charList.add("D");
		charList.add("E");
		charList.add("F");
		charList.add("G");
		charList.add("H");
		charList.add("I");
		charList.add("J");

		shipLocY = 500;
		shipLocX = 40;
		countShip = 1;

		createSquareOnTable();
		createSquareOnTable2();
		createSeperator();

		ship1 = createShips(5);
		ship2 = createShips(4);
		ship3 = createShips(3);
		ship4 = createShips(3);
		ship5 = createShips(2);
		
		txt_sira = new JLabel("S\u0131ra Sende");
		txt_sira.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txt_sira.setHorizontalAlignment(SwingConstants.CENTER);
		txt_sira.setVisible(false);
		txt_sira.setBounds(563, 627, 158, 26);
		layeredPane.add(txt_sira);
		
		

		//out = null;
		try {
			out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		textField = new JTextField();
		textField.setBounds(493, 362, 252, 26);
		layeredPane.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String goingMsg = textField.getText();

				if (!goingMsg.equals("")) {
					try {
						out.writeUTF(goingMsg);
						out.flush();

						addMessage(goingMsg, title);

						textField.setText("");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});
		btnNewButton.setBackground(SystemColor.activeCaption);
		btnNewButton.setIcon(new ImageIcon("arrow.png"));
		btnNewButton.setBounds(749, 362, 50, 26);
		layeredPane.add(btnNewButton);

		btnNewButton_1 = new JButton("HAZIR");
		

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(Game.title.equals("Server")) {
					myTurn=true;
					txt_sira.setVisible(true);
				}
				else {
					itTurn=true;
				}

				try {
					DataOutputStream out = new DataOutputStream(socket.getOutputStream());

					out.writeUTF("Hazýrým");
					addMessage("Hazýrým", title);
					am_i_ready = true;

					for (int i = 0; i < 10; i++) {
						for (int j = 0; j < 10; j++) {
							out.writeUTF(table[i][j] + "");
						}

					}
					
					btnNewButton_1.setVisible(false);
				} catch (IOException e) { // TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		btnNewButton_1.setBackground(new Color(58, 140, 255));
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnNewButton_1.setBounds(563, 517, 158, 73);
		layeredPane.add(btnNewButton_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(493, 40, 306, 299);
		layeredPane.add(scrollPane);

		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		scrollPane.setViewportView(textPane);
		
		

		DataInputStream in;
		String comingMsg = "";
		table_i = 0;
		table_j = 0;

		try {
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

			// reads message from client until "Over" is sent
			while (!comingMsg.equals("Over")) {
				try {

					comingMsg = in.readUTF();

					if (comingMsg.equals("Hazýrým")) {
						is_it_ready = true;
					}

					if (!comingMsg.equals("")) {

						if (comingMsg.startsWith("9ad0214eed7fc36c3bebc62d096574aa")) {
							String command = comingMsg.substring(32);
							String command2="";
							String command3="";
							//JOptionPane.showMessageDialog(null, "panel item count:"+panel.getComponentCount());
							
							//JOptionPane.showMessageDialog(null, "Command: "+command+" "+command2+" "+command3);
							
							if(command.contains("change")) {
								
								String[] x = command.split(" ");
								command = x[0];
								command2= x[1];
								command3= x[2];
							}
							
							switch (command) {
							case "turn":
								myTurn=true;
								
								txt_sira.setVisible(true);
								break;
								
							case "change":
								for (int i = 0; i < panel.getComponentCount(); i++) {
									//System.out.println(panel.getComponent(i).getName()+" ");
									
									if(panel.getComponent(i).getBackground()==Color.LIGHT_GRAY) {
										//System.out.println("square"+command2);
										
										if(panel.getComponent(i).getName().equals("square"+command2))
										{
											
											
											if(command3.equals("red")) {
												
												panel.getComponent(i).setBackground(Color.RED);
												// shipPanel kýrmýzý yapýlacak
											}
											
											
										}
									}
									
									
								}
								break;
								
							case "finish":
								
								if(title.equals("Server")) {
									JOptionPane.showMessageDialog(null,"Oyun Bitti Kazanan "+"Client","BattleShip",1);
								}
								else {
									JOptionPane.showMessageDialog(null,"Oyun Bitti Kazanan "+"Server","BattleShip",1);
								}
								
								dispose();
								
								
								
								break;
								
							
								
							

							default:
								break;
							}
							
							//addMessage(comingMsg, "Admin");

						} else if (comingMsg.equals("0") || comingMsg.equals("1")) {
							enemyTable[table_i][table_j++] = Integer.parseInt(comingMsg);

							if (table_j > 9) {
								table_i++;
								table_j = 0;
							}

							/*
							 * for (int i = 0; i < enemyTable.length; i++) { for (int j = 0; j <
							 * enemyTable.length; j++) { System.out.print(" " + i + "" + j + ":" +
							 * enemyTable[i][j]); } System.out.println(); }
							 */
						}

						else if (title.equals("Client")) {
							addMessage(comingMsg, "Server");
						} else {
							addMessage(comingMsg, "Client");
						}

					}

				} catch (IOException i) {
					System.out.println("Mesaj okuma hatasý: " + i.getMessage());
				}

				/*
				 * if (am_i_ready == true && is_it_ready == true) {
				 * 
				 * addMessage("Ýki kiþide hazýr :D", "Admin"); }
				 */

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addMessage(String message, String from) {

		String currentText = textPane.getText();
		textPane.setText(currentText + "\n" + from + " : " + message + "\n");

	}

	private void createSquareOnTable() {
		int j = 1;
		int z = 0;
		int tag = 1;

		for (int i = 0; i < 121; i++) {

			square = new JPanel(new BorderLayout(15, 15));
			square.setName("square");
			if (i == 0) {
				panel.add(square);
				
				// square.setBackground(Color.LIGHT_GRAY);
			}

			else if (i < 11) {
				panel.add(new JLabel(j++ + "", SwingConstants.CENTER));
				
			} else if (i % 11 == 0) {
				panel.add(new JLabel(charList.get(z++) + "", SwingConstants.CENTER));
				
			} else {
				panel.add(square);
				square.setName("square" + tag++);
				square.setBackground(Color.LIGHT_GRAY);
				square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}

		}

	}

	private JPanel createShips(int dim) {
		JPanel shipPanel = new JPanel();
		shipPanel.setName("ship" + countShip++);
		// shipPanel.setBorder(new LineBorder(Color.BLACK, 2));
		shipPanel.setPreferredSize(boardSize);

		if (shipLocY > 700) {
			shipLocY = 500;
			shipLocX = 300;

		}

		shipPanel.setBounds(shipLocX, shipLocY, 35 * dim, 35);
		shipLocY += (shipPanel.getHeight() * 2);
		// System.out.println("shipLocY:"+shipLocY);
		layeredPane.add(shipPanel, new Integer(1));
		shipPanel.setLayout(new GridLayout(0, dim));

		for (int i = 0; i < dim; i++) {
			JPanel square = new JPanel(new BorderLayout(0, 0));
			square.setBackground(new Color(58, 140, 255));
			square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			// square.requestFocusInWindow();
			shipPanel.add(square);

		}

		shipPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

				if (am_i_ready == false) {
					int bestMatchIdx = -1;
					double bestMatchDistance = 25;

					for (int i = 0; i < panel.getComponentCount(); i++) {

						// System.out.println("isim:"+panel.getComponent(i).getName());

						if (panel.getComponent(i).getLocationOnScreen()
								.distance(arg0.getLocationOnScreen()) < bestMatchDistance
								&& !panel.getComponent(i).getName().equals(null)) {

							bestMatchDistance = panel.getComponent(i).getLocationOnScreen()
									.distance(arg0.getLocationOnScreen());
							bestMatchIdx = i;

						}

					}
					// System.out.println("CurrIdx:"+currIdx);

					int newX = (int) panel.getComponent(bestMatchIdx).getLocationOnScreen().getX() - 35;
					int newY = (int) panel.getComponent(bestMatchIdx).getLocationOnScreen().getY() - 23;
					int newWidth = shipPanel.getWidth();
					int newHeight = shipPanel.getHeight();

					shipPanel.setBounds(newX, newY, newWidth, newHeight);

					if (!panel.getComponent(bestMatchIdx).getName().equals(null)) {
						int num = Integer
								.parseInt(panel.getComponent(bestMatchIdx - (shipPanel.getComponentCount() / 2))
										.getName().substring(6));
						// System.out.println("Konum:"+num+" BestMatchIdx:"+bestMatchIdx);

						int column = num / 10;
						int row = num % 10;
						for (int i = 0; i < shipPanel.getComponentCount(); i++) {
							table[column][row++] = 1;
						}

						// System.out.println(panel.getComponentAt(newX, newY).getName());

						/*
						 * for (int i = 0; i < table.length; i++) { for (int j = 0; j < table.length;
						 * j++) { System.out.print(" "+i+""+j+":"+table[i][j]); } System.out.println();
						 * }
						 */
					}
					// checkShip();
				}

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (am_i_ready == false) {
					shipPanel.setBounds(shipPanel.getX(), shipPanel.getY(), shipPanel.getHeight(),
							shipPanel.getWidth());

					// Graphics2D gx = (Graphics2D) shipPanel.getGraphics();
					// gx.rotate(0.6, getX() + getWidth()/2, getY() + getHeight()/2);
				}

			}

		});

		shipPanel.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent arg0) {

			}

			@Override
			public void mouseDragged(MouseEvent arg0) {

				if (am_i_ready == false) {
					int X = arg0.getX() + shipPanel.getX() - (shipPanel.getWidth() / 2);
					int Y = arg0.getY() + shipPanel.getY() - (shipPanel.getHeight() / 2);

					shipPanel.setBounds(X, Y, shipPanel.getWidth(), shipPanel.getHeight());
				}

			}
		});
		return shipPanel;

	}

	public void getBtnName(JButton button) {
		String clicked = button.getName();
		System.out.println("Clicked :" + clicked);
	}

	private void createSquareOnTable2() {
		int j = 1;
		int z = 0;
		int count = 1;

		for (int i = 0; i < 121; i++) {

			square = new JPanel(new BorderLayout(15, 15));
			btn = new JButton();
			btn.setBounds(0, 0, 15, 15);
			btn.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent arg0) {

					try {
						if (is_it_ready == true) {
							DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());

							if (myTurn) {
								String clicked_String = arg0.getComponent().getName();
								//System.out.println("Clicked :" + clicked_String);
								int clicked = Integer.parseInt(clicked_String);

								int row = clicked % 10;
								int column = clicked / 10;
								arg0.getComponent().setEnabled(false);

								if (enemyTable[column][row] == 1) {
									arg0.getComponent().setBackground(Color.RED);
									outStream.writeUTF("9ad0214eed7fc36c3bebc62d096574aa" + "change "+arg0.getComponent().getName()+" red");
									enemyTable[column][row] =0;

									
									  if(isFinish()) {
									  JOptionPane.showMessageDialog(null,"Oyun Bitti Kazanan "+title,"BattleShip",1); 
									  outStream.writeUTF("9ad0214eed7fc36c3bebc62d096574aa" + "finish");
									 
									  dispose();
									 
									  }
									 

								} else {
									arg0.getComponent().setBackground(Color.WHITE);
									outStream.writeUTF("9ad0214eed7fc36c3bebc62d096574aa" +"turn");
									txt_sira.setVisible(false);
									myTurn = false;
									
								}
							} else {
								JOptionPane.showMessageDialog(null, "Sýra Sizde Deðil!!! ", "BattleShip", 1);
							}

						}
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			});
			if (i == 0) {
				rakipPanel.add(square);

				// square.setBackground(Color.LIGHT_GRAY);
			}

			else if (i < 11) {
				rakipPanel.add(new JLabel(j++ + "", SwingConstants.CENTER));
			} else if (i % 11 == 0) {
				rakipPanel.add(new JLabel(charList.get(z++) + "", SwingConstants.CENTER));
			} else {
				rakipPanel.add(btn);
				btn.setBackground(Color.LIGHT_GRAY);
				btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				btn.setName((count++) + "");

				// rakipPanel.add(square);
				// square.setBackground(Color.LIGHT_GRAY);
				// square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				// square.setName((count++)+"");

			}

		}

	}

	private void createSeperator() {
		JPanel seperator = new JPanel();
		seperator.setBorder(new LineBorder(Color.GRAY, 2));
		seperator.setPreferredSize(boardSize);
		seperator.setBounds(850, 0, 10, 800);
		seperator.setBackground(Color.GRAY);
		layeredPane.add(seperator);
	}

	public boolean isFinish() {
		for (int i = 0; i < enemyTable.length; i++) {
			for (int j = 0; j < enemyTable.length; j++) {
				if (enemyTable[i][j] == 1) {
					return false;

				}
			}

		}
		return true;
	}

	private void checkShip() {
		for (int i = 0; i < panel.getComponentCount(); i++) {
			System.out.println(panel.getComponent(i).getName());
		}
	}
}
