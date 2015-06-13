package jameshigashiyama.com.mywingman.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import butterknife.InjectView;
import jameshigashiyama.com.mywingman.R;
import jameshigashiyama.com.mywingman.activites.AddAirmanActivity;
import jameshigashiyama.com.mywingman.activites.EditAirmanActivity;

/**
 * Created by James on 6/5/2015.
 */
public class SpinnerHelperAdapter  extends EditAirmanActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = SpinnerHelperAdapter.class.getSimpleName();
    public Context mContext;
    private int mValue;



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
