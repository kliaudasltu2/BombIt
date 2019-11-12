package ktu.bombit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BombIt.db";

    private static final String TABLE_PLAYER = "player_table";
    private static final String ID = "ID";
    private static final String EMAIL = "EMAIL";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";

    private static final String TABLE_SCORE = "score_table";
    private static final String SCORE = "SCORE";
    private static final String PLAYER_ID = "PLAYER_ID";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_PLAYER + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, EMAIL TEXT, DESCRIPTION TEXT)" );
        db.execSQL("create table " + TABLE_SCORE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, SCORE INTEGER, PLAYER_ID TEXT, FOREIGN KEY(PLAYER_ID) REFERENCES PLAYER_TABLE(ID))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + TABLE_PLAYER);

        db.execSQL("Drop table if exists " + TABLE_SCORE);
        onCreate(db);
    }

    public boolean addPlayer(String name, String email, String password, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, name);
        contentValues.put(PASSWORD, password );
        contentValues.put(EMAIL, email);
        contentValues.put(DESCRIPTION, description);
        long result = db.insert(TABLE_PLAYER, null, contentValues);
        if(result == -1){
            return  false;
        }else
            return true;

    }

    public Cursor getPlayer(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_PLAYER + " where USERNAME=?" , new String[] {username});
        return  res;
    }

    public Cursor getPlayers(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from (" +
                "select player_table.ID, player_table.USERNAME, score_table.SCORE " +
                "from player_table " +
                "Left JOIN score_table " +
                "on player_table.ID = score_table.PLAYER_ID " +
                "order by player_table.id, score_table.SCORE ) " +
                " group by id order by score desc", null);
        return  res;
    }


}
