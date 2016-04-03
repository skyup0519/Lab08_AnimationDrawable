package com.example.andy.lab08_animationdrawable;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private final int minnumber = 0;
    private ImageView m_tv_image;
    private TextView m_tv_message;
    private AnimationDrawable m_tv_frame_animation;
    private Handler m_handler = new Handler(); //Handle
    private Handler m_handler2 = new Handler(); //Handle2
    private ImageView m_tv_2_image;
    private TextView m_tv_2_message;
    private TextView m_tv_3_message;
    private TextView m_tv_4_message;
    private Resources res;
    private Drawable drawable_logo;
    private CharSequence drawable_name;
    private TypedArray array_logo;
    private TypedArray array_name;
    private int maxnumber;
    private Runnable duke_stop = new duke_stop();
    private Runnable go = new run_Nba();
    private Runnable stop = new stop_nba();
    private Runnable countdown_Timer = new 計時器_下();
    private Runnable countdown_Timer_上 = new 計時器_上();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findID();
        initFrameAnimation();
        initNba_logos();
        initNba_Names();
    }

    private void initNba_logos() {
        res = getResources(); //取得資源
        array_logo = res.obtainTypedArray(R.array.nba_logo); //資源中取得 指定的陣列
        drawable_logo = array_logo.getDrawable(0);           //從陣列中 取得"第幾項"(且是哪種類型)
        m_tv_2_image.setBackground(drawable_logo);           //設定Background(填入 上列所選的)
        maxnumber = array_logo.length() - 1;

    }

    private void initNba_Names() {
        res = getResources();
        array_name = res.obtainTypedArray(R.array.nba_name);
        drawable_name = array_name.getText(0);
        m_tv_2_message.setText(drawable_name);

    }

    private void findID() {
        m_tv_image = (ImageView) findViewById(R.id.tv_image);
        m_tv_message = (TextView) findViewById(R.id.tv_message);

        m_tv_2_image = (ImageView) findViewById(R.id.tv_2_image);
        m_tv_2_message = (TextView) findViewById(R.id.tv_2_message);
        m_tv_3_message = (TextView) findViewById(R.id.tv_3_message);
        m_tv_4_message = (TextView) findViewById(R.id.tv_4_message);
    }

    private void initFrameAnimation() {
        m_tv_image.setBackgroundResource(R.drawable.frame_animation);
        m_tv_frame_animation = (AnimationDrawable) m_tv_image.getBackground();


    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_button_START:
                m_tv_frame_animation.start();
                break;

            case R.id.tv_button_STOP:
                m_tv_frame_animation.stop();
                break;
            case R.id.tv_button_5_SECS:
                animation_5sec();
                break;
            case R.id.tv_button_GO:
                GO();
                break;

            case R.id.tv_button_倒數:
                倒數();
                break;
        }
    }

    int time = 10;

    private void GO() {
        m_handler.post(go); //Handler 叫 Runnable 工作， 內容是"run_Nba"
        boolean result = m_handler.postDelayed(stop, time * 1000);
        m_tv_3_message.setText((result ? "交付成功" : "交付失敗"));
    }

    private void 倒數() {
        m_handler2.post(countdown_Timer);
        m_handler.post(go); //Handler 叫 Runnable 工作， 內容是"run_Nba"
        m_handler.postDelayed(stop, time * 1000);
    }

    private void animation_5sec() {

        boolean resurt = m_handler.postDelayed(duke_stop, time * 1000);
        m_tv_frame_animation.start();
        m_handler2.post(countdown_Timer_上);
    }

    private class duke_stop implements Runnable {

        @Override
        public void run() {
            m_tv_frame_animation.stop();
        }
    }

    private class 計時器_上 implements Runnable {

        @Override
        public void run() {

            m_tv_message.setText(String.valueOf(time));
            time--;
            if (time >= 0) {
                m_handler.postDelayed(this, 1000);
            } else {
                time = 10;
                m_tv_message.setText("時間到囉");
            }
        }
    }

    private class 計時器_下 implements Runnable {

        @Override
        public void run() {

            m_tv_4_message.setText(String.valueOf(time));
            time--;
            if (time >= 0) {
                m_handler.postDelayed(this, 1000);
            } else {
                time = 10;
            }
        }
    }

    private class run_Nba implements Runnable {
        @Override
        public void run() {
            int number = ((int) (Math.random() * (maxnumber - minnumber + 1)) + minnumber);
            drawable_logo = array_logo.getDrawable(number);      //從陣列中 取得"第幾項"(且是哪種類型)
            m_tv_2_image.setBackground(drawable_logo);           //設定Background(填入 上列所選的)

            drawable_name = array_name.getText(number);
            m_tv_2_message.setText(drawable_name);
            m_handler.postDelayed(this, 100);
        }
    }                    //做完事情後 再叫自己做事情

    private class stop_nba implements Runnable {

        @Override
        public void run() {
            m_handler.removeCallbacks(go);
            m_tv_3_message.setText("開始比賽");

        }
    }


//    private class Task implements Runnable{
//        @Override
//        public void run() {
//            m_tv_frame_animation.stop();
//            m_tv_message.setText("時間到囉");}


}