package fr.sport.rinkoid.kickers;

public class Kicker {
    private String rank;
    private String name;

    public Kicker(String rank, String name) {
        this.rank = rank;
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }
}
