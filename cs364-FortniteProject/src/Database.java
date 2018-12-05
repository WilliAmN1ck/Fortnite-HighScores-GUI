import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

public class Database {

	//global vars
	private String dbURL ="jdbc:sqlite:fortniteDb.db";
	private static Connection conn;
	
	private String CREATE_TABLE = "CREATE Table "; //need to add a "(" before adding columns for each table
	private String DROP_TABLE = "DROP TABLE IF EXISTS ";
	
	private String[] TABLE_NAMES = { "User", "EpicLifetimeStats", "UserAccountId", "RecentMatches", "GameModeStats"};
	private final String insertEpicLifetime =  "INSERT INTO EpicLifetimeStats (EpicUsername, AccountId, Top5s, Top3s, Top6s, Top10, Top12s, Top25s, Score, "
			+ "MatchesPlayed, Wins, WinPercentage, Kills, Kd) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	private final String insertRecentMatch = "INSERT INTO RecentMatches (EpicUsername, matchId, DateCollected, Kills, Matches, Score, Playlist, "
			+ "TrnRating, top1) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private final String insertGameMode =  "INSERT INTO GameModeStats (EpicUsername, GameMode, Score, Top1, Top3, Top5, Top6, Top10, Top12, Top25, Deaths, Kills, "
			+ "Matches, WinRatio, ScorePerMatch, Kd, KillsPerGame) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private final String insertUserAccountId = "INSERT INTO UserAccountId (DbUsername, AccountIds) VALUES (?,?);";
	
	private final String updateGameModeStats = "UPDATE GameModeStats\r\n" + 
			"   SET EpicUsername = 'EpicUsername',\r\n" + 
			"       GameMode = 'GameMode',\r\n" + 
			"       Score = 'Score',\r\n" + 
			"       Top1 = 'Top1',\r\n" + 
			"       Top3 = 'Top3',\r\n" + 
			"       Top5 = 'Top5',\r\n" + 
			"       Top6 = 'Top6',\r\n" + 
			"       Top10 = 'Top10',\r\n" + 
			"       Top12 = 'Top12',\r\n" + 
			"       Top25 = 'Top25',\r\n" + 
			"       Deaths = 'Deaths',\r\n" + 
			"       Kills = 'Kills',\r\n" + 
			"       Matches = 'Matches',\r\n" + 
			"       WinRatio = 'WinRatio',\r\n" + 
			"       ScorePerMatch = 'ScorePerMatch',\r\n" + 
			"       Kd = 'Kd',\r\n" + 
			"       KillsPerGame = 'KillsPerGame'\r\n" + 
			" WHERE EpicUsername = ?";
	
	private final String updateMatches = "UPDATE RecentMatches\r\n" + 
			"   SET EpicUsername = 'EpicUsername',\r\n" + 
			"       matchId = 'matchId',\r\n" + 
			"       DateCollected = 'DateCollected',\r\n" + 
			"       Kills = 'Kills',\r\n" + 
			"       Matches = 'Matches',\r\n" + 
			"       Score = 'Score',\r\n" + 
			"       Playlist = 'Playlist',\r\n" + 
			"       TrnRating = 'TrnRating',\r\n" + 
			"       top1 = 'top1'\r\n" + 
			" WHERE EpicUsername = '?'";
	
	private final String updateUserIds = "UPDATE [User-AccountId]\r\n" + 
			"   SET DbUsername = 'DbUsername',\r\n" + 
			"       AccountIds = 'AccountIds'\r\n" + 
			" WHERE DbUsername = '?'";
	
	//update values here -- check to make sure right amount of question marks
	
	private String updateRecentMatches = "UPDATE RecentMatches\r\n" + 
			"   SET EpicUsername = ?,\r\n" + 
			"       matchId = ?,\r\n" + 
			"       DateCollected = ?,\r\n" + 
			"       Kills = ?,\r\n" + 
			"       Matches = ?,\r\n" + 
			"       Score = ?,\r\n" + 
			"       Playlist = ?,\r\n" + 
			"       TrnRating = ?,\r\n" + 
			"       top1 = ?\r\n" + 
			" WHERE EpicUsername = ? AND \r\n" + 
			"       matchId = ? AND \r\n" + 
			"       DateCollected = ? AND \r\n" + 
			"       Kills = ? AND \r\n" + 
			"       Matches = ? AND \r\n" + 
			"       Score = ? AND \r\n" + 
			"       Playlist = ? AND \r\n" + 
			"       TrnRating = ? AND \r\n" + 
			"       top1 = ?;";
	private String updateLifetime = "UPDATE EpicLifetimeStats\r\n" + 
			"   SET EpicUsername = ?,\r\n" + 
			"       AccountId = ?,\r\n" + 
			"       Top5s = ?,\r\n" + 
			"       Top3s = ?,\r\n" + 
			"       Top6s = ?,\r\n" + 
			"       Top10 = ?,\r\n" + 
			"       Top12s = ?,\r\n" + 
			"       Top25s = ?,\r\n" + 
			"       Score = ?,\r\n" + 
			"       MatchesPlayed = ?,\r\n" + 
			"       Wins = ?,\r\n" + 
			"       [Win%] = '?%',\r\n" + 
			"       Kills = ?,\r\n" + 
			"       Kd = ?\r\n" + 
			" WHERE EpicUsername = ? AND \r\n" + 
			"       AccountId = ? AND \r\n" + 
			"       Top5s = ? AND \r\n" + 
			"       Top3s = ? AND \r\n" + 
			"       Top6s = ? AND \r\n" + 
			"       Top10 = ? AND \r\n" + 
			"       Top12s = ? AND \r\n" + 
			"       Top25s = ? AND \r\n" + 
			"       Score = ? AND \r\n" + 
			"       MatchesPlayed = ? AND \r\n" + 
			"       Wins = ? AND \r\n" + 
			"       \"Win%\" = ? AND \r\n" + 
			"       Kills = ? AND \r\n" + 
			"       Kd = ?;";
	private String updateGameMode = "UPDATE GameModeStats\r\n" + 
			"   SET EpicUsername = ?,\r\n" + 
			"       GameMode = ?,\r\n" + 
			"       Score = ?,\r\n" + 
			"       Top1 = ?,\r\n" + 
			"       Top3 = ?,\r\n" + 
			"       Top6 = ?,\r\n" + 
			"       Top12 = ?,\r\n" + 
			"       Top25 = ?,\r\n" + 
			"       Deaths = ?,\r\n" + 
			"       Kills = ?,\r\n" + 
			"       Matches = ?,\r\n" + 
			"       WinRatio = ?,\r\n" + 
			"       ScorePerMatch = ?,\r\n" + 
			"       Kd = ?,\r\n" + 
			"       KillsPerGame = ?\r\n" + 
			" WHERE EpicUsername = ? AND \r\n" + 
			"       GameMode = ? AND \r\n" + 
			"       Score = ? AND \r\n" + 
			"       Top1 = ? AND \r\n" + 
			"       Top3 = ? AND \r\n" + 
			"       Top6 = ? AND \r\n" + 
			"       Top12 = ? AND \r\n" + 
			"       Top25 = ? AND \r\n" + 
			"       Deaths = ? AND \r\n" + 
			"       Kills = ? AND \r\n" + 
			"       Matches = ? AND \r\n" + 
			"       WinRatio = ? AND \r\n" + 
			"       ScorePerMatch = ? AND \r\n" + 
			"       Kd = ? AND \r\n" + 
			"       KillsPerGame = ?;";
	private String updateUserAccountId = "UPDATE [User-AccountId]\r\n" + 
			"   SET DbUsername = ?,\r\n" + 
			"       AccountIds = ?\r\n" + 
			" WHERE DbUsername = ? AND \r\n" + 
			"       AccountIds = ?;";
	
	//may only need one of these objects...
	private PreparedStatement userStmt;
	private PreparedStatement epicStmt;
	private PreparedStatement matchStmt;
	private PreparedStatement gameModeStmt;
	private PreparedStatement userAccountStmt;
	private PreparedStatement stmt;
	
	//delete the table
	private boolean dropTable = false;
	
	public Database() {
		if (dropTable == true) {
			//dropTable();
		}
		connect();
	}
	
	/**
	 * automatically called when created a database
	 */
	private void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(dbURL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * call this when closing database
	 */
	public void disconnect() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param tablename the table to drop in the database
	 */
	private void dropTable(String tableName) {
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(DROP_TABLE + " " + tableName);
    		System.out.println("Dropped table " + tableName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
    /**

     * call this after creating the database

     */

    public void openConnection() {
    	try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	try {
    		//establish a connection to the database
			conn = DriverManager.getConnection(dbURL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**

     * call this when done using the database/close program

     */

    public void closeConnection() {
    	try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * PRE: query is fully made as a functioning sql query
	 * @param query the query to run
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ResultSet runQuery(String query) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet results = stmt.executeQuery();
		return results;
	}
	
	/**
	 * good for SELECT FROM WHERE with one where argument 
	 * @param query the query in sql format
	 * @param arg the argument for the query
	 * @return set of results that satisfy the query
	 * @throws SQLException
	 */
	public ResultSet runQuery(String query, String arg) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, arg);
		ResultSet results = stmt.executeQuery();
		return results;
	}
	
	/**
	 * 
	 * @param query
	 * @param user
	 * @param arg the argument to 
	 * @param int tells which complex query is being used {1,2,3}
	 * @return
	 * @throws SQLException
	 */
	public ResultSet runComplexQuery(String query, User user, String arg, int queryNum) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, arg);
		ResultSet results = stmt.executeQuery();
		return results;
	}
	
	/**
	 * anytime a user is added, their stats are automatically added to the database
	 * @param query the sql formatted query 
	 * @param user the object to insert appropriate values into the preparedStatment
	 * @throws SQLException
	 */
	public void addUserToDb(String query, User user, int mode, String apiKey) throws SQLException {
		//hash pw
		String hashedPass = BCrypt.hashpw(user.getPass(), BCrypt.gensalt());
		
		if(BCrypt.checkpw(user.getPass(), hashedPass)) {
			user.setPass(hashedPass);
		} else {
			System.out.println("coudl not add user to database because passwords do not match when hashed");
			return;
			//do nothing..?
		}
		String username = user.dbUsername;
		String pass = hashedPass;
		stmt = conn.prepareStatement(query);
		stmt.setString(1, username);
		stmt.setString(2, pass);
		stmt.executeUpdate();
		//whenever a user is added -- populate database with their info..make new myhttp object and insert stats for each game mode, lifetime, etc.
		//or else just call these methods from the register window
		MyHttpURLConnection api = new MyHttpURLConnection(apiKey, mode, user.epicUsername);
		//populate DB gameModeStats 
		user.accountIds = api.getAccountId();
		for(int i = 0; i < api.gameModeStats.size(); i++) {
			insertGameModeStat(insertGameMode, api.gameModeStats.get(i), user);
		}
		//populate DB recentMatches 
		for(int i = 0; i < api.recentMatches.size(); i++) {
			insertRecentMatch(insertRecentMatch,api.recentMatches.get(i), user);
		}
		//populate DB lifeTime stats
		String q = "SELECT * FROM EpicLifetimeStats WHERE EpicUsername = '" + username + "'";
		if (checkIfTableExists(q, user.epicUsername)) {
			//update table 
		} else {
			//insert
			insertLifetimeStat(insertEpicLifetime, api.lifetime, user);
		}
		
		//insert accountId that belongs to this user
		insertUserAccountIds(insertUserAccountId, user);
	}
	/**
	 * 
	 * @param query
	 * @param gameModeStat
	 * @param user
	 * @throws SQLException
	 */
	public boolean insertGameModeStat(String query, GameMode gameModeStat, User user) throws SQLException {
		stmt = conn.prepareStatement(query);
		boolean inserted = false;

		stmt.setString(1, user.epicUsername);
		stmt.setString(2,  gameModeStat.gameMode);
		stmt.setInt(3, gameModeStat.score);
		stmt.setInt(4, gameModeStat.top1);
		stmt.setInt(5, gameModeStat.top3);
		stmt.setInt(6, gameModeStat.top5);
		stmt.setInt(7, gameModeStat.top6);
		stmt.setInt(8, gameModeStat.top10);
		stmt.setInt(9, gameModeStat.top12);
		stmt.setInt(10, gameModeStat.top25);
		stmt.setInt(11, gameModeStat.deaths);
		stmt.setInt(12, gameModeStat.kills);
		stmt.setInt(13, gameModeStat.matches);
		stmt.setDouble(14, gameModeStat.winRatio);
		stmt.setDouble(15, gameModeStat.scorePerMatch);
		stmt.setDouble(16, gameModeStat.kd);
		stmt.setDouble(17, gameModeStat.killsPerGame);
		
		stmt.executeUpdate();
		inserted = true;
		
		return inserted;
	}
	/**
	 * 
	 * @param query
	 * @param lifetime
	 * @param user
	 * @throws SQLException
	 */
	public boolean insertLifetimeStat(String query, LifetimeStat lifetime, User user) throws SQLException {
		stmt = conn.prepareStatement(query);
		boolean inserted = false;
		String ids = "";
		ArrayList<String> actIds = user.getId();
		int i = 0; 
		while(i < actIds.size()) {
			if (i < actIds.size()-1) {
				ids += actIds.get(i) + ", ";
			} else { //at last id
				ids += actIds.get(i);
			}
			i++;
		}
		
		stmt.setString(1, user.epicUsername);
		stmt.setString(2, ids);
		stmt.setInt(3, lifetime.top5s);
		stmt.setInt(4, lifetime.top3s);
		stmt.setInt(5, lifetime.top6s);
		stmt.setInt(6, lifetime.top10);
		stmt.setInt(7, lifetime.top12s);
		stmt.setInt(8, lifetime.top25s);
		stmt.setInt(9, lifetime.score);
		stmt.setInt(10, lifetime.matchesPlayed);
		stmt.setInt(11, lifetime.wins);
		stmt.setDouble(12, lifetime.winPercent);
		stmt.setInt(13, lifetime.kills);
		stmt.setDouble(14, lifetime.kd);
		
		stmt.executeUpdate();
		
		inserted = true;
		return inserted;
	}
	/**
	 * 
	 * @param query the query to run - if running from another class
	 * @param recentMatch
	 * @param user
	 * @throws SQLException
	 */
	public boolean insertRecentMatch(String query, RecentMatch recentMatch, User user) throws SQLException {
		stmt = conn.prepareStatement(query);
		boolean inserted = false;
		
		stmt.setString(1, user.epicUsername);
		stmt.setString(2, recentMatch.matchId);
		stmt.setString(3, recentMatch.dateCollected);
		stmt.setInt(4, recentMatch.kills);
		stmt.setInt(5,  recentMatch.matches);
		stmt.setInt(6, recentMatch.score);
		stmt.setString(7, recentMatch.playlist);
		stmt.setDouble(8, recentMatch.trnRating);
		stmt.setInt(9, recentMatch.top1);
		
		stmt.executeUpdate();
		inserted = true;
		
		return inserted;
	}
	/**
	 * ran only when a database account does not exist
	 * @param query
	 * @param user
	 * @throws SQLException
	 */
	public boolean insertUserAccountIds(String query, User user) throws SQLException {
		stmt = conn.prepareStatement(query);
		boolean inserted = false;
		String ids = "";
		ArrayList<String> actIds = user.getId();
		int i = 0; 
		//grab id's from user
		while(i < actIds.size()) {
			if (i < actIds.size()-1) {
				ids += actIds.get(i) + ", ";
			} else { //at last id
				ids += actIds.get(i);
			}
			i++;
		}
		System.out.println("accoutn id adding to database is: " + ids);
		stmt.setString(1, user.dbUsername);
		stmt.setString(2, ids);
		stmt.executeUpdate();
		
		inserted = true;
		return inserted;
	}
	
	/**
	 * may not use this method... can just use the runQuery function
	 * @param username the username of the dbusername or the epicusername to check in the database for
	 * @param query the sql query to execute
	 * @return true if user exists in the table
	 */
	public boolean checkIfUserExists(String query, String username) {
		ResultSet rs = null;
    	boolean userInDb = false;
    	try {
    		stmt = conn.prepareStatement(query);
    		stmt.setString(1, username); //first column in either User table or EpicLifetimeStats table is the username to lok for
    		rs = stmt.executeQuery();
			String temp = "";
			while(rs.next()) {
				//column 1 in the database is the username field
				temp = rs.getString(1);
				if (username.equalsIgnoreCase(temp)) {
					userInDb = true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return userInDb;
	}
	
	public boolean checkIfTableExists(String query, String username) {
		boolean exists = false;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			String temp = "";
			while(rs.next()) {
				//column 1 in the database is the username field
				temp = rs.getString(1);
				if (username.equalsIgnoreCase(temp)) {
					exists = true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exists;
	}
	
    /**
     * method used to check stored hashed passwords in the database
     * @param username the username to search for in the database
     * @param givenPass the password as entered by the user when logging in
     * @return true if passwords match, false otherwise
     */
    public boolean passwordsMatch(String username, String givenPass) {
    	ResultSet rs = null;
    	String hashedPass = "";
    	try {
			Statement st = conn.createStatement();
			rs = st.executeQuery("SELECT DbUsername, DbPassword FROM User WHERE DbUsername = '" + username + "'");
			String temp = "";
			while(rs.next()) {
				//column 1 in the database is the username field
				temp = rs.getString(1);
				if (username.equalsIgnoreCase(temp)) {
					//grab the hashed password from the database
					hashedPass = rs.getString(2);
					//may need a null check here
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return BCrypt.checkpw(givenPass, hashedPass);
    }
    
}
/* how I want the columns formatted originally -- updated column constrains in global insert statements
 * 
 * 	private String[] USER_COLS = {"DbUsername TEXT PRIMARY KEY, "
			+ "AccountId TEXT, " //the id generated from the api 
			+ "DbPassword TEXT);",
			//start LifetimeStats cols		
			"EpicUsername TEXT PRIMARY KEY, "
			+"AccountId TEXT, "  //foreign key
			+"TotalKD  TEXT, " //need to figure out m,n
			+"TotalTop25s TEXT, "
			+"TotalTop5s TEXT, "
			+"TotalTop3s TEXT, "
			+"TotalWins TEXT, "
			+"TotalKills TEXT, "
			+"Win% TEXT, "
			+"TotalKPM TEXT, "
			+"AvgSurvTime TEXT);",
			//start User-Account cols
			"DbUsername TEXT PRIMARY KEY, " 
			+ "AccountIds TEXT);", //list of values 
			//start RecentMatches col
			"MatchId TEXT PRIMARY KEY AUTO_INCREMENT, "
			+"EpicUsername TEXT, " //foreign key
			+"GameMode TEXT, "
			+"TimePlayed TEXT, "
			+"DamageToStructures TEXT, " 
			+"DamageToPlayers TEXT, "
			+"Assists TEXTt, "
			+"Kills TEXT, "
			+"Accuracy TEXT);",
			//start GameModeStats cols
			"EpicUsername TEXT PRIMARY KEY, " //foreign key?
			+"GameMode TEXT, " //foreign key
			+"Score TEXT, "
			+"Top1 TEXT, " 
			+"Top3 TEXT, " 
			+"Top5 TEXT, "
			+"Top6 TEXT, "
			+"Top12 TEXT, "
			+"Top25 TEXT, "
			+"Deaths TEXT, " 
			+"Kills TEXT, "
			+"Matches TEXT, "
			+"WinRatio TEXT, "
			+"ScorePerMin TEXT, "
			+"AvgTimePlayed TEXT, "
			+"MinPlayed TEXT, "
			+"Kd TEXT, "
			+"KillsPerMatch TEXT, "
			+"ScorePerMatch TEXT);"	
};
 */
/* commented out because storing everything as TEXT
private String[] USER_COLS = {"AccountId Int PRIMARY KEY AUTO_INCREMENT, " 
								+ "DbUsername TEXT NOT NULL, " 
								+ "DbPassword TEXT);",
								//start LifetimeStats cols		
								"EpicUsername TEXT PRIMARY KEY, "
								+"AccountId Int, "  //foreign key
								+"TotalKD  NUMERIC(M,N), " //need to figure out m,n
								+"TotalTop25s Int, "
								+"TotalTop5s Int, "
								+"TotalTop3s Int, "
								+"TotalWins Int, "
								+"TotalKills Int, "
								+"Win% NUMNERIC(M,N), "
								+"TotalKPM Int, "
								+"AvgSurvTime NUMERIC(M,N));",
								//start RecentMatches col
								"MatchId Int PRIMARY KEY AUTO_INCREMENT, "
								+"EpicUsername TEXT, " //foreign key
								+"GameMode Int, "
								+"TimePlayed Int, "
								+"DamageToStructures Int, " 
								+"DamageToPlayers Int, "
								+"Assists Int, "
								+"Kills Int, "
								+"Accuracy NUMERIC(M,N));",
								//start GameModeStats cols
								"EpicUsername TEXT PRIMARY KEY, " //foreign key?
								+"GameMode Int, " //foreign key
								+"Score NUMERIC (M,N), "
								+"Top1 Int, " 
								+"Tio3 Int, " 
								+"Top5 Int, "
								+"Top6 Int, "
								+"Top12 Int, "
								+"Top 25 Int, "
								+"Deaths Int, " 
								+"Kills Int, "
								+"Matches Int, "
								+"WinRatio NUMERIC(M,N), "
								+"ScorePerMin NUMERICAN(M,N), "
								+"AvgTimePlayed NUMERIC(M,N), "
								+"MinPlayed Int, "
								+"Kd NUMERIC(M,N), "
								+"KillsPerMatch NUMERIC(M,N), "
								+"ScorePerMatch NUMERIC(M,N));"
								
};*/
