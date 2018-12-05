import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Homepage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel textField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Homepage frame = new Homepage(new User("", "", 0, ""));
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
	public Homepage(User user) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JLabel();
		textField.setBounds(140, 11, 166, 20);
		textField.setText("Welcome to Nick's FN Tracker!");
		contentPane.add(textField);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.setBorderPainted(false);
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignInWindow win = new SignInWindow(user);
				win.setVisible(true);
				dispose();
			}
		});
		btnSignIn.setBounds(169, 71, 89, 23);
		contentPane.add(btnSignIn);
		
		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//create account screen
				RegisterWindow win = new RegisterWindow(user);
				win.setVisible(true);
				dispose();
			}
		});
		btnCreateAccount.setBounds(169, 105, 137, 23);
		contentPane.add(btnCreateAccount);
		
		JButton btnNewButton = new JButton("Continue as Guest");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//open searchWindow
				user.isGuest = true; //set to guest permissions
				SearchWindow win = new SearchWindow(user);
				win.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(169, 139, 137, 23);
		contentPane.add(btnNewButton);
		
		JLabel pic = new JLabel("");
		pic.setBounds(31, 30, 371, 201);
		pic.setIcon(new ImageIcon("N:\\Fall 2018\\CS 364\\cs364-FortniteProject\\images\\fnscreensaver.jpg"));
		pic.setVisible(true);
		contentPane.add(pic);
	}
}
