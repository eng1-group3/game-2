package io.github.yetti_eng;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
/**
 * This class handles saving and loading achievements.
 * It writes data to a simple text file so the game remembers what you unlocked
 * even after you close it.
 */
public class Achievements {

    private final FileHandle file;
    /**
     * Default constructor.
     * Creates (or finds) a file named "achievements.txt" in the game folder.
     */
    public Achievements() {
        file = Gdx.files.local("achievements.txt");
    }
    /**
     * Constructor used for testing.
     * Allows us to pass a fake file or a specific file path.
     *
     * @param file The file handle to use
     */
    public Achievements(FileHandle file) {
        this.file = file;
    }

    /**
     * Unlocks an achievement by writing it to the file.
     * It first checks if it's already unlocked so we don't write the same thing twice.
     *
     * @param key The unique name of the achievement (e.g., "longboi_master")
     */
    public void unlock(String key) {
        if (!isUnlocked(key)) {
            file.writeString(key + "=true\n", true);
        }
    }

    /**
     * Checks if an achievement has been unlocked yet.
     * It reads the whole text file and looks for the specific key.
     *
     * @param key The unique name of the achievement
     * @return true if found, false otherwise
     */
    public boolean isUnlocked(String key) {
        if (!file.exists()) return false;
        return file.readString().contains(key + "=true");
    }

    /**
     * Deletes the achievements file.
     * Useful for resetting the game progress completely.
     */
    public void deleteFile() {
        if (file.exists()) {
            file.delete();
        }
    }
}
