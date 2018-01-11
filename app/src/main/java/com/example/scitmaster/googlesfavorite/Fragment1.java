package com.example.scitmaster.googlesfavorite;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ActionBarContextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hy.BaseContract;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//김하영
public class Fragment1 extends Fragment implements BaseContract.View{
    private Button mButton;
    private BaseContract.Presenter mPresenter;
    private EditText pw_input, id_input;
    private String id_string, pw_string;

    public Fragment1() {

    }

    public static Fragment1 newInstance() {
        return new Fragment1();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
       final View root =  inflater.inflate(R.layout.fragment_fragment1, container, false);

        mButton = root.findViewById(R.id.btn_click);
        Log.v("test", "버튼작동: "+mButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //view 의 id 와 pw 를 가지고 온다
                id_input = root.findViewById(R.id.id_input);
                pw_input = root.findViewById(R.id.pw_input);

                id_string = id_input.getText().toString();
                pw_string = pw_input.getText().toString();

                SendThread sendThread = new SendThread();
                sendThread.start();
            }
        });
        return root;
    }


    @Override
    public void setPresenter(BaseContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void ShowToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    public void isNotMember(View view){
        Toast.makeText(getActivity(), "홈페이지에서 가입해주세요.", Toast.LENGTH_SHORT).show();
    }//isNotMember

    //Thread 내부 클래스
    class SendThread extends Thread{

        // 각자의 ip 주소 쓰기
        String addr = "http://10.10.11.202:8888/googles_favorite_v1/app_login?id=" + id_string + "&pwd=" + pw_string;

        @Override
        public void run() {
            try {
                // URL 연결 및 데이터 전송을 위한 기본 세팅
                URL url = new URL(addr);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("dataType", "json");
                urlConnection.setConnectTimeout(30000);

                // Client 로부터 받아오는 데이터를 StringBuilder 로 받는다.
                StringBuilder responseFromClient = new StringBuilder();

                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStreamReader inputStream = new InputStreamReader(urlConnection.getInputStream());

                    int dataFromClient;
                    // inputStream 에서 들어오는 데이터가 '-1(데이터가 없을때)'가 아니면 데이터를 읽어들인다.
                    while((dataFromClient=inputStream.read()) != -1){
                        // 읽어들인 데이터를 String 화 시킨다.
                        responseFromClient.append((char)dataFromClient);
                    }//while

                    if(responseFromClient.toString().equals("Success")){
                        // 아이디 및 비번이 일치했을 때
                        //intent.putExtra("loginUser", id_string);
                        mHandler.sendEmptyMessage(1);

                    }else if(responseFromClient.toString().equals("Fail")){
                        // 아이디 및 비번이 일치하지 않을 때
                        mHandler.sendEmptyMessage(0);
                        return;
                    }//inner if
                    //finish();
                }//if
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//run
    }//sendThread - inner Class

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    Toast.makeText(getActivity(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    id_input.setText(null);
                    pw_input.setText(null);
                    break;
                case 1:
                    Toast.makeText(getActivity(), "성공", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("Login", "true");

                    startActivityForResult(intent, 1);
                    //startActivity(intent);
                    break;
            }
        }
    };//handler
}//outer class
