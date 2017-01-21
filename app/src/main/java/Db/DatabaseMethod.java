package Db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseMethod {
    public static final String DATABASE_NAME = "valentine_weeks.db";
    public static final int DATABASE_VERSION = 1;
    public static final String LOHRI_ID = "ID";
    public static final String LOHRI_NAME = "Messages";
    private static final String DB_PATH_SUFFIX = "/data/data/satnam.valentinelove/databases/";
    static Context mCtx;
    public SQLiteDatabase mSqLiteDatabase, mSqLiteDatabaseRead;


    /* public DatabaseMethod open() throws SQLException {

         mSqLiteDatabase = DatabaseHelper.getInstance(mCtx)
                 .getWritableDatabase();
         mSqLiteDatabaseRead = DatabaseHelper.getInstance(mCtx)
                 .getReadableDatabase();
         return this;
     }*/
    public DatabaseMethod(Context ctx) {
        mCtx = ctx;

    }

    private static String getDatabasePath() {
        return DB_PATH_SUFFIX + DATABASE_NAME;
    }

    public SQLiteDatabase openDataBase() throws SQLException {

        mSqLiteDatabase = DatabaseHelper.getInstance(mCtx)
                .getWritableDatabase();
        mSqLiteDatabaseRead = DatabaseHelper.getInstance(mCtx)
                .getReadableDatabase();
        File dbFile = mCtx.getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying sucess from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null,
                SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);

    }

    public void openDatabase() throws SQLException {


    }

    public boolean emptyFunriddles() {
        Cursor c = null;
        boolean empty = false;
        if (mSqLiteDatabase.isOpen()) {
            c = mSqLiteDatabase.rawQuery(
                    "SELECT COUNT(*) FROM MessagesLohri", null);

            if (c != null && c.moveToFirst()) {
                empty = (c.getInt(0) == 0);
            }

            c.close();
        }
        return empty;

    }

    public Cursor getFunriddlesData() {
        Cursor c = null;
        if (mSqLiteDatabase.isOpen()) {
            String sql = "SELECT  * FROM MessagesLohri";
            c = mSqLiteDatabase.rawQuery(sql, null);
        }
        return c;
    }

    public void close() {
        DatabaseHelper.getInstance(mCtx).close();
    }

    public void CopyDataBaseFromAsset() throws IOException {
        InputStream myInput = mCtx.getAssets().open(DATABASE_NAME);
// Path to the just created empty db
        String outFileName = getDatabasePath();
// if the path doesn't exist first, create it
        File f = new File(DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();
// Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
// transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
// Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
}
