package io.github.yetti_eng;

/**
 * Tracks the number of hidden, positive and negative events so they can be
 * displayed on the screen
 * Counts for each can be incremented and retrieved through getter methods.
 */
public class EventCounter {
    private static int hidden = 0;
    private static int positive = 0;
    private static int negative = 0;

    public static void incrementHidden() {
        hidden += 1;
    }

    public static void incrementPositive() {
        positive += 1;
    }

    public static void incrementNegative() {
        negative += 1;
    }

    public static int getHiddenCount() {
        return hidden;
    }

    public static int getPositiveCount() {
        return positive;
    }

    public static int getNegativeCount() {
        return negative;
    }

    public static void reset() {
        hidden = 0;
        positive = 0;
        negative = 0;
    }

}
