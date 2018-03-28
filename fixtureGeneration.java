package Project1.FixtureGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        if (f.isFile()) {
            Scanner scanner = null;
            try {
                scanner = new Scanner(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            while (scanner.hasNext()) {
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
        while (!exit) {
            System.out.println("1) Login Existing User\n2) Create New User\n\n0) Exit");
            System.out.println("--------------------");
            System.out.print("?) ");

            int option;

            option = Integer.parseInt(sc.nextLine());

            switch (option) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    loginAdmin();
                    break;
                case 2:
                    createAdmin();
                    break;
            }
        }
    }

    /**
     * This Method allows the user to log in to an existing user using name and password.
     * It gives the user 3 attempts. If login is successful, the program will show the leagues associated with the user.
     */
    private static void loginAdmin() throws IOException {
        int attempts = 3;
        while (attempts > 0) {
            System.out.println("\n====================");
            System.out.println("Attempts Remaining: " + attempts);
            System.out.println("\n====================");
            System.out.println("Username: ");
            String username = sc.nextLine();
            System.out.println("Password: ");
            String password = sc.nextLine();

            loggedInAdmin = checkAdmins(username, password);

            if (loggedInAdmin != null) {
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

        if (checkAdmins(username, password) != null) {
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
        if (listLeaguesFile.exists()) {
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
        while (!exit) {
            System.out.println("0) Go Back");
            System.out.println("--------------------");
            System.out.println("1) List Leagues");
            System.out.println("2) Create New League");
            System.out.println("--------------------");
            System.out.print("?) ");

            int option;
            option = Integer.parseInt(sc.nextLine());

            switch (option) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    showleague();
                    break;
                case 2:
                    createNewLeague();
                    break;
            }
        }
    }

    /**
     * This method checks if the leagues file is empty and shows the leagues belonging to the logged in Admin.
     */
    private static void showleague() throws IOException {
        if (leagues.isEmpty()) {
            System.out.println("\nNo Leagues Exist\n");
            return;
        }

        ArrayList<ArrayList<String>> adminLeagues = new ArrayList<>();
        for (ArrayList<String> league : leagues) {
            if (league.get(2).equals(loggedInAdmin.get(0))) {
                adminLeagues.add(league);
            }
        }

        if (adminLeagues.isEmpty()) {
            System.out.println("\nNo Leagues Exist\n");
            return;
        }

        System.out.println("0) Go Back");
        System.out.println("--------------------");
        for (int i = 0; i < adminLeagues.size(); i++) {
            System.out.println(i + 1 + ") " + adminLeagues.get(i).get(1));
        }
        System.out.println("--------------------");
        System.out.print("?) ");

        int option;
        option = Integer.parseInt(sc.nextLine());

        if (option == 0) {
            return;
        }

        if (option < 0 || option > adminLeagues.size()) {
            System.out.println("Invalid Option.");
            return;
        }

        ArrayList<String> league = adminLeagues.get(option - 1);
        ArrayList<ArrayList<String>> teams = new ArrayList<>();
        readTeams(league, teams);
        showTeams(teams);
        leagueOptions(league, teams);
    }

    /**
     * This method creates a new league when the Create New League method is called from the switch.
     * The method creates a new file for the outcomes fixtures and teams from the league.
     * The method then links to the League Options method.
     */
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

    /**
     * This method reads from the league ArrayList and reads all the teams from the league ArrayList into the Teams ArrayList.
     *
     * @param league
     * @param teams
     * @throws FileNotFoundException
     */
    private static void readTeams(ArrayList<String> league, ArrayList<ArrayList<String>> teams) throws FileNotFoundException {
        File teamsFile = new File(league.get(3));
        if (teamsFile.exists()) {
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

    /**
     * This Method reads from the teams ArrayList and displays the teams to the console.
     *
     * @param teams
     */
    private static void showTeams(ArrayList<ArrayList<String>> teams) {
        System.out.println("\n--------------------");
        for (ArrayList<String> team : teams) {
            System.out.println(team.get(0) + " - " + team.get(1));
        }
        if (teams.isEmpty()) {
            System.out.println("No Teams.");
        }
        System.out.println("--------------------\n");
    }

    private static boolean additionalTeam = false;
    private static String[][] fixtures;
    private static ArrayList<ArrayList<String>> outcomes = new ArrayList<>();

    /**
     * This method has different options to change the leagues, Adding new teams, Displaying fixtures and Displaying the Table.
     * The method links between each other method using a switch.
     * It also adds on an extra team if their is an odd number of teams so that there can be a 'bye' game.
     *
     * @param league
     * @param teams
     * @throws IOException
     */
    private static void leagueOptions(ArrayList<String> league, ArrayList<ArrayList<String>> teams) throws IOException {
        boolean exit = false;

        while (!exit) {
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
                case 0:
                    exit = true;
                    break;
                case 1:
                    addNewTeam(teams, league.get(3));
                    break;
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
                case 3:
                    prepareFiles(league.get(3), league.get(4), league.get(5));
                    break;
                case 4:
                    showTeams(teams);
                    break;
            }
        }
    }

    /**
     * This Method adds a new team to the league file and lonks to the WriteTeams method.
     *
     * @param teams
     * @param teamFileName
     * @throws FileNotFoundException
     */
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

    /**
     * This method reads from the outcomes file and generates the fixtures between teams, and also generated the reverse fixture of the game.
     * The method then links to the writeFixtures method to write the fixtures and the writeOutcomes method to write the outcomes.
     *
     * @param fixturesFileName
     * @param roundAmount
     * @param teamAmount
     * @param outcomesFileName
     * @throws IOException
     */
    private static void readFixturesOutcomes(String fixturesFileName, int roundAmount, int teamAmount, String outcomesFileName) throws IOException {
        int matchesPerRound = teamAmount / 2;
        int totalMatches = roundAmount * 2 * matchesPerRound;
        fixtures = new String[roundAmount * 2][matchesPerRound];

        boolean newFixtures = true; // If new fixtures have been generated, then previous outcomes are set to null

        File fixturesFile = new File(fixturesFileName);
        if (fixturesFile.exists()) {
            // Check to see if the correct amount of fixtures are stored in the fixturesFile
            int lineNumber = (int) Files.lines(Paths.get(fixturesFileName)).count();
            if (lineNumber == totalMatches) {
                // If correct amount of fixtures, we can use the outcomes stored in outcomesFile
                newFixtures = false;
            }
        }

        //1st Leg Fixtures
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

        // 2nd Leg (Reverse Fixtures)
        for (int roundNumber = 0; roundNumber < roundAmount; roundNumber++) {
            for (int matchNumber = 0; matchNumber < matchesPerRound; matchNumber++) {
                String fixtureAsText = fixtures[roundNumber][matchNumber];
                String[] elementsOfFixture = fixtureAsText.split(" v ");
                fixtures[roundNumber + roundAmount][matchNumber] = elementsOfFixture[1] + " v " + elementsOfFixture[0];
            }
        }

        // Save fixtures to .csv file
        writeFixtures(fixturesFileName);

        // Initialise outcomes ArrayList with null values
        for (int i = 0; i < totalMatches; i++) {
            outcomes.add(i, null);
        }

        // No need to read outcomes if new fixtures have been created
        if (!newFixtures) {
            File outcomesFile = new File(outcomesFileName);
            if (outcomesFile.exists()) {
                Scanner list;
                list = new Scanner(outcomesFile);
                while (list.hasNext()) {
                    ArrayList<String> outcome = new ArrayList<>(3);
                    String entryFromFile = list.nextLine();
                    String fileElements[] = entryFromFile.split(",");
                    outcome.add(fileElements[0]);
                    outcome.add(fileElements[1]);
                    outcome.add(fileElements[2]);
                    outcomes.set(Integer.parseInt(outcome.get(0)), outcome); // Keep outcomes in order by setting outcome position to it's fixtureId
                }
                list.close();
            }
        } else {
            writeOutcomes(outcomesFileName);
        }
    }

    /**
     * This method displays the fixtures onto the console and displays an appropriate message if now outcomes provided.
     *
     * @param teams
     */
    private static void displayFixtures(ArrayList<ArrayList<String>> teams) {
        int teamAmount = teams.size();
        if (additionalTeam) teamAmount++;
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
                if (homeTeamId - 1 == teams.size()) homeTeam = "bye";
                else homeTeam = teams.get(homeTeamId - 1).get(1);
                if (awayTeamId - 1 == teams.size()) awayTeam = "bye";
                else awayTeam = teams.get(awayTeamId - 1).get(1);
                System.out.print((fixtureId + 1) + ")\tMatch " + (matchNumber + 1) + ": " + homeTeam + " v " + awayTeam);
                if (!(outcomes.get(fixtureId) == null)) {
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

    /**
     * This method enables the user to edit any of the fixtures to change them to whatever they like or it can automatically set outcomes to the fixtures.
     *
     * @param teams
     * @param fixturesFileName
     * @param outcomesFileName
     * @throws FileNotFoundException
     */
    private static void fixtureOptions(ArrayList<ArrayList<String>> teams, String fixturesFileName, String outcomesFileName) throws FileNotFoundException {
        while (true) {
            System.out.println("0) Go Back");
            System.out.println("--------------------");
            System.out.println("Enter Fixture Number to Edit the Outcome");
            System.out.println("a) Automatically Choose Outcomes");
            System.out.println("--------------------");
            System.out.print("?) ");

            String input = sc.nextLine();

            if (input.equals("a")) {
                automateOutcomes(outcomesFileName);
                displayFixtures(teams);
                continue;
            }

            int option = Integer.parseInt(input);

            if (option == 0) {
                return;
            }

            if (option < 0 || option > fixtures.length * fixtures[0].length) {
                System.out.println("Invalid Option.");
                return;
            }

            editOutcome(option - 1, outcomesFileName);
        }
    }

    /**
     * This method randomly chooses outcomes for the fixtures and writes to the outcomes file.
     *
     * @param outcomesFileName
     * @throws FileNotFoundException
     */
    private static void automateOutcomes(String outcomesFileName) throws FileNotFoundException {
        System.out.println("Use fixturesG array to generate random scores for outcomes ArrayList");
        System.out.println("outcomes Arraylist: fixtureID,homeScore,awayScore");

        for (int i = 0; i < outcomes.size(); i++) {
            ArrayList<String> outcome = outcomes.get(i);
            if (outcome == null) {
                int homeScore, awayScore;
                String.valueOf(homeScore = (int) (Math.random() * 5));
                String.valueOf(awayScore = (int) (Math.random() * 5));
                outcome = new ArrayList<>(3);
                outcome.add(String.valueOf(i));
                outcome.add(String.valueOf(homeScore));
                outcome.add(String.valueOf(awayScore));
                outcomes.set(i, outcome);
            }
        }
        writeOutcomes(outcomesFileName);
    }

    /**
     * This method lets the user edit the fixtures and writes the outcomes to the outcomes file.
     *
     * @param fixtureId
     * @param outcomeFileName
     * @throws FileNotFoundException
     */
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
        for (ArrayList<String> admin : admins) {
            if (admin.get(1).equals(username) & admin.get(2).equals(password)) {
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
        for (ArrayList<String> admin : admins) {
            pw.println(admin.get(0) + "," + admin.get(1) + "," + admin.get(2));
        }
        pw.close();
    }

    /**
     * This method writes the leagues to a league file.
     *
     * @throws FileNotFoundException
     */
    private static void writeLeagues() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(leaguesFileName);
        for (ArrayList<String> league : leagues) {
            pw.println(league.get(0) + "," + league.get(1) + "," + league.get(2) + "," + league.get(3) + "," +
                    league.get(4) + "," + league.get(5));
        }
        pw.close();
    }

    /**
     * This method writes the teams to the teams file.
     *
     * @param teams
     * @param teamFileName
     * @throws FileNotFoundException
     */
    private static void writeTeams(ArrayList<ArrayList<String>> teams, String teamFileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(teamFileName);
        for (ArrayList<String> team : teams) {
            pw.println(team.get(0) + "," + team.get(1));
        }
        pw.close();
    }

    /**
     * This method writes the Fixtures out to the fixtures file.
     *
     * @param fixturesFileName
     * @throws FileNotFoundException
     */
    private static void writeFixtures(String fixturesFileName) throws FileNotFoundException {
        int roundAmount = fixtures.length;
        int matchesPerRound = fixtures[0].length;

        PrintWriter pw = new PrintWriter(fixturesFileName);

        for (int roundNumber = 0; roundNumber < roundAmount; roundNumber++) {
            for (int matchNumber = 0; matchNumber < matchesPerRound; matchNumber++) {
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

    /**
     * This method writes the outcomes of the fixtures to the outcomes file.
     *
     * @param outcomesFileName
     * @throws FileNotFoundException
     */
    private static void writeOutcomes(String outcomesFileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(outcomesFileName);
        for (ArrayList<String> outcome : outcomes) {
            if (!(outcome == null) && outcome.size() == 3) {
                pw.println(outcome.get(0) + "," + outcome.get(1) + "," + outcome.get(2));
            }
        }
        pw.close();
    }
}

class GenerateLeaderBoard {
    /**
     *	Conor Finnegan: 17184258
     */
    public static ArrayList<ArrayList<String>> teamsG;
    public static ArrayList<ArrayList<Integer>> fixturesG;
    public static ArrayList<ArrayList<Integer>> resultsG;
    public static int [][] leaderBoard;

    public static void prepareFiles(String teamsFileName, String fixturesFileName, String outcomesFileName) throws IOException {
        /**If the .txt files can't be found, an error message will be displayed.*/
        boolean readFile;
        readFile = readFilesIntoArrayLists(teamsFileName, fixturesFileName, outcomesFileName);
        if (!readFile)
            System.out.println("One or more files do not exist.");
        else
        {
            /**If the .txt files are found, the leaderboard will be created.*/
            createEmptyLeaderBoard();
            processResults();
            orderLeaderBoard();
            displayLeaderboard();
        }
    }

    public static boolean readFilesIntoArrayLists(String teamsFileName, String fixturesFileName, String outcomesFileName) throws IOException
    {
        /**I am adding the objects declared above*/
        String fileElements[];
        File inputFile1 = new File(teamsFileName);
        File inputFile2 = new File(fixturesFileName);
        File inputFile3 = new File(outcomesFileName);

        teamsG = new ArrayList<ArrayList<String>>();
        teamsG.add(new ArrayList<String>());
        teamsG.add(new ArrayList<String>());

        fixturesG = new ArrayList<ArrayList<Integer>>();
        fixturesG.add(new ArrayList<Integer>());
        fixturesG.add(new ArrayList<Integer>());
        fixturesG.add(new ArrayList<Integer>());

        resultsG = new ArrayList<ArrayList<Integer>>();
        resultsG.add(new ArrayList<Integer>());
        resultsG.add(new ArrayList<Integer>());
        resultsG.add(new ArrayList<Integer>());

        /**If all the files are found, the teamsG, fixturesG and outcomes will be displayed*/
        if (inputFile1.exists() && inputFile2.exists() && inputFile3.exists())
        {
            Scanner in;
            /**Scanner reads the .txt files*/
            in = new Scanner(inputFile1);
            while(in.hasNext())
            {
                /**This splits the .txt file into separate substrings which go in separate columns, eg "1","AFC Bournemouth"*/
                fileElements = (in.nextLine()).split(",");
                /**After it is split, the .get method retrieves the data from the file*/
                teamsG.get(0).add(fileElements[0]);
                teamsG.get(1).add(fileElements[1]);
            }
            in.close();

            in = new Scanner(inputFile2);
            while(in.hasNext())
            {
                fileElements = (in.nextLine()).split(",");
                fixturesG.get(0).add(Integer.parseInt(fileElements[0]));
                fixturesG.get(1).add(Integer.parseInt(fileElements[1]));
                fixturesG.get(2).add(Integer.parseInt(fileElements[2]));
            }
            in.close();

            in = new Scanner(inputFile3);
            while(in.hasNext())
            {
                fileElements = (in.nextLine()).split(",");
                resultsG.get(0).add(Integer.parseInt(fileElements[0]));
                resultsG.get(1).add(Integer.parseInt(fileElements[1]));
                resultsG.get(2).add(Integer.parseInt(fileElements[2]));
            }
            in.close();
            return true;
        }
        else
            return false;
    }

    public static void createEmptyLeaderBoard()
    {
        /** finds out the number of teamsG which determines the number of rows*/
        int rows = teamsG.get(0).size();
        int columns = 14;
        leaderBoard = new int[rows][columns];
        for (int i = 0; i < leaderBoard.length; i++)
            leaderBoard[i][0] = Integer.parseInt(teamsG.get(0).get(i));
    }

    public static void processResults()
    {
        int fixtureNumber, homeScore, awayScore, homeNumber, awayNumber;
        int position;
        for (int i = 0; i < resultsG.get(0).size(); i++)
        {
            /**I'm using the .get method to get the fixturesG, home score, away score, etc*/
            fixtureNumber  = resultsG.get(0).get(i);
            homeScore  = resultsG.get(1).get(i);
            awayScore  = resultsG.get(2).get(i);
            position       = fixturesG.get(0).indexOf(fixtureNumber);
            homeNumber = fixturesG.get(1).get(position);
            awayNumber = fixturesG.get(2).get(position);
            /**If the game ends in a draw, both teamsG get a point*/
            if (homeScore == awayScore)
            {											   /**0,1,0 = 0 wins, 1 draw, 0 losses*/
                recordFixtureResultForHomeTeam(homeNumber,0,1,0,homeScore,awayScore,1);
                recordFixtureResultForAwayTeam(awayNumber,0,1,0,homeScore,awayScore,1);
            }
            /**If the home team wins, they gain 3 points and the away team gets none*/
            else if (homeScore > awayScore)
            {
                recordFixtureResultForHomeTeam(homeNumber,1,0,0,homeScore,awayScore,3);
                recordFixtureResultForAwayTeam(awayNumber,0,0,1,homeScore,awayScore,0);
            }
            /**If the away team wins, they get the points*/
            else
            {
                recordFixtureResultForHomeTeam(homeNumber,0,0,1,homeScore,awayScore,0);
                recordFixtureResultForAwayTeam(awayNumber,1,0,0,homeScore,awayScore,3);
            }
        }
    }
    /**This records the wins, losses, draws, home and away team scores, and points for the home team*/
    public static void recordFixtureResultForHomeTeam(int hTN, int w, int d, int l,
    int hTS, int aTS, int p)
    {
        leaderBoard[hTN-1][1]++;
        leaderBoard[hTN-1][2]+= w;
        leaderBoard[hTN-1][3]+= d;
        leaderBoard[hTN-1][4]+= l;
        leaderBoard[hTN-1][5]+= hTS;
        leaderBoard[hTN-1][6]+= aTS;
        leaderBoard[hTN-1][12] += (hTS - aTS);
        leaderBoard[hTN-1][13] += p;
    }
    /**This does the same for the away team"*/
    private static void recordFixtureResultForAwayTeam(int aTN, int w, int d, int l, int hTS, int aTS, int p) {
        leaderBoard[aTN-1][1]++;
        leaderBoard[aTN-1][7]+= w;
        leaderBoard[aTN-1][8]+= d;
        leaderBoard[aTN-1][9]+= l;
        leaderBoard[aTN-1][10]+= aTS;
        leaderBoard[aTN-1][11]+= hTS;
        leaderBoard[aTN-1][12] += (aTS - hTS);
        leaderBoard[aTN-1][13] += p;
    }

    /**This fills in the empty leaderboard*/
    public static void orderLeaderBoard()
    {
        int [][] temp = new int[leaderBoard.length][leaderBoard[0].length];
        boolean finished = false;
        /**This while loop doesn't end until all of the empty rows and columns are filled*/
        while (!finished)
        {
            finished = true;
            for (int i = 0; i < leaderBoard.length - 1; i++)
            {
                if (leaderBoard[i][13] < leaderBoard[i + 1][13])
                {
                    for (int j = 0; j < leaderBoard[i].length; j++)
                    {
                        temp[i][j]            = leaderBoard[i][j];
                        leaderBoard[i][j]     = leaderBoard[i + 1][j];
                        leaderBoard[i + 1][j] = temp[i][j];
                    }
                    finished = false;
                }
            }
        }
    }

    /**This displays the leaderboard*/
    public static void displayLeaderboard()
    {
        int aTeamNumber;
        String aTeamName, formatStringTeamName;
        String longestTeamName       = teamsG.get(1).get(0);
        int    longestTeamNameLength = longestTeamName.length();

        for (int i = 1; i < teamsG.get(1).size(); i++)
        {
            longestTeamName = teamsG.get(1).get(i);
            if (longestTeamNameLength < longestTeamName.length())
                longestTeamNameLength = longestTeamName.length();
        }
        formatStringTeamName = "%-" + (longestTeamNameLength + 2) + "s";
        System.out.printf(formatStringTeamName,"Team Name");
        System.out.println("  GP  HW  HD  HL  GF  GA  AW  AD  AL  GF  GA   GD   TP");

        for (int i = 0; i < leaderBoard.length; i++)
        {
            aTeamNumber       = leaderBoard[i][0];
            aTeamName         = teamsG.get(1).get(aTeamNumber - 1);
            System.out.printf(formatStringTeamName, aTeamName);
            System.out.printf("%4d", leaderBoard[i][1]);
            System.out.printf("%4d", leaderBoard[i][2]);
            System.out.printf("%4d", leaderBoard[i][3]);
            System.out.printf("%4d", leaderBoard[i][4]);
            System.out.printf("%4d", leaderBoard[i][5]);
            System.out.printf("%4d", leaderBoard[i][6]);
            System.out.printf("%4d", leaderBoard[i][7]);
            System.out.printf("%4d", leaderBoard[i][8]);
            System.out.printf("%4d", leaderBoard[i][9]);
            System.out.printf("%4d", leaderBoard[i][10]);
            System.out.printf("%4d", leaderBoard[i][11]);
            System.out.printf("%5d", leaderBoard[i][12]);
            System.out.printf("%5d", leaderBoard[i][13]);
            System.out.println();
        }
    }
}