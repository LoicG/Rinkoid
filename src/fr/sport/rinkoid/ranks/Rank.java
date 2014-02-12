package fr.sport.rinkoid.ranks;

public class Rank {
    private String club;
    private String points;
    private String win;
    private String draw;
    private String lost;
    private String diff;

    public Rank(String club, String points, String win, String draw,
            String lost, String diff) {
        this.club = club;
        this.points = points;
        this.win = win;
        this.draw = draw;
        this.lost = lost;
        this.diff = diff;
    }

    public String getClub() {
        return club;
    }

    public String getPoints() {
        return points;
    }

    public String getWin() {
        return win;
    }

    public String getDraw() {
        return draw;
    }

    public String getLost() {
        return lost;
    }

    public String getDiff() {
        return diff;
    }
}

