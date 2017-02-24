package yuyu.mymedia;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import  android.widget.*;
import  android.os.*;
import  java.util.*;


/**
 * Created by yuyu on 2017/2/24.
 */

public class MyListView extends Activity {
    private ListView listView;
    //private List<String> data = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
        setContentView(listView);
    }

    private List<String> getData(){

        List<String> data = new ArrayList<String>();

        ContentResolver c=getContentResolver();
        Cursor cur = c.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null,
                null, null,null);
        int num=cur.getCount();
        cur.moveToFirst();
        for (int i=0;i<num;i++){
            String datapath=cur.getString(cur.getColumnIndex(MediaStore.Video.Media.DATA));
            data.add(datapath);
            cur.moveToNext();
        }

        return data;
    }
}
