package com.example.scitmaster.googlesfavorite;

import com.example.hy.BaseContract;

/**
 * Created by SCITMaster on 2018-01-08.
 */

public class PresenterImpl implements BaseContract.Presenter{
    private final BaseContract.View mBaseContractView;

    public PresenterImpl(BaseContract.View BaseContractView) {
        mBaseContractView = BaseContractView;
        mBaseContractView.setPresenter(this);
    }

    @Override
    public void buttonClick() {
        mBaseContractView.ShowToast("Success");
    }
}
