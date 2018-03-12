package Project1;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class fixtureGeneration {
    private static Administrator loggedInAdmin = null;
    private static String adminFileName = "adminFile.csv";
    private static ArrayList<Administrator> admins = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int numberOfTeams, totalNumberOfRounds, numberOfMatchesPerRound;
        int roundNumber, matchNumber, homeTeamNumber, awayTeamNumber, even, odd;
        boolean additionalTeamIncluded = false;
        String selection;
        String[][] fixtures;
        String[][] reverseFixtures;
        String[] elementsOfFixtures;
        String fixtureAsText;

        loadAdministrators();

        createLoginUser();

        writeAdministrators();


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

    private static void loadAdministrators() {
        File f = new File(adminFileName);
        if(f.isFile()) {
            Scanner scanner = null;
            try {
                scanner = new Scanner(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] values = line.split(",");
                admins.add(new Administrator(values[0], values[1]));
            }
            scanner.close();
        } else {
            try {
                f.createNewFile();
                System.out.println("Created new " + adminFileName);
            } catch (IOException e) {
                System.out.println("Failed to create: " + adminFileName + "\n" + e.getStackTrace());
            }
        }
    }

    private static void writeAdministrators() {
        try {
            PrintWriter pw = new PrintWriter(adminFileName);
            for(Administrator a : admins) {
                pw.println(a.getCSV());
            }
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Failed to create: " + adminFileName + "\n" + e.getStackTrace());
        }
    }

    private static void createLoginUser() {
        boolean exit = false;
        while(!exit) {
            System.out.println("1) Login Existing User\n2) Create New User\n\n0) Exit");
            System.out.println("--------------------");
            System.out.print("?) ");

            int option = -1;

            option = Integer.parseInt(sc.nextLine());

            switch (option) {
                case 0: exit = true; break;
                case 1: loginAdmin(); break;
                case 2: createAdmin(); break;
            }
        }
    }

    private static void loginAdmin() {
        int attempts = 3;
        while(attempts > 0) {
            System.out.println("\n====================");
            System.out.println("Attempts Remaining: " + attempts);
            System.out.println("\n====================");
            System.out.println("Username: ");
            String username = sc.nextLine();
            System.out.println("Password: ");
            String password = sc.nextLine();

            loggedInAdmin = checkAdmins(username, password);

            if(loggedInAdmin != null) {
                System.out.println("Successfully Logged In\n");
                attempts = -1;
                listLeagues();
            } else {
                System.out.println("No User Found\n");
                attempts--;
            }
        }
    }

    private static void createAdmin() {
        System.out.println("\n====================");
        System.out.println("Username: ");
        String username = sc.nextLine();
        System.out.println("Password: ");
        String password = sc.nextLine();

        if(checkAdmins(username, password) != null) {
            System.out.println("User Already Exists");
        } else {
            Administrator newAdmin = new Administrator(username, password); //create new administrator from users inputs
            admins.add(newAdmin); //add them to the array of admins
            loggedInAdmin = newAdmin; //set the new user as the current loggedin user
            listLeagues();
        }
    }

    /**
     * Shows a list of leagues administered by the logged in administrator
     */
    private static void listLeagues() {
        System.out.println("0) Logout");
        System.out.println("--------------------");
        System.out.print("?) ");

        int option = -1;
        option = Integer.parseInt(sc.nextLine());

        switch (option) {
            case 0: loggedInAdmin = null; return;
        }
    }

    private static Administrator checkAdmins(String username, String password) {
        for(Administrator a : admins) {
            if(a.check(username, password)) return a;
        }
        return null;
    }
}