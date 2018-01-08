package com.example.scitmaster.googlesfavorite;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hy.BaseContract;

import org.w3c.dom.Text;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//김하영
public class Fragment1 extends Fragment implements BaseContract.View{
    private Button mButton;
    private BaseContract.Presenter mPresenter;

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
        View root =  inflater.inflate(R.layout.fragment_fragment1, container, false);
        mButton = root.findViewById(R.id.btn_click);
        Log.v("test", "버튼작동: "+mButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.buttonClick();
            }
        });
        return root;
    }

    public void clickButton(View view){
        // 각자의 ip 주소 쓰기
        String addr = "http://10.10.11.202:8888/googles_favorite_v1/app_login?id=" + "a" + "&pwd=" + "a";

        // URL 연결 및 데이터 전송을 위한 기본 세팅
        URL url = null;
        try {
            url = new URL(addr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("dataType", "json");
            urlConnection.setConnectTimeout(30000);

            // Client 로부터 받아오는 데이터를 StringBuilder 로 받는다.
            StringBuilder responseFromClient = new StringBuilder();

            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStream = new InputStreamReader(urlConnection.getInputStream());

                int dataFromClient;
                // inputStream 에서 들어오는 데이터가 '-1(데이터가 없을때)'가 아니면 데이터를 읽어들인다.
                while ((dataFromClient = inputStream.read()) != -1) {
                    // 읽어들인 데이터를 String 화 시킨다.
                    responseFromClient.append((char) dataFromClient);
                }//while
            }//if
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setPresenter(BaseContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void ShowToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
