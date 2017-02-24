package yuyu.mymedia;

import android.content.Intent;
import android.media.MediaPlayer;
import  android.app.*;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import  android.util.*;
import android.widget.Toast;
import android.database.*;
import  android.content.ContentResolver;
import  android.provider.*;
import java.io.IOException;

public class MainActivity extends Activity {
    private MediaPlayer mp;
    private SurfaceView sv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        final int pw= metric.widthPixels;     // 屏幕宽度（像素）
        final int ph = metric.heightPixels;   // 屏幕高度（像素）
        mp=new MediaPlayer();
        sv=(SurfaceView)findViewById(R.id.surfaceView);
        Button play=(Button)findViewById(R.id.play);
        Button select=(Button)findViewById(R.id.select_media);
        final Button pause=(Button)findViewById(R.id.pause);
        Button stop=(Button)findViewById(R.id.stop);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getApplicationContext(), "media finish!",
                        Toast.LENGTH_SHORT).show();
                mp.reset();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mp.setDataSource("/storage/1D98-0641/2.mp4");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mp.setDisplay(sv.getHolder());
                try {
                    mp.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                RelativeLayout.LayoutParams rl= new RelativeLayout.LayoutParams(mp.getVideoWidth(),mp.getVideoHeight());
                rl.addRule(RelativeLayout.CENTER_HORIZONTAL);
                sv.setLayoutParams(rl);
                mp.start();
            }
        });


       select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentResolver c=getContentResolver();
                Cursor cur = c.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null,
                                     null, null,null);
                int num=cur.getCount();
                cur.moveToFirst();
                for (int i=0;i<num;i++){
                    String datapath=cur.getString(cur.getColumnIndex(MediaStore.Video.Media.DATA));
                    cur.moveToNext();
                    Log.e("TAG", datapath);
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mp.isPlaying()) {
                    mp.pause();
                    pause.setText("resume");
                }
                else {
                    mp.start();
                    pause.setText("pause");

                }
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            mp.stop();
//            mp.reset();
                Intent intent=new Intent(MainActivity.this,MyListView.class);
                startActivity(intent);

            }
        });






    }
}
