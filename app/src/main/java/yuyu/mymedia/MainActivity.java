package yuyu.mymedia;

import android.content.Intent;
import android.media.MediaPlayer;
import  android.app.*;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import  android.util.*;
import android.widget.SeekBar;
import android.widget.Toast;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    private MediaPlayer mp;
    private SurfaceView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=this.getIntent();
        final String datapath=intent.getStringExtra("datap");
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        final int pw= metric.widthPixels;     // 屏幕宽度（像素）
        final int ph = metric.heightPixels;   // 屏幕高度（像素）

        mp=new MediaPlayer();
        sv=(SurfaceView)findViewById(R.id.surfaceView);
        Button play=(Button)findViewById(R.id.play);
        Button select=(Button)findViewById(R.id.select);
        final Button pause=(Button)findViewById(R.id.pause);
        Button stop=(Button)findViewById(R.id.stop);
        final SeekBar seekbar=(SeekBar)findViewById(R.id.seekBar2);
        RelativeLayout.LayoutParams b= new RelativeLayout.LayoutParams(pw/4,(int)(ph*(1)/10));
        select.setWidth(pw/4);
        select.setHeight((int)(ph*(0.5)/10));
        play.setWidth(pw/4);
        play.setHeight((int)(ph*(0.5)/10));
        pause.setWidth(pw/4);
        pause.setHeight((int)(ph*(0.5)/10));
        stop.setWidth(pw/4);
        stop.setHeight((int)(ph*(0.5)/10));
        RelativeLayout.LayoutParams rl= new RelativeLayout.LayoutParams(pw,(int)(ph*(8)/10));
        rl.addRule(RelativeLayout.CENTER_HORIZONTAL);
        sv.setLayoutParams(rl);





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
                    mp.setDataSource(datapath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mp.setDisplay(sv.getHolder());
                try {
                    mp.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mp.start();
                Timer timer=new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        float x=mp.getDuration();
                        float i=mp.getCurrentPosition();
                        float p=i/x*100;
                        int g=(int)p;
                     seekbar.setProgress(g);
                    }
                },0, 1000);
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
                    }
                });


            }
        });

       select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MyListView.class);
                startActivity(intent);
                finish();

                }

        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (mp.isPlaying()) {
//                    mp.pause();
//                    pause.setText("resume");
//                }
//                else {
//                    mp.start();
//                    pause.setText("pause");
//
//                }
                mp.pause();
                mp.seekTo(10000);
                mp.start();

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mp.stop();
            mp.reset();

            }
        });

    }

}
