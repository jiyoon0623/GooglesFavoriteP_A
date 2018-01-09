package com.example.hy;

/**
 * Created by SCITMaster on 2018-01-08.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
    void ShowToast(String text);
}//BaseView
