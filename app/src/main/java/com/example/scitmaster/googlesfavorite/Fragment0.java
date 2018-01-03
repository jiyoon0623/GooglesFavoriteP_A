package com.example.scitmaster.googlesfavorite;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class Fragment0 extends Fragment {

    public Fragment0() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            msg = getArguments().getString("데이터");
//            Log.d("받아왔는지 확인", msg);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment0,container,false);
        Button btn_test = (Button) view.findViewById(R.id.btn_test);

        return inflater.inflate(R.layout.fragment_fragment0, container, false);
    }

    public void getMsg(View view){
        TextView tv_test = view.findViewById(R.id.tv_test);
//        String msg = getActivity().getIntent().getStringExtra("msg");
        String msg = getArguments().getString("데이터");
        Log.d("받아왔는지 확인", msg);
        tv_test.setText(msg);

    }
}
