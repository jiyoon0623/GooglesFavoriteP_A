package com.example.scitmaster.googlesfavorite;

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

import java.lang.reflect.GenericArrayType;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button btn_ex;
    private Button btn_qna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //페이지 어댑터 연결
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        btn_ex = (Button) findViewById(R.id.btn_ex);
        btn_qna = (Button) findViewById(R.id.btn_qna);
        /*
vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
vp.setCurrentItem(0);

btn_first.setOnClickListener(movePageListener);
btn_first.setTag(0);
btn_second.setOnClickListener(movePageListener);
btn_second.setTag(1);
btn_third.setOnClickListener(movePageListener);
btn_third.setTag(2);

*/
        //ViewPage의 페이지를 관리하는 어댑터
//        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        //앱이 실행됐을 때, 첫번째 페이지로 초기화 하는 부분
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    //값이 들어오면 Fragment클래스를 열어준다
                    case 0:
                        Log.d("1번태그", "???");
                        return new Fragment1();
                    case 1:
                        Log.d("2번태그", "???");
                        return new Fragment2();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
//        viewPager.setCurrentItem(0);

        btn_ex.setOnClickListener(movePageListener);
        btn_ex.setTag(0); //고유 이름표를 달아주는 setTag()
        btn_qna.setOnClickListener(movePageListener);
        btn_qna.setTag(1);
        Log.d("태그 설정 되었는가",String.valueOf(btn_ex.getTag()));

    }
        View.OnClickListener movePageListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();
                Log.d("현재 태그",String.valueOf(tag));
                viewPager.setCurrentItem(tag);

            }
        };


//       private class PagerAdapter extends FragmentStatePagerAdapter {
//
//           public PagerAdapter(FragmentManager fm) {
//               super(fm);
//               Log.d("들어오는가2","???");
//           }
//
//           @Override
//           public Fragment getItem(int position) {
//               switch (position){
//                  //값이 들어오면 Fragment클래스를 열어준다
//                  case 0:
//                      Log.d("들어오는가1","???");
//                      return new Fragment1();
//                  case 1:
//                      return new Fragment2();
//                  default:
//                      return null;
//              }
//           }
//
//
//          @Override
//          public int getCount() {
//              //viewPager에 들어가는 페이지 갯수
//              return 3;
//          }
//      }

}

