package Project1.FixtureGenerator;

import java.io.*;
import java.util.*;
public class GenerateLeaderBoard {
    /**
     *	Conor Finnegan: 17184258
     */
    public static ArrayList<ArrayList<String>>  teams;
    public static ArrayList<ArrayList<Integer>> fixtures;
    public static ArrayList<ArrayList<Integer>> results;
    public static int [][] leaderBoard;
    public static void main(String [] args) throws IOException
    {
        /**If the .txt files can't be found, an error message will be displayed.*/
        boolean readFile;
        readFile = readFilesIntoArrayLists();
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

    public static boolean readFilesIntoArrayLists() throws IOException
    {
        /**I'm declaring new objects. These are the files read*/
        String filename1 = "Teams20152016.txt";
        String filename2 = "Fixtures20152016.txt";
        String filename3 = "Outcomes20152016.txt";

        /**I am adding the objects declared above*/
        String fileElements[];
        File inputFile1 = new File(filename1);
        File inputFile2 = new File(filename2);
        File inputFile3 = new File(filename3);

        teams = new ArrayList<ArrayList<String>>();
        teams.add(new ArrayList<String>());
        teams.add(new ArrayList<String>());

        fixtures = new ArrayList<ArrayList<Integer>>();
        fixtures.add(new ArrayList<Integer>());
        fixtures.add(new ArrayList<Integer>());
        fixtures.add(new ArrayList<Integer>());

        results = new ArrayList<ArrayList<Integer>>();
        results.add(new ArrayList<Integer>());
        results.add(new ArrayList<Integer>());
        results.add(new ArrayList<Integer>());

        /**If all the files are found, the teams, fixtures and outcomes will be displayed*/
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
                teams.get(0).add(fileElements[0]);
                teams.get(1).add(fileElements[1]);
            }
            in.close();

            in = new Scanner(inputFile2);
            while(in.hasNext())
            {
                fileElements = (in.nextLine()).split(",");
                fixtures.get(0).add(Integer.parseInt(fileElements[0]));
                fixtures.get(1).add(Integer.parseInt(fileElements[1]));
                fixtures.get(2).add(Integer.parseInt(fileElements[2]));
            }
            in.close();

            in = new Scanner(inputFile3);
            while(in.hasNext())
            {
                fileElements = (in.nextLine()).split(",");
                results.get(0).add(Integer.parseInt(fileElements[0]));
                results.get(1).add(Integer.parseInt(fileElements[1]));
                results.get(2).add(Integer.parseInt(fileElements[2]));
            }
            in.close();
            return true;
        }
        else
            return false;
    }

    public static void createEmptyLeaderBoard()
    {
        /** finds out the number of teams which determines the number of rows*/
        int rows = teams.get(0).size();
        int columns = 14;
        leaderBoard = new int[rows][columns];
        for (int i = 0; i < leaderBoard.length; i++)
            leaderBoard[i][0] = Integer.parseInt(teams.get(0).get(i));
    }

    public static void processResults()
    {
        int fixtureNumber, homeScore, awayScore, homeNumber, awayNumber;
        int position;
        for (int i = 0; i < results.get(0).size(); i++)
        {
            /**I'm using the .get method to get the fixtures, home score, away score, etc*/
            fixtureNumber  = results.get(0).get(i);
            homeScore  = results.get(1).get(i);
            awayScore  = results.get(2).get(i);
            position       = fixtures.get(0).indexOf(fixtureNumber);
            homeNumber = fixtures.get(1).get(position);
            awayNumber = fixtures.get(2).get(position);
            /**If the game ends in a draw, both teams get a point*/
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
    public static void recordFixtureResultForAwayTeam(int aTN, int w, int d, int l,
                                                      int hTS, int aTS, int p)
    {
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
        String longestTeamName       = teams.get(1).get(0);
        int    longestTeamNameLength = longestTeamName.length();

        for (int i = 1; i < teams.get(1).size(); i++)
        {
            longestTeamName = teams.get(1).get(i);
            if (longestTeamNameLength < longestTeamName.length())
                longestTeamNameLength = longestTeamName.length();
        }
        formatStringTeamName = "%-" + (longestTeamNameLength + 2) + "s";
        System.out.printf(formatStringTeamName,"Team Name");
        System.out.println("  GP  HW  HD  HL  GF  GA  AW  AD  AL  GF  GA   GD   TP");

        for (int i = 0; i < leaderBoard.length; i++)
        {
            aTeamNumber       = leaderBoard[i][0];
            aTeamName         = teams.get(1).get(aTeamNumber - 1);
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

