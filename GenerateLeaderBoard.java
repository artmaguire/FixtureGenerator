package Project1.FixtureGenerator;

import java.io.*;
import java.util.*;
public class GenerateLeaderBoard {
    /**
     *	Conor Finnegan: 17184258
     */
    public static ArrayList<ArrayList<String>> teamsG;
    public static ArrayList<ArrayList<Integer>> fixturesG;
    public static ArrayList<ArrayList<Integer>> resultsG;
    public static int [][] leaderBoard;
    public static void main(String [] args) throws IOException
    {
        prepareFiles("Teams20152016.txt", "Fixtures20152016.txt", "Outcomes20152016.txt");
    }

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

