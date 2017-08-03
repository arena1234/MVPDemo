package com.wq.demo.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.wq.demo.R;
import com.wq.demo.bean.Keys;
import com.wq.demo.presenter.IController;

/**
 * Created by qiangwang on 8/3/17.
 */

public class Module implements IModule {
    private IController.ContentValue mContentValue;
    private IController.MenuValue mMenuValue;
    private SharedPreferences mSharedPreferences;
    private Context mContext;

    public Module(Context context) {
        mContext = context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        getMenuValue();
        getContentValue();
    }

    private IController.MenuValue getMenuValue(String menu) {
        if (menu.equals(Keys.VALUE_MENU_RECYCLER)) {
            mMenuValue = IController.MenuValue.RecyclerView;
        } else if (menu.equals(Keys.VALUE_MENU_TAB)) {
            mMenuValue = IController.MenuValue.TabPage;
        } else {
            mMenuValue = IController.MenuValue.NULL;
        }

        return mMenuValue;
    }

    @Override
    public IController.MenuValue getMenuValue() {
        String menu = mSharedPreferences.getString(Keys.KEY_MENU, Keys.VALUE_MENU_RECYCLER);
        return getMenuValue(menu);
    }

    @Override
    public void setMenuValue(String menu) {
        SharedPreferences.Editor e = mSharedPreferences.edit();
        e.putString(Keys.KEY_MENU, menu);
        e.apply();
    }

    @Override
    public IController.ContentValue getContentValue() {
        switch (getMenuValue()) {
            case RecyclerView:
                String content = mSharedPreferences.getString(Keys.KEY_OPTION_RECYCLE, null);
                if(content != null) {
                    if (content.equals(mContext.getString(R.string.option_recycle_list))) {
                        mContentValue = IController.ContentValue.RecyclerViewList;
                    } else if (content.equals(mContext.getString(R.string.option_recycle_grid))) {
                        mContentValue = IController.ContentValue.RecyclerViewGrid;
                    } else if (content.equals(mContext.getString(R.string.option_recycle_flow))) {
                        mContentValue = IController.ContentValue.RecyclerViewFlow;
                    } else {
                        mContentValue = IController.ContentValue.NULL;
                    }
                } else {
                    mContentValue = IController.ContentValue.NULL;
                }
                break;
            default:
                mContentValue = IController.ContentValue.NULL;
                break;
        }
        return mContentValue;
    }

    @Override
    public void setContentValue(String content) {

    }
}
