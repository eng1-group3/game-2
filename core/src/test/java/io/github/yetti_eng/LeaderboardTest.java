package io.github.yetti_eng;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


// Hard to test individual methods as one call to getTopScores calls them all and they are not standalone
// Therefore testing getTopScores() works correctly tests the whole class
public class LeaderboardTest {

    File tempFile;
    Leaderboard leaderboard;

    @BeforeEach
    public void setupTempFile() {
        try {
            tempFile = File.createTempFile("TestLeaderboard", ".txt");
            tempFile.deleteOnExit();
        } catch(IOException e) {
            tempFile = null; // This *should* never happen (fingers crossed)
        }
    
    leaderboard = new Leaderboard(tempFile.getAbsolutePath());

    }
    
    @Test
    public void addToLeaderboardTest() {
        // Given
        leaderboard.addToLeaderboard("Jacob", Integer.MAX_VALUE);
        leaderboard.addToLeaderboard("everyoneElse", 1);

        // When
        List<LeaderboardEntry> topscores = leaderboard.getTopScores();

        // Then
        LeaderboardEntry expectedEntry1 = new LeaderboardEntry("Jacob", Integer.MAX_VALUE);
        LeaderboardEntry expectedEntry2 = new LeaderboardEntry("everyoneElse", 1);
        assertEquals(expectedEntry1, topscores.get(0));
        assertEquals(expectedEntry2, topscores.get(1));
    }

        @Test
    public void getTopScoresFiveTest() {
        // Given leaderboard entries
        leaderboard.addToLeaderboard("Jacob", Integer.MAX_VALUE);
        leaderboard.addToLeaderboard("everyoneElse", 1);
        leaderboard.addToLeaderboard("person21", 21);
        leaderboard.addToLeaderboard("person78", 78);
        leaderboard.addToLeaderboard("person52", 52);

        // When topscores is called
        List<LeaderboardEntry> topscores = leaderboard.getTopScores();

        // Then the elements of topscores should be sorted in score order
        LeaderboardEntry expectedEntry1 = new LeaderboardEntry("Jacob", Integer.MAX_VALUE);
        LeaderboardEntry expectedEntry2 = new LeaderboardEntry("person78", 78);
        LeaderboardEntry expectedEntry3 = new LeaderboardEntry("person52", 52);
        LeaderboardEntry expectedEntry4 = new LeaderboardEntry("person21", 21);
        LeaderboardEntry expectedEntry5 = new LeaderboardEntry("everyoneElse", 1);
        assertEquals(expectedEntry1, topscores.get(0));
        assertEquals(expectedEntry2, topscores.get(1));
        assertEquals(expectedEntry3, topscores.get(2));
        assertEquals(expectedEntry4, topscores.get(3));
        assertEquals(expectedEntry5, topscores.get(4));
    }

    @Test
    public void getTopScoresSixEntriesTest() {
        // Given
        leaderboard.addToLeaderboard("Jacob", Integer.MAX_VALUE);
        leaderboard.addToLeaderboard("everyoneElse", 1);
        leaderboard.addToLeaderboard("aVeryBadPerson", 0);
        leaderboard.addToLeaderboard("person21", 21);
        leaderboard.addToLeaderboard("person78", 78);
        leaderboard.addToLeaderboard("person52", 52);

        // When
        List<LeaderboardEntry> topscores = leaderboard.getTopScores();

        // Then
        LeaderboardEntry expectedEntry1 = new LeaderboardEntry("Jacob", Integer.MAX_VALUE);
        LeaderboardEntry expectedEntry2 = new LeaderboardEntry("person78", 78);
        LeaderboardEntry expectedEntry3 = new LeaderboardEntry("person52", 52);
        LeaderboardEntry expectedEntry4 = new LeaderboardEntry("person21", 21);
        LeaderboardEntry expectedEntry5 = new LeaderboardEntry("everyoneElse", 1);
        assertEquals(expectedEntry1, topscores.get(0));
        assertEquals(expectedEntry2, topscores.get(1));
        assertEquals(expectedEntry3, topscores.get(2));
        assertEquals(expectedEntry4, topscores.get(3));
        assertEquals(expectedEntry5, topscores.get(4));
        // aVeryBadPerson should not be in this list as they have been thrown off the leaderboard (leaderboard only has 5 spots)
        assertThrows(IndexOutOfBoundsException.class, () -> topscores.get(5));
    }
}
