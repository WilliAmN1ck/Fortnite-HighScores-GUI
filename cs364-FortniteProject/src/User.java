import java.util.ArrayList;

public class User {
	
	public String dbUsername;
	private String dbPassword;
	ArrayList<String> accountIds;
	boolean isGuest;
	public int platform;
	public String epicUsername;
	
	public User(String username, String password, int platform, String epicUser) {
		dbUsername = username;
		dbPassword = password;
		accountIds = new ArrayList<String>();
		this.platform = platform;
		isGuest = false;
		epicUsername = epicUser;
	}
	
	public String getPass() {
		return dbPassword;
	}
	
	public void setPass(String newPass) {
		dbPassword = newPass;
	}
	
	public ArrayList<String> getId() {
		return accountIds;
	}
}
