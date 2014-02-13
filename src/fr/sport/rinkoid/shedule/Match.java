package fr.sport.rinkoid.shedule;

public class Match {
    private String home;
    private String score;
    private String outside;

    public Match(String home, String score, String outside) {
        super();
        this.home = home;
        this.score = score;
        this.outside = outside;
    }

    public String getHome() {
        return home;
    }

    public String getScore() {
        return score;
    }

    public String getOutside() {
        return outside;
    }
}

