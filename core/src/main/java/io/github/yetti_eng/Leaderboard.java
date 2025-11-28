package io.github.yetti_eng;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;                  // Import the File class
import java.io.FileNotFoundException;

public class Leaderboard {
    private int leaderboardSize = 5;

    public void addToLeaderboard(String playerName, int score) {
        try (FileWriter myWriter = new FileWriter("leaderboard.txt", true)) {
            myWriter.write(playerName + " " + score + "\n");
            System.out.println(playerName + " " + score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getTopScores(){
        File leaderboard = new File("leaderboard.txt");

        try (Scanner myReader = new Scanner(leaderboard)) {
            LeaderboardEntry topScores[] = new LeaderboardEntry[leaderboardSize];
            Arrays.fill(topScores, Integer.MIN_VALUE);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                int lowestScore = topScores[0].score;
                int lowestScoreIndex = 0;

                for (int i = 1; i < topScores.length; i++) {
                    if (topScores[i].score < lowestScore) {
                        lowestScoreIndex = i;
                        lowestScore = topScores[i].score;
                    }
                }

                int score = Integer.parseInt(data.substring(data.indexOf(" ") + 1));
                String playerName = data.substring(0, data.indexOf(" "));
                System.out.println(playerName);

                if(score > lowestScore){
                    topScores[lowestScoreIndex].score = score;
                }
                Arrays.sort(topScores);
                System.out.println(Arrays.toString(topScores));
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
