package com.wq.demo.presenter;

import android.content.Context;

/**
 * Created by qiangwang on 7/25/17.
 */

public interface IController {
    enum ContentValue{
        RecyclerViewList,
        RecyclerViewGrid,
        RecyclerViewFlow,
        RecyclerViewOption,
        NULL,
    }
    enum MenuValue{
        RecyclerView,
        TabPage,
        NULL,
    }
    void replaceContent(ContentValue content);
    Context getContext();
}
