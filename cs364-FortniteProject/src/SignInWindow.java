import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class SignInWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JButton homepage;

	//sql statements
	private final String searchDbUsers = "SELECT DbUsername FROM User WHERE DbUsername = ?";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignInWindow frame = new SignInWindow(new User("","", 0, ""));
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
	public SignInWindow(User user) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 339, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setText("Username");
		txtUsername.setToolTipText("Enter your Username");
		txtUsername.setBounds(118, 83, 86, 20);
		contentPane.add(txtUsername);
		
		txtPassword = new JPasswordField();
		txtPassword.setText("password");
		txtPassword.setToolTipText("Enter password");
		txtPassword.setBounds(118, 113, 86, 20);
		contentPane.add(txtPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//login clicked... search database for username and check if password matches
				Database db = new Database();
				boolean isValid = true; //might not need
				String epicHandle ="";
				String acountIdQuery = "SELECT AccountIds FROM UserAccountId WHERE DbUsername = '" + txtUsername.getText() +"'";
				ResultSet rs;
				System.out.println(acountIdQuery);
				if (txtUsername.getText() == "") {
					isValid = false;
				}
				try {
					rs = db.runQuery(acountIdQuery);
					if (rs.isClosed()) {
						isValid = false;
					} else {
						String accountId = rs.getString(1);
						String epicUserQuery = "SELECT EpicUsername FROM EpicLifetimeStats WHERE AccountId = '" + accountId + "'";
						rs = db.runQuery(epicUserQuery);
						if (rs.isClosed()) {
							isValid = false;
						} else {
							epicHandle = rs.getString(1);
							if (isValid) {
								User loginInfo = new User(txtUsername.getText().toString(), txtPassword.getPassword().toString(), 0, epicHandle);
								SearchWindow win = new SearchWindow(loginInfo);
								win.setVisible(true);
								dispose();	
							}
						
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				


				
				db.closeConnection();
			}
		});
		btnLogin.setBounds(124, 144, 74, 23);
		contentPane.add(btnLogin);
		
		homepage = new JButton("Homepage");
		homepage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Homepage win = new Homepage(user);
				win.setVisible(true);
				dispose();
			}
		});
		homepage.setBounds(109, 11, 105, 23);
		contentPane.add(homepage);
		
		JLabel lblPhoto = new JLabel("photo");
		lblPhoto.setIcon(new ImageIcon("N:\\Fall 2018\\CS 364\\cs364-FortniteProject\\images\\signinsccreensaver.jpg"));
		lblPhoto.setBounds(0, -8, 329, 200);
		contentPane.add(lblPhoto);
	}
}
