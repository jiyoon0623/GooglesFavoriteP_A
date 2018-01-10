package com.example.scitmaster.googlesfavorite;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hy.BaseContract;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//김하영
public class Fragment1_1 extends Fragment implements BaseContract.View{
    private BaseContract.Presenter mPresenter;

    public Fragment1_1() {

    }

    public static Fragment1_1 newInstance() {
        return new Fragment1_1();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
       final View root =  inflater.inflate(R.layout.fragment_fragment1_1, container, false);
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

}//outer class
