package Db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by ss22493 on 29-09-2016.
 */
public class DBAssettsFolder extends SQLiteOpenHelper {
    public static final String DATABASE_PATH = "/data/data/satnam.valentinelove/databases/";
    // Database Name
    public static final String DATABASE_NAME = "valentine_weeks.db";
    // Contacts Table Columns names
    public static final String ROSE_ID = "id";
    public static final String ROSE_NAME = "msg";
    public static final String PROPOSE_ID = "Id";
    public static final String PROPOSE_NAME = "msg";
    public static final String CHOCOLATE_ID = "id";
    public static final String CHOCOLATE_NAME = "msg";
    public static final String TEDDY_ID = "id";
    public static final String TEDDY_NAME = "msg";
    public static final String PROMISE_ID = "id";
    public static final String PROMISE_NAME = "msg";
    public static final String HUG_ID = "id";
    public static final String HUG_NAME = "msg";
    public static final String KISS_ID = "id";
    public static final String KISS_NAME = "msg";
    public static final String VALENTINE_ID = "id";
    public static final String VALENTINE_NAME = "msg";
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Contacts table name
    private static final String TABLE_CONTACT = "rose";
    Context ctx;
    private SQLiteDatabase db;

    public DBAssettsFolder(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }

    public Cursor getRoseMessages(String table) {
        db = DatabaseHelper.getInstance(ctx)
                .getReadableDatabase();
        Cursor c = null;
        String sql = "SELECT * FROM " + table;
        c = db.rawQuery(sql, null);
        return c;
    }


    public Cursor getProposeMessages() {
        db = DatabaseHelper.getInstance(ctx)
                .getReadableDatabase();
        Cursor c = null;
        String sql = "SELECT  * FROM propose";
        c = db.rawQuery(sql, null);
        return c;
    }

    public Cursor getChocolateMessages() {
        db = DatabaseHelper.getInstance(ctx)
                .getReadableDatabase();
        Cursor c = null;
        String sql = "SELECT  * FROM chocolate";
        c = db.rawQuery(sql, null);
        return c;
    }

    public Cursor getTeddyMessages() {
        db = DatabaseHelper.getInstance(ctx)
                .getReadableDatabase();
        Cursor c = null;
        String sql = "SELECT  * FROM teddy";
        c = db.rawQuery(sql, null);
        return c;
    }


    public Cursor getPromiseMessages() {
        db = DatabaseHelper.getInstance(ctx)
                .getReadableDatabase();
        Cursor c = null;
        String sql = "SELECT  * FROM promis";
        c = db.rawQuery(sql, null);
        return c;
    }


    public Cursor getHugMessages() {
        db = DatabaseHelper.getInstance(ctx)
                .getReadableDatabase();
        Cursor c = null;
        String sql = "SELECT  * FROM hug";
        c = db.rawQuery(sql, null);
        return c;
    }


    public Cursor getKissMessages() {
        db = DatabaseHelper.getInstance(ctx)
                .getReadableDatabase();
        Cursor c = null;
        String sql = "SELECT  * FROM Kiss";
        c = db.rawQuery(sql, null);
        return c;
    }


    public Cursor getValentineMessages() {
        db = DatabaseHelper.getInstance(ctx)
                .getReadableDatabase();
        Cursor c = null;
        String sql = "SELECT  * FROM valentine";
        c = db.rawQuery(sql, null);
        return c;
    }


    public void openDataBase() throws SQLException {
        String path = DATABASE_PATH + DATABASE_NAME;
        if (db != null && db.isOpen()) {
            return;
        }
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDataBase() {
        if (db != null) {
            db.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
}
