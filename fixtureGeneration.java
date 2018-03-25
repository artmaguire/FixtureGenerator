package Project1.FixtureGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class fixtureGeneration {
    private static Administrator loggedInAdmin = null;
    private static String adminFileName = "adminFile.csv";
    private static String leaguesFileName = "leaguesFileName.csv";
    private static ArrayList<Administrator> admins = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {

        loadAdministrators();

        createLoginUser();

        writeAdministrators();

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

    /**
     * This method writes the username and password to the file using a PrintWriter.
     */
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

    /**
     * Creates the interface for interacting with the program, Logging in, Creating a new user and exiting hte program.
     * The switch in this Method links the method between the loadAdmin Method and the createAdmin Method.
     */
    private static void createLoginUser() throws FileNotFoundException {
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

    /**
     * This Method allows the user to log in to an existing user using name and password.
     * It gives the user 3 attempts. If login is successful, the program will show the leagues associated with the user.
     */
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
    private static void createAdmin() throws FileNotFoundException {
        System.out.println("\n====================");
        System.out.println("Username: ");
        String username = sc.nextLine();
        System.out.println("Password: ");
        String password = sc.nextLine();

        if(checkAdmins(username, password) != null) {
            System.out.println("User Already Exists");
        } else {
            Administrator newAdmin = new Administrator(username, password); //create new administrator object from users inputs
            admins.add(newAdmin); //add them to the array of admins
            loggedInAdmin = newAdmin; //set the new user as the current logged in user
                listLeagues();
        }
    }

    /**
     * Shows a list of leagues administered by the logged in administrator
     */
    private static void listLeagues() throws FileNotFoundException {
        boolean exit = false;
        while(!exit) {
            System.out.println("0) Logout");
            System.out.println("--------------------");
            System.out.println("1) List Leagues");
            System.out.println("2) Create New League");
            System.out.println("--------------------");
            System.out.print("?) ");

            int option = -1;
            option = Integer.parseInt(sc.nextLine());

            switch (option) {
                case 0: exit = true; break;
                case 1: showleague(); break;
            }
        }
    }

    private static void showleague() throws FileNotFoundException {
        File listLeaguesFile = new File(leaguesFileName);
        ArrayList<ArrayList<ArrayList<String>>> leagueEntries = new ArrayList<>();
        ArrayList<String> leagueID = new ArrayList<>();
        ArrayList<String> leagueName = new ArrayList<>();
        ArrayList<String> leagueAdmin = new ArrayList<>();
        Scanner list;
        if (!listLeaguesFile.exists()) {
            System.out.println("\nNo Leagues Exist\n");
        } else {
            list = new Scanner(listLeaguesFile);
            while (list.hasNext()) {
                String entryFromFile = list.nextLine();
                String fileElements[] = entryFromFile.split(",");
                leagueID.add(fileElements[0]);
                leagueName.add(fileElements[1]);
                leagueAdmin.add(fileElements[2]);
                leagueEntries.get(0).add(leagueID);
                leagueEntries.get(1).add(leagueName);
                leagueEntries.get(2).add(leagueAdmin);
                System.out.println("Leagues\n" + leagueAdmin + leagueID + leagueName);
            }
            list.close();
        }
    }


    private static void createNewLeague() throws IOException {
        File listLeaguesFile = new File(leaguesFileName);
        listLeaguesFile.createNewFile(); //If file already exists, will do nothing.
        System.out.println("0) Go Back");
        System.out.println("--------------------");
        System.out.println("1) How many teams would you like in your league?");
        System.out.println("--------------------");
        System.out.print("?) ");

        int option = -1;
        option = Integer.parseInt(sc.nextLine());

        switch (option) {
            case 0: return;
            case 1: //Thomas' file.
        }
    }

    /**
     * Checks all entries in the admins ArrayList for a valid username and password and returns boolean value.
     */
    private static Administrator checkAdmins(String username, String password) {
        for(Administrator a : admins) {
            if(a.check(username, password)) return a;
        }
        return null;
    }
}