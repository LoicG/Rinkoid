package fr.sport.rinkoid;

import java.util.ArrayList;
import java.util.LinkedList;

import fr.sport.rinkoid.kickers.Kicker;
import fr.sport.rinkoid.ranks.Rank;
import fr.sport.rinkoid.shedule.Match;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "rinkoidDB";

    private static final String KICKERS_TABLE = "kickers_table";
    private static final String NAME_ATTRIBUT = "name_attribut";
    private static final String CHAMPIONSHIP_ATTRIBUT = "championship_attribut";
    private static final String CLUB_ATTRIBUT = "club_attribut";
    private static final String GOALS_ATTRIBUT = "goals_attribut";
    private static final String CREATE_KICKERS_TABLE = "CREATE TABLE "
            + KICKERS_TABLE + "(" + NAME_ATTRIBUT + " TEXT NOT NULL,"
            + CHAMPIONSHIP_ATTRIBUT + " TEXT NOT NULL," + CLUB_ATTRIBUT
            + " TEXT NOT NULL," + GOALS_ATTRIBUT + " INTEGER NOT NULL" + ")";

    private static final String RANKS_TABLE = "ranks_table";
    private static final String POINTS_ATTRIBUT = "points_attribut";
    private static final String DAYS_ATTRIBUT = "days_attribut";
    private static final String WIN_ATTRIBUT = "win_attribut";
    private static final String DRAW_ATTRIBUT = "draw_attribut";
    private static final String LOST_ATTRIBUT = "lost_attribut";
    private static final String DIFF_ATTRIBUT = "diff_attribut";
    private static final String CREATE_RANKS_TABLE = "CREATE TABLE "
            + RANKS_TABLE + "(" + CLUB_ATTRIBUT + " TEXT NOT NULL,"
            + CHAMPIONSHIP_ATTRIBUT + " TEXT NOT NULL," + POINTS_ATTRIBUT
            + " INTEGER NOT NULL," + DAYS_ATTRIBUT + " INTEGER NOT NULL,"
            + WIN_ATTRIBUT + " INTEGER NOT NULL," + DRAW_ATTRIBUT
            + " INTEGER NOT NULL," + LOST_ATTRIBUT + " INTEGER NOT NULL,"
            + DIFF_ATTRIBUT + " INTEGER NOT NULL" + ")";

    private static final String SCHEDULE_N1_TABLE = "schedule_n1_table";
    private static final String SCHEDULE_N2N_TABLE = "schedule_n2n_table";
    private static final String SCHEDULE_N2S_TABLE = "schedule_n2s_table";
    private static final String DAY_ATTRIBUT = "day_attribut";
    private static final String DATE_ATTRIBUT = "date_attribut";
    private static final String HOME_ATTRIBUT = "home_attribut";
    private static final String SCORE_ATTRIBUT = "score_attribut";
    private static final String OUTSIDE_ATTRIBUT = "outside_attribut";

    private String createShedule(String name) {
        return "CREATE TABLE " + name + "(" + DAY_ATTRIBUT
                + " INTEGER NOT NULL," + DATE_ATTRIBUT + " DATE NOT NULL,"
                + HOME_ATTRIBUT + " TEXT NOT NULL," + SCORE_ATTRIBUT + " TEXT,"
                + OUTSIDE_ATTRIBUT + " TEXT NOT NULL" + ")";
    }

    private static DatabaseHelper mInstance = null;
 
    public static DatabaseHelper getInstance(Context ctx) {
        if(mInstance == null) {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_KICKERS_TABLE);
        db.execSQL(CREATE_RANKS_TABLE);
        db.execSQL(createShedule(SCHEDULE_N1_TABLE));
        db.execSQL(createShedule(SCHEDULE_N2N_TABLE));
        db.execSQL(createShedule(SCHEDULE_N2S_TABLE));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void SaveKicker(ArrayList<Kicker> kickers, int championship) {
        String division = Tools.ConvertChampionship(championship);
        SQLiteDatabase database = getWritableDatabase();
        if(database != null) {
            database.delete(KICKERS_TABLE, CHAMPIONSHIP_ATTRIBUT + "='" + division
                    + "'", null);
            for(Kicker kicker : kickers) {
                ContentValues value = new ContentValues();
                value.put(NAME_ATTRIBUT, kicker.getName());
                value.put(CHAMPIONSHIP_ATTRIBUT, division);
                value.put(CLUB_ATTRIBUT, kicker.getClub());
                value.put(GOALS_ATTRIBUT, kicker.getGoals());
                database.insert(KICKERS_TABLE, null, value);
            }
            database.close();
        }
    }

    public void SaveRanks(ArrayList<Rank> ranks, int championship) {
        String division = Tools.ConvertChampionship(championship);
        SQLiteDatabase database = getWritableDatabase();
        if(database != null) {
            database.delete(RANKS_TABLE, CHAMPIONSHIP_ATTRIBUT + "='" + division
                    + "'", null);
            for (Rank rank : ranks) {
                ContentValues value = new ContentValues();
                value.put(CLUB_ATTRIBUT, rank.getClub());
                value.put(CHAMPIONSHIP_ATTRIBUT, division);
                value.put(POINTS_ATTRIBUT, rank.getPoints());
                value.put(DAYS_ATTRIBUT, rank.getDays());
                value.put(WIN_ATTRIBUT, rank.getWin());
                value.put(DRAW_ATTRIBUT, rank.getDraw());
                value.put(LOST_ATTRIBUT, rank.getLost());
                value.put(DIFF_ATTRIBUT, rank.getDiff());
                database.insert(RANKS_TABLE, null, value);
            }
            database.close();
        }
    }

    public void SaveMatchs(ArrayList<Match> matchs, int championship, int day) {
        SQLiteDatabase database = getWritableDatabase();
        if(database != null) {
            String table = getScheduleTable(Tools.ConvertChampionship(championship));
            database.delete(table, DAY_ATTRIBUT + "='" + day + "'", null);
            for (Match match : matchs) {
                ContentValues value = new ContentValues();
                value.put(DAY_ATTRIBUT, day);
                value.put(DATE_ATTRIBUT, "");
                value.put(HOME_ATTRIBUT, match.getHome());
                value.put(SCORE_ATTRIBUT, match.getScore());
                value.put(OUTSIDE_ATTRIBUT, match.getOutside());
                database.insert(table, null, value);
            }
            database.close();
        }
    }

    private String getScheduleTable(String championship) {
        if (championship == "N1")
            return SCHEDULE_N1_TABLE;
        else if (championship == "N2N")
            return SCHEDULE_N2N_TABLE;
        else
            return SCHEDULE_N2S_TABLE;
    }

    public ArrayList<Match> GetMatchs(int championship, int day) {
        ArrayList<Match> matchs = new ArrayList<Match>();
        String query = "SELECT * FROM "
                + getScheduleTable(Tools.ConvertChampionship(championship))
                + " WHERE " + DAY_ATTRIBUT + " = '" + day + "'";
        SQLiteDatabase database = getReadableDatabase();
        if(database != null) {
            Cursor c = database.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {
                    matchs.add(new Match(c.getString(c
                            .getColumnIndex(HOME_ATTRIBUT)), c.getString(c
                            .getColumnIndex(SCORE_ATTRIBUT)), c.getString(c
                            .getColumnIndex(OUTSIDE_ATTRIBUT)), c.getString(c
                            .getColumnIndex(DATE_ATTRIBUT))));
                } while (c.moveToNext());
            }
            c.close();
            database.close();
        }
        return matchs;
    }

    public ArrayList<Kicker> GetKickers(int championship) {
        ArrayList<Kicker> kickers = new ArrayList<Kicker>();
        kickers.add(new Kicker("", 0, ""));
        String query = "SELECT * FROM " + KICKERS_TABLE + " WHERE "
                + CHAMPIONSHIP_ATTRIBUT + " = '"
                + Tools.ConvertChampionship(championship) + "'" + " ORDER BY "
                + GOALS_ATTRIBUT + " DESC, " + NAME_ATTRIBUT + " ASC";
        SQLiteDatabase database = getReadableDatabase();
        if(database != null) {
            Cursor c = database.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {
                    kickers.add(new Kicker(c.getString(c
                            .getColumnIndex(NAME_ATTRIBUT)), c.getInt(c
                            .getColumnIndex(GOALS_ATTRIBUT)), c.getString(c
                            .getColumnIndex(CLUB_ATTRIBUT))));
                } while (c.moveToNext());
            }
            c.close();
            database.close();
        }
        return kickers;
    }

    public ArrayList<Rank> GetRanks(int championship) {
        ArrayList<Rank> ranks = new ArrayList<Rank>();
        ranks.add(new Rank("", 0, 0, 0, 0, 0, 0));
        String query = "SELECT * FROM " + RANKS_TABLE + " WHERE "
                + CHAMPIONSHIP_ATTRIBUT + " = '"
                + Tools.ConvertChampionship(championship) + "'" + " ORDER BY "
                + POINTS_ATTRIBUT + " DESC, " + DIFF_ATTRIBUT + " DESC, "
                + CLUB_ATTRIBUT + " ASC";
        SQLiteDatabase database = getReadableDatabase();
        if(database != null) {
            Cursor c = database.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {
                    ranks.add(new Rank(
                            c.getString(c.getColumnIndex(CLUB_ATTRIBUT)), c
                                    .getInt(c.getColumnIndex(POINTS_ATTRIBUT)), c
                                    .getInt(c.getColumnIndex(DAYS_ATTRIBUT)), c
                                    .getInt(c.getColumnIndex(WIN_ATTRIBUT)), c
                                    .getInt(c.getColumnIndex(DRAW_ATTRIBUT)), c
                                    .getInt(c.getColumnIndex(LOST_ATTRIBUT)), c
                                    .getInt(c.getColumnIndex(DIFF_ATTRIBUT))));
                } while (c.moveToNext());
            }
            c.close();
            database.close();
        }
        return ranks;
    }

    private int Count(String query) {
        SQLiteDatabase database = getReadableDatabase();
        if(database!=null) {
            Cursor c = database.rawQuery(query, null);
            int count = c.getCount();
            c.close();
            database.close();
            return count;
        }
        return 0;
    }

    private boolean IsEmpty(int championship) {
        String query = "SELECT  * FROM " +
        getScheduleTable(Tools.ConvertChampionship(championship));
        if(Count(query) == 0)
            return  true;
        query = "SELECT * FROM " + RANKS_TABLE + " WHERE "
                + CHAMPIONSHIP_ATTRIBUT + " = '"
                + Tools.ConvertChampionship(championship) +"'";
        return Count(query) == 0;
    }

    public LinkedList<Integer> NeedFirstUpdate() {
        LinkedList<Integer> championship = new LinkedList<Integer>();
        for(int i = 0; i< Tools.CHAMPIONSHIP_COUNT; ++i) {
            if(IsEmpty(i))
                championship.add(i);
        }
        return championship;
    }

    public void Clear() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(KICKERS_TABLE, "", null);
        database.delete(RANKS_TABLE, "", null);
        database.delete(SCHEDULE_N1_TABLE, "", null);
        database.delete(SCHEDULE_N2N_TABLE, "", null);
        database.delete(SCHEDULE_N2S_TABLE, "", null);
        database.close();
    }
}
