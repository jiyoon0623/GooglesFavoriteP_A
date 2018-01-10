package com.example.scitmaster.googlesfavorite;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.w3c.dom.Text;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//이지윤
public class Fragment0 extends Fragment {

    private Button btn_request;
    private TextView tv_test;
    private String msg;
    private String aaa;
    private String jsonText;
//    private VideoView videoView;
    private WebView webView;

    public Fragment0() {
    //기본 생성자
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
        Log.d("*onCreateView","fragment0 in");
        View view = inflater.inflate(R.layout.fragment_fragment0,container,false);

        btn_request = (Button) view.findViewById(R.id.btn_request) ;
        tv_test = view.findViewById(R.id.tv_test0);

        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Fragment0 OnClick","Fragment0 OnClick");
                AnotherThread thread = new AnotherThread("http://10.10.15.144:8888/goose/jsontest");
                thread.start();
                tv_test.setVisibility(View.VISIBLE);
            }
        });

        webView = (WebView)view.findViewById(R.id.webView);
        webView.setWebViewClient(new MyWebClient());
        WebSettings set = webView.getSettings();
        set.setJavaScriptEnabled(true); // 자바스크립트를 쓸 수 있게
        set.setBuiltInZoomControls(true); // 손가락으로 화면을 확대하고 축소하게끔
        webView.loadUrl("http://10.0.2.2:8888/goose/");

        return view;
    }

    public void test(View view) { // Request 버튼을 눌러 서버에 요청을 보냄. 서버에 보낼 요청정보랑, 서버에 접속할 서버 주소, 프로젝트 주소

        Log.d("btn_request", "1");
        AnotherThread thread = new AnotherThread("http://10.10.15.144:8888/goose/jsontest");
        thread.start();
    }

    private class AnotherThread extends Thread{//innerclasas로 Thread 상속 - run() 오버라이드
        String addr;
        public AnotherThread(String addr){
            this.addr = addr;
        }
        @Override
        public void run() {
            super.run();

            String result = test2(addr); // 주소 값을 넣어주면 응답 값을 받음
            Message message = handler.obtainMessage();
            message.obj = result;
            handler.sendMessage(message);
        }

        private String test2(String addr){ //주소값
            StringBuilder sb = new StringBuilder();//요청을 보낼 서버를 주소를 텍스트로 받음
            try {
                URL url = new URL(addr);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                if (httpURLConnection != null){
                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setRequestMethod("POST");//POST로 서버에 요청을 보내겠다
                    //여기까지가 서버의 요청을 보내기 위한 준비 작업

                    Log.d("test2", "1");
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    outputStream.write("안드로이드로부터의 메시지".getBytes("utf-8"));//getOutputStream의 write메소드 불러서, 문자열이랑 세팅정보 해서 넣음.
                    outputStream.close(); //자원(메모리) 반환
                    //여기까지가 보내는 작업

                    //서버가 보낸 응답 코드가 getResponseCode, 서버가 요청을 잘 받았을 때 보내주는 모드가 http_ok(상수)
                    Log.d("test2", "2");
                    if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){//응답을 잘 보냈을 때
                        Log.d("test2", "3");
                        InputStreamReader is = new InputStreamReader(httpURLConnection.getInputStream());

                        int ch;
                        while ((ch = is.read()) != -1){//다 읽었으면 read메소드가 -1을 반환시키기 때문에 빠져 나옴
                            sb.append((char)ch);//캐릭터 형태로 append해서 반환시킨다.
                        }
                        is.close();
                        jsonText = sb.toString();//서버가 전송해 준 문자정보는 jsonText에 들어감
                        Log.d("jsonText",jsonText);
                    }else Log.d("test2", "4");

                    httpURLConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return sb.toString();//test 메소드 종료
        }
    }

    //Thread와 Thread끼리는 handler로 소통
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            tv_test.setText(jsonText);
        }
    };

        class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            view.loadUrl(request.getUrl().toString());
            return super.shouldOverrideUrlLoading(view, request);
        }
    }
}
