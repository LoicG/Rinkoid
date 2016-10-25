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
            // Version 1810: 2013-2014
            // Version 2211: 2014-2015
            // Version 2644: 2015-2016
            // Version 3055: 2016-2017
            return "http://stat.ffrs.asso.fr/stats/match/"+ type +"/3055";
        case N2N:
            // Version 2214: 2013-2014
            // Version 1812: 2014-2015
            // Version 2647: 2015-2016
            // Version 3059: 2016-2017
            return "http://stat.ffrs.asso.fr/stats/match/"+ type +"/3059";
        case N2S:
            // Version 1853: 2013-2014
            // Version 2215: 2014-2015
            // Version 2648: 2015-2016
            // Version 3060: 2016-2017
            return "http://stat.ffrs.asso.fr/stats/match/"+ type +"/3060";
        default:
            return "";
        }
    }
}
