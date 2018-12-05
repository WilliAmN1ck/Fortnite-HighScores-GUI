import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;

public class RegisterWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JPasswordField passConfirmation;

	JRadioButton pcRadio;
	JRadioButton xboxRadio;
	JRadioButton ps4Radio;
	//sql query statements
	private final String searchDbUsers = "SELECT DbUsername FROM User WHERE DbUsername = ?";
	private final String insertUser = "INSERT INTO User (DbUsername, DbPassword) VALUES (?, ?);";
	private final String insertUserAccountId = "INSERT INTO User-AccountId (DbUsername, AccountIds) VALUES (?,?);";
	private JTextArea txtUsernameAlreadyExists;
	private static String apiKey = "03e71539-3aff-4c15-9724-fe97e7d39552";
	private JTextField epicName;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterWindow frame = new RegisterWindow(new User("","", 0, ""));
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
	//TODO: add a pc, xbox or ps4 radiobutton.... and implement xbox,ps4 stat retrieval
	public RegisterWindow(User user) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 339, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		epicName = new JTextField();
		epicName.setBounds(103, 7, 116, 20);
		epicName.setToolTipText("Enter your epic username here");
		contentPane.add(epicName);
		
		txtUsername = new JTextField();
		txtUsername.setToolTipText("Enter your Username");
		txtUsername.setBounds(103, 38, 116, 20);
		contentPane.add(txtUsername);
		
		txtPassword = new JPasswordField();
		txtPassword.setToolTipText("Enter password");
		txtPassword.setBounds(103, 69, 116, 20);
		contentPane.add(txtPassword);
		//error message
		txtUsernameAlreadyExists = new JTextArea();
		txtUsernameAlreadyExists.setForeground(Color.RED);
		txtUsernameAlreadyExists.setOpaque(false);
		txtUsernameAlreadyExists.setText("Username already exists!");
		txtUsernameAlreadyExists.setBounds(67, 155, 246, 20);
		txtUsernameAlreadyExists.setVisible(false);
		contentPane.add(txtUsernameAlreadyExists);
		
		JButton btnLogin = new JButton("Register");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean isValid = true;
				//login clicked... search database for username and check if password matches
				String epicUser = epicName.getText();
				if (epicUser.equals("")) {
					isValid = false;
				}
				User user = new User(txtUsername.getText().toString(), txtPassword.getPassword().toString(), 0, epicUser);
				
				Database db = new Database();
				
				if (!db.checkIfUserExists(searchDbUsers, user.dbUsername) && isValid) { //user does not exist so need to add it to the database
					txtUsernameAlreadyExists.setVisible(false);
					//make sure error message goes away
					try {
						int mode = 0;
						if (xboxRadio.isSelected()) {
							mode =1;
						} else if (ps4Radio.isSelected()) {
							mode = 2;
						}
						//add user to db and populate with the stats in the addUserToDB
						db.addUserToDb(insertUser, user, mode, apiKey);
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else { //already exists.. display error message
					//display error message
					txtUsernameAlreadyExists.setVisible(true);
					isValid = false;
				}
				if (isValid) {
					db.closeConnection();
					SearchWindow win = new SearchWindow(user);
					win.setVisible(true);
					dispose();
				} else {
					db.closeConnection();
				}
			}
		});
		
		passConfirmation = new JPasswordField();
		passConfirmation.setToolTipText("confirm pass");
		passConfirmation.setBounds(103, 100, 116, 20);
		contentPane.add(passConfirmation);
		btnLogin.setBounds(227, 131, 86, 23);
		contentPane.add(btnLogin);
		
		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Homepage home = new Homepage(new User("","", 0, ""));
				home.setVisible(true);
				dispose();
				
			}
		});
		btnMainMenu.setBounds(10, 131, 99, 23);
		contentPane.add(btnMainMenu);
		
		pcRadio = new JRadioButton("Pc");
		pcRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				xboxRadio.setSelected(false);
				ps4Radio.setSelected(false);
			}
		});
		pcRadio.setBounds(11, 37, 48, 23);
		contentPane.add(pcRadio);
		
		xboxRadio = new JRadioButton("Xbox");
		xboxRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ps4Radio.setSelected(false);
				pcRadio.setSelected(false);
			}
		});
		xboxRadio.setBounds(11, 68, 55, 23);
		contentPane.add(xboxRadio);
		
		ps4Radio = new JRadioButton("PS4");
		ps4Radio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xboxRadio.setSelected(false);
				pcRadio.setSelected(false);
			}
		});
		ps4Radio.setBounds(11, 99, 55, 23);
		contentPane.add(ps4Radio);
		

		

		
		JLabel lblPhoto = new JLabel("photo");
		lblPhoto.setIcon(new ImageIcon("N:\\Fall 2018\\CS 364\\cs364-FortniteProject\\images\\signinsccreensaver.jpg"));
		lblPhoto.setBounds(0, -8, 329, 200);
		contentPane.add(lblPhoto);
	}
}
