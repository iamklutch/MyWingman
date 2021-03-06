package jameshigashiyama.com.mywingman.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by James   on 5/29/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    private static String DB_NAME = "airmanDatabase.db";
    private static final int VERSION = 1;

    public static final String CURRENT_USER_TABLE = "current_user";
    public static final String COLUMN_USERNAME = "username";
    public static final String PARENT_ID = "parent_id";
    public static final String AIRMAN_TABLE = "airmen";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_RANK = "rank";
    public static final String COLUMN_RANK_VALUE = "rank_value";
    public static final String COLUMN_LAST_FOUR = "last_four";
    // dates are in epoch time
    public static final String COLUMN_DOR = "date_of_rank";
    public static final String COLUMN_DES = "date_entered_service";

    // when upgrading, add the new columns and increment the VERSION above
    private static final String CREATE_AIRMAN_TABLE =
            "CREATE TABLE " + AIRMAN_TABLE + " ( " + BaseColumns._ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + PARENT_ID + " INTEGER, " +
                    COLUMN_LAST_NAME + " TEXT, " + COLUMN_FIRST_NAME + " TEXT, " +
                    COLUMN_AGE + " TEXT, " + COLUMN_RANK + " TEXT, " +
                    COLUMN_RANK_VALUE + " INTEGER, " + COLUMN_LAST_FOUR + " TEXT, " +
                    COLUMN_DOR + " TEXT, " + COLUMN_DES + " TEXT)";
    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + CURRENT_USER_TABLE + " ( " + BaseColumns._ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERNAME + " TEXT)";

    public DatabaseHelper(Context context) {super(context, DB_NAME, null, VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_AIRMAN_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
