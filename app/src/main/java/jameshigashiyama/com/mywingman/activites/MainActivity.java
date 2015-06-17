package jameshigashiyama.com.mywingman.activites;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;

import jameshigashiyama.com.mywingman.R;
import jameshigashiyama.com.mywingman.db.DatabaseMethods;
import jameshigashiyama.com.mywingman.db.ParseMethods;

public class MainActivity extends ActionBarActivity { // implements ActionBar.TabListener{

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int BACKUP = 0;
    public static final int RESTORE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get a current user (if one) and sends to login if null
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        ParseUser currentUser = ParseUser.getCurrentUser();


        if (currentUser == null) {
            navigateToLogin();
        } else if (currentUser.isNew()) {
            DatabaseMethods db = new DatabaseMethods(this);
            boolean created = db.createUserEntry(currentUser.getUsername());
            if (created == true) {
                db.createDummyAirman();
            } else {
                Toast.makeText(this, "Problem creating database", Toast.LENGTH_SHORT).show();
            }
//            navigateToAddAirman();
        } else {


        }

        AdView mAdView = (AdView) findViewById(R.id.adViewMain);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void navigateToAddAirman() {
        Intent intent = new Intent(this, AddAirmanActivity.class);
        startActivity(intent);
    }

    private void navigateToViewAirmen() {
        Intent intent = new Intent(this, ViewAirmenActivity.class);
        startActivity(intent);
    }

    private void navigateToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_logout:
                ParseUser.logOut();
                navigateToLogin();
                break;
            case R.id.action_settings:
                navigateToSettings();
                break;
            case R.id.action_backup_restore:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(R.array.backup_restore, mDialogListener);
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.action_add_airman:
                navigateToAddAirman();
                break;
            case R.id.action_view_airmen:
                navigateToViewAirmen();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected DialogInterface.OnClickListener mDialogListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    ParseMethods pm = new ParseMethods();
                    ParseObject message = pm.createMessage(MainActivity.this);
                    if (message == null) {
                            Toast.makeText(MainActivity.this, "Problem sending message", Toast.LENGTH_SHORT).show();
                        } else {
                            pm.send(message);
                        }
                    break;
                case 1:
                    Toast.makeText(MainActivity.this,
                            "Restore Database", Toast.LENGTH_SHORT).show();
                            ParseMethods pmm = new ParseMethods();
                            pmm.retrieveDatabase();
                    break;
            }
        }
    };
}
