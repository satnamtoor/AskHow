package satnam.valentinelove;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.splash.SplashConfig;

import animation.MyBounceInterpolator;


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
        getSeekBarValue();
        getSeekBarDurValue();
        getSeekBarValueTime();
    }

    private void initial() {

        btnClick = (Button) findViewById(R.id.btnClick);
        btnClick.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnClick:

                animateButton();
                break;
        }
    }

    void animateButton() {
        // Load the animation
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        double animationDuration = getSeekBarDurValue() * 1000;
        myAnim.setDuration((long) animationDuration);

        // Use custom animation interpolator to achieve the bounce effect
        MyBounceInterpolator interpolator = new MyBounceInterpolator(getSeekBarValueTime(), getSeekBarValue());

        myAnim.setInterpolator(interpolator);

        // Animate the button
       /* Button button = (Button)findViewById(R.id.play_button);
        button.startAnimation(myAnim);
        playSound();*/
        btnClick.startAnimation(myAnim);
        // Run button animation again after it finished
        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                // animateButton();\
                // Toast.makeText(ListMsg.this, "CLICK--Click", Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent(Initial.this, ListMsg.class);
                startActivity(mIntent);
            }
        });
    }


    double getSeekBarValue() {
        return (19 + 1.0) / (1.0 / 0.5);
    }

    double getSeekBarDurValue() {
        return (9 + 1.0) / (1.0 / 0.1);
    }

    double getSeekBarValueTime() {
        return (19 + 1.0) / (1.0 / 0.01);
    }
}
