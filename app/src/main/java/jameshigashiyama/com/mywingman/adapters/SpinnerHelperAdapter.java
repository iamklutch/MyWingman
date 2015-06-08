package jameshigashiyama.com.mywingman.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import jameshigashiyama.com.mywingman.activites.AddAirmanActivity;

/**
 * Created by James on 6/5/2015.
 */
public class SpinnerHelperAdapter  implements AdapterView.OnItemSelectedListener {

    public static final String TAG = SpinnerHelperAdapter.class.getSimpleName();
    public Context mContext;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Log.d(TAG, "Made it to the onItemSelected method");
//        Toast.makeText(parent.getContext(), "Spinner Item selected: " + parent.getItemAtPosition(position).toString()
//               , Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
