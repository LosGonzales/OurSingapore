package sg.edu.rp.c346.id20013676.myndpsongs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "simpleIslands.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_ISLAND = "islands";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_AREA = "area";
    private static final String COLUMN_STARS = "stars";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createIslandTableSql = "CREATE TABLE " + TABLE_ISLAND + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_AREA + " INTEGER, "
                + COLUMN_STARS + " INTEGER ) ";
        db.execSQL(createIslandTableSql);
        Log.i("info", "created tables");

        //Dummy records, to be inserted when the database is created
        for (int i = 0; i< 4; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, "Data number " + i);
            values.put(COLUMN_DESCRIPTION, "Data number " + i);
            values.put(COLUMN_AREA, i);
            values.put(COLUMN_STARS, i);

            db.insert(TABLE_ISLAND, null, values);
        }
        Log.i("info", "dummy records inserted");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("ALTER TABLE " + TABLE_ISLAND + " ADD COLUMN  genre TEXT ");

    }


    public long insertIsland(String name, String description, int area, int stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_AREA, area);
        values.put(COLUMN_STARS, stars);
        long result = db.insert(TABLE_ISLAND, null, values);
        db.close();
        Log.d("SQL Insert","ID:"+ result); //id returned, shouldn’t be -1
        return result;
    }

    public ArrayList<Island> getAllIslands() {
        ArrayList<Island> Islands = new ArrayList<Island>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME + ","
                + COLUMN_DESCRIPTION + ","
                + COLUMN_AREA + ","
                + COLUMN_STARS +
                " FROM " + TABLE_ISLAND;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                int area = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Island Island = new Island(name, description, area, stars);
                Island.set_id(id);
                Islands.add(Island);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return Islands;
    }

    public int updateIsland(Island data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_DESCRIPTION, data.getDescription());
        values.put(COLUMN_AREA, data.getArea());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.get_id())};
        int result = db.update(TABLE_ISLAND, values, condition, args);
        db.close();
        return result;
    }


    public int deleteIsland(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_ISLAND, condition, args);
        db.close();
        return result;
    }
    public ArrayList<Island> getAllIslands(String keyword) {
        ArrayList<Island> Islands = new ArrayList<Island>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_AREA, COLUMN_STARS};
        String condition = COLUMN_STARS + " = ?";
        String[] args = {keyword};
        Cursor cursor = db.query(TABLE_ISLAND, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                int area = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Island Island = new Island(name, description, area, stars);
                Island.set_id(id);
                Islands.add(Island);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return Islands;
    }

    public ArrayList<String> getAllAreas() {
        ArrayList<String> areas = new ArrayList<String>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME + ","
                + COLUMN_DESCRIPTION + ","
                + COLUMN_AREA + ","
                + COLUMN_STARS +
                " FROM " + TABLE_ISLAND;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int area = cursor.getInt(3);
                areas.add(area + "");
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return areas;
    }

    public ArrayList<Island> filterArea(String keyword) {
        ArrayList<Island> Islands = new ArrayList<Island>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_AREA, COLUMN_STARS};
        String condition = COLUMN_AREA + " = ?";
        String[] args = {keyword};
        Cursor cursor = db.query(TABLE_ISLAND, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                int area = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Island Island = new Island(name, description, area, stars);
                Island.set_id(id);
                Islands.add(Island);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return Islands;
    }


}

