package com.example.scitmaster.googlesfavorite;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.GenericArrayType;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button btn_ex;
    private Button btn_qna;
    private int tag;
    private Button btn_main;
    public Fragment0 fragment0;
    private Button btn_sample;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = getIntent();

        if (intent != null && intent.getStringExtra("Login") != null){
            if ( intent.getStringExtra("Login").equals("true")) {
                this.setTitle("Google's Favorite                          Signed In");
            }
        }

        SharedPreferences shared = getSharedPreferences("autoLogin_checkbox", Activity.MODE_PRIVATE);
        SharedPreferences.Editor e_shared = shared.edit();
        e_shared.putString("autoLogin_checked", null);
        e_shared.commit();



        //페이지 어댑터 연결
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        btn_main = (Button) findViewById(R.id.btn_main);
        btn_ex = (Button) findViewById(R.id.btn_ex);
        btn_qna = (Button) findViewById(R.id.btn_qna);
        btn_sample = (Button) findViewById(R.id.btn_sample);

        btn_main.setOnClickListener(movePageListener);
        btn_main.setTag(0);
        btn_ex.setOnClickListener(movePageListener);
        btn_ex.setTag(1); //고유 이름표를 달아주는 setTag()
        btn_qna.setOnClickListener(movePageListener);
        btn_qna.setTag(2);
        btn_sample.setOnClickListener(movePageListener);
        btn_sample.setTag(3);

        android.app.FragmentManager fragmentManager =getFragmentManager();
        //ViewPage의 페이지를 관리하는 어댑터
//        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        //앱이 실행됐을 때, 첫번째 페이지로 초기화 하는 부분
        viewPager.setAdapter(new pagerAdapter(getSupportFragmentManager()));


    }
        View.OnClickListener movePageListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tag = (int) view.getTag();
                Log.d("현재 태그",String.valueOf(tag));
//                int num = viewPager.getCurrentItem();
                viewPager.setCurrentItem(tag);
            }
        };




    private class pagerAdapter extends FragmentStatePagerAdapter
    {

        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            position = tag;
            switch (position) {
                    //값이 들어오면 Fragment 클래스를 열어준다
                    case 0:
                        return new Fragment0();
                    case 1:
                        if (intent != null && intent.getStringExtra("Login") != null) {
                            if (intent.getStringExtra("Login").equals("true")) {
                                return new Fragment1_1();
                            }
                        }
                        return new Fragment1();

                    case 2:
                        return new Fragment2();
                case 3:
                    return new Fragment3();
                    default:
                    Log.d("Default", "IN");
                    return null;
                }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}

