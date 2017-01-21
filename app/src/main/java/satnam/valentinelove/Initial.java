package satnam.valentinelove;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.splash.SplashConfig;


public class Initial extends AppCompatActivity implements View.OnClickListener {

    StartAppAd startappad = new StartAppAd(this);
    Button btnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppAd.showSplash(this, savedInstanceState,
                new SplashConfig()
                        .setTheme(SplashConfig.Theme.GLOOMY)
                        .setLogo(R.mipmap.ic_launcher)
                        .setAppName(getResources().getString(R.string.app_name))
                //.setCustomScreen(R.layout.your_splash_screen_layout_id)
        );
        setContentView(R.layout.initial);

        initial();
        //satnam
    }

    private void initial() {

        btnClick = (Button) findViewById(R.id.btnClick);
        btnClick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnClick:
                Intent mIntent = new Intent(Initial.this, ListMsg.class);
                startActivity(mIntent);

                break;
        }
    }
}
