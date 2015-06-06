package jameshigashiyama.com.mywingman.activites;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseUser;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jameshigashiyama.com.mywingman.Airman;
import jameshigashiyama.com.mywingman.R;
import jameshigashiyama.com.mywingman.adapters.SpinnerHelperAdapter;
import jameshigashiyama.com.mywingman.db.DatabaseMethods;
import jameshigashiyama.com.mywingman.support.RankHelper;

public class AddAirmanActivity extends ActionBarActivity {

    @InjectView(R.id.lastNameEditText)EditText mLastName;
    @InjectView(R.id.firstNameEditText)EditText mFirstName;
    private String mEncryptedData;
    private String mAge;
    private String mRank;
    private Airman mAirman;
    private Spinner ageSpin, rankSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_airman);
        ButterKnife.inject(this);

        addItemsOnAgeSpinner();
        addListenerOnSpinnerItemSelection();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_airman, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        int age;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            mAge = ageSpin.getSelectedItem().toString();
            mRank = rankSpin.getSelectedItem().toString();
            age = Integer.parseInt(mAge);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addItemsOnAgeSpinner() {

        ageSpin = (Spinner)findViewById(R.id.ageSpinner);
        Number[] ageArray = new Number[60];
            for (int i = 0; i < ageArray.length; i++) {
                ageArray[i] = i;
            }


        ArrayAdapter<Number>adapter = new ArrayAdapter<>
                (AddAirmanActivity.this, android.R.layout.simple_spinner_item, ageArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpin.setAdapter(adapter);
    }

    public void addListenerOnSpinnerItemSelection() {

        ageSpin = (Spinner)findViewById(R.id.ageSpinner);
        rankSpin = (Spinner)findViewById(R.id.rankSpinner);
        ageSpin.setOnItemSelectedListener(new SpinnerHelperAdapter());
        rankSpin.setOnItemSelectedListener(new SpinnerHelperAdapter());

    }


    public void addListenerOnButton() {
        ageSpin = (Spinner)findViewById(R.id.ageSpinner);
        rankSpin = (Spinner)findViewById(R.id.rankSpinner);

    }

    private String encryptThis(String pass, String data) {
        try {
            Crypto crypto = new Crypto(pass);
            mEncryptedData = crypto.encrypt(data);
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return mEncryptedData;
    }

    protected boolean saveAirman(){
        ParseUser currentUser = ParseUser.getCurrentUser();
        String cipher = currentUser.getObjectId();

        String lastName = mLastName.getText().toString();
        String firstName = mLastName.getText().toString();
        mAge = ageSpin.getSelectedItem().toString();
        mRank = rankSpin.getSelectedItem().toString();
        RankHelper rank = new RankHelper();
        int rankValue = rank.RankValueHelper(mRank);


        String encryptedLastName = encryptThis(cipher, lastName);
        String encryptedFirstName = encryptThis(cipher, firstName);
        String encryptedAge = encryptThis(cipher, mAge);
        String encryptedRank = encryptThis(cipher, mRank);

        mAirman = new Airman(0, "", "", "", "", 0, "", "", "");
        mAirman.setLastName(encryptedLastName);
        mAirman.setFirstName(encryptedFirstName);
        mAirman.setAge(encryptedAge);
        mAirman.setRank(encryptedRank);
        mAirman.setRankValue(rankValue);


        // Saves everything in the Airman Object to the database
        DatabaseMethods databaseMethods = new DatabaseMethods(AddAirmanActivity.this);
        databaseMethods.create(mAirman);

        return true;
    }

}



