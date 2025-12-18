package io.github.yetti_eng;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Achievements {

    private final FileHandle file;

    public Achievements() {
        file = Gdx.files.local("achievements.txt");
    }

    public Achievements(FileHandle file) {
        this.file = file;
    }


    public void unlock(String key) {
        if (!isUnlocked(key)) {
            file.writeString(key + "=true\n", true);
        }
    }


    public boolean isUnlocked(String key) {
        if (!file.exists()) return false;
        return file.readString().contains(key + "=true");
    }


    public void deleteFile() {
        if (file.exists()) {
            file.delete();
        }
    }
}
