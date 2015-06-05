package jameshigashiyama.com.mywingman.activites;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jameshigashiyama.com.mywingman.Airman;
import jameshigashiyama.com.mywingman.R;
import jameshigashiyama.com.mywingman.adapters.SpinnerAdapter;

public class AddAirmanActivity extends ActionBarActivity implements AdapterViewCompat.OnItemSelectedListener{

    @InjectView(R.id.lastNameEditText)EditText mLastName;
    @InjectView(R.id.firstNameEditText)EditText mFirstName;
    @InjectView(R.id.ageSpinner)Spinner mAirmanAge;
    @InjectView(R.id.rankSpinner)Spinner mAirmanRank;
    private String mEncryptedData;
    private int mId;
    private Airman mAirman;
    private Spinner ageSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_airman);
        ButterKnife.inject(this);

        //Spinner interface implementation
        Number[] ageArray = new Number[100];
        for (int i = 0; i < ageArray.length; i++) {
            ageArray[i] = i + 1;
        }

        ageSpin = (Spinner)findViewById(R.id.ageSpinner);
        ArrayAdapter<Number>adapter = new ArrayAdapter<>
                (AddAirmanActivity.this, android.R.layout.simple_spinner_item, ageArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpin.setAdapter(adapter);
//        ageSpin.setOnItemSelectedListener();


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

    @Override
    public void onItemSelected(AdapterViewCompat<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterViewCompat<?> parent) {

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

}



