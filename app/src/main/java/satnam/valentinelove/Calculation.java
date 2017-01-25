package satnam.valentinelove;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import at.grabner.circleprogress.CircleProgressView;

/**
 * Created by ss22493 on 25-01-2017.
 */
public class Calculation extends AppCompatActivity implements View.OnClickListener {

    public Random ran;
    int res;
    TextView tv;
    CircleProgressView mCircleView;
    Bundle extras;
    String strYourName = "", strPartnerName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_result);
        initial();
        tv.setText(strYourName + " " + "Love's" + " " + strPartnerName + "\n" + "\n" + "\t" + "\t" + res + "%");
    }

    public void initial() {
        extras = getIntent().getExtras();
        ran = new Random();
        res = ran.nextInt(100 - 90) + 90;
        tv = (TextView) findViewById(R.id.title_result);
        mCircleView = (CircleProgressView) findViewById(R.id.circleView);
        mCircleView.setOnProgressChangedListener(new CircleProgressView.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(float value) {
                // Log.d(TAG, "Progress Changed: " + value);
            }
        });
        mCircleView.setValueAnimated(res, 4500);
        if (extras != null) {
            strYourName = extras.getString("YOUR_NAME");
            strPartnerName = extras.getString("PARTNER_NAME");
        }
    }

    @Override
    public void onClick(View v) {

    }
}
