package fr.sport.rinkoid;

import java.util.ArrayList;

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
    private static final String CREATE_KICKERS_TABLE = "CREATE TABLE " + KICKERS_TABLE
            + "(" + NAME_ATTRIBUT + " TEXT NOT NULL," 
            + CHAMPIONSHIP_ATTRIBUT + " TEXT NOT NULL,"
            + CLUB_ATTRIBUT + " TEXT NOT NULL,"
            + GOALS_ATTRIBUT + " INTEGER NOT NULL" + ")";

    private static final String RANKS_TABLE = "ranks_table";
    private static final String POINTS_ATTRIBUT = "points_attribut";
    private static final String WIN_ATTRIBUT = "win_attribut";
    private static final String DRAW_ATTRIBUT = "draw_attribut";
    private static final String LOST_ATTRIBUT = "lost_attribut";
    private static final String DIFF_ATTRIBUT = "diff_attribut";
    private static final String CREATE_RANKS_TABLE = "CREATE TABLE " + RANKS_TABLE
            + "(" + CLUB_ATTRIBUT + " TEXT NOT NULL," 
            + CHAMPIONSHIP_ATTRIBUT + " TEXT NOT NULL,"
            + POINTS_ATTRIBUT + " INTEGER NOT NULL,"
            + WIN_ATTRIBUT + " INTEGER NOT NULL,"
            + DRAW_ATTRIBUT + " INTEGER NOT NULL,"
            + LOST_ATTRIBUT + " INTEGER NOT NULL,"
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
        return "CREATE TABLE " + name +
                "(" + DAY_ATTRIBUT + " INTEGER NOT NULL," 
                + DATE_ATTRIBUT + " DATE NOT NULL,"
                + HOME_ATTRIBUT + " TEXT NOT NULL,"
                + SCORE_ATTRIBUT + " TEXT,"
                + OUTSIDE_ATTRIBUT + " TEXT NOT NULL" + ")";
    }

    public DatabaseHelper(Context context) {
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

    public void SaveKicker(String name, String championship, String club, int goals) {
        ContentValues value = new ContentValues();
        value.put(NAME_ATTRIBUT, name);
        value.put(CHAMPIONSHIP_ATTRIBUT, championship);
        value.put(CLUB_ATTRIBUT, club);
        value.put(GOALS_ATTRIBUT, goals);
        getWritableDatabase().insert(KICKERS_TABLE, null, value);
    }

    public void SaveRank(String club, String championship, int points, int win,
        int draw, int lost, int diff) {
        ContentValues value = new ContentValues();
        value.put(CLUB_ATTRIBUT, club);
        value.put(CHAMPIONSHIP_ATTRIBUT, championship);
        value.put(POINTS_ATTRIBUT, points);
        value.put(WIN_ATTRIBUT, win);
        value.put(DRAW_ATTRIBUT, draw);
        value.put(LOST_ATTRIBUT, lost);
        value.put(DIFF_ATTRIBUT, diff);
        getWritableDatabase().insert(RANKS_TABLE, null, value);
    }

    public void SaveMatch(int day, String championship, String date, String home,
            String score, String outside) {
        ContentValues value = new ContentValues();
        value.put(DAY_ATTRIBUT, day);
        value.put(DATE_ATTRIBUT, date);
        value.put(HOME_ATTRIBUT, home);
        value.put(SCORE_ATTRIBUT, score);
        value.put(OUTSIDE_ATTRIBUT, outside);
        getWritableDatabase().insert(getScheduleTable(championship), null, value);
    }

    private String getScheduleTable(String championship) {
        if(championship == "N1")
            return SCHEDULE_N1_TABLE;
        else if( championship == "N2N")
            return SCHEDULE_N2N_TABLE;
        else
            return SCHEDULE_N2S_TABLE;
    }

    public int GetScheduleCount(String championship) {
        String query = "SELECT MAX(" + DAY_ATTRIBUT + ") FROM " +
                getScheduleTable(championship);
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        int count = 0;
        if(cursor.moveToFirst())
        {
            do
            {
                count = cursor.getInt(0);
            } while(cursor.moveToNext());
        }
        return count;
    }

    public ArrayList<Match> GetMatchs(String championship, int day ) {
        ArrayList<Match> matchs = new ArrayList<Match>();
        String query = "SELECT * FROM " + getScheduleTable(championship) +
                " WHERE " + DAY_ATTRIBUT + " = '" + day + "'";
        Cursor c = getReadableDatabase().rawQuery(query, null);
        if(c.moveToFirst()) {
            do {
                matchs.add(new Match( c.getString(c.getColumnIndex(HOME_ATTRIBUT)),
                        c.getString(c.getColumnIndex(SCORE_ATTRIBUT)),
                        c.getString(c.getColumnIndex(OUTSIDE_ATTRIBUT))));
            } while (c.moveToNext());
        }
        return matchs;
    }

    public ArrayList<Kicker> GetKickers(String championship) {
        ArrayList<Kicker> kickers = new ArrayList<Kicker>();
        String query = "SELECT * FROM " + KICKERS_TABLE +
                " WHERE " + CHAMPIONSHIP_ATTRIBUT + " = '" + championship + "'" +
                " ORDER BY " + GOALS_ATTRIBUT + " DESC, " + NAME_ATTRIBUT + " ASC";
        Cursor c = getReadableDatabase().rawQuery(query, null);
        if(c.moveToFirst()) {
            do {
                kickers.add(new Kicker( c.getString(c.getColumnIndex(NAME_ATTRIBUT)),
                        c.getString(c.getColumnIndex(GOALS_ATTRIBUT)),
                        c.getString(c.getColumnIndex(CLUB_ATTRIBUT))));
            } while (c.moveToNext());
        }
        return kickers;
    }

    public ArrayList<Rank> GetRanks(String championship) {
        ArrayList<Rank> ranks = new ArrayList<Rank>();
        String query = "SELECT * FROM " + RANKS_TABLE +
                " WHERE " + CHAMPIONSHIP_ATTRIBUT + " = '" + championship + "'" +
                " ORDER BY " + POINTS_ATTRIBUT + " DESC, " + DIFF_ATTRIBUT + " DESC, "
                + CLUB_ATTRIBUT + " ASC";
        Cursor c = getReadableDatabase().rawQuery(query, null);
        if(c.moveToFirst()) {
            do {
                ranks.add(new Rank( c.getString(c.getColumnIndex(CLUB_ATTRIBUT)),
                        c.getString(c.getColumnIndex(POINTS_ATTRIBUT)),
                        c.getString(c.getColumnIndex(WIN_ATTRIBUT)),
                        c.getString(c.getColumnIndex(DRAW_ATTRIBUT)),
                        c.getString(c.getColumnIndex(LOST_ATTRIBUT)),
                        c.getString(c.getColumnIndex(DIFF_ATTRIBUT))));
            } while (c.moveToNext());
        }
        return ranks;
    }

    public void Clear() {
      getWritableDatabase().delete(KICKERS_TABLE, "", null);
      getWritableDatabase().delete(RANKS_TABLE, "", null);
      getWritableDatabase().delete(SCHEDULE_N1_TABLE, "", null);
      getWritableDatabase().delete(SCHEDULE_N2N_TABLE, "", null);
      getWritableDatabase().delete(SCHEDULE_N2S_TABLE, "", null);
    }
}
