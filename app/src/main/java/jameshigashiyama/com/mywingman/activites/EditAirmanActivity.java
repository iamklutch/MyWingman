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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.parse.ParseUser;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jameshigashiyama.com.mywingman.Airman;
import jameshigashiyama.com.mywingman.R;
import jameshigashiyama.com.mywingman.adapters.SpinnerHelperAdapter;
import jameshigashiyama.com.mywingman.db.DatabaseMethods;
import jameshigashiyama.com.mywingman.support.DateHelper;
import jameshigashiyama.com.mywingman.support.RankHelper;

public class EditAirmanActivity extends ActionBarActivity{


    @InjectView(R.id.editLastNameAEA)    TextView mEditLastNameText;
    @InjectView(R.id.editFirstNameAEA)    TextView mEditFirstNameText;
    @InjectView(R.id.editLastFourAEA)   TextView mEditLastFour;
    @InjectView(R.id.DORtextViewAEA)    TextView mDORText;
    @InjectView(R.id.DEStextViewAEA)    TextView mDESText;
    @InjectView(R.id.DORdateButtonAEA)    Button mDORButton;
    @InjectView(R.id.DESdateButtonAEA)    Button mDESButton;
    private String mAge;
    private String mRank;
    private Airman mAirman;
    private int mAgeSetSpinner, mRankSetSpinner;
    private Spinner ageSpin, rankSpin;
    private int mYear, mMonth, mDay;
    private int mDatabaseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_airman);
        ButterKnife.inject(this);
        setTitle("View/Edit");

        Intent intent = getIntent();
        mDatabaseId = intent.getIntExtra("ViewID",0);
        showCurrentAirman(mDatabaseId);

        addItemsOnAgeSpinner();
        addListenerOnSpinnerItemSelection();
        addListenerOnButton();

        AdView mAdView = (AdView) findViewById(R.id.adViewEditAirman);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_airman, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_edit_cancel:
                finish();
                break;
            case R.id.action_save_airman:
                updateAirman();
                Intent intent = new Intent(this, ViewAirmenActivity.class);
                startActivity(intent);
                break;
            case R.id.action_edit_delete:
                DatabaseMethods dbm = new DatabaseMethods(EditAirmanActivity.this);
                dbm.delete(mDatabaseId);
                intent = new Intent(this, ViewAirmenActivity.class);
                startActivity(intent);

                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void addItemsOnAgeSpinner() {

        ageSpin = (Spinner) findViewById(R.id.ageSpinnerAEA);
        Number[] ageArray = new Number[42];
        for (int i = 0, a = 18; i < ageArray.length; i++, a++) {
            ageArray[i] = a;
        }


        ArrayAdapter<Number> adapter = new ArrayAdapter<>
                (EditAirmanActivity.this, android.R.layout.simple_spinner_item, ageArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpin.setAdapter(adapter);

        ageSpin.setSelection(mAgeSetSpinner - 18);
    }

    public void addListenerOnSpinnerItemSelection() {

        ageSpin = (Spinner) findViewById(R.id.ageSpinnerAEA);
        rankSpin = (Spinner) findViewById(R.id.rankSpinnerAEA);


        rankSpin.setSelection(mRankSetSpinner);

        ageSpin.setOnItemSelectedListener(new SpinnerHelperAdapter());
        rankSpin.setOnItemSelectedListener(new SpinnerHelperAdapter());


    }


    public void addListenerOnButton() {

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

    }

    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        //gets previously input date
        if (id == 999) {
            int year, month, day;
            String date = mDORText.getText().toString();
            DateHelper dh = new DateHelper();
            year = dh.DateYearStripper(date);
            month = dh.DateMonthStripper(date);
            day = dh.DateDayStripper(date);
            DatePickerDialog DORDateDialog = new DatePickerDialog
                    (this, myDORListener, year, month, day);
            DORDateDialog.getDatePicker().setSpinnersShown(true);
            return DORDateDialog;
        } else {
            //gets previously input date
            int year, month, day;
            String date = mDESText.getText().toString();
            DateHelper dh = new DateHelper();
            year = dh.DateYearStripper(date);
            month = dh.DateMonthStripper(date);
            day = dh.DateDayStripper(date);
            DatePickerDialog DESDateDialog = new DatePickerDialog
                    (this, myDESListener, year, month, day);
            DESDateDialog.getDatePicker().setSpinnersShown(true);
            return DESDateDialog;
        }
    }

    private DatePickerDialog.OnDateSetListener myDORListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            DateHelper dh = new DateHelper();
            String formattedDate = dh.DateChangerThreeCharMonth(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            mDORText.setText(formattedDate);
        }
    };

    private DatePickerDialog.OnDateSetListener myDESListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            DateHelper dh = new DateHelper();
            String formattedDate = dh.DateChangerThreeCharMonth(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            String addedMonths = dh.AddMonths(formattedDate);
            mDESText.setText(addedMonths);
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

    protected boolean updateAirman() {
        if (mEditLastFour.length() > 4){
            Toast.makeText(this, getString(R.string.ssn_warning), Toast.LENGTH_LONG).show();
        }

        ParseUser currentUser = ParseUser.getCurrentUser();
        String cipher = currentUser.getObjectId();

        String lastName = mEditLastNameText.getText().toString();
        String firstName = mEditFirstNameText.getText().toString();
        mAge = ageSpin.getSelectedItem().toString();
        mRank = rankSpin.getSelectedItem().toString();
        RankHelper rank = new RankHelper();
        int rankValue = rank.RankValueHelper(mRank);
        String lastFour = mEditLastFour.getText().toString();
        String DOR = mDORText.getText().toString();
        String DES = mDESText.getText().toString();


        String encryptedLastName = encryptThis(cipher, lastName);
        String encryptedFirstName = encryptThis(cipher, firstName);
        String encryptedAge = encryptThis(cipher, mAge);
        String encryptedRank = encryptThis(cipher, mRank);
        String encryptedFour = encryptThis(cipher, lastFour);
        String encryptedDOR = encryptThis(cipher, DOR);
        String encryptedDES = encryptThis(cipher, DES);

        mAirman = new Airman(0, 0,"", "", "", "", 0, "", "", "");
        mAirman.setLastName(encryptedLastName);
        mAirman.setFirstName(encryptedFirstName);
        mAirman.setAge(encryptedAge);
        mAirman.setRank(encryptedRank);
        mAirman.setRankValue(rankValue);
        mAirman.setLastFour(encryptedFour);
        mAirman.setDOR(encryptedDOR);
        mAirman.setDES(encryptedDES);

        // Saves everything in the Airman Object to the database
        DatabaseMethods databaseMethods = new DatabaseMethods(EditAirmanActivity.this);
        databaseMethods.update(mAirman, mDatabaseId);

        return true;
    }

    protected void showCurrentAirman(int id) {
        DatabaseMethods db = new DatabaseMethods(this);
        Airman airman = db.ReadSingleAirman(id);


        mDatabaseId = airman.getId();
        int parentId = airman.getParentId();
        mAgeSetSpinner = Integer.parseInt(airman.getAge());
        mRankSetSpinner = airman.getRankValue();
        mEditLastNameText.setText(airman.getLastName());
        mEditFirstNameText.setText(airman.getFirstName());
        mDORText.setText(airman.getDOR());
        mDESText.setText(airman.getDES());
        mEditLastFour.setText(airman.getLastFour());

    }

}
