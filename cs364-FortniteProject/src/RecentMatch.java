
public class RecentMatch {
	public String epicUsername, matchId, dateCollected, playlist;
	public int kills, matches, score, top1;
	double trnRating;
	
	public RecentMatch(String user, String id, String date, int kills,
			int matches, int score, String playlist, double trnRating, 
			int top1) {
		this.epicUsername = user;
		this.matchId = id;
		this.dateCollected = date;
		this.kills = kills;
		this.matches = matches;
		this.score = score;
		this.playlist = playlist;
		this.trnRating = trnRating;
		this.top1 = top1;
	}
	
}
