package com.wq.demo.model;

import com.wq.demo.presenter.IController;

/**
 * Created by qiangwang on 8/3/17.
 */

public interface IModule {
    IController.MenuValue getMenuValue();
    void setMenuValue(String menu);
    IController.ContentValue getContentValue();
    void setContentValue(String content);
}
