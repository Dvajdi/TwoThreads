package ru.twice.forge.mytwothreads;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static Handler handler;
    static int i;
    static Thread t;
    TextView tv;
int mSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.textView);


        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.d("dom",""+msg.what);
                tv.setBackgroundColor(Color.argb(255, msg.what,255,255));
            }
        };

        //go2();
        runTimer();
    }


    void go(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 255; i++) {
                    handler.sendEmptyMessage(i);
                }
                Sleeper.sleep(10);
            }
        });
        t.start();
    }
    void go2(){
        t = new Thread(new Runnable() {
            public void run() {
                for (int j = 0; j < 20; j++) {
                    for (int i = 1; i <= 255; i++) {
                        Sleeper.sleep(5);
                        handler.sendEmptyMessage(i);
                    }

                    for (int i = 255; i >= 0; i--) {
                        Sleeper.sleep(5);
                        handler.sendEmptyMessage(i);
                    }
                }
            }
        });
        t.start();
    }

    private void runTimer() {
        final TextView timeTextView = (TextView) findViewById(R.id.textView);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String time = "" + mSeconds;
                timeTextView.setText(time);
                timeTextView.setBackgroundColor(Color.rgb(mSeconds, mSeconds,255));
                    mSeconds++;
                Log.d("dom",""+mSeconds);
                handler.postDelayed(this, 50);

            }
        });
    }
}
