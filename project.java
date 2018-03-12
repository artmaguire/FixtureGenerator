package Project1;

import javax.swing.*;
import java.util.ArrayList;

public class project {

    public static void main(String[] args) {
        /*String userInput, selection;
        int numberOfTeams, totalNumberOfRounds, numberOfMatchesPerRound, roundNumber, matchNumber, homeTeamNumber, awayTeamNumber;
        String[][] fixtures = new String[0][0];
        userInput = JOptionPane.showInputDialog(null, "How many teams would you like in your league?");

        selection = getNumberOfTeams("Number Of Teams", "Please enter a number in the range 2 to 99");

        if (userInput != null) {
            numberOfTeams = Integer.parseInt(userInput);
            if (numberOfTeams % 2 == 1) {
                numberOfTeams++;
            }
            totalNumberOfRounds = numberOfTeams - 1;
            numberOfMatchesPerRound = numberOfTeams / 2;


            ArrayList<Administrator> administrators = new ArrayList<>();
            String adminName, adminPasswordString;
            adminName = JOptionPane.showInputDialog(null, "Enter Username");
            adminPasswordString = JOptionPane.showInputDialog(null, "Enter Password");
            int adminPassword = Integer.parseInt(adminPasswordString);

            Administrator a1 = new Administrator(adminName, adminPassword);
            administrators.add(a1);
            *//*a1.setName(adminName);
            a1.setPassword(adminPassword);*//*

            String leagueName, leagueIDString;
            int leagueID;
            leagueName = JOptionPane.showInputDialog(null, "Enter League Name");
            leagueIDString = JOptionPane.showInputDialog(null, "Enter League ID");
            leagueID = Integer.parseInt(leagueIDString);
            League l1 = new League(leagueID, leagueName);
            ArrayList<League> leagues = new ArrayList<>();
            leagues.add(l1);
            l1.setAdmin(administrators.get(0));
            l1.setId(leagueID);
            l1.setName(leagueName);
            l1.setNumberOfTeams(numberOfTeams);

            for (roundNumber = 0; roundNumber < totalNumberOfRounds; roundNumber++) {
                for (matchNumber = 0; matchNumber < numberOfMatchesPerRound; matchNumber++) {
                    homeTeamNumber = (roundNumber + matchNumber) % (numberOfTeams - 1);
                    awayTeamNumber = (numberOfTeams - 1 - matchNumber + roundNumber) % (numberOfTeams - 1);
                    if (matchNumber == 0) {
                        awayTeamNumber = numberOfTeams - 1;
                    }
                    fixtures[roundNumber][matchNumber] = (homeTeamNumber + 1) + " vs " + (awayTeamNumber + 1);
                }
            }
        }
    }

    public static String getNumberOfTeams(String windowMessage, String windowTitle) {

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
    }*/
    }
}    