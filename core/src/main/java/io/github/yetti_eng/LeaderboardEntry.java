package io.github.yetti_eng;
import java.util.Comparator;

// WHOLE FILE NEW CODE

/**
 * Represents an entry in the leaderboard.
 * Each entry consists of a name, score, and position.
 */
public class LeaderboardEntry {
    private String playerName;
    private int score;
    private int position = -1;

    /**
     * Creates a leaderboard entry with the given player name and their score.
     * @param playerName name of the player.
     * @param score player's score.
     */
    public LeaderboardEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    /**
     * Creates a default player.
     * Entry has no name and the score is the minimum integer value.
     */
    public LeaderboardEntry() {
        this.playerName = null;
        this.score = Integer.MIN_VALUE;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Creates a string representation of the name and score of the leaderboard entry.
     * @return the leaderboard entry in string format.
     */
    @Override
    public String toString() {
        return this.score + " " + this.playerName;
    }

    /**
     * Compares the name and score of two leaderboard entries.
     * @param obj the reference object with which to compare.
     * @return true if the entries have the exact same name and score; false if otherwise.
     */
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

/**
 * Comparator used to sort {@link LeaderboardEntry} objects by score in descending order.
 */
class SortLeaderboard implements Comparator<LeaderboardEntry> {
    /**
     * Compares two leaderboard entries based on their score.
     * @param o1 the first leaderboard entry
     * @param o2 the second leaderboard entry
     * @return negative value if {@code o2} has a higher score than {@code o1}, zero if scores are equal,
     *      or positive value if otherwise.
     */
    @Override
    public int compare(LeaderboardEntry o1, LeaderboardEntry o2) {
        return Integer.compare(o2.getScore(), o1.getScore());
    }
}

