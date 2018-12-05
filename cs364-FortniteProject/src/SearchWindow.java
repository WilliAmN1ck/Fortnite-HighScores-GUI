import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import java.awt.Component;

public class SearchWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtTypeTheEpic;
	private JTextField usernameField;
	private JTextField name1;
	private JTextField name2;
	private JTextField name3;
	private JTextField name4;
	private JTextField name5;
	private JTextField score5;
	private JTextField score4;
	private JTextField score3;
	private JTextField score2;
	private JTextField score1;
	private JTextField nameCol;
	private JTextField scoreCol;
	private JTextField winCol;
	private JTextField killCol;
	private JTextField wins1;
	private JTextField wins2;
	private JTextField wins3;
	private JTextField wins4;
	private JTextField wins5;
	private JTextField kills1;
	private JTextField kills2;
	private JTextField kills3;
	private JTextField kills4;
	private JTextField kills5;
	private JTextField errorMsg;
	private JSpinner spinner;
	private JSpinner modeSpinner;
	private JRadioButton statsFilter, winsFilter;
	private static String apiKey = "03e71539-3aff-4c15-9724-fe97e7d39552";
	
	//sql query statements
	private final String searchEpicUsers = "SELECT EpicUsername FROM EpicLifetimeStats WHERE EpicUsername = ?";
	private final String searchOneUser = "SELECT EpicUsername, Score, Wins, Kills FROM EpicLifetimeStats WHERE EpicUsername = ?";
	
	private final String insertEpicLifetime =  "INSERT INTO EpicLifetimeStats (EpicUsername, AccountId, Top5s, Top3s, Top6s, Top10, Top12s, Top25s, Score, "
			+ "MatchesPlayed, Wins, Win%, Kills, Kd) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	private final String insertRecentMatch = "INSERT INTO RecentMatches (EpicUsername, matchId, DateCollected, Kills, Matches, Score, Playlist, "
			+ "TrnRating, top1) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private final String insertGameMode =  "INSERT INTO GameModeStats (EpicUsername, GameMode, Score, Top1, Top3, Top5, Top6, Top10, Top12, Top25, Deaths, Kills, "
			+ "Matches, WinRatio, ScorePerMatch, Kd, KillsPerGame) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	//complex query sql strings and statements
	// complex #1
	
	//complex #2
	
	//complex #3
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchWindow frame = new SearchWindow(new User("temp", "temp", 0, ""));
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
	//TODO: add radiobuttons for different search criteria
	//show all stats radiobtn
	//show *insert different options here*
	//have a comparisonbtn that will pop up another search window
	//after searching should pop up a new statistics window
	public SearchWindow(User user) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 314, 329);
		contentPane = new JPanel();
		contentPane.setBackground(Color.RED);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSearch = new JButton("Search");
		
		btnSearch.setBounds(104, 66, 89, 14);
		contentPane.add(btnSearch);
		
		//add actionEvent listener here for if enter is pressed and then go to search function
		usernameField = new JTextField();
		usernameField.setText("Username");
		usernameField.setForeground(Color.WHITE);
		usernameField.setToolTipText("Type username here");
		usernameField.setOpaque(false);
		usernameField.setBackground(Color.WHITE);
		usernameField.setBounds(97, 40, 96, 20);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		txtTypeTheEpic = new JTextField();
		txtTypeTheEpic.setEditable(false);
		txtTypeTheEpic.setBorder(null);
		txtTypeTheEpic.setBackground(Color.RED);
		txtTypeTheEpic.setForeground(new Color(0, 0, 0));
		txtTypeTheEpic.setText("Type the Epic username below");
		txtTypeTheEpic.setBounds(56, 0, 186, 14);
		contentPane.add(txtTypeTheEpic);
		txtTypeTheEpic.setColumns(10);
		
		name1 = new JTextField();
		name1.setForeground(Color.CYAN);
		name1.setBorder(null);
		name1.setOpaque(false);
		name1.setBounds(10, 113, 86, 20);
		contentPane.add(name1);
		name1.setColumns(10);
		
		name2 = new JTextField();
		name2.setForeground(Color.CYAN);
		name2.setBorder(null);
		name2.setOpaque(false);
		name2.setBounds(10, 144, 86, 20);
		contentPane.add(name2);
		name2.setColumns(10);
		
		name3 = new JTextField();
		name3.setForeground(Color.CYAN);
		name3.setBorder(null);
		name3.setOpaque(false);
		name3.setBounds(10, 177, 86, 20);
		contentPane.add(name3);
		name3.setColumns(10);
		
		name4 = new JTextField();
		name4.setForeground(Color.CYAN);
		name4.setBorder(null);
		name4.setOpaque(false);
		name4.setBounds(10, 208, 86, 20);
		contentPane.add(name4);
		name4.setColumns(10);
		
		name5 = new JTextField();
		name5.setForeground(Color.CYAN);
		name5.setBorder(null);
		name5.setOpaque(false);
		name5.setBounds(10, 239, 86, 20);
		contentPane.add(name5);
		name5.setColumns(10);
		
		score5 = new JTextField();
		score5.setForeground(Color.RED);
		score5.setBorder(null);
		score5.setOpaque(false);
		score5.setBounds(106, 239, 46, 20);
		contentPane.add(score5);
		score5.setColumns(10);
		
		score4 = new JTextField();
		score4.setForeground(Color.RED);
		score4.setBorder(null);
		score4.setOpaque(false);
		score4.setBounds(106, 208, 46, 20);
		contentPane.add(score4);
		score4.setColumns(10);
		
		score3 = new JTextField();
		score3.setForeground(Color.RED);
		score3.setBorder(null);
		score3.setOpaque(false);
		score3.setBounds(106, 177, 46, 20);
		contentPane.add(score3);
		score3.setColumns(10);
		
		score2 = new JTextField();
		score2.setForeground(Color.RED);
		score2.setBorder(null);
		score2.setOpaque(false);
		score2.setBounds(106, 144, 46, 20);
		contentPane.add(score2);
		score2.setColumns(10);
		
		score1 = new JTextField();
		score1.setForeground(Color.RED);
		score1.setBorder(null);
		score1.setOpaque(false);
		score1.setBounds(106, 113, 46, 20);
		contentPane.add(score1);
		score1.setColumns(10);
		
		nameCol = new JTextField();
		nameCol.setForeground(Color.RED);
		nameCol.setText("Username");
		nameCol.setOpaque(false);
		nameCol.setBounds(10, 83, 86, 20);
		contentPane.add(nameCol);
		
		scoreCol = new JTextField();
		scoreCol.setForeground(Color.RED);
		scoreCol.setText("Score");
		scoreCol.setOpaque(false);
		scoreCol.setBounds(106, 82, 46, 20);
		contentPane.add(scoreCol);
		
		winCol = new JTextField();
		winCol.setText("Wins");
		winCol.setForeground(Color.RED);
		winCol.setOpaque(false);
		winCol.setBounds(162, 83, 46, 20);
		contentPane.add(winCol);
		
		killCol = new JTextField();
		killCol.setForeground(Color.RED);
		killCol.setText("Kills");
		killCol.setOpaque(false);
		killCol.setBounds(218, 83, 46, 20);
		contentPane.add(killCol);
		
		
		wins1 = new JTextField();
		wins1.setForeground(Color.GREEN);
		wins1.setBorder(null);
		wins1.setOpaque(false);
		wins1.setBounds(162, 113, 46, 20);
		contentPane.add(wins1);
		wins1.setColumns(10);
		
		wins2 = new JTextField();
		wins2.setForeground(Color.GREEN);
		wins2.setBorder(null);
		wins2.setOpaque(false);
		wins2.setBounds(162, 144, 46, 20);
		contentPane.add(wins2);
		wins2.setColumns(10);
		
		wins3 = new JTextField();
		wins3.setForeground(Color.GREEN);
		wins3.setBorder(null);
		wins3.setOpaque(false);
		wins3.setBounds(162, 177, 46, 20);
		contentPane.add(wins3);
		wins3.setColumns(10);
		
		wins4 = new JTextField();
		wins4.setForeground(Color.GREEN);
		wins4.setBorder(null);
		wins4.setOpaque(false);
		wins4.setBounds(162, 208, 46, 20);
		contentPane.add(wins4);
		wins4.setColumns(10);
		
		wins5 = new JTextField();
		wins5.setForeground(Color.GREEN);
		wins5.setBorder(null);
		wins5.setOpaque(false);
		wins5.setBounds(162, 239, 46, 20);
		contentPane.add(wins5);
		wins5.setColumns(10);
		
		kills1 = new JTextField();
		kills1.setForeground(Color.WHITE);
		kills1.setBorder(null);
		kills1.setOpaque(false);
		kills1.setBounds(218, 113, 46, 20);
		contentPane.add(kills1);
		kills1.setColumns(10);
		
		kills2 = new JTextField();
		kills2.setForeground(Color.WHITE);
		kills2.setBorder(null);
		kills2.setOpaque(false);
		kills2.setBounds(218, 144, 46, 20);
		contentPane.add(kills2);
		kills2.setColumns(10);
		
		kills3 = new JTextField();
		kills3.setForeground(Color.WHITE);
		kills3.setBorder(null);
		kills3.setOpaque(false);
		kills3.setBounds(218, 177, 46, 20);
		contentPane.add(kills3);
		kills3.setColumns(10);
		
		kills4 = new JTextField();
		kills4.setForeground(Color.WHITE);
		kills4.setBorder(null);
		kills4.setOpaque(false);
		kills4.setBounds(218, 208, 46, 20);
		contentPane.add(kills4);
		kills4.setColumns(10);
		
		kills5 = new JTextField();
		kills5.setForeground(Color.WHITE);
		kills5.setBorder(null);
		kills5.setOpaque(false);
		kills5.setBounds(218, 239, 46, 20);
		contentPane.add(kills5);
		kills5.setColumns(10);
		
		statsFilter = new JRadioButton("Stats Filter");
		statsFilter.setForeground(Color.RED);
		statsFilter.setOpaque(false);
		statsFilter.setBounds(205, 21, 92, 20);
		contentPane.add(statsFilter);
		
		errorMsg = new JTextField();
		errorMsg.setBorder(null);
		errorMsg.setVisible(false);
		
		winsFilter = new JRadioButton("Wins Filter");
		winsFilter.setBorder(null);
		winsFilter.setOpaque(false);
		winsFilter.setForeground(Color.RED);
		winsFilter.setBounds(205, 59, 92, 23);
		contentPane.add(winsFilter);
		
		modeSpinner = new JSpinner();
		modeSpinner.setAlignmentX(Component.LEFT_ALIGNMENT);
		modeSpinner.setModel(new SpinnerListModel(new String[] {"Solo", "Duos", "Squads"}));
		modeSpinner.setBounds(10, 40, 86, 20);
		contentPane.add(modeSpinner);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerListModel(new String[] {"MatchesPlayed", "Kills", "Wins", "WinPercentage", "Kd", "Score", "Top3s", "Top5s", "Top12s", "Top25s"}));
		spinner.setBounds(197, 40, 100, 20);
		contentPane.add(spinner);
		
		//change user view if guest status
		if(user.isGuest) {
			statsFilter.setVisible(false);
			winsFilter.setVisible(false);
			spinner.setVisible(false);
			modeSpinner.setVisible(false);
		}
		
		errorMsg.setFont(new Font("Tahoma", Font.BOLD, 16));
		errorMsg.setText("ERROR: Could not find username");
		errorMsg.setForeground(Color.ORANGE);
		errorMsg.setOpaque(false);
		errorMsg.setBounds(11, 245, 276, 34);
		contentPane.add(errorMsg);
		
		JButton homeBtn = new JButton("Homepage");
		homeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Homepage win = new Homepage(user);
				win.setVisible(true);
				dispose();
			}
		});
		homeBtn.setBounds(101, 18, 96, 20);
		contentPane.add(homeBtn);
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//check if the compareBtn is checked -- will do compareBtn if both are checked
				errorMsg.setVisible(false);
				boolean canSearch = false;
				Database db = new Database();
				MyHttpURLConnection api = null;
				ResultSet rs =null;
				String nameToSearch = usernameField.getText();
				int complexQuery = 0;
				boolean userExists = false;
				boolean nameBlank = false;
				if(nameToSearch.equalsIgnoreCase("Username") || nameToSearch == "") {
					nameBlank = true;
					nameToSearch = user.epicUsername;
				} 	
				userExists =db.checkIfUserExists(searchEpicUsers, nameToSearch);
				
				if (nameBlank && !statsFilter.isSelected() && !winsFilter.isSelected()) { //name is blank and stats filter is not selected -- pop up error
					//pop up enter a username message
					System.out.println("name blank and stats filter not selected");
					canSearch = false;
				} else if (!userExists && !statsFilter.isSelected() && !winsFilter.isSelected()) { // a name was typed in that wasnt in the db
					//if the name exists, use the api to retrieve the stats 
					System.out.println("user does not exist in database and stats filter not selected");
					api = new MyHttpURLConnection(apiKey, user.platform, nameToSearch);
					if (api.userExists) {
						canSearch = true;
					} else {
						//if no name exists, pop up name does not exists error
					}
					
				} else { 
					//user is in the database or its a statsFilter
					System.out.println("user is in the database or its a stats filter");
					if (!userExists) { //if user doesn't exist it is a statfilter or winsFilter
						nameToSearch = user.epicUsername;
						api = new MyHttpURLConnection(apiKey, user.platform, nameToSearch);
						if (api.userExists) {
							canSearch = true;
						} else {
							canSearch = false;
							//if no name exists, pop up name does not exists error
						}
					} else { //user exists
						canSearch = true;
					}
					//else user typed in the name in the search bar and that is the one to search for
				}
				//canSearch will be true if the user's data was able to be pulled
				if(canSearch) {
					clearInfo();//clears any data in the textfields
					//might be able to call this whenever a search is entered
					/*
	private JTextField nameCol;
	private JTextField scoreCol;
	private JTextField winCol;
	private JTextField killCol;
					 */
					if (statsFilter.isSelected()) { //need to open a drop down menu to select from -- query 2
						
						complexQuery = 2;
						boolean isDouble = false;
						String statToFilter = spinner.getValue().toString();
						System.out.println("querying database with a stats filter for username " + user.epicUsername + " for stat " + statToFilter);
						
						String complex2 = "SELECT EpicUsername, " + statToFilter + " FROM EpicLifetimeStats GROUP BY EpicUsername HAVING " + statToFilter
								+ " > (SELECT " + statToFilter + " FROM EpicLifetimeStats WHERE EpicUsername = '" 
								+ user.epicUsername + "') ORDER BY " + statToFilter + " DESC LIMIT 5";
						
						if(statToFilter.equals("Score")) {
							//do not need to change column names -- just search the top 5 of the normal col stats
							
							System.out.println(complex2);
							compareSearch(db, complex2, false);
						} else {
							//need to change score column name to the stat to filter name if showing more than one column of values..
							//otherwise just need to change score column name
							scoreCol.setText(statToFilter);
							if (statToFilter.equals("WinPercentage")) {
								isDouble = true;
							}
							compareSearch(db, complex2, isDouble);
						}
					} else if (winsFilter.isSelected()) { //do not need to relabel columns
						complexQuery = 3;
						String modeToFilter = modeSpinner.getValue().toString();
						String modeInApiFormat = "";
						//change score col to say wins if just displaying wins
						scoreCol.setText("Wins");
						if (modeToFilter.equals("Solo")) { //solo
							modeInApiFormat = "p2";
						} else if (modeToFilter.equals("Squads")) { //squads 
							modeInApiFormat = "p9";
						} else { //mode to filter == "p10" = Duos
							modeInApiFormat = "p10";
						}
						winCol.setText("Kills");
						killCol.setText("Deaths");
						String complex3 = "SELECT EpicUsername, sum(Top1), Kills, Deaths FROM GameModeStats WHERE GameMode = '" + modeInApiFormat + "' " 
								+ "GROUP BY EpicUsername HAVING sum(Top1) > (SELECT sum(Top1) FROM GameModeStats WHERE EpicUsername = '"
								+ user.epicUsername + "' GROUP BY gameMode) ORDER BY sum(top1) DESC LIMIT 5";
						System.out.println(complex3);
						gameModeWins(db, complex3);
					} // else just a regular stats look up of one person
					else {
						//go to to search function and search database
						System.out.println("normal search for " + nameToSearch);
						try {
							rs = db.runQuery(searchOneUser, nameToSearch);
							addToTextFields(rs);
							//fill in stats
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				} else {
					errorMsg.setVisible(true);
				}
				db.closeConnection();
			}
		});
		

		
		JLabel lblBgpic = new JLabel("bgpic");
		lblBgpic.setIcon(new ImageIcon("N:\\Fall 2018\\CS 364\\cs364-FortniteProject\\images\\searchwindowbg.jpg"));
		lblBgpic.setBounds(0, 18, 306, 272);
		contentPane.add(lblBgpic);
		
		//query and display the top 5 players that are currently stored in the database
		displayTop5();
		
	}

	private ResultSet compareSearch(Database db, String complex2, boolean isDouble) { //query 2
		ResultSet rs =null;

		try {
			rs = db.runQuery(complex2);
			int numUsers = 0;//tells which rows to manipulate in the gui
			String temp = "";
			int num = 0;
			//displays info into textfields -- could be own method
			while(rs.next()) {
				numUsers++;
				if (numUsers == 1) {
					name1.setText(rs.getString(1));
					if (isDouble) {
						score1.setText(rs.getDouble(2) + "");
					} else {
						score1.setText(rs.getInt(2) + "");
					}
					
					//kills1.setText(rs.getInt(3) + "");
					//deaths1.setText(rs.getInt(4) + "");
				} else if (numUsers == 2) {
					name2.setText(rs.getString(1));
					if (isDouble) {
						score2.setText(rs.getDouble(2) + "");
					} else {
						score2.setText(rs.getInt(2) + "");
					}
					//kills2.setText(rs.getInt(3) + "");
					//deaths2.setText(rs.getInt(4) + "");
				} else if (numUsers == 3) {
					name3.setText(rs.getString(1));
					if (isDouble) {
						score3.setText(rs.getDouble(2) + "");
					} else {
						score3.setText(rs.getInt(2) + "");
					}
					//kills3.setText(rs.getInt(3) + "");
					//deaths3.setText(rs.getInt(4) + "");
				} else if (numUsers == 4) {
					name4.setText(rs.getString(1));
					if (isDouble) {
						score4.setText(rs.getDouble(2) + "");
					} else {
						score4.setText(rs.getInt(2) + "");
					}
					//kills4.setText(rs.getInt(3) + "");
					//deaths4.setText(rs.getInt(4) + "");
				} else if (numUsers == 5) {
					name5.setText(rs.getString(1));
					if (isDouble) {
						score5.setText(rs.getDouble(2) + "");
					} else {
						score5.setText(rs.getInt(2) + "");
					}
					//kills5.setText(rs.getInt(3) + "");
					//deaths5.setText(rs.getInt(4) + "");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	private ResultSet gameModeWins(Database db, String complex3) { //query 3
		ResultSet rs =null;
		try {
			rs = db.runQuery(complex3);
			int numUsers = 0;//tells which rows to manipulate in the gui
			String temp = "";
			int num = 0;
			while(rs.next()) {
				numUsers++;
				if (numUsers == 1) {
					name1.setText(rs.getString(1));
					score1.setText(rs.getInt(2) + "");
					wins1.setText(rs.getInt(3) + "");
					kills1.setText(rs.getInt(4) + "");
				} else if (numUsers == 2) {
					name2.setText(rs.getString(1));
					score2.setText(rs.getInt(2) + "");
					wins2.setText(rs.getInt(3) + "");
					kills2.setText(rs.getInt(4) + "");
				} else if (numUsers == 3) {
					name3.setText(rs.getString(1));
					score3.setText(rs.getInt(2) + "");
					wins3.setText(rs.getInt(3) + "");
					kills3.setText(rs.getInt(4) + "");
				} else if (numUsers == 4) {
					name4.setText(rs.getString(1));
					score4.setText(rs.getInt(2) + "");
					wins4.setText(rs.getInt(3) + "");
					kills4.setText(rs.getInt(4) + "");
				} else if (numUsers == 5) {
					name5.setText(rs.getString(1));
					score5.setText(rs.getInt(2) + "");
					wins5.setText(rs.getInt(3) + "");
					kills5.setText(rs.getInt(4) + "");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * clears anything entered into text boxes
	 */
	private void clearInfo() {
		scoreCol.setText("Score");
		winCol.setText("Wins");
		killCol.setText("Kills");
		 usernameField.setText("Username");
		 name1.setText("");
		 name2.setText("");
		 name3.setText("");
		 name4.setText("");
		 name5.setText("");
		 score5.setText("");
		 score4.setText("");
		 score3.setText("");
		 score2.setText("");
		 score1.setText("");
		 wins1.setText("");
		 wins2.setText("");
		 wins3.setText("");
		 wins4.setText("");
		 wins5.setText("");
		 kills1.setText("");
		 kills2.setText("");
		 kills3.setText("");
		 kills4.setText("");
		 kills5.setText("");
		 //statsFilter.setSelected(false);
		 //winsFilter.setSelected(false);
		 errorMsg.setVisible(false);;
	}
	
	private void displayTop5() { //query 1
		String q = "SELECT EpicUsername, Score, Wins, Kills FROM EpicLifetimeStats ORDER BY Score DESC LIMIT 5";
		Database db = new Database();
		//name, wins, kills, deaths == column order
		try {
			ResultSet rs = db.runQuery(q);
			int numUsers = 0;//tells which rows to manipulate in the gui
			String temp = "";
			int num = 0;
			while(rs.next()) {
				numUsers++;
				if (numUsers == 1) {
					name1.setText(rs.getString(1));
					score1.setText(rs.getInt(2) + "");
					wins1.setText(rs.getInt(3) + "");
					kills1.setText(rs.getInt(4) + "");
				} else if (numUsers == 2) {
					name2.setText(rs.getString(1));
					score2.setText(rs.getInt(2) + "");
					wins2.setText(rs.getInt(3) + "");
					kills2.setText(rs.getInt(4) + "");
				} else if (numUsers == 3) {
					name3.setText(rs.getString(1));
					score3.setText(rs.getInt(2) + "");
					wins3.setText(rs.getInt(3) + "");
					kills3.setText(rs.getInt(4) + "");
				} else if (numUsers == 4) {
					name4.setText(rs.getString(1));
					score4.setText(rs.getInt(2) + "");
					wins4.setText(rs.getInt(3) + "");
					kills4.setText(rs.getInt(4) + "");
				} else if (numUsers == 5) {
					name5.setText(rs.getString(1));
					score5.setText(rs.getInt(2) + "");
					wins5.setText(rs.getInt(3) + "");
					kills5.setText(rs.getInt(4) + "");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addToTextFields(ResultSet rs) throws SQLException {
		int numUsers = 0;//tells which rows to manipulate in the gui
		String temp = "";
		int num = 0;
		while(rs.next()) {
			numUsers++;
			if (numUsers == 1) {
				name1.setText(rs.getString(1));
				score1.setText(rs.getInt(2) + "");
				wins1.setText(rs.getInt(3) + "");
				kills1.setText(rs.getInt(4) + "");
			} else if (numUsers == 2) {
				name2.setText(rs.getString(1));
				score2.setText(rs.getInt(2) + "");
				wins2.setText(rs.getInt(3) + "");
				kills2.setText(rs.getInt(4) + "");
			} else if (numUsers == 3) {
				name3.setText(rs.getString(1));
				score3.setText(rs.getInt(2) + "");
				wins3.setText(rs.getInt(3) + "");
				kills3.setText(rs.getInt(4) + "");
			} else if (numUsers == 4) {
				name4.setText(rs.getString(1));
				score4.setText(rs.getInt(2) + "");
				wins4.setText(rs.getInt(3) + "");
				kills4.setText(rs.getInt(4) + "");
			} else if (numUsers == 5) {
				name5.setText(rs.getString(1));
				score5.setText(rs.getInt(2) + "");
				wins5.setText(rs.getInt(3) + "");
				kills5.setText(rs.getInt(4) + "");
			}
		}
	}
}
