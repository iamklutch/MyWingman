package jameshigashiyama.com.mywingman.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

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
        String cipher = mContext.getString(R.string.decryption_password);

        SQLiteDatabase database = open();

        Cursor cursor = database.rawQuery("SELECT * FROM airmen", null);

        ArrayList<Airman> airmen = new ArrayList<>();

        cursor.moveToFirst();

        do {

            int id = cursor.getInt(0);
            int age = cursor.getInt(2);
            String name = cursor.getString(1);
            String rank = cursor.getString(3);
            String decryptedName = decryptThis(cipher, name);
            String decryptedRank = decryptThis(cipher, rank);
            Airman airman = new Airman(id, decryptedName, age, decryptedRank);
            airman.setId(id);
            airman.setName(decryptedName);
            airman.setAge(age);
            airman.setRank(decryptedRank);
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
        airmanValues.put(DatabaseHelper.COLUMN_NAME, airman.getName());
        airmanValues.put(DatabaseHelper.COLUMN_AGE, airman.getAge());
        airmanValues.put(DatabaseHelper.COLUMN_RANK, airman.getRank());

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
