package ru.twice.forge.mytwothreads;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    MyTask mt;
    MyNewTask mnt;
    static TextView tv,tv2;
    ArrayList objects;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("qwe", "create MainActivity: " + this.hashCode());

        tv = (TextView) findViewById(R.id.tv);
        tv2 = (TextView) findViewById(R.id.tv2);

        /*mt = (MyTask) getLastCustomNonConfigurationInstance();

        if (mt == null) {
            mt = new MyTask();
            mt.execute();
        }

        mt.link(this);

        mnt = (MyNewTask) getLastCustomNonConfigurationInstance();

        if (mnt == null) {
            mnt = new MyNewTask();
            mnt.execute();
        }

        mt.link(this);*/

        objects=(ArrayList)getLastCustomNonConfigurationInstance();

        if(objects==null){
            objects=new ArrayList();

            mt = new MyTask();
           // mt.execute();

            mnt = new MyNewTask();
            mnt.execute();

            objects.add(mt);
            objects.add(mnt);
        }

        mt=(MyTask)objects.get(0);mt.link(this);
        mnt=(MyNewTask)objects.get(1);mnt.link(this);


       // mt.cancel(false);
        Log.d("qwe", "create MyTask: " + mt.hashCode());
        Log.d("life", "life " + "onCreate");
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {

        mt.unLink();
        mnt.unLink();
        return objects;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("life", "life " + "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("life", "life " + "onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("life", "life " + "onResume");
    }

    static class MyTask extends AsyncTask<String, Integer, Void> {

        MainActivity activity;

        // получаем ссылку на MainActivity
        void link(MainActivity act) {
            activity = act;
        }

        // обнуляем ссылку
        void unLink() {
            activity = null;
        }

        @Override
        protected Void doInBackground(String... params) {
            try {

                /*for (int j = 0; j < 10; j++) {*/

            while(true) {
                for (int i = 1; i <= 255; i++) {
                    TimeUnit.MILLISECONDS.sleep(5);
                    publishProgress(i);
                       if(activity!=null){
                        Log.d("qwe", "i = " + i + ", MyTask: " + this.hashCode()
                                + ", MainActivity: " + activity.hashCode());}
                }
                for (int i = 255; i >= 0; i--) {
                    TimeUnit.MILLISECONDS.sleep(5);
                    publishProgress(i);


                       /* if(activity!=null){
                            Log.d("qwe", "i = " + i + ", MyTask: " + this.hashCode()
                                    + ", MainActivity: " + activity.hashCode());}*/
                }

            }

                   /* if(j==9){j=0;}}*/

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            activity.tv.setBackgroundColor(Color.argb(255,values[0],255,values[0]));


        }
    }

    static class MyNewTask extends AsyncTask<String, Integer, Void> {

        MainActivity activity;

        // получаем ссылку на MainActivity
        void link(MainActivity act) {
            activity = act;
        }

        // обнуляем ссылку
        void unLink() {
            activity = null;
        }

        @Override
        protected Void doInBackground(String... params) {
            try {

            while(true) {
                for (int i = 1; i <= 255; i++) {
                    TimeUnit.MILLISECONDS.sleep(5);
                    publishProgress(i);
                       if(activity!=null){
                        Log.d("qwe", "i = " + i + ", MyTask: " + this.hashCode()
                                + ", MainActivity: " + activity.hashCode());}
                }
                for (int i = 255; i >= 0; i--) {
                    TimeUnit.MILLISECONDS.sleep(5);
                    publishProgress(i);
                       /* if(activity!=null){
                            Log.d("qwe", "i = " + i + ", MyTask: " + this.hashCode()
                                    + ", MainActivity: " + activity.hashCode());}*/
                }

            }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            activity.tv2.setText("i = "+values[0]);
            activity.tv2.setBackgroundColor(Color.argb(255, 255, values[0], values[0]));

        }
    }
}
