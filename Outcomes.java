package Project1.FixtureGenerator;

/**
 *	Conor Finnegan 17184258
 */
public class Outcomes {
    public static void main (String[] args) {
        int homeScore, awayScore;
        /**There a 380 fixtures, so this code will generate 380 unique outcomes*/
        for (int i = 0; i < 379; i++) {
            /**This generates random scores for both teams. I capped the scores at 5 as anything higher would be unlikely*/
            homeScore = (int)(Math.random() * 5);
            awayScore = (int)(Math.random() * 5);

            /**This will print the match number, and both teams' scores*/
            System.out.println(homeScore + " vs " + awayScore);
            System.out.println();
        }
    }
}