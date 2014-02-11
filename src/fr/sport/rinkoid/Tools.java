package fr.sport.rinkoid;

public class Tools {
    public static final int PAGES = 3;
    public static final int SCHEDULE_PAGE = 0;
    public static final int RANKS_PAGE = 1;
    public static final int KICKERS_PAGE = 2;
    public static final int N1 = 0;
    public static final int N2N = 1;
    public static final int N2S = 2;

    public static String ConvertChampionship(int id) {
        switch(id) {
        case N1:
            return "N1";
        case N2N:
            return "N2N";
        case N2S:
            return "N2S";
        default:
            return "";
        }
    }
}
