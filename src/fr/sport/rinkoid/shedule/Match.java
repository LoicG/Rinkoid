package fr.sport.rinkoid.shedule;

public class Match {
    private String home;
    private String score;
    private String outside;
    private String date;

    public Match(String home, String score, String outside, String date) {
        super();
        this.home = home;
        this.score = score;
        this.outside = outside;
        this.date = date;
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

    public String getDate() {
        return date;
    }
}

