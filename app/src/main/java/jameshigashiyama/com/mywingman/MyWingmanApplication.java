package jameshigashiyama.com.mywingman;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by James on 5/12/2015.
 */
public class MyWingmanApplication extends Application {

    @Override
    public void onCreate() {
        // Enable Local Datastore.
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "kVOYJdn5wQoq5SwY3kb512g1C77dWdTuBhs4PCxW", "oJID9VuDpAbPiyUAUQei5t26EJry4oAEaAVErjCk");


    }
}
