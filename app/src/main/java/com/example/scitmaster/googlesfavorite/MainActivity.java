package com.example.scitmaster.googlesfavorite;

import android.app.ProgressDialog;
import android.content.Intent;
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

import java.io.InputStream;
import java.lang.reflect.GenericArrayType;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button btn_ex;
    private Button btn_qna;
    private int tag;
    private Button btn_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //페이지 어댑터 연결
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        btn_main = (Button) findViewById(R.id.btn_main);
        btn_ex = (Button) findViewById(R.id.btn_ex);
        btn_qna = (Button) findViewById(R.id.btn_qna);

        btn_main.setOnClickListener(movePageListener);
        btn_main.setTag(0);
        btn_ex.setOnClickListener(movePageListener);
        btn_ex.setTag(1); //고유 이름표를 달아주는 setTag()
        btn_qna.setOnClickListener(movePageListener);
        btn_qna.setTag(2);
        Log.d("2번 태그 설정 되었는가",String.valueOf(btn_qna.getTag()));

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
                    //값이 들어오면 Fragment클래스를 열어준다
                    case 0:
                        return new Fragment0();
                    case 1:
//                        Log.d("case 0/tag0/FG1", String.valueOf(tag));
                        return new Fragment1();
                    case 2:
//                        Log.d("case 1/tag1/FG2", String.valueOf(tag));
                        return new Fragment2();
                    default:
                    Log.d("디폴트", "???");
                    return null;
                }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    private class DownThread extends Thread {
        private String addr;

        public DownThread(String addr){//생성자
            this.addr = addr;
        }

        @Override
        public void run() { //이 쓰레드에서 진행 될 로직을 작성
            super.run();

            try {//바이트로 이미지를 쪼개 받아서, inputStream에 넣어주는 것.
                // (InputStream is = new URI(addr).openStream()) 매개변수로 저렇게 쓰면 java1.6부터는 close()안해도 알아서 자원 반환해준다.
                InputStream is = new URL(addr).openStream();
                Message message = handler.obtainMessage();
                message.obj= "Hello Spring"; //오브젝트 형식이기 때문에 뭐든 다 넣을 수 있다
                handler.sendMessage(message); // 메시지 송신
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {//쓰레드간의 통신으 위한 교각
            super.handleMessage(msg);
        }
    };

    public void test(View view){
        Log.d("클릭 됨", "클릭");
        Intent intent = new Intent(MainActivity.this, Fragment0.class);
        Fragment0 fragment0 = new Fragment0();
        Bundle bundle = new Bundle();
        bundle.putString("데이터","from activity");
        Log.d("번들 안의 메시지", bundle.getString("데이터"));
        fragment0.setArguments(bundle);
        Log.d("프레그먼트로 보내진 메시지", fragment0.getArguments().getString("데이터"));
//        intent.putExtra("msg","메시지 보내기");
//        startActivity();
    }

}

