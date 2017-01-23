package fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import Adapter.MessageBaseAdapter;
import Db.DBAssettsFolder;
import satnam.valentinelove.R;


public class FragmentCommon extends Fragment {
    //  TextView textView;


    ListView recycler_view;
    View view;
    DBAssettsFolder mAssettsFolder;
    String strTableName, strMessages;
    ArrayList<String> mStringArrayListMessages;
    MessageBaseAdapter messagesAdapter;
    //  private RecyclerView.LayoutManager mLayoutManager;

    public static FragmentCommon newInstance(String text) {
        FragmentCommon fragmentCommon = new FragmentCommon();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_common, container, false);
        /*textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(getArguments().getString("text"));
*/
        strTableName = getArguments().getString("text");
        Toast.makeText(getActivity(), "" + getArguments().getString("text"), Toast.LENGTH_LONG).show();
        initial(view);
        return view;
    }

    private void initial(View mView) {
        recycler_view = (ListView) mView.findViewById(R.id.recycler_view);
       /* recycler_view.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(mLayoutManager);*/
        mAssettsFolder = new DBAssettsFolder(getActivity());
        File database = getActivity().getDatabasePath(DBAssettsFolder.DATABASE_NAME);
        if (false == database.exists()) {
            mAssettsFolder.getReadableDatabase();
            if (copyData(getActivity())) {
                //Toast.makeText(MainActivity.this, "METHODS", Toast.LENGTH_LONG).show();
            }
            if (!strTableName.equalsIgnoreCase("")) {
                getMessagesData(strTableName);
            }
        } else {
            if (!strTableName.equalsIgnoreCase("")) {
                getMessagesData(strTableName);
            }
        }
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


    private void getMessagesData(String strTableName) {
        mStringArrayListMessages = new ArrayList<String>();
        Cursor c = mAssettsFolder.getRoseMessages(strTableName);

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            {
                do {

                    strMessages = c
                            .getString(c
                                    .getColumnIndex(DBAssettsFolder.ROSE_NAME));
                    mStringArrayListMessages.add(strMessages);
                } while (c.moveToNext());
            }
            c.close();

        }
        messagesAdapter = new MessageBaseAdapter(getActivity(), mStringArrayListMessages);
        recycler_view.setAdapter(messagesAdapter);
        messagesAdapter.notifyDataSetChanged();

    }


}
