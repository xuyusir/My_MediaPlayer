package yuyu.mymedia;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import  android.widget.*;
import  android.os.*;
import  java.util.*;


/**
 * Created by yuyu on 2017/2/24.
 */

public class MyListView extends AppCompatActivity {
    private ListView listView;
    private List<String> data = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        listView = new ListView(this);
        data=getData();
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,data));
        setContentView(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MyListView.this,Surfaceview.class);
                intent.putExtra("datap",data.get(position));
                startActivity(intent);
                finish();

            }
        });
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
