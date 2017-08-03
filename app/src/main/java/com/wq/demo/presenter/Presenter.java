package com.wq.demo.presenter;

import com.wq.demo.model.Module;

/**
 * Created by qiangwang on 8/3/17.
 */

public class Presenter {
    private IController mController;
    private Module mModule;
    public Presenter(IController controller){
        mController = controller;
        mModule = new Module(mController.getContext());
    }

    public void setMenuValue(String value){
        mModule.setMenuValue(value);
    }

    public IController.ContentValue getContentValue(){
        return mModule.getContentValue();
    }
}
