package satnam.valentinelove;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.splash.SplashConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import Db.DBAssettsFolder;
import LocalNotification.AlarmReceiver;
import animation.MyBounceInterpolator;


public class Initial extends AppCompatActivity implements View.OnClickListener {

    StartAppAd startappad = new StartAppAd(this);
    Button btnClick, btnGame;
    String[] tables = {"rose", "propose", "chocolate", "teddy", "promis", "hug", "Kiss", "valentine"};
    String random = "";
    DBAssettsFolder mAssettsFolder;
    String strMessages;
    AlarmManager am;

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
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        btnClick = (Button) findViewById(R.id.btnClick);
        btnClick.setOnClickListener(this);

        btnGame = (Button) findViewById(R.id.btnGame);
        btnGame.setOnClickListener(this);

        int idx = new Random().nextInt(tables.length);
        random = (tables[idx]);


        mAssettsFolder = new DBAssettsFolder(Initial.this);
        File database = Initial.this.getDatabasePath(DBAssettsFolder.DATABASE_NAME);
        if (false == database.exists()) {
            mAssettsFolder.getReadableDatabase();
            if (copyData(Initial.this)) {
                //Toast.makeText(MainActivity.this, "METHODS", Toast.LENGTH_LONG).show();
            }
            if (!random.equalsIgnoreCase("")) {
                getMessagesData(random);
            }
        } else {
            if (!random.equalsIgnoreCase("")) {
                getMessagesData(random);
            }
        }

    }


    private void getMessagesData(String strTableName) {
        Cursor c = mAssettsFolder.getRendomMessages(strTableName);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            {
                do {
                    strMessages = c
                            .getString(c
                                    .getColumnIndex(DBAssettsFolder.ROSE_NAME));
                } while (c.moveToNext());
            }
            c.close();

        }
        scheduleNotification(getNotification(strMessages), 10);//24 * 60 * 60 * 1000

    }


    private boolean copyData(Context mContext) {
        try {
            InputStream in = mContext.getAssets().open(DBAssettsFolder.DATABASE_NAME);
            String outputfilename = DBAssettsFolder.DATABASE_PATH + DBAssettsFolder.DATABASE_NAME;
            OutputStream out = new FileOutputStream(outputfilename);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            out.flush();
            out.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnClick:

                animateButton("msg");
                break;

            case R.id.btnGame:

                animateButton("game");
                break;

            default:
                break;

        }
    }

    void animateButton(final String status) {
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
                if (status.equalsIgnoreCase("msg")) {
                    Intent mIntent = new Intent(Initial.this, ListMsg.class);
                    startActivity(mIntent);
                } else {

                    Intent mIntent = new Intent(Initial.this, FaceRecogination.class);
                    startActivity(mIntent);


                }
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


    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Today Love Message");
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setAutoCancel(true);
        builder.setStyle(new Notification.BigTextStyle().bigText(content));
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        return builder.build();
    }

}
