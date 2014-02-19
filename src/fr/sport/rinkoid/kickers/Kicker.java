package fr.sport.rinkoid.kickers;

public class Kicker {
    private String name;
    private int goals;
    private String club;

    public Kicker(String name, int goals, String club) {
        this.name = name;
        this.goals = goals;
        this.club = club;
    }

    public String getName() {
        return name;
    }

    public int getGoals() {
        return goals;
    }

    public String getClub() {
        return club;
    }
}
