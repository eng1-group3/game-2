package io.github.yetti_eng;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.files.FileHandle;

public class AchievementsTest {
    FileHandle fileHandle;

    @BeforeEach
    public void setupTempFile() { 
        File tempFile;
        try {
            tempFile = File.createTempFile("TestAchievements", ".txt");
            tempFile.deleteOnExit();
        } catch(IOException e) {
            tempFile = null; // This *should* never happen (fingers crossed)
        }
        fileHandle = new FileHandle(tempFile);
    }

    @Test
    public void unlockTest() {
        Achievements achievements = new Achievements(fileHandle);

        achievements.unlock("achievement1");
        assertTrue(achievements.isUnlocked("achievement1"));
    }

    @Test
    public void unlockMultipleTest() {
        Achievements achievements = new Achievements(fileHandle);

        achievements.unlock("achievement1");
        achievements.unlock("achievement2");
        achievements.unlock("achievement3");
        assertTrue(achievements.isUnlocked("achievement1"));
        assertTrue(achievements.isUnlocked("achievement2"));
        assertTrue(achievements.isUnlocked("achievement3"));
    }

    @Test
    public void notUnlockedTest() {
        Achievements achievements = new Achievements(fileHandle);

        assertFalse(achievements.isUnlocked("achievement1"));
    }

    @Test
    public void variousUnlockedAndNotUnlockedTest() {
        Achievements achievements = new Achievements(fileHandle);

        achievements.unlock("unlocked1");
        achievements.unlock("unlocked2");
        achievements.unlock("unlocked3");

        assertTrue(achievements.isUnlocked("unlocked1"));
        assertFalse(achievements.isUnlocked("notUnlocked1"));
        assertTrue(achievements.isUnlocked("unlocked2"));
        assertFalse(achievements.isUnlocked("notUnlocked2"));
        assertTrue(achievements.isUnlocked("unlocked3"));
    }

    @Test
    public void unlockTwiceTest() {
        // Just checks that unlocking the same achievement twice doesn't cause any crashes
        Achievements achievements = new Achievements(fileHandle);

        achievements.unlock("achievement1");
        achievements.unlock("achievement1");

        assertTrue(achievements.isUnlocked("achievement1"));
    }

    @Test
    public void deleteExistingFileTest() {
        FileHandle mockFileHandle = mock(FileHandle.class);
        Achievements achievements = new Achievements(mockFileHandle);

        when(mockFileHandle.exists()).thenReturn(true);

        achievements.deleteFile();
        verify(mockFileHandle).delete();
    }

    @Test
    public void deleteMissingFileTest() {
        FileHandle mockFileHandle = mock(FileHandle.class);
        Achievements achievements = new Achievements(mockFileHandle);

        when(mockFileHandle.exists()).thenReturn(false);

        achievements.deleteFile();
        verify(mockFileHandle, never()).delete();
    }
}
