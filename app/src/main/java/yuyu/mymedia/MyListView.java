package yuyu.mymedia;

import android.app.Activity;
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
        data.add("测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");

        return data;
    }
}
