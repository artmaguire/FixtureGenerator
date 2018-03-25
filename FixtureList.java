package Project1.FixtureGenerator;

import java.util.*;

public class FixtureList {

    /**
     *	@author Thomas Langley: 17215145
     *
     *	This file allows the user to input the number of teamsG to be added to a table of fixutres being generated for a given league.
     *
     *	A message is outputted, checking if the user would like to enter another team onto the league. The user answers this question,
     *	by inputting the int value corresponding to a string response.
     *	The input is then stored as a string value.
     *	If nothing has been inputted, an appropriate error message is outputted.
     *	If the input does not match the expected pattern, an appropriate error message is outputted.
     *	If the user has selected the option to not add any teamsG, an appropriate message is outputted.
     *	If the user has selected the option to add a given number of teamsG to the league, an appropriate message is outputted requesting
     *	the user to input the number of teamsG they would like to add.
     *	If the input does not match the expected patter, an appropriate error message is outputted.
     *	The program checks if there is an odd number of teamsG. If there is, an appropriate message is displayed, but a normal
     *	fixture table is still generated.
     *
     *	The number of rounds is calculated as the number of teamsG minus 1.
     *	The number of matches per round is calculated as the number of teamsG divided by two.
     *	An array is created to store the information about the fixturesG.
     *	A for loop assigns a fixture to each match of each round in the league.
     *	A revised fixture table is created, to allow for the addition of the games that will not actually be played, the "byes".
     *	The corresponding round and match numbers are outputted, while an appropriate message is outputted informing the user of the
     *	presence of byes.
     */



    public static void main(String[] args) {
        int teamAmount = 0, roundAmount, matchesPerRound, roundNumber, matchNumber = 0, homeTeamNumber, awayTeamNumber, even, odd;
        boolean additionalTeam = false;
        String[][] fixtures, revisedFixtures;
        String[] elementsOfFixture;
        String fixtureAsText;
        Scanner input = new Scanner(System.in);
        String pattern = "[0-9]{1,2}";
        String requestTeamPatttern = "[1-2]{1}";
        String errorMessage1 = "No input has been entered, please restart the program and enter another input.";
        String errorMessage2 = "The input provided does not match the expected format. Please enter ";
        int requestTeam, teamsAdded = 0;

        System.out.println("Would you like to add another team to the league? Note: Enter the value \n1. Yes, I would like to add another team.\n2. No, please move on.\n\n0)Exit\n?)");
        requestTeam = Integer.parseInt(input.nextLine());

        if (requestTeam == -1) {
            System.out.println(errorMessage1);
        } else if (!(requestTeam == 1)) {
            System.out.println(errorMessage2 + "either the number 1 or the number 2.");
        }

        boolean exit = false;

        switch (requestTeam) {
            case 0:
                exit = true;
                return;
            case 1:
                System.out.println("Please enter the number of teamsG you would like to add.");
                teamAmount = Integer.parseInt(input.nextLine());
                break;
            case 2:
                System.out.println("No teamsG will be added to the league.");
                return;
        }

        /*if (!(teamsAdded.matches(pattern))) {
            System.out.println(errorMessage2 + "a value between 2 and 99.");
        } */
        if (teamAmount % 2 == 1) {
            teamAmount++;
            additionalTeam = true;
        }

        roundAmount = teamAmount - 1;
        matchesPerRound = teamAmount / 2;

        fixtures = new String[roundAmount * 2][matchesPerRound]; //double roundAmount for the mirror fixturesG
        for (roundNumber = 0; roundNumber < roundAmount; roundNumber++) {
            for (matchNumber = 0; matchNumber < matchesPerRound; matchNumber++) {
                homeTeamNumber = (roundNumber + matchNumber) % (teamAmount - 1);
                awayTeamNumber = (teamAmount - 1 - matchNumber + roundNumber) % (teamAmount - 1);

                if (matchNumber == 0) {
                    awayTeamNumber = teamAmount - 1;
                }

                fixtures[roundNumber][matchNumber] = (homeTeamNumber + 1) + " v " + (awayTeamNumber + 1);
            }
        }

        /*revisedFixtures = new String[roundAmount][matchesPerRound];
        even = 0;
        odd = teamAmount / 2;
        for (roundNumber = 0; roundNumber < roundAmount; roundNumber++) {
            if (roundNumber % 2 == 1) {
                revisedFixtures[roundNumber] = fixturesG[even++];
            } else {
                revisedFixtures[roundNumber] = fixturesG[odd++];
            }
        }

        fixturesG = revisedFixtures;*/

        for (roundNumber = 0; roundNumber < roundAmount; roundNumber++) {
            //if (roundNumber % 2 == 1) {
            for (matchNumber = 0; matchNumber < matchesPerRound; matchNumber++) {
                fixtureAsText = fixtures[roundNumber][matchNumber];
                elementsOfFixture = fixtureAsText.split(" v ");
                fixtures[roundNumber + roundAmount][matchNumber] = elementsOfFixture[1] + " v " + elementsOfFixture[0];
            }
            //}
        }

        for (roundNumber = 0; roundNumber < fixtures.length; roundNumber++) {
            System.out.println("Round " + (roundNumber + 1) + ":\n");

            for (matchNumber = 0; matchNumber < matchesPerRound; matchNumber++) {
                System.out.println("\tMatch " + (matchNumber + 1) + ": " + fixtures[roundNumber][matchNumber] + "\t\n");
            }

            if (additionalTeam) {
                System.out.println("Since you had  " + (teamAmount - 1) + " teamsG to begin with (an odd number), fixture " + " against team number " + teamAmount + " are byes.");
            }
        }
    }
}