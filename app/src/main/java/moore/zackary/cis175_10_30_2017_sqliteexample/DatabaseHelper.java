package moore.zackary.cis175_10_30_2017_sqliteexample;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Zackary on 10/29/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "demo.db";
    public static final String TABLE_NAME = "names";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        database.execSQL("CREATE TABLE names (NAMES TEXT PRIMARY KEY NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1)
    {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    public boolean dbf_initRows() {
        if ( dbf_numberOfRows() == 0 ) {
            SQLiteDatabase db = this.getWritableDatabase();

            db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('Zack');");
            db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('Gabriel');");
            db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('Shannon');");
            db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('Darrel');");

            db.close();
            return true;
        }
        else {
            return false;
        }
    }

    public int dbf_numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return numRows;
    }

    public ArrayList<String> dbf_getAllRecords() {
        ArrayList<String> lv_list = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                lv_list.add(cursor.getString(cursor.getColumnIndex("NAMES")));
            } while (cursor.moveToNext());
        }
        db.close();

        return lv_list;
    }

    public void dbf_deletePart(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE NAMES = '" + name + "'");
        db.close();
    }

    public void dbf_appendPart(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('" + name + "');");
        db.close();
    }
}
