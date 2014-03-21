package application.rinkoid.ranks;

public class Rank {
    private String club;
    private int points;
    private int days;
    private int win;
    private int draw;
    private int lost;
    private int diff;

    public Rank(String club, int points, int days, int win, int draw,
            int lost, int diff) {
        this.club = club;
        this.points = points;
        this.days = days;
        this.win = win;
        this.draw = draw;
        this.lost = lost;
        this.diff = diff;
    }

    public String getClub() {
        return club;
    }

    public int getPoints() {
        return points;
    }

    public int getWin() {
        return win;
    }

    public int getDraw() {
        return draw;
    }

    public int getLost() {
        return lost;
    }

    public int getDiff() {
        return diff;
    }

    public int getDays() {
        return days;
    }
}

