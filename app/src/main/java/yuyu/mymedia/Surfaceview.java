package yuyu.mymedia;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.os.Handler;
import  android.content.Intent;
import  android.widget.*;
import  java.io.*;
import java.util.*;
import  android.view.View.OnClickListener;


public class Surfaceview extends AppCompatActivity {
    MediaPlayer mp;
    SurfaceView sv;
    Handler mHandler;
    Runnable mRunnable;
    List<String> L1;
    String data=new String();




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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.surfacexml);
        Intent intent=this.getIntent();
        String datap=intent.getStringExtra("datap");
        data=datap;


        sv=(SurfaceView)findViewById(R.id.surfaceView2);
        final SurfaceHolder holder=sv.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                mp.setDisplay(holder);
                mHandler.removeCallbacks(mRunnable);
                mHandler.postDelayed(mRunnable,4000);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });
        final LinearLayout linearLayout=(LinearLayout)findViewById(R.id.test);
        mp=new MediaPlayer();
        final Button bplay=(Button)findViewById(R.id.play);
        bplay.setText("pause");
        Button bbefore=(Button)findViewById(R.id.before);
        Button bnext=(Button)findViewById(R.id.next);
        final SeekBar seekbar=(SeekBar)findViewById(R.id.seekbar);
        mHandler= new Handler();
        Timer timer=new Timer();


        try {
            mp.setDataSource(datap);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mp.start();





        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                float x=mp.getDuration();
                float i=mp.getCurrentPosition();
                float p=i/x*100;
                int g=(int)p;
                seekbar.setProgress(g);
            }
        },0, 500);

       sv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mHandler.removeCallbacks(mRunnable);
                linearLayout.setVisibility(View.VISIBLE);
                mHandler.postDelayed(mRunnable,4000);
                return false;
            }
        });



        mRunnable = new Runnable() {
            @Override
            public void run() {
                linearLayout.setVisibility(View.INVISIBLE);
            }
        };



                seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    float x=seekBar.getProgress();
                        float a=mp.getDuration();
                        float i=x/100*a;
                        int j=(int)i;
                        mp.pause();
                        mp.seekTo(j);
                        mp.start();
                        mHandler.removeCallbacks(mRunnable);
                        linearLayout.setVisibility(View.VISIBLE);
                        mHandler.postDelayed(mRunnable,4000);
                    }
                });



        bplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             if (mp.isPlaying()) {
                  mp.pause();
                  bplay.setText("play");              }

           else {
                   mp.start();
                 bplay.setText("pause");

               }
                mHandler.removeCallbacks(mRunnable);
                linearLayout.setVisibility(View.VISIBLE);
                mHandler.postDelayed(mRunnable,4000);

            }
        });


        bbefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=new String();
                int i;
                int q=0;
               L1=getData();
               i= L1.size();
                while (q<i){

                    if (L1.get(q).equals(data)) {
                        if (q == 0) {
                            break;
                        } else {
                            mp.reset();
                            s = L1.get(q - 1);
                            try {
                                mp.setDataSource(s);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                mp.prepare();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            mp.start();
                            data=s;
                            break;
                        }
                    }
                    else {
                        q++;
                    }
                }
            }
        });

        bnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=new String();
                int i;
                int q=0;
                L1=getData();
                i= L1.size();
                while (q<i){

                    if (L1.get(q).equals(data)) {
                        if (q == i-1) {
                            break;
                        } else {
                            mp.reset();
                            s = L1.get(q + 1);
                            try {
                                mp.setDataSource(s);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                mp.prepare();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            mp.start();
                            data=s;
                            break;
                        }
                    }
                    else {
                        q++;
                    }
                }

            }
        });
  }

}
