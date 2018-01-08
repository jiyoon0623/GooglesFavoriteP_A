package com.example.hy;

/**
 * Created by SCITMaster on 2018-01-08.
 */

public interface BaseContract {
    interface View extends BaseView<Presenter>{

    }
    interface Presenter extends BasePresenter{
        void buttonClick();
    }
}
