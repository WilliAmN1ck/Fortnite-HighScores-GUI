
public class LifetimeStat {

	public String EpicUsername, accountId;
	public double winPercent, kd;
	public int top5s, top3s, top6s, top10, top12s, top25s, score, matchesPlayed,
		wins, kills;
	public LifetimeStat(String user, String accountId, int top5s, int top3s, int top6s,
			int top10, int top12s, int top25s, int score, int matchesPlayed, int wins,
			double winPercentage, int kills, double kd) {
		this.EpicUsername = user;
		this.accountId = accountId;
		this.top5s = top5s;
		this.top3s = top3s;
		this.top10 = top10;
		this.top12s = top12s;
		this.top25s = top25s;
		this.score = score;
		this.matchesPlayed = matchesPlayed;
		this.wins = wins;
		this.winPercent = winPercentage;
		this.kills = kills;
		this.kd = kd;
	}
}
