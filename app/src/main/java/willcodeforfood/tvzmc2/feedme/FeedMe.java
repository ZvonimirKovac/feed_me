package willcodeforfood.tvzmc2.feedme;

import android.app.Application;

import com.firebase.client.Firebase;

public class FeedMe extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
}
