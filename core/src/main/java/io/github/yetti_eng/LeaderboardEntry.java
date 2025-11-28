package io.github.yetti_eng;
import java.util.Comparator;

public class LeaderboardEntry{
    public String playerName;
    public int score;

    public LeaderboardEntry(String playerName, int score){
        this.playerName = playerName;
        this.score = score;
    }
}

class SortLeaderboard implements Comparator<LeaderboardEntry> {

    @Override
    public int compare(LeaderboardEntry o1, LeaderboardEntry o2) {
        return Integer.compare(o1.score, o2.score);
    }
}

