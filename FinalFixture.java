package Project1.FixtureGenerator;
import java.util.*;
import java.io.*;

    /**
     * @author Thomas Langley
     *
     * Under this header, the varaibles used throughout the program are declared.
     * A scanner class is created to accept user input.
     * Three-dimensional arrays are created to store the fixtures generated.
     * A pattern for the number of teams added to the fixtures is created.
     * The user is requested to input the name of the file they would like to add the fixtures to.
     * A statement is outputted, requesting a response from the user, which is answered in the int values corresponding to the avaliable responses.
     * An appropriate error message is outputted if the response does not match what is permitted in the program.
     * The other method headers are called for the program's other operations.
     */

    public class FinalFixture {
        public static void main(String[] args) {
            int requestTeam = -1, useFile = -1, addTeam = -1, roundNumber = 0, match = 0, matchNumber = 0, teamAmount = 0, roundAmount = 0, totalMatches = 0, matchesPerRound = 0, homeTeamNumber, awayTeamNumber, maxMatches;

            Scanner input = new Scanner(System.in);
            ArrayList<ArrayList<String>> fixtures = new ArrayList<ArrayList<String>>();
            ArrayList<ArrayList<String>> reversedFixtures = new ArrayList<ArrayList<String>>();
            String pattern = "[0-9]{1,2}";
            String errorMessage1 = "No input has been entered. Resart the program and enter a valid input.";
            String errorMessage2 = "The file name you have inputted already exists. Enter a file name that does not exist.";
            String errorMessage3 = "The input provided does not match the expected format. Enter ";

            System.out.println("Enter the name of the file you would like to create for fixture generation (Note: The file type must be a .txt or .csv type file)");
            String filename = input.nextLine();

            if (filename == null) {
                System.out.println(errorMessage1);
            } else if (filename.indexOf((filename.length() - 3), filename.length()) != ".txt" ||  filename.indexOf((filename.length() - 3), filename.length()) != ".csv") {
                System.out.print(errorMessage2 + "either a .txt file or a .csv file.");
            } else {
                File userFile = new File(filename);
                userFile = new File(filename);

                if (userFile.createNewFile() == false) {
                    System.out.println(errorMessage2);
                } else {

                    System.out.println("Would you like to use the file name you have inputted anyway?\nYes\nNo");
                    useFile = Integer.parseInt(input.nextLine());

                    if (useFile == -1) {
                        System.out.println(errorMessage1);
                    } else if (!useFile != 1 || useFile!= 2) {
                        System.out.println(errorMessage3 + "either the value 1 or 2.");
                    } else if (useFile== 2) {
                        System.out.println("No teams will be given fixtures.");
                    } else {
                        System.out.println("Would you like to add another team to the league? (Note: Enter the value corresponding to your answer)\n1. Yes, I would like to add another team to the leauge.\n2. No, I would like to generate the fixtures already already created.");
                        requestTeam = Integer.parseInt(input.nextLine());

                        if (requestTeam == -1) {
                            System.out.println(errorMessage1);
                        } else if (!requestTeam != 1 || requestTeam != 2) {
                            System.out.println(errorMessage3 + "either the value 1 or 2.");
                        } else if (requestTeam == 2) {
                            System.out.println("No teams will be given fixtures.");
                        } else {
                            GenerateFixtures();
                            OutputFixtures();
                        }
                    }
                }
            }
        }


        /**
         * This method counts the number of lines in the file name the user has inputted,
         * which corresponds to the number of matches that could potentially have been generate before.
         * A FileReader is created to read through the file.
         * A LineNumberReader counts the number of lines in the file.
         * A while loop is created, so that the variable lines is incremented while there lines in the file that contain content.
         * The overall number of matches in the fixtures is the number of lines in the file selected.
         */
        public int CountMatches() {
            int lines = 0;
            FileReader fr = new FileReader(filename);
            LineNumberReader lnr = new LineNumberReader(fr);
            while (lnr.readLine != null) {
                lines++;
            }
            totalMatches = lines;
        }


        /**
         * This method generates the fixture of the league, based on the amount of teams the user would like to add.
         * If the program accepts the number of teams to be added, and the number of teams is odd, another team is added to the league ensure each team has an equal number of matches.
         * The fixtures generated are then reversed for being outputted.
         * The fixtures are then saved to the file inputted.
         */
        public void generateFixtures() {
            System.out.println("Enter the number of teams you would like to add.");
            addTeam = Integer.parseInt(input.nextLine());

            if (addTeam == -1) {
                System.out.println(errorMessage1);
            } else if (!addTeam.matches(pattern)) {
                System.out.println(errorMessage3 + "a value between 1 and 99.");
            } else {
                if (addTeam % 2 == 1) {
                    teamAmount = addTeam++;
                } else {
                    teamAmount = addTeam;
                    roundAmount = teamAmount - 1;
                    matchesPerRound = teamAmount / 2;
                    matchNumber = totalMatches;
                    maxMatches = roundAmount * matchesPerRound;

                    for (int i = 0; i < teamAmount; i++) {
                        if (matchesPerRound % i == 0) {
                            roundNumber++;
                        }
                    }

                    homeTeamNumber = (roundNumber + matchNumber) % (teamAmount - 1);
                    awayTeamNumber = (teamAmount - 1 - matchNumber + roundNumber) % (teamAmount - 1);

                    if (matchNumber == 0) {
                        awayTeamNumber = teamAmount - 1;
                    }

                    fixtures.set(i, i).split(",").set(1, homeTeamNumber).set(2, awayTeamNumber);
                }

                reversedFixtures = Collections.reverse(fixtures);
                fixtures = reversedFixtures;

                writeFile();
            }

            public void writeFile() {
                Filewriter writer = null;
                writer = new Filewriter(filename);

                for (String str : fixtures) {
                    writer.write(wtr);
                }

                writer.close();
            }

            /**
             * The elements generated for the file are then outputted, and a statement is outputted at the end, acknowledging the generation of the fixtures.
             */
	public static OutputFixtures() {
                FileReader fileReader = new FileReader(filename);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                System.out.println("Round Number\t\tMatch Number\t\tHome Team Number\t\tAwayTeamNumber");
                for (int i = 0; i < totalMatches; i++) {
                    roundNumber = (totalMatches / matchNumber);
                    matchNumber = totalMatches;
                    teamAmount = (totalMatches / roundNumber + 1);
                    homeTeamNumber = (roundNumber + matchNumber) % (teamAmount - 1);
                    awayTeamNumber = (teamAmount - 1 - matchNumber + roundNumber) % (teamAmount - 1);

                    System.out.println(roundNumber + "\t\t" + matchNumber + "\t\t" + homeTeamNumber + "\t\t" + awayTeamNumber);
                }

                BufferedReader.close();
                System.out.println("The fixtures have been generated.");
            }
        }
    }