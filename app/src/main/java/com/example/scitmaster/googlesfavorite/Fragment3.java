package com.example.scitmaster.googlesfavorite;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class Fragment3 extends Fragment {

    private EditText et_domain;
    private EditText et_cc;
    private EditText et_key;
    private Button btn_start;
    private String jsonText;
    private String domain;
    private String cc;
    private String key;
    private TextView tv_result;


    // 이지윤
    public Fragment3() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);
        Log.d("*onCreateView", "Fragment3 in");
        et_domain = (EditText) view.findViewById(R.id.et_domain);
        et_cc = (EditText) view.findViewById(R.id.et_cc);
        et_key = (EditText) view.findViewById(R.id.et_key);
        btn_start = (Button) view.findViewById(R.id.btn_start);
        tv_result = (TextView) view.findViewById(R.id.tv_result);


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                domain = et_domain.getText().toString();
                cc = et_cc.getText().toString();
                key = et_key.getText().toString();
                Log.d("Fragment3 OnClick", "Fragment3 OnClick의 도메인 값은"+domain);
                Fragment3.AnotherThread thread = new Fragment3.AnotherThread("http://10.10.15.144:8888/goose/android", domain, cc, key);
                thread.start();

//                Log.d("버튼 클릭 후 jsonText",jsonText);
//
//                if (jsonText == null) return;
//                JSONArray jsonArray = null;
//                JSONObject jsonObject = null;
//                try {
//                    jsonArray = new JSONArray(jsonText);
//                    StringBuilder sb2 = new StringBuilder();
//                    for (int i=0; i<jsonArray.length(); i++){
//                        jsonObject = jsonArray.getJSONObject(i);
//                        sb2.append("번호");//key와value
//                        sb2.append(jsonObject.getString("index"));
//                        sb2.append("이름");
//                        sb2.append(jsonObject.getString("name"));
//                        sb2.append("\n");
//                    }
//                    tv_result.setText(sb2.toString());//textView에 붙이기
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.d("클릭 종료", "종료");

//                tv_test.setVisibility(View.VISIBLE);
            }
        });
        return  view;
    }
             private class AnotherThread extends Thread{//innerclass로 Thread 상속 - run() 오버라이드
                String addr;
                String domain;
                String cc;
                String key;

                public AnotherThread(String addr, String domain, String cc, String key){
                    this.addr = addr;
                    this.domain = domain;
                    this.cc = cc;
                    this. key = key;

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


                            Log.d("Fragment3_test2", "1");
                            Log.d("안에 들어가 있는 값", domain+" ");

                            OutputStream outputStream = httpURLConnection.getOutputStream();
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                            outputStream.write((domain+"/").getBytes("utf-8"));
//                            outputStream.write((cc+"/").getBytes("utf-8"));
//                            outputStream.write(key.getBytes("utf-8"));
                            writer.write(domain+","+cc+","+key+",");
                            writer.flush();
                            writer.close();
//                            outputStream.write("안드로이드로부터의 메시지".getBytes("utf-8"));//getOutputStream의 write메소드 불러서, 문자열이랑 세팅정보 해서 넣음.
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

                                JSONObject jsonObject = new JSONObject(jsonText);
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("수정해야 할 iFrame의 개수: ");//key와value
                                sb2.append(jsonObject.getString("iframeList_size"));
                                sb2.append("\n");
                                sb2.append("수정해야 할 VIDEO 목록: ");
                                sb2.append(jsonObject.getString("videoList"));
                                sb2.append("\n");
                                sb2.append("수정해야 할 Youtube 목록: ");
                                sb2.append(jsonObject.getString("youtubeList"));
                                sb2.append("\n");
                                Log.d("붙일 텍스트: ", sb2.toString());
                                tv_result.setText(sb2.toString());//textView에 붙이기

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

//                    tv_test.setText(jsonText);
                }
            };

    }

