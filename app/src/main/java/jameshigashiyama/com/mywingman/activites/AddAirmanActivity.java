package jameshigashiyama.com.mywingman.activites;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.Calendar;

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
    @InjectView(R.id.lastFourEditText)EditText mLastFour;
    @InjectView(R.id.DORdateButton)Button mDORButton;
    @InjectView(R.id.DESdateButton)Button mDESButton;
    @InjectView(R.id.DORtextView)TextView mDORText;
    @InjectView(R.id.DEStextView)TextView mDESText;
    @InjectView(R.id.saveButton)Button mSaveButton;
    @InjectView(R.id.cancelButton)Button mCancelButton;
    private String mAge;
    private String mRank;
    private Airman mAirman;
    private Spinner ageSpin, rankSpin;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_airman);
        ButterKnife.inject(this);

        addItemsOnAgeSpinner();
        addListenerOnSpinnerItemSelection();
        addListenerOnButton();

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addItemsOnAgeSpinner() {

        ageSpin = (Spinner)findViewById(R.id.ageSpinner);
        Number[] ageArray = new Number[42];
            for (int i = 0, a = 18; i < ageArray.length; i++, a++) {
                    ageArray[i] = a;
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
//        ageSpin = (Spinner)findViewById(R.id.ageSpinner);
//        rankSpin = (Spinner)findViewById(R.id.rankSpinner);
        mDORButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);

            }
        });

        mDESButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(001);
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAirman();

                Intent intent = new Intent(AddAirmanActivity.this, AddAirmanActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(AddAirmanActivity.this, ViewAirmenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }

    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog (int id) {
        if (id == 999) {
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DATE);
            DatePickerDialog DORDateDialog = new DatePickerDialog
                    (this, myDORListener, mYear, mMonth, mDay);
            DORDateDialog.getDatePicker().setSpinnersShown(true);
            return DORDateDialog;
        }else {
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DATE);
            DatePickerDialog DESDateDialog = new DatePickerDialog
                    (this, myDESListener, mYear, mMonth, mDay);
            DESDateDialog.getDatePicker().setMinDate(1788922061);
            return DESDateDialog;
        }
    }

    private DatePickerDialog.OnDateSetListener myDORListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mDORText.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
        }
    };

    private DatePickerDialog.OnDateSetListener myDESListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mDESText.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
        }
    };


    private String encryptThis(String pass, String data) {
        String encryptedData = "";
        try {
            Crypto crypto = new Crypto(pass);
             encryptedData = crypto.encrypt(data);
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return encryptedData;
    }

    protected boolean saveAirman(){
        ParseUser currentUser = ParseUser.getCurrentUser();
        String cipher = currentUser.getObjectId();

        String lastName = mLastName.getText().toString();
        String firstName = mFirstName.getText().toString();
        mAge = ageSpin.getSelectedItem().toString();
        mRank = rankSpin.getSelectedItem().toString();
        RankHelper rank = new RankHelper();
        int rankValue = rank.RankValueHelper(mRank);
        String lastFour = mLastFour.getText().toString();
        String DOR = mDORText.getText().toString();
        String DES = mDESText.getText().toString();


        String encryptedLastName = encryptThis(cipher, lastName);
        String encryptedFirstName = encryptThis(cipher, firstName);
        String encryptedAge = encryptThis(cipher, mAge);
        String encryptedRank = encryptThis(cipher, mRank);
        String encryptedFour = encryptThis(cipher, lastFour);
        String encryptedDOR = encryptThis(cipher, DOR);
        String encryptedDES = encryptThis(cipher, DES);

        mAirman = new Airman(0, "", "", "", "", 0, "", "", "");
        mAirman.setLastName(encryptedLastName);
        mAirman.setFirstName(encryptedFirstName);
        mAirman.setAge(encryptedAge);
        mAirman.setRank(encryptedRank);
        mAirman.setRankValue(rankValue);
        mAirman.setLastFour(encryptedFour);
        mAirman.setDOR(encryptedDOR);
        mAirman.setDES(encryptedDES);

        // Saves everything in the Airman Object to the database
        DatabaseMethods databaseMethods = new DatabaseMethods(AddAirmanActivity.this);
        databaseMethods.create(mAirman);

        return true;
    }

}



