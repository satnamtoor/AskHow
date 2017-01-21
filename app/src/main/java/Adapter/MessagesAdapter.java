package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import satnam.valentinelove.R;

/**
 * Created by ss22493 on 21-01-2017.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> mArrayListMessages;


    public MessagesAdapter(Context mContext, List<String> mArrayListMessages) {
        this.mContext = mContext;
        this.mArrayListMessages = mArrayListMessages;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_cardview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return mArrayListMessages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtMessages;
        public ImageView imageBack;

        public MyViewHolder(View view) {
            super(view);
            txtMessages = (TextView) view.findViewById(R.id.txtMessages);
            imageBack = (ImageView) view.findViewById(R.id.imageBack);
        }
    }

}
