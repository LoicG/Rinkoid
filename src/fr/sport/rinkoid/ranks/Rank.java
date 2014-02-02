package fr.sport.rinkoid.ranks;

public class Rank {
    private String rank;
    private String name;

    public Rank(String rank, String name) {
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
