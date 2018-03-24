package Project1.FixtureGenerator;

public class DisplayLeagues {
    private int leagueNumber;
    private String leagueName;
    private String administrator;

    public DisplayLeagues(int leagueNumber, String leagueName, String administrator) {
        this.leagueNumber = leagueNumber;
        this.leagueName = leagueName;
        this.administrator = administrator;
    }

    public int getLeagueNumber() {
        return leagueNumber;
    }

    public void setLeagueNumber(int leagueNumber) {
        this.leagueNumber = leagueNumber;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;


    }
}

