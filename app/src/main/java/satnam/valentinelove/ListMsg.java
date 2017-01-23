package satnam.valentinelove;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ycl.tabview.library.TabView;
import com.ycl.tabview.library.TabViewChild;

import java.util.ArrayList;
import java.util.List;

import fragments.FragmentCommon;

/**
 * Created by ss22493 on 21-01-2017.
 */
public class ListMsg extends AppCompatActivity {

    TabView tabView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_in_xmlactivity);
        initial();
        List<TabViewChild> tabViewChildList = new ArrayList<>();
        TabViewChild tabViewChild01 = new TabViewChild(R.mipmap.tab01_sel, R.mipmap.tab01_sel, "1", FragmentCommon.newInstance("rose"));
        TabViewChild tabViewChild02 = new TabViewChild(R.mipmap.tab01_sel, R.mipmap.tab01_sel, "2", FragmentCommon.newInstance("propose"));
        TabViewChild tabViewChild03 = new TabViewChild(R.mipmap.tab01_sel, R.mipmap.tab01_sel, "3", FragmentCommon.newInstance("chocolate"));
        TabViewChild tabViewChild04 = new TabViewChild(R.mipmap.tab01_sel, R.mipmap.tab01_sel, "4", FragmentCommon.newInstance("teddy"));
        TabViewChild tabViewChild05 = new TabViewChild(R.mipmap.tab01_sel, R.mipmap.tab01_sel, "5", FragmentCommon.newInstance("promis"));
        TabViewChild tabViewChild06 = new TabViewChild(R.mipmap.tab01_sel, R.mipmap.tab01_sel, "6", FragmentCommon.newInstance("hug"));
        TabViewChild tabViewChild07 = new TabViewChild(R.mipmap.tab01_sel, R.mipmap.tab01_sel, "7", FragmentCommon.newInstance("Kiss"));
        TabViewChild tabViewChild08 = new TabViewChild(R.mipmap.tab01_sel, R.mipmap.tab01_sel, "8", FragmentCommon.newInstance("valentine"));


        tabViewChildList.add(tabViewChild01);
        tabViewChildList.add(tabViewChild02);
        tabViewChildList.add(tabViewChild03);
        tabViewChildList.add(tabViewChild04);
        tabViewChildList.add(tabViewChild05);
        tabViewChildList.add(tabViewChild06);
        tabViewChildList.add(tabViewChild07);
        tabViewChildList.add(tabViewChild08);
        //end add data
        tabView.setTabViewDefaultPosition(2);
        tabView.setTabViewChild(tabViewChildList, getSupportFragmentManager());
        tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int position, ImageView currentImageIcon, TextView currentTextView) {
                Toast.makeText(getApplicationContext(), "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initial() {
        tabView = (TabView) findViewById(R.id.tabView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);


        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_item:
                //
                // animateButton();
                Intent mIntent = new Intent(ListMsg.this, FavList.class);
                startActivity(mIntent);
                return true;

            case android.R.id.home:
                finish();
                // close this activity and return to preview activity (if there is any)

            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
