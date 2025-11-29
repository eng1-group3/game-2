package io.github.yetti_eng;
import java.util.Comparator;

public class LeaderboardEntry {
    private String playerName;
    private int score;

    public LeaderboardEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public LeaderboardEntry() {
        this.playerName = null;
        this.score = Integer.MIN_VALUE;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void print() {
        System.out.println(this.score + " " + this.playerName);
    }

    @Override
    public boolean equals(Object obj) {
        LeaderboardEntry other = (LeaderboardEntry) obj;
        if (this == other) {
            return true;
        }

        if (this.getPlayerName().equals(other.getPlayerName()) && this.getScore() == other.getScore()) {
            return true;
        }
        return false;
    }
}

class SortLeaderboard implements Comparator<LeaderboardEntry> {

    @Override
    public int compare(LeaderboardEntry o1, LeaderboardEntry o2) {
        return Integer.compare(o1.getScore(), o2.getScore());
    }
}

