package io.github.yetti_eng;

/**
 * Handles real-time countdowns. Can be paused.
 */
public class Timer {
    private boolean active = false;
    private boolean finished = false;
    private int duration;
    private int endTime;

    public Timer(int duration) {
        assert duration >= 0 : "The duration of a Timer cannot be neagtive.";
        this.duration = duration;
    }

    /**
     * Plays the Timer (if it is not already playing or finished).
     * While playing, it is active, and counts down its duration.
     */
    public void play() {
        if (finished || active) return;
        active = true;
        endTime = currentTimeSeconds() + duration;
    }

    /**
     * Pauses the Timer (if it is not already paused or finished).
     * While paused, it is not active, and its duration is stored.
     */
    public void pause() {
        if (finished || !active) return;
        active = false;
        duration = endTime - currentTimeSeconds();
    }

    /**
     * Finishes the Timer (if it is not already finished).
     * While finished, it is not active, its duration is 0, and it cannot be played or paused.
     */
    public void finish() {
        if (finished || !active) return;
        active = false;
        finished = true;
        duration = 0;
    }

    /**
     * @return true if the Timer is currently active; false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @return true if the Timer is currently finished; false otherwise.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * @return true if the Timer is currently active AND its full duration has passed; false otherwise.
     */
    public boolean hasElapsed() {
        return active && currentTimeSeconds() >= endTime;
    }

    /**
     * @return The remaining duration of the Timer in seconds.
     */
    public int getRemainingTime() {
        // If timer is not active, use stored duration value
        if (!active) return duration;
        // Otherwise calculate dynamically
        return endTime - currentTimeSeconds();
    }

    private int currentTimeSeconds() {
        return Math.toIntExact(System.currentTimeMillis() / 1000);
    }

    public String formatTimer(int seconds) {
        return (seconds / 60) + ":" + String.format("%02d", seconds % 60);
    }
}
