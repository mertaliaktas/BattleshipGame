import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;

public class Game extends JFrame {

	private JPanel contentPane;
	private JFrame gameFrame;
	private JLayeredPane layeredPane;
    Dimension boardSize;
    JPanel panel;
    JLabel myTahta_lbl;
    JLabel enemyTahta_lbl;
    
    ArrayList<String> charList;
    
    JPanel rakipPanel;
  

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game frame = new Game();
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
	public Game() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 1000);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setUndecorated(true);
		setVisible(true);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		boardSize = new Dimension(400, 400);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.BLACK, 2));
		panel.setPreferredSize(boardSize);
		panel.setBounds(40,40, boardSize.width-4, boardSize.height-4);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(11, 11));
		//panel.add(leftPanel);
		
		rakipPanel = new JPanel();
		rakipPanel.setBorder(new LineBorder(Color.BLACK, 2));
		rakipPanel.setPreferredSize(boardSize);
		rakipPanel.setBounds(920,40, boardSize.width-4, boardSize.height-4);
		contentPane.add(rakipPanel);
		rakipPanel.setLayout(new GridLayout(11, 11));
		

		myTahta_lbl = new JLabel();
		myTahta_lbl.setText("Senin Tahtan");
		myTahta_lbl.setBounds(panel.getX()+(panel.getWidth()/2)-50, panel.getY()-50, 100, 50);
		contentPane.add(myTahta_lbl);
		
		enemyTahta_lbl = new JLabel();
		enemyTahta_lbl.setText("Rakip Tahta");
		enemyTahta_lbl.setBounds(rakipPanel.getX()+(rakipPanel.getWidth()/2)-50, rakipPanel.getY()-50, 100, 50);
		contentPane.add(enemyTahta_lbl);
		
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
		
		
        
		
		createSquareOnTable();
		createSquareOnTable2();
		createSeperator();
		createShips();
		
		
	}
	
	private void createSquareOnTable() {
		int j=1;
		int z=0;
		
		for (int i = 0; i < 121; i++) {
			
			JPanel square = new JPanel(new BorderLayout(15,15));
			if(i==0) {
				panel.add(square);
				//square.setBackground(Color.LIGHT_GRAY);
			}
			
			else if(i<11 ) {
				panel.add(new JLabel(j++ +"",SwingConstants.CENTER));
			}
			else if(i%11==0) {
				panel.add(new JLabel(charList.get(z++)+"",SwingConstants.CENTER));
			}
			else {
				panel.add(square);
				square.setBackground(Color.LIGHT_GRAY);
				square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
			
			
			
			
			
		}
		
	}
	
	private void createShips() {
		JPanel shipPanel = new JPanel();
		//shipPanel.setBorder(new LineBorder(Color.DARK_GRAY, 10));
		shipPanel.setPreferredSize(boardSize);
		shipPanel.setBounds(40,500, 185, 35);
		contentPane.add(shipPanel);
		shipPanel.setLayout(new GridLayout(0, 5));
		for (int i = 0; i < 5; i++) {
			JPanel square = new JPanel(new BorderLayout(0,0));
			square.setBackground(Color.BLUE);
			square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			shipPanel.add(square);
		}
		
		shipPanel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				int X=arg0.getX()+shipPanel.getX();
	            int Y=arg0.getY()+shipPanel.getY();
	            shipPanel.setFocusable(true);
	            shipPanel.setBounds(X,Y,150,30);
				
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
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	private void createSquareOnTable2() {
		int j=1;
		int z=0;
		
		for (int i = 0; i < 121; i++) {
			
			JPanel square = new JPanel(new BorderLayout(15,15));
			if(i==0) {
				rakipPanel.add(square);
				//square.setBackground(Color.LIGHT_GRAY);
			}
			
			else if(i<11 ) {
				rakipPanel.add(new JLabel(j++ +"",SwingConstants.CENTER));
			}
			else if(i%11==0) {
				rakipPanel.add(new JLabel(charList.get(z++)+"",SwingConstants.CENTER));
			}
			else {
				rakipPanel.add(square);
				square.setBackground(Color.LIGHT_GRAY);
				square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
			
			
			
			
			
		}
		
	}
	
	private void createSeperator() {
		JPanel seperator = new JPanel();
		seperator.setBorder(new LineBorder(Color.GRAY, 2));
		seperator.setPreferredSize(boardSize);
		seperator.setBounds(850,0, 10, 800);
		seperator.setBackground(Color.GRAY);
		contentPane.add(seperator);
	}
}
