
public class GameMode {

	public String epicUsername;
	public String gameMode;
	public int score;
	public int top1, top3, top5, top6, top10, top12, top25, deaths, kills, matches;
	public double winRatio, scorePerMatch, kd, killsPerGame;
	private boolean isAdmin;
	
	public GameMode(String user, String gameMode, int score, int top1, int top3, int top5, int top6, int top10, int top12,
			int top25, int deaths, int kills, int matches,
			double winRatio, double scorePerMatch, double kd, double kpg) {
			this.epicUsername = user;
			this.gameMode = gameMode;
			this.score = score;
			this.top1 = top1;
			this.top3 = top3;
			this.top5 = top5;
			this.top6 = top6;
			this.top10 = top10;
			this.top12 = top12;
			this.top25 = top25;
			this.deaths = deaths;
			this.kills = kills;
			this.matches = matches;
			this.winRatio = winRatio;
			this.scorePerMatch = scorePerMatch;
			this.kd = kd;
			this.killsPerGame = kpg;
			isAdmin = false;
	}

}
