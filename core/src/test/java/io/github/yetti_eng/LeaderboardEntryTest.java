package io.github.yetti_eng;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LeaderboardEntryTest {
    
    @Test
    public void equalsExactSameTest() {
        LeaderboardEntry entry1 = new LeaderboardEntry("testName1", 200);
        
        assertTrue(entry1.equals(entry1));
    }

    @Test
    public void equalsSameValuesTest() {
        LeaderboardEntry entry1 = new LeaderboardEntry("testName1", 200);
        LeaderboardEntry entry2 = new LeaderboardEntry("testName1", 200);
        
        assertTrue(entry1.equals(entry2));
        assertTrue(entry2.equals(entry1));
    }

    @Test
    public void equalsDifferentTest() {
        LeaderboardEntry entry1 = new LeaderboardEntry("testName1", 200);
        LeaderboardEntry entry2 = new LeaderboardEntry("testName1", 300);
        
        assertFalse(entry1.equals(entry2));
        assertFalse(entry2.equals(entry1));
    }

    @Test
    public void sortLeaderboardExactSameTest() {
        LeaderboardEntry entry1 = new LeaderboardEntry("testName1", 200);
        
        SortLeaderboard entryComparator = new SortLeaderboard();

        assertEquals(0, entryComparator.compare(entry1, entry1));
    }

    @Test
    public void sortLeaderboardSameValuesTest() {
        LeaderboardEntry entry1 = new LeaderboardEntry("testName1", 200);
        LeaderboardEntry entry2 = new LeaderboardEntry("testName1", 200);

        SortLeaderboard entryComparator = new SortLeaderboard();

        assertEquals(0, entryComparator.compare(entry1, entry2));
    }

    @Test
    public void sortLeaderboardGreaterThanTest() {
        LeaderboardEntry entry1 = new LeaderboardEntry("testName1", 300);
        LeaderboardEntry entry2 = new LeaderboardEntry("testName1", 200);

        SortLeaderboard entryComparator = new SortLeaderboard();

        assertEquals(-1, entryComparator.compare(entry1, entry2));
    }

    @Test
    public void sortLeaderboardLessThanTest() {
        LeaderboardEntry entry1 = new LeaderboardEntry("testName1", 200);
        LeaderboardEntry entry2 = new LeaderboardEntry("testName1", 300);

        SortLeaderboard entryComparator = new SortLeaderboard();

        assertEquals(1, entryComparator.compare(entry1, entry2));
    }
}
