package io.github.yetti_eng;
import java.io.FileWriter;
import java.util.*;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

public class Leaderboard {
    private int leaderboardSize = 5;
    private List<LeaderboardEntry> topScores = new ArrayList();

    public void addToLeaderboard(String playerName, int score) {
        try (FileWriter myWriter = new FileWriter("leaderboard.txt", true)) {
            myWriter.write(playerName + " " + score + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToLeaderboard(int score) {
        try (FileWriter myWriter = new FileWriter("leaderboard.txt", true)) {
            myWriter.write("default" + " " + score + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List getTopScores() {
        sortTopScores();
        setPositions();
        return topScores;
    }

    private void sortTopScores(){
        File leaderboard = new File("leaderboard.txt");
        topScores.clear();

        try (Scanner scanner = new Scanner(leaderboard)) {
            LeaderboardEntry lowestScore =  new LeaderboardEntry();

            // Add leaderboardSize entries to the topScores arraylist
            for (int i = 0; i < leaderboardSize; i++) {
                topScores.add(new LeaderboardEntry("", 0));
            }

            // Looks through each line of the leaderboard.txt file
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();

                // Lowest score is to the first entry of the arraylist
                // Goes through each element of the top scores and if the score is lower than the current lowest score,
                // it becomes the new lowest score
                lowestScore = (LeaderboardEntry) topScores.get(0);
                for (Object object : topScores) {
                    LeaderboardEntry entry = (LeaderboardEntry) object;
                    if (entry.getScore() < lowestScore.getScore()) {
                        lowestScore = entry;
                    }
                }

                // For current line in the leaderboard file, the first part is the name,
                // and the second part is the score
                String playerName = data.substring(0, data.indexOf(" "));
                int score = Integer.parseInt(data.substring(data.indexOf(" ") + 1));
                LeaderboardEntry leaderboardEntry = new LeaderboardEntry(playerName, score);

                // If the score of the current line is higher than the lowest score,
                // the entry gets added to the topEntries list, replacing the lowest score
                if(leaderboardEntry.getScore() > lowestScore.getScore()){
                    topScores.add(leaderboardEntry);
                    topScores.remove(lowestScore);
                }
            }


            try (Scanner scanner2 = new Scanner(leaderboard)) {
                // Looks through each line of the leaderboard.txt file
                while (scanner2.hasNextLine()) {
                    String data = scanner2.nextLine();

                    // Gets the playerName and score for the line of leaderboard.txt
                    String playerName = data.substring(0, data.indexOf(" "));
                    int score = Integer.parseInt(data.substring(data.indexOf(" ") + 1));
                    LeaderboardEntry entry = new LeaderboardEntry(playerName, score);

                    // Adds entry if the score is tied with last place of the leaderboard
                    if (score == lowestScore.getScore() && !topScores.contains(entry)) {
                        topScores.add(new LeaderboardEntry(playerName, score));
                    }
                }
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // Sorts topScores using comparators
            Comparator myComparator = new SortLeaderboard();
            topScores.sort(myComparator);

            System.out.println("\nTop scores:");
            for (Object obj : topScores) {
                LeaderboardEntry entry = (LeaderboardEntry) obj;
                entry.printEntry();
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    private void setPositions(){
        int position = 1;
        int lastScore = -1;

        for (int i = 0; i < topScores.size(); i++) {
            LeaderboardEntry entry = topScores.get(i);

            if (entry.getScore() != lastScore) {
                position = i + 1;
            }

            entry.setPosition(position);
            lastScore = entry.getScore();
        }
    }
}
