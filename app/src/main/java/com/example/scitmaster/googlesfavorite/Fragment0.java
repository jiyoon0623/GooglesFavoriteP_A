package com.example.scitmaster.googlesfavorite;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

//이지윤
public class Fragment0 extends Fragment {

    private Button btn_test;
    private TextView tv_test;
    private View view;
    private String msg;
    private String aaa;

    public Fragment0() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            msg = getArguments().getString("데이터");
            Log.d("getArguments가 null이 아니면", msg);
        }else {
            Log.d("null","받아온 데이터가 없습니다.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("*onCreateView","in");


        btn_test = (Button) view.findViewById(R.id.btn_test0) ;
        tv_test = view.findViewById(R.id.tv_test0);

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("* Fragment0's btn_test","in");
                tv_test.setText(msg);
            }
        });
        return inflater.inflate(R.layout.fragment_fragment0,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void getMsg(View view){
        view = this.view;
        Log.d("getMsg","들어옴");
        msg = getArguments().getString("데이터");
        Log.d("getMsg의 msg 확인", msg);
        onStart();
    }
}
