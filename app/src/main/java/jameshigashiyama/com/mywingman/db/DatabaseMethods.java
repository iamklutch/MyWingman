package jameshigashiyama.com.mywingman.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.parse.ParseUser;

import java.io.File;
import java.util.ArrayList;

import jameshigashiyama.com.mywingman.Airman;
import jameshigashiyama.com.mywingman.activites.Crypto;

/**
 * Created   by James on 5/29/2015.
 */
public class DatabaseMethods {

    private String mEncryptedData;
    private Context mContext;
    private DatabaseHelper mDatabaseHelper;
    private Number[] mAirmanLocationKey;
    private Number mAirmanId;



    public DatabaseMethods(Context context) {
        mContext = context;
        mDatabaseHelper = new DatabaseHelper(context);
    }


    public Boolean checkDatabase(Context context) {
        File dbFile = context.getDatabasePath("airmanDatabase.db");
        boolean databaseStatus = false;

        if (dbFile.exists()){
            databaseStatus = true;
        } else {
            databaseStatus = false;
        }
        return databaseStatus;
    }

    public ArrayList<Airman> readAirmen(){

        ParseUser currentUser = ParseUser.getCurrentUser();
        String cipher = currentUser.getObjectId();
        int parentId = getParentId();

        SQLiteDatabase database = open();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.AIRMAN_TABLE +
                " WHERE " + DatabaseHelper.PARENT_ID + " IS \"" + parentId + "\"", null);
        ArrayList<Airman> airmen = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    // id(int), lastName, firstName, age, rank, rankValue(int), Last4, DOR, DES
                    int id = cursor.getInt(0);
                    int userParentId = cursor.getInt(1);
                    String lastName = cursor.getString(2);
                    String firstName = cursor.getString(3);
                    String age = cursor.getString(4);
                    String rank = cursor.getString(5);
                    int rankValue = cursor.getInt(6);
                    String lastFour = cursor.getString(7);
                    String DOR = cursor.getString(8);
                    String DES = cursor.getString(9);

                    String decryptedLastName, decryptedFirstName, decryptedAge, decryptedRank,
                            decryptedLastFour, decryptedDOR, decryptedDES;
                    if (lastName == null) {
                        decryptedLastName = " ";
                    } else {
                        decryptedLastName = decryptThis(cipher, lastName);
                    }
                    if (firstName == null) {
                        decryptedFirstName = " ";
                    } else {
                        decryptedFirstName = decryptThis(cipher, firstName);
                    }
                    if (age == null) {
                        decryptedAge = " ";
                    } else {
                        decryptedAge = decryptThis(cipher, age);
                    }
                    if (rank == null) {
                        decryptedRank = " ";
                    } else {
                        decryptedRank = decryptThis(cipher, rank);
                    }
                    if (lastFour == null) {
                        decryptedLastFour = " ";
                    } else {
                        decryptedLastFour = decryptThis(cipher, lastFour);
                    }
                    if (DOR == null) {
                        decryptedDOR = " ";
                    } else {
                        decryptedDOR = decryptThis(cipher, DOR);
                    }
                    if (DES == null) {
                        decryptedDES = " ";
                    } else {
                        decryptedDES = decryptThis(cipher, DES);
                    }

                    Airman airman = new Airman(id, userParentId, lastName, firstName, decryptedAge,
                            decryptedRank, rankValue, decryptedLastFour, decryptedDOR, decryptedDES);

                    airman.setId(id);
                    airman.setParentId(userParentId);
                    airman.setLastName(decryptedLastName);
                    airman.setFirstName(decryptedFirstName);
                    airman.setAge(decryptedAge);
                    airman.setRank(decryptedRank);
                    airman.setRankValue(rankValue);
                    airman.setLastFour(decryptedLastFour);
                    airman.setDOR(decryptedDOR);
                    airman.setDES(decryptedDES);

                    airmen.add(airman);

                } while (cursor.moveToNext());

                cursor.close();
                close(database);
            }else {
                createDummyAirman();
//                readAirmen();
            }
        }else {
            createDummyAirman();
 //           readAirmen();
        }
        return airmen;

    }

    public Airman ReadSingleAirman(int viewId) {

        ParseUser currentUser = ParseUser.getCurrentUser();
        String cipher = currentUser.getObjectId();
        int parentId = getParentId();
        setKeys();
        mAirmanId = mAirmanLocationKey[viewId];
        SQLiteDatabase database = open();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.AIRMAN_TABLE +
                " WHERE _ID IS \"" + mAirmanId + "\"", null);
    //    ArrayList<Airman> singleAirman = new ArrayList<>();


        cursor.moveToFirst();

        int id = cursor.getInt(0);
        parentId = cursor.getInt(1);
        String lastName = cursor.getString(2);
        String firstName = cursor.getString(3);
        String age = cursor.getString(4);
        String rank = cursor.getString(5);
        int rankValue = cursor.getInt(6);
        String lastFour = cursor.getString(7);
        String DOR = cursor.getString(8);
        String DES = cursor.getString(9);

        String decryptedLastName, decryptedFirstName, decryptedAge, decryptedRank,
                decryptedLastFour, decryptedDOR, decryptedDES;
        if(lastName == null) { decryptedLastName = " ";}else{decryptedLastName = decryptThis(cipher, lastName);}
        if(firstName == null) {decryptedFirstName = " ";}else{decryptedFirstName = decryptThis(cipher, firstName);}
        if(age == null) {decryptedAge = " ";}else{decryptedAge= decryptThis(cipher, age);}
        if(rank == null) {decryptedRank = " ";}else{decryptedRank = decryptThis(cipher, rank);}
        if(lastFour == null) {decryptedLastFour = " ";}else{decryptedLastFour = decryptThis(cipher, lastFour);}
        if(DOR == null) {decryptedDOR = " ";}else{decryptedDOR = decryptThis(cipher, DOR);}
        if(DES == null) {decryptedDES = " ";}else{decryptedDES = decryptThis(cipher, DES);}

        Airman SingleAirman = new Airman(id, parentId, lastName, firstName, decryptedAge,
                decryptedRank, rankValue, decryptedLastFour, decryptedDOR, decryptedDES);

        SingleAirman.setId(id);
        SingleAirman.setParentId(parentId);
        SingleAirman.setLastName(decryptedLastName);
        SingleAirman.setFirstName(decryptedFirstName);
        SingleAirman.setAge(decryptedAge);
        SingleAirman.setRank(decryptedRank);
        SingleAirman.setRankValue(rankValue);
        SingleAirman.setLastFour(decryptedLastFour);
        SingleAirman.setDOR(decryptedDOR);
        SingleAirman.setDES(decryptedDES);

        cursor.close();
        close(database);

        return SingleAirman;
    }

    private SQLiteDatabase open(){
        return mDatabaseHelper.getWritableDatabase();
    }

    private void close (SQLiteDatabase database) {
        database.close();
    }

    public void update (Airman airman, int viewId) {

//        mAirmanId = mAirmanLocationKey[viewId];
        int parentId = getParentId();
        SQLiteDatabase database = open();
        database.beginTransaction();

        ContentValues airmanValues = new ContentValues();
        airmanValues.put(DatabaseHelper.PARENT_ID, parentId);
        airmanValues.put(DatabaseHelper.COLUMN_LAST_NAME, airman.getLastName());
        airmanValues.put(DatabaseHelper.COLUMN_FIRST_NAME, airman.getFirstName());
        airmanValues.put(DatabaseHelper.COLUMN_AGE, airman.getAge());
        airmanValues.put(DatabaseHelper.COLUMN_RANK, airman.getRank());
        airmanValues.put(DatabaseHelper.COLUMN_RANK_VALUE, airman.getRankValue());
        airmanValues.put(DatabaseHelper.COLUMN_LAST_FOUR, airman.getLastFour());
        airmanValues.put(DatabaseHelper.COLUMN_DOR, airman.getDOR());
        airmanValues.put(DatabaseHelper.COLUMN_DES, airman.getDES());

        database.update(DatabaseHelper.AIRMAN_TABLE, airmanValues, "_ID = " + viewId, null);
        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);

    }

    public void create(Airman airman, int parentId) {

        SQLiteDatabase database = open();
        database.beginTransaction();

        ContentValues airmanValues = new ContentValues();
        airmanValues.put(DatabaseHelper.PARENT_ID, parentId);
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

    public boolean createUserEntry (String username) {
        boolean created = false;
        try {
            SQLiteDatabase database = open();
            database.beginTransaction();

            ContentValues userValue = new ContentValues();
            userValue.put(DatabaseHelper.COLUMN_USERNAME, username);

            database.insert(DatabaseHelper.CURRENT_USER_TABLE, null, userValue);
            database.setTransactionSuccessful();
            database.endTransaction();
            close(database);

            created = true;
        }
        finally {
            return created;
        }
    }

    public boolean createDummyAirman() {

        ParseUser currentUser = ParseUser.getCurrentUser();
        String cipher = currentUser.getObjectId();
        int parentId = getParentId();

        Airman airman = new Airman(0, 0, "", "", "", "", 0, "", "", "");

        String encryptedLastName = encryptThis(cipher, "Snuffy");
        String encryptedFirstName = encryptThis(cipher, "Airman");
        String encryptedAge = encryptThis(cipher, "18");
        String encryptedRank = encryptThis(cipher, "Amn");

        airman.setLastName(encryptedLastName);
        airman.setFirstName(encryptedFirstName);
        airman.setAge(encryptedAge);
        airman.setRank(encryptedRank);
        airman.setRankValue(2);

        // Saves everything in the Airman Object to the database
        DatabaseMethods databaseMethods = new DatabaseMethods(mContext);
        databaseMethods.create(airman, parentId);

        return true;
    }

    public void delete(int airmanId){
        SQLiteDatabase database = open();
        database.beginTransaction();
        database.delete(DatabaseHelper.AIRMAN_TABLE, " _ID = " + airmanId, null);
        database.setTransactionSuccessful();
        database.endTransaction();
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

    private String encryptThis(String pass, String data) {
        try {
            Crypto crypto = new Crypto(pass);
            mEncryptedData = crypto.encrypt(data);
        } catch (Exception e) {
            Toast.makeText(mContext, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return mEncryptedData;
    }

    private Number[] setKeys () {
//        DatabaseMethods dataSource = new DatabaseMethods(DatabaseMethods.this);
        ArrayList<Airman> airmen = readAirmen();

        mAirmanLocationKey = new Number[airmen.size()];
        for (int i = 0;i < airmen.size(); i++){
            mAirmanLocationKey[i] = airmen.get(i).getId();
        }
        return mAirmanLocationKey;
    }

    public int getParentId() {
        ParseUser user = ParseUser.getCurrentUser();
        String username = user.getUsername();

        SQLiteDatabase database = open();
        database.beginTransaction();
        Cursor cursor = database.rawQuery("SELECT _ID FROM " + DatabaseHelper.CURRENT_USER_TABLE +
                " WHERE " + DatabaseHelper.COLUMN_USERNAME + " IS \"" + username + "\"", null);

        cursor.moveToFirst();

        int userKey = cursor.getInt(0);

        cursor.close();
        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);

        return userKey;
    }
}
