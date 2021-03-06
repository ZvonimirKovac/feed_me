package willcodeforfood.tvzmc2.feedme.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import willcodeforfood.tvzmc2.feedme.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SECONDS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);

                SplashActivity.this.finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SECONDS);
    }
}
