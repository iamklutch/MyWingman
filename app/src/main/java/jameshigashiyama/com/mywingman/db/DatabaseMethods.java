package jameshigashiyama.com.mywingman.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.ArrayList;

import jameshigashiyama.com.mywingman.R;
import jameshigashiyama.com.mywingman.Airman;
import jameshigashiyama.com.mywingman.activites.Crypto;

/**
 * Created   by James on 5/29/2015.
 */
public class DatabaseMethods {

    private String mEncryptedData;
    private Context mContext;
    private DatabaseHelper mDatabaseHelper;

    public DatabaseMethods(Context context) {
        mContext = context;
        mDatabaseHelper = new DatabaseHelper(context);
    }

    public ArrayList<Airman> read() {
        ArrayList<Airman> airmen = readAirman();
        return airmen;
    }

    public ArrayList<Airman> readAirman(){

        ParseUser currentUser = ParseUser.getCurrentUser();
        String cipher = currentUser.getObjectId();

        SQLiteDatabase database = open();

        Cursor cursor = database.rawQuery("SELECT * FROM airmen", null);

        ArrayList<Airman> airmen = new ArrayList<>();

        cursor.moveToFirst();

        do {

            // id(int), lastName, firstName, age, rank, rankValue(int), Last4, DOR, DES
            int id = cursor.getInt(0);
            String lastName = cursor.getString(1);
            String firstName = cursor.getString(2);
            String age = cursor.getString(3);
            String rank = cursor.getString(4);
            int rankValue = cursor.getInt(5);
            String lastFour = cursor.getString(6);
            String DOR = cursor.getString(7);
            String DES = cursor.getString(8);

            String decryptedLastName = decryptThis(cipher, lastName);
            String decryptedFirstName = decryptThis(cipher, firstName);
            String decryptedAge = decryptThis(cipher, age);
            String decryptedRank = decryptThis(cipher, rank);
            String decryptedLastFour = decryptThis(cipher, lastFour);
            String decryptedDOR = decryptThis(cipher, DOR);
            String decryptedDES = decryptThis(cipher, DES);

            Airman airman = new Airman(id, decryptedLastName, decryptedFirstName, decryptedAge,
                    decryptedRank, rankValue, decryptedLastFour, decryptedDOR, decryptedDES);

            airman.setId(id);
            airman.setLastName(decryptedLastName);
            airman.setFirstName(decryptedFirstName);
            airman.setAge(decryptedAge);
            airman.setRank(decryptedRank);
            airman.setRankValue(rankValue);
            airman.setLastFour(decryptedLastFour);
            airman.setDOR(decryptedDOR);
            airman.setDES(decryptedDES);

            airmen.add(airman);

        }while(cursor.moveToNext());

        cursor.close();
        close(database);
        return airmen;
    }

    private SQLiteDatabase open(){
        return mDatabaseHelper.getWritableDatabase();
    }

    private void close (SQLiteDatabase database) {
        database.close();
    }

    public void create(Airman airman) {
        SQLiteDatabase database = open();
        database.beginTransaction();

        ContentValues airmanValues = new ContentValues();
        airmanValues.put(DatabaseHelper.COLUMN_LAST_NAME, airman.getLastName());
        airmanValues.put(DatabaseHelper.COLUMN_FIRST_NAME, airman.getFirstName());
        airmanValues.put(DatabaseHelper.COLUMN_AGE, airman.getAge());
        airmanValues.put(DatabaseHelper.COLUMN_RANK, airman.getRank());
        airmanValues.put(DatabaseHelper.COLUMN_RANK_VALUE, airman.getRankValue());
        airmanValues.put(DatabaseHelper.COLUMN_LAST_FOUR, airman.getLastFour());
        airmanValues.put(DatabaseHelper.COLUMN_DOR, airman.getDOR());
        airmanValues.put(DatabaseHelper.COLUMN_DES, airman.getDES());

        database.insert(DatabaseHelper.AIRMAN_TABLE, null, airmanValues);
        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    private String decryptThis(String pass, String encryptedData) {
        try {
            Crypto crypto = new Crypto(pass);
            mEncryptedData = crypto.decrypt(encryptedData);
        } catch (Exception e) {
            Toast.makeText(mContext, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return mEncryptedData;
    }
}
