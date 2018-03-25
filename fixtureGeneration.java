package Project1.FixtureGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import static Project1.FixtureGenerator.GenerateLeaderBoard.prepareFiles;

public class fixtureGeneration {
    private static ArrayList<String> loggedInAdmin = null;
    private static String adminFileName = "adminFile.csv";
    private static String leaguesFileName = "leaguesFileName.csv";
    private static ArrayList<ArrayList<String>> admins = new ArrayList<>();
    private static ArrayList<ArrayList<String>> leagues = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        loadAdministrators();

        createLoginUser();

        writeAdministrators();
        writeLeagues();
    }

    /**
     * @author Art Maguire: 16150201
     * Program reads from a file and splits the values into an array and then adds values into the global ArrayList.
     * If the file does not exist, the program will create a new file with the adminFileName.
     */
    private static void loadAdministrators() {
        File f = new File(adminFileName);
        if(f.isFile()) {
            Scanner scanner = null;
            try {
                scanner = new Scanner(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                ArrayList<String> admin = new ArrayList<>();
                admin.add(values[0]);
                admin.add(values[1]);
                admin.add(values[2]);
                admins.add(admin);
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

    /**
     * Creates the interface for interacting with the program, Logging in, Creating a new user and exiting hte program.
     * The switch in this Method links the method between the loadAdmin Method and the createAdmin Method.
     */
    private static void createLoginUser() throws IOException {
        boolean exit = false;
        while(!exit) {
            System.out.println("1) Login Existing User\n2) Create New User\n\n0) Exit");
            System.out.println("--------------------");
            System.out.print("?) ");

            int option;

            option = Integer.parseInt(sc.nextLine());

            switch (option) {
                case 0: exit = true; break;
                case 1: loginAdmin(); break;
                case 2: createAdmin(); break;
            }
        }
    }

    /**
     * This Method allows the user to log in to an existing user using name and password.
     * It gives the user 3 attempts. If login is successful, the program will show the leagues associated with the user.
     */
    private static void loginAdmin() throws IOException {
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
                try {
                    listLeagues();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("No User Found\n");
                attempts--;
            }
        }
    }


    /**
     * This Method creates a new Administrator and adds it to the admins ArrayList.
     * The program will check the checkAdmins Method to see if the user exists,
     * if the user does exist, it will show an appropriate error message.
     */
    private static void createAdmin() throws IOException {
        System.out.println("\n====================");
        System.out.println("Username: ");
        String username = sc.nextLine();
        System.out.println("Password: ");
        String password = sc.nextLine();

        if(checkAdmins(username, password) != null) {
            System.out.println("User Already Exists");
        } else {
            ArrayList<String> admin = new ArrayList<>(3);
            admin.add(String.valueOf(admins.size() + 1));
            admin.add(username);
            admin.add(password);
            admins.add(admin);
            loggedInAdmin = admin; //set the new user as the current logged in user
            listLeagues();
        }
    }

    /**
     * Shows a list of leagues administered by the logged in administrator
     */
    private static void listLeagues() throws IOException {
        leagues.clear(); //Empties arraylist before reading leagues from file
        File listLeaguesFile = new File(leaguesFileName);
        if(listLeaguesFile.exists()) {
            Scanner list;
            list = new Scanner(listLeaguesFile);
            while (list.hasNext()) {
                ArrayList<String> league = new ArrayList<>(4);
                String entryFromFile = list.nextLine();
                String fileElements[] = entryFromFile.split(",");
                league.add(fileElements[0]);
                league.add(fileElements[1]);
                league.add(fileElements[2]);
                league.add(fileElements[3]);
                league.add(fileElements[4]);
                league.add(fileElements[5]);
                leagues.add(league);
            }
            list.close();
        }

        boolean exit = false;
        while(!exit) {
            System.out.println("0) Go Back");
            System.out.println("--------------------");
            System.out.println("1) List Leagues");
            System.out.println("2) Create New League");
            System.out.println("--------------------");
            System.out.print("?) ");

            int option;
            option = Integer.parseInt(sc.nextLine());

            switch (option) {
                case 0: exit = true; break;
                case 1: showleague(); break;
                case 2: createNewLeague(); break;
            }
        }
    }

    private static void showleague() throws IOException {
        if (leagues.isEmpty()) {
            System.out.println("\nNo Leagues Exist\n");
            return;
        }

        ArrayList<ArrayList<String>> adminLeagues = new ArrayList<>();
        for(ArrayList<String> league : leagues) {
            if(league.get(2).equals(loggedInAdmin.get(0))) {
                adminLeagues.add(league);
            }
        }

        if(adminLeagues.isEmpty()) {
            System.out.println("\nNo Leagues Exist\n");
            return;
        }

        System.out.println("0) Go Back");
        System.out.println("--------------------");
        for(int i = 0; i < adminLeagues.size(); i++) {
            System.out.println(i + 1 + ") " + adminLeagues.get(i).get(1));
        }
        System.out.println("--------------------");
        System.out.print("?) ");

        int option;
        option = Integer.parseInt(sc.nextLine());

        if(option == 0) {
            return;
        }

        if(option < 0 || option > adminLeagues.size()) {
            System.out.println("Invalid Option.");
            return;
        }

        ArrayList<String> league = adminLeagues.get(option - 1);
        ArrayList<ArrayList<String>> teams = new ArrayList<>();
        readTeams(league, teams);
        showTeams(teams);
        leagueOptions(league, teams);
    }


    private static void createNewLeague() throws IOException {
        File listLeaguesFile = new File(leaguesFileName);
        listLeaguesFile.createNewFile(); //If file already exists, will do nothing.

        System.out.println("0) Go Back");
        System.out.println("--------------------");
        System.out.println("Name you league.");

        String name;
        name = sc.nextLine();

        if (name.equals("0")) {
            return;
        }

        ArrayList<String> league = new ArrayList<>(4);
        league.add(String.valueOf(leagues.size() + 1)); //League ID
        league.add(name);
        league.add(loggedInAdmin.get(0)); //Logged In AdminID

        String leagueTeamsFileName = league.get(0) + "teamsG.csv";
        File leagueTeamFile = new File(leagueTeamsFileName);
        leagueTeamFile.createNewFile();

        league.add(leagueTeamsFileName);

        String leagueFixturesFileName = league.get(0) + "fixturesG.csv";
        File leagueFixturesFile = new File(leagueFixturesFileName);
        leagueFixturesFile.createNewFile();

        league.add(leagueFixturesFileName);

        String leagueOutcomesFileName = league.get(0) + "outcomes.csv";
        File leagueOutcomesFile = new File(leagueOutcomesFileName);
        leagueOutcomesFile.createNewFile();

        league.add(leagueOutcomesFileName);

        leagues.add(league);

        ArrayList<ArrayList<String>> teams = new ArrayList<>(); //create empty teamsG arraylist
        leagueOptions(league, teams);
    }

    private static void readTeams(ArrayList<String> league, ArrayList<ArrayList<String>> teams) throws FileNotFoundException {
        File teamsFile = new File(league.get(3));
        if(teamsFile.exists()) {
            Scanner list;
            list = new Scanner(teamsFile);
            while (list.hasNext()) {
                ArrayList<String> team = new ArrayList<>(2);
                String entryFromFile = list.nextLine();
                String fileElements[] = entryFromFile.split(",");
                team.add(fileElements[0]);
                team.add(fileElements[1]);
                teams.add(team);
            }
            list.close();
        }
    }

    private static void showTeams(ArrayList<ArrayList<String>> teams) {
        System.out.println("\n--------------------");
        for(ArrayList<String> team : teams) {
            System.out.println(team.get(0) + " - " + team.get(1));
        }
        if(teams.isEmpty()) {
            System.out.println("No Teams.");
        }
        System.out.println("--------------------\n");
    }

    private static boolean additionalTeam = false;
    private static String[][] fixtures;
    private static ArrayList<ArrayList<String>> outcomes = new ArrayList<>();

    private static void leagueOptions(ArrayList<String> league, ArrayList<ArrayList<String>> teams) throws IOException {
        boolean exit = false;

        while(!exit) {
            System.out.println("0) Go Back");
            System.out.println("--------------------");
            System.out.println("1) Add New Team");
            if (!teams.isEmpty()) {
                System.out.println("2) Display All Fixtures");
                System.out.println("3) Display Table");
                System.out.println("4) Display Teams");
            }
            System.out.println("--------------------");
            System.out.print("?) ");

            int option;
            option = Integer.parseInt(sc.nextLine());

            switch (option) {
                case 0: exit = true; break;
                case 1: addNewTeam(teams, league.get(3)); break;
                case 2: {
                    int teamAmount = teams.size();

                    if (teamAmount % 2 == 1) {
                        teamAmount++;
                        additionalTeam = true;
                    }
                    int roundAmount = teamAmount - 1;

                    readFixturesOutcomes(league.get(4), roundAmount, teamAmount, league.get(5));
                    displayFixtures(teams);
                    fixtureOptions(teams, league.get(4), league.get(5));
                    break;
                }
                case 3: prepareFiles(league.get(3), league.get(4), league.get(5)); break;
                case 4: showTeams(teams); break;
            }
        }
    }

    private static void addNewTeam(ArrayList<ArrayList<String>> teams, String teamFileName) throws FileNotFoundException {
        System.out.println("0) Go Back");
        System.out.println("--------------------");
        System.out.println("Enter Team Name.");

        String name;
        name = sc.nextLine();

        if (name.equals("0")) {
            return;
        }

        ArrayList<String> team = new ArrayList<>(2);
        team.add(String.valueOf(teams.size() + 1)); //League ID
        team.add(name);
        teams.add(team);

        writeTeams(teams, teamFileName);
    }

    private static void readFixturesOutcomes(String fixturesFileName, int roundAmount, int teamAmount, String outcomesFileName) throws FileNotFoundException {
        System.out.println("Load fixturesG into variable from: " + fixturesFileName);
        System.out.println("Using roundAmount: " + roundAmount + " & teamAmount: " + teamAmount);

        int matchesPerRound = teamAmount / 2;
        fixtures = new String[roundAmount * 2][matchesPerRound];

        File fixturesFile = new File(fixturesFileName);
        if(fixturesFile.exists()) {
            System.out.println("Need to read fixturesG from: " + fixturesFileName + " into fixturesG array");
            System.out.println("If fixturesG array is not completely full, need to re-generate all fixturesG");
        }

        boolean newFixtures = false;// shows if fixturesG have been generated, then previous outcomes are nulled

        if (fixtures[0][0] == null) { //generate new fixturesG
            newFixtures = true;
            for (int roundNumber = 0; roundNumber < roundAmount; roundNumber++) {
                for (int matchNumber = 0; matchNumber < matchesPerRound; matchNumber++) {
                    int homeTeamNumber = (roundNumber + matchNumber) % (teamAmount - 1);
                    int awayTeamNumber = (teamAmount - 1 - matchNumber + roundNumber) % (teamAmount - 1);

                    if (matchNumber == 0) {
                        awayTeamNumber = teamAmount - 1;
                    }

                    fixtures[roundNumber][matchNumber] = (homeTeamNumber + 1) + " v " + (awayTeamNumber + 1);
                }
            }

            for (int roundNumber = 0; roundNumber < roundAmount; roundNumber++) {
                for (int matchNumber = 0; matchNumber < matchesPerRound; matchNumber++) {
                    String fixtureAsText = fixtures[roundNumber][matchNumber];
                    String[] elementsOfFixture = fixtureAsText.split(" v ");
                    fixtures[roundNumber + roundAmount][matchNumber] = elementsOfFixture[1] + " v " + elementsOfFixture[0];
                }
            }

            writeFixtures(fixturesFileName);
        }

        int totalMatches = roundAmount * 2 * matchesPerRound;

        for(int i = 0; i < totalMatches; i++) { //initialise arraylist with null values
            outcomes.add(i, null);
        }

        //if(!newFixtures) {
            File outcomesFile = new File(outcomesFileName);
            if(outcomesFile.exists()) {
                Scanner list;
                list = new Scanner(outcomesFile);
                while (list.hasNext()) {
                    ArrayList<String> outcome = new ArrayList<>(3);
                    String entryFromFile = list.nextLine();
                    String fileElements[] = entryFromFile.split(",");
                    outcome.add(fileElements[0]);
                    outcome.add(fileElements[1]);
                    outcome.add(fileElements[2]);
                    outcomes.set(Integer.parseInt(outcome.get(0)), outcome);
                }
                list.close();
            }
        //}
    }

    private static void displayFixtures(ArrayList<ArrayList<String>> teams) {
        int teamAmount = teams.size();
        if(additionalTeam) teamAmount++;
        int matchesPerRound = teamAmount / 2;

        for (int roundNumber = 0; roundNumber < fixtures.length; roundNumber++) {
            System.out.println("Round " + (roundNumber + 1) + ":\n");

            for (int matchNumber = 0; matchNumber < matchesPerRound; matchNumber++) {
                int fixtureId = roundNumber * matchesPerRound + matchNumber;
                String fixtureAsText = fixtures[roundNumber][matchNumber];
                String[] elementsOfFixture = fixtureAsText.split(" v ");
                int homeTeamId = Integer.parseInt(elementsOfFixture[0]);
                int awayTeamId = Integer.parseInt(elementsOfFixture[1]);
                String homeTeam;
                String awayTeam;
                if(homeTeamId - 1 == teams.size()) homeTeam = "bye";
                else homeTeam = teams.get(homeTeamId - 1).get(1);
                if(awayTeamId - 1 == teams.size()) awayTeam = "bye";
                else awayTeam = teams.get(awayTeamId - 1).get(1);
                System.out.print((fixtureId + 1) + ")\tMatch " + (matchNumber + 1) + ": " + homeTeam + " v " + awayTeam);
                if(!(outcomes.get(fixtureId) == null)) {
                    ArrayList<String> outcome = outcomes.get(fixtureId);
                    System.out.print(", " + outcome.get(1) + " v " + outcome.get(2));
                } else {
                    System.out.println(", No Outcome Provided Yet.");
                }
                System.out.println("\n");
            }

            if (additionalTeam) {
                //System.out.println("Since you had  " + (teamAmount - 1) + " teamsG to begin with (an odd number), fixture " + " against team number " + teamAmount + " are byes.");
            }
        }
    }

    private static void fixtureOptions(ArrayList<ArrayList<String>> teams, String fixturesFileName, String outcomesFileName) throws FileNotFoundException {
        while(true) {
            System.out.println("0) Go Back");
            System.out.println("--------------------");
            System.out.println("Enter Fixture Number to Edit the Outcome");
            System.out.println("a) Automatically Choose Outcomes");
            System.out.println("--------------------");
            System.out.print("?) ");

            String input = sc.nextLine();

            if(input.equals("a")) {
                automateOutcomes(outcomesFileName);
                displayFixtures(teams);
                continue;
            }

            int option = Integer.parseInt(input);

            if(option == 0) {
                return;
            }

            if(option < 0 || option > fixtures.length * fixtures[0].length) {
                System.out.println("Invalid Option.");
                return;
            }

            editOutcome(option - 1, outcomesFileName);
        }
    }

    private static void automateOutcomes(String outcomesFileName) throws FileNotFoundException {
        System.out.println("Use fixturesG array to generate random scores for outcomes ArrayList");
        System.out.println("outcomes Arraylist: fixtureID,homeScore,awayScore");

        for(int i = 0; i < outcomes.size(); i++) {
            ArrayList<String> outcome = outcomes.get(i);
            if(outcome == null) {
                outcome = new ArrayList<>(3);
                outcome.add(String.valueOf(i));
                outcome.add("randomHomeScore");
                outcome.add("randomAwayScore");
                outcomes.set(i, outcome);
            }
        }

        writeOutcomes(outcomesFileName);
    }

    private static void editOutcome(int fixtureId, String outcomeFileName) throws FileNotFoundException {
        System.out.println("Enter Home Team Score:");
        String homeScore = sc.nextLine();

        System.out.println("Enter Away Team Score:");
        String awayScore = sc.nextLine();

        ArrayList<String> outcome = new ArrayList<>(3);
        outcome.add(String.valueOf(fixtureId));
        outcome.add(homeScore);
        outcome.add(awayScore);

        outcomes.set(fixtureId, outcome);
        writeOutcomes(outcomeFileName);
    }

    private static void generateLeaderboard(String teamsFileName, String fixturesFileName, String outcomesFileName) {
        System.out.println("Uses: " + teamsFileName + ", " + fixturesFileName + " & " + outcomesFileName + " to generate Leaderboard. Conor.");
    }

    /**
     * Checks all entries in the admins ArrayList for a valid username and password and returns boolean value.
     */
    private static ArrayList<String> checkAdmins(String username, String password) {
        for(ArrayList<String> admin : admins) {
            if(admin.get(1).equals(username) & admin.get(2).equals(password)) {
                return admin;
            }
        }
        return null;
    }

    /**
     * This method writes the username and password to the file using a PrintWriter.
     */
    private static void writeAdministrators() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(adminFileName);
        for(ArrayList<String> admin : admins) {
            pw.println(admin.get(0) + "," + admin.get(1) + "," + admin.get(2));
        }
        pw.close();
    }

    private static void writeLeagues() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(leaguesFileName);
        for (ArrayList<String> league : leagues) {
            pw.println(league.get(0) + "," + league.get(1) + "," + league.get(2) + "," + league.get(3) + "," +
                    league.get(4) + "," + league.get(5));
        }
        pw.close();
    }

    private static void writeTeams(ArrayList<ArrayList<String>> teams, String teamFileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(teamFileName);
        for (ArrayList<String> team : teams) {
            pw.println(team.get(0) + "," + team.get(1));
        }
        pw.close();
    }

    private static void writeFixtures(String fixturesFileName) throws FileNotFoundException {
        int roundAmount = fixtures.length;
        int matchesPerRound = fixtures[0].length;

        PrintWriter pw = new PrintWriter(fixturesFileName);

        for(int roundNumber = 0; roundNumber < roundAmount; roundNumber++) {
            for(int matchNumber = 0; matchNumber < matchesPerRound; matchNumber++) {
                int fixtureId = roundNumber * matchesPerRound + matchNumber;
                String fixtureAsText = fixtures[roundNumber][matchNumber];
                String[] elementsOfFixture = fixtureAsText.split(" v ");
                int homeTeamId = Integer.parseInt(elementsOfFixture[0]);
                int awayTeamId = Integer.parseInt(elementsOfFixture[1]);

                pw.println(fixtureId + "," + homeTeamId + "," + awayTeamId);
            }
        }
        pw.close();
        System.out.println("Fixtures Saved");
    }

    private static void writeOutcomes(String outcomesFileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(outcomesFileName);
        for (ArrayList<String> outcome : outcomes) {
            if(!(outcome == null) && outcome.size() == 3) {
                pw.println(outcome.get(0) + "," + outcome.get(1) + "," + outcome.get(2));
            }
        }
        pw.close();
    }
}