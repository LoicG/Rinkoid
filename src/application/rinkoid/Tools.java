package application.rinkoid;

public class Tools {
    public static final int PAGES = 3;
    public static final int SCHEDULE_PAGE = 0;
    public static final int RANKS_PAGE = 1;
    public static final int KICKERS_PAGE = 2;
    public static final int N1 = 0;
    public static final int N2N = 1;
    public static final int N2S = 2;
    public static final int CHAMPIONSHIP_COUNT = 3;

    public static String ConvertChampionship(int championship) {
        switch(championship) {
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

    public static String GetKickersUrl(int championship) {
        return GetUrl(championship, "buteurs");
    }

    public static String GetRanksUrl(int championship) {
        return GetUrl(championship, "classement");
    }

    public static String GetUrl(int championship, int page) {
        switch(page) {
        case SCHEDULE_PAGE:
            return GetSheduleUrl(championship);
        case RANKS_PAGE:
            return GetRanksUrl(championship);
        case KICKERS_PAGE:
            return GetKickersUrl(championship);
        default:
            return "";
        }
    }

    public static int GetDaysCount(int championship) {
        switch(championship) {
        case N1:
            return 22;
        case N2N:
            return 18;
        case N2S:
            return 18;
        default:
            return 0;
        }
    }

    public static String GetSheduleUrl(int championship) {
        return GetUrl(championship, "resultats");
    }

    private static String GetUrl(int championship, String type) {
        switch(championship) {
        case N1:
            return "http://stat.ffrs.asso.fr/stats/match/"+ type +"/1810";
        case N2N:
            return "http://stat.ffrs.asso.fr/stats/match/"+ type +"/1812";
        case N2S:
            return "http://stat.ffrs.asso.fr/stats/match/"+ type +"/1853";
        default:
            return "";
        }
    }
}
