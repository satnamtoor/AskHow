package satnam.valentinelove;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Adapter.FavBaseAdapter;
import Db.DatabaseMethod;
import Model.JBFavList;

/**
 * Created by ss22493 on 23-01-2017.
 */
public class FavList extends AppCompatActivity {

    ListView recycler_view;
    ArrayList<JBFavList> mFavArrayList;
    DatabaseMethod mDataBaseMethod;
    FavBaseAdapter messageBaseAdapter;
    JBFavList mJbFavList;
    private TextView txtNotFound;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_common);
        initial();
        if (mDataBaseMethod.emptyFavTable() == true) {
            recycler_view.setVisibility(View.GONE);
            txtNotFound.setVisibility(View.VISIBLE);
        } else {
            recycler_view.setVisibility(View.VISIBLE);
            txtNotFound.setVisibility(View.GONE);
        }
    }

    private void initial() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        txtNotFound = (TextView) findViewById(R.id.txtNotFound);
        recycler_view = (ListView) findViewById(R.id.recycler_view);
        mDataBaseMethod = new DatabaseMethod(FavList.this);
        mDataBaseMethod.openDataBase();
        displayActivityData();
    }


    public void displayActivityData() {

        Cursor mCursor = mDataBaseMethod.getFavData();
        if (mCursor != null && mCursor.getCount() > 0) {
            getActivityFromDB(mCursor);
        }
        mCursor.close();
    }

    private void getActivityFromDB(Cursor c) {
        mFavArrayList = new ArrayList<JBFavList>();
        if (c != null) {

            c.moveToFirst();
            do {
                mJbFavList = new JBFavList();
                int fav_id = c.getInt(c
                        .getColumnIndex(DatabaseMethod.FAV_ID));
                String strActivityName = c.getString(c
                        .getColumnIndex(DatabaseMethod.FAV_MSG_ID));

                mJbFavList.setId(fav_id);
                mJbFavList.setMsg(strActivityName);
                mFavArrayList.add(mJbFavList);
            } while (c.moveToNext());
        }
        c.close();

        messageBaseAdapter = new FavBaseAdapter(FavList.this, mFavArrayList);
        recycler_view.setAdapter(messageBaseAdapter);
        messageBaseAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);


    }
}
