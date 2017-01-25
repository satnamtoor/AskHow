package Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Db.DatabaseMethod;
import Model.JBFavList;
import satnam.valentinelove.R;

/**
 * Created by ss22493 on 24-01-2017.
 */
public class FavBaseAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<JBFavList> mArrayListMessages;
    DatabaseMethod mDataBaseMethod;

    public FavBaseAdapter(Context mContext, ArrayList<JBFavList> mArrayListMessages) {

        this.mContext = mContext;
        this.mArrayListMessages = mArrayListMessages;
        mDataBaseMethod = new DatabaseMethod(mContext);
        mDataBaseMethod.openDataBase();
    }

    @Override
    public int getCount() {
        return mArrayListMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final viewholder mViewholder;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        if (convertView == null) {
            mViewholder = new viewholder();
            convertView = layoutInflater.inflate(R.layout.adapter_cardview,
                    null);
            mViewholder.txtMessages = (TextView) convertView.findViewById(R.id.txtMessages);
            mViewholder.imageBack = (ImageView) convertView.findViewById(R.id.imageBack);
            mViewholder.btnFav = (Button) convertView.findViewById(R.id.btnFav);
            mViewholder.btnShare = (Button) convertView.findViewById(R.id.btnShare);
            mViewholder.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);


            convertView.setTag(mViewholder);
        } else {
            mViewholder = (viewholder) convertView.getTag();
        }
        mViewholder.btnFav.setVisibility(View.GONE);
        mViewholder.btnDelete.setVisibility(View.VISIBLE);

        mViewholder.btnFav.setTag(position);
        mViewholder.btnShare.setTag(position);
        mViewholder.btnDelete.setTag(position);

        mViewholder.txtMessages.setText(mArrayListMessages.get(position).getMsg());

        mViewholder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                // mDataBaseMethod.insertData(mArrayListMessages.get(position).toString());
                Toast.makeText(mContext, "Msg add to fav list", Toast.LENGTH_LONG).show();
            }
        });


        mViewholder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                mDataBaseMethod.deleteFav(mArrayListMessages.get(position).getId());
                Toast.makeText(mContext, "Delete successfully", Toast.LENGTH_LONG).show();
                notifyDataSetChanged();
                ((Activity) mContext).finish();
            }
        });


        mViewholder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();

            }
        });


        return convertView;
    }

    class viewholder {
        public TextView txtMessages;
        public ImageView imageBack;
        public Button btnFav, btnShare, btnDelete;


    }
}