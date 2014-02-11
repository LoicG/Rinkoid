package fr.sport.rinkoid;

import java.util.ArrayList;

import fr.sport.rinkoid.kickers.Kicker;
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

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_KICKERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void Save( String name, String championship, String club, int goals )
    {
        ContentValues value = new ContentValues();
        value.put(NAME_ATTRIBUT, name);
        value.put(CHAMPIONSHIP_ATTRIBUT, championship);
        value.put(CLUB_ATTRIBUT, club);
        value.put(GOALS_ATTRIBUT, goals);
        getWritableDatabase().insert(KICKERS_TABLE, null, value);
    }

    public ArrayList<Kicker> GetKickers( String championship )
    {
        ArrayList<Kicker> kickers = new ArrayList<Kicker>();
        String selectQuery = "SELECT  * FROM " + KICKERS_TABLE +
                " WHERE " + CHAMPIONSHIP_ATTRIBUT + " = '" + championship + "'" +
                " ORDER BY " + GOALS_ATTRIBUT + " DESC, " + NAME_ATTRIBUT + " ASC";
        Cursor c = getReadableDatabase().rawQuery(selectQuery, null);
        if(c.moveToFirst()) {
            do {
                kickers.add(new Kicker( c.getString(c.getColumnIndex(NAME_ATTRIBUT)),
                        c.getString(c.getColumnIndex(GOALS_ATTRIBUT)),
                        c.getString(c.getColumnIndex(CLUB_ATTRIBUT))));
            } while (c.moveToNext());
        }
        return kickers;
    }

    public void Clear() {
        getWritableDatabase().delete(KICKERS_TABLE, "", null);
    }
}
