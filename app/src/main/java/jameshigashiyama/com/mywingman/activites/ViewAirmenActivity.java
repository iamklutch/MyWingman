package jameshigashiyama.com.mywingman.activites;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import jameshigashiyama.com.mywingman.Airman;
import jameshigashiyama.com.mywingman.R;
import jameshigashiyama.com.mywingman.adapters.AirmanAdapter;
import jameshigashiyama.com.mywingman.db.DatabaseMethods;

// made by me

public class ViewAirmenActivity extends Activity {

    private String mEncryptedData;
    PlaceholderFragment fragment;
//    Intent serviceIntent;
//    BroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_airmen);

        fragment = new PlaceholderFragment();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }

    }

    @Override
    protected void onDestroy() {
//        stopService(serviceIntent);
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, getString(R.string.toast_about), Toast.LENGTH_LONG).show();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    private class AirmanReciever extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {


        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private RecyclerView mRecyclerView;
        private AirmanAdapter mAirmanAdapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            DatabaseMethods dataSource = new DatabaseMethods(this.getActivity());
            ArrayList<Airman> airmen = dataSource.read();
            View rootView = inflater.inflate(R.layout.get_airman_fragment, container, false);
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.feedRecyclerView);
            mRecyclerView.setHasFixedSize(true);
            android.support.v7.widget.GridLayoutManager layoutManager =
                    new android.support.v7.widget.GridLayoutManager(getActivity(),1);
            mRecyclerView.setLayoutManager(layoutManager);
            mAirmanAdapter = new AirmanAdapter(this.getActivity(),airmen);
            mRecyclerView.setAdapter(mAirmanAdapter);
            return rootView;
        }
    }
}
