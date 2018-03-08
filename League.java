package Project1;

import java.util.ArrayList;

public class League {
        private int id;
        private String name;
        private Administrator admin;
        ArrayList<Team> teams = new ArrayList<>();
        private int numberOfTeams;

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Administrator getAdmin() {
        return admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public League(int id, String name) {
        this.id = id;

        this.name = name;
    }

    public void addTeam (Team team) {
        teams.add(team);
    }


}