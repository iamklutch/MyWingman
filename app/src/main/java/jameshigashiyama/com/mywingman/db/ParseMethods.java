package jameshigashiyama.com.mywingman.db;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.apache.commons.io.IOUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jameshigashiyama.com.mywingman.activites.MainActivity;
import jameshigashiyama.com.mywingman.support.ParseConstants;

/**
 * Created by James on 6/16/2015.
 */
public class ParseMethods {


    private final static String TAG = ParseMethods.class.getSimpleName();


    public ParseObject createMessage (Context context) {
        Uri mFileUri = getOutputMediaFileUri();
        ParseObject message = new ParseObject(ParseConstants.USER_DATABASE);
        message.put(ParseConstants.USER_NAME, ParseUser.getCurrentUser().getUsername());
        message.put(ParseConstants.UPLOADED_BY, ParseUser.getCurrentUser().getObjectId());

        byte[] fileBytes = getByteArrayFromFile(context, mFileUri);

        if (fileBytes == null) {
            return null;
        }
        else {
            ParseFile file = new ParseFile("airmanDatabase.db", fileBytes);
            message.put(ParseConstants.KEY_FILE, file);
            return message;
        }
    }

    public Uri getOutputMediaFileUri() {
        return Uri.fromFile(new File("data/data/jameshigashiyama.com.mywingman/databases/airmanDatabase.db"));
    }

    private String timeNow () {

        Date now = new Date();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);

        return timestamp;
    }

    public static byte[] getByteArrayFromFile(Context context, Uri uri) {
        byte[] fileBytes = null;
        InputStream inStream = null;
        ByteArrayOutputStream outStream = null;

//        if (uri.getScheme().equals("content")) {
            try {
                inStream = context.getContentResolver().openInputStream(uri);
                outStream = new ByteArrayOutputStream();

                byte[] bytesFromFile = new byte[1024*1024]; // buffer size (1 MB)
                int bytesRead = inStream.read(bytesFromFile);
                while (bytesRead != -1) {
                    outStream.write(bytesFromFile, 0, bytesRead);
                    bytesRead = inStream.read(bytesFromFile);
                }

                fileBytes = outStream.toByteArray();
            }
            catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
            finally {
                try {
                    inStream.close();
                    outStream.close();
                }
                catch (IOException e) { /*( Intentionally blank */ }
            }
//        }
//        else {
//            try {
//                File file = new File(uri.getPath());
//                FileInputStream fileInput = new FileInputStream(file);
//                fileBytes = IOUtils.toByteArray(fileInput);
//            }
//            catch (IOException e) {
//                Log.e(TAG, e.getMessage());
//            }
//        }

        return fileBytes;
    }

    public void retrieveDatabase () {

        ParseQuery<ParseObject> query = new ParseQuery<>(ParseConstants.USER_DATABASE);
        query.whereEqualTo(ParseConstants.UPLOADED_BY, ParseUser.getCurrentUser().getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                boolean noFile = list.isEmpty();
                try {
                    ParseObject parseObject = list.get(0);
                    boolean dataAvail = parseObject.isDataAvailable();
                    ParseFile parseFile = parseObject.getParseFile("file");
                    try {
                        byte[] bytes = parseFile.getData();
                        getFileFromByteArray(bytes, "data/data/jameshigashiyama.com.mywingman/databases/airmanDatabase.db");
                    } catch (ParseException pe) {
                        // didn't get filr
                    }
//                    byteFile.getDataInBackground(new GetDataCallback() {
//                        @Override
//                        public void done(byte[] bytes, ParseException e) {
//                            if (e == null) {
//                                getFileFromByteArray(bytes, "data/data/jameshigashiyama.com.mywingman/databases/airmanDatabase.db");
//                            } else {
//                                //                     Log.d(, "Error: ");
//                            }
//                        }
//                    });
                } finally {
                    //do some shit
                }
            }
        });
    }


    private void getFileFromByteArray (byte[] byteArrayFile, String mediaUri) {

        try {
            FileOutputStream outputStream = new FileOutputStream(mediaUri);
            try {
                outputStream.write(byteArrayFile);
                outputStream.close();
            }catch (IOException e) {

            }

        } catch (FileNotFoundException e ) {

        }
    }

    public void send(ParseObject message) {
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
//                    //success
//                    Toast.makeText(RecipientsActivity.this,getString(R.string.success_message),
//                            Toast.LENGTH_LONG).show();
//                    sendPushNotifications();
                }
                else {
//                    Log.e(TAG, e.getMessage());
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(RecipientsActivity.this);
//                    builder.setMessage(getString(R.string.error_sending_message))
//                            .setTitle(getString(R.string.sorry))
//                            .setPositiveButton(android.R.string.ok, null);
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
                }
            }
        });
    }

}
