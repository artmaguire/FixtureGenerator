package Project1;

import javax.swing.*;

public class fixtureGeneration {

    public static void main(String[] args) {

        int numberOfTeams, totalNumberOfRounds, numberOfMatchesPerRound;
        int roundNumber, matchNumber, homeTeamNumber, awayTeamNumber, even, odd;
        boolean additionalTeamIncluded = false;
        String selection;
        String[][] fixtures;
        String[][] reverseFixtures;
        String[] elementsOfFixtures;
        String fixtureAsText;

        /*selection = getNumberOfTeams("Number Of Teams", "Please enter a number in the range 2 to 99");
        if (selection != null) {
            numberOfTeams = Integer.parseInt(selection);
            if (numberOfTeams % 2 == 1) {
                numberOfTeams++;
                additionalTeamIncluded = true;
            }
            totalNumberOfRounds = numberOfTeams-1;
            numberOfMatchesPerRound = numberOfTeams/2;
            fixtures = new String[totalNumberOfRounds][numberOfMatchesPerRound];

           /* for (roundNumber = 0; roundNumber < totalNumberOfRounds; roundNumber++) {
                for (matchNumber = 0; matchNumber < numberOfMatchesPerRound; matchNumber++) {
                    homeTeamNumber = (roundNumber + matchNumber) % (numberOfTeams - 1);
                    awayTeamNumber = (numberOfTeams - 1 - matchNumber + roundNumber) % (numberOfTeams - 1);
                    if (matchNumber == 0) {
                        awayTeamNumber = numberOfTeams - 1;
                    }
                    fixtures[roundNumber][matchNumber] = (homeTeamNumber + 1) + " vs " + (awayTeamNumber + 1);
                    }
                }/*
                reverseFixtures = new String[totalNumberOfRounds][numberOfMatchesPerRound];
            even = 0;
            odd = numberOfTeams / 2;
            for (int i = 0; i < fixtures.length; i++) {
                if (i % 2 == 0) {
                    reverseFixtures[i] = fixtures[even++];
                } else {
                    reverseFixtures[i] = fixtures[odd++];
                }
            }
            fixtures = reverseFixtures;

            for (roundNumber = 0; roundNumber < fixtures.length; roundNumber++) {
                if (roundNumber % 2 == 1) {
                    fixtureAsText = fixtures[roundNumber][0];
                    elementsOfFixtures = fixtureAsText.split(" vs ");
                    fixtures[roundNumber][0] = elementsOfFixtures[1] + " vs " + elementsOfFixtures[0];
                }
            }
            for (roundNumber = 0; roundNumber < totalNumberOfRounds; roundNumber++) {
                System.out.println("Round " + (roundNumber + 1) + "\t\t");
                for (matchNumber = 0; matchNumber < numberOfMatchesPerRound; matchNumber++) {
                    System.out.println("\tMatch " + (matchNumber + 1) + ": " + fixtures[roundNumber][matchNumber] + "\t");
                    System.out.println();
                }
                System.out.println("\nYou will have to use the mirror image");
                System.out.println(" of these fixtures for return fixtures.");
                if (additionalTeamIncluded) {
                    System.out.println("\nSince you had " +  (numberOfTeams - 1) + " teams at the outset (uneven number) , fixtures " + "against team number " + numberOfTeams + " are byes");
                }
            }
        }
    } */

   /* public static String getNumberOfTeams(String windowMessage, String windowTitle) {

        boolean validInput = false;
        int numberOfnumberOfTeams;
        String userInput = "", pattern = "[0-9]{1,2}";
        String errorMessage = "Invalid Input. \n\nYou are not permitted to have 2 to 99 teams. \nSelect OK to retry";

        while (!(validInput)) {
            userInput = JOptionPane.showInputDialog(null, windowMessage, windowTitle, 3);
            if (userInput == null) {
                validInput = true;
            } else if (!(userInput.matches(pattern))) {
                JOptionPane.showMessageDialog(null, errorMessage, "Error in user input.", 2);
            } else {
                numberOfnumberOfTeams = Integer.parseInt(userInput);
                if (numberOfnumberOfTeams < 2) {
                    JOptionPane.showMessageDialog(null, errorMessage, "Error in user input", 2);
                } else {
                    validInput = true;
                }
            }
        }
        return userInput;
    }
}} */
    }

}