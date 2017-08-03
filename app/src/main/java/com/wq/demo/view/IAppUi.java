package com.wq.demo.view;

import com.wq.demo.presenter.IController;
import com.wq.demo.presenter.Presenter;

/**
 * Created by qiangwang on 8/3/17.
 */

public interface IAppUi {
    void closeDrawers();
    void replaceContent(IController.ContentValue content);
    Presenter getPresenter();
}
