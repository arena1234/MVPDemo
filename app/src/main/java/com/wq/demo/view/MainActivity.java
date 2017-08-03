package com.wq.demo.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wq.demo.R;
import com.wq.demo.adapter.GridAdapter;
import com.wq.demo.adapter.ListAdapter;
import com.wq.demo.bean.Item;
import com.wq.demo.presenter.IController;
import com.wq.demo.presenter.Presenter;
import com.wq.demo.widget.CustomDrawerLayout;
import com.wq.demo.widget.CustomToast;

public class MainActivity extends BaseActivity implements IAppUi, IController {
    private ActionBarDrawerToggle mDrawerToggle;
    private CustomDrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private FrameLayout mContentLayout;
    private RecyclerView mRecyclerView;
    private BaseQuickAdapter mAdapter;
    private ContentValue mContentValue = ContentValue.NULL;
    private Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (CustomDrawerLayout) findViewById(R.id.main_drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mContentLayout = (FrameLayout) findViewById(R.id.layout_content);

        mPresenter = new Presenter(this);
        initMenu();
        replaceContent(ContentValue.RecyclerViewList);
    }

    private void initMenu() {
        mToolbar.setTitle(R.string.toolbar_title);                  // 设置Toolbar标题
        mToolbar.setTitleTextColor(getColor(R.color.colorTitle));   // 设置标题颜色
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);           // 设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setCustomDrawerListener(mDrawerToggle);

        getFragmentManager().
                beginTransaction().
                replace(R.id.layout_menu, new MenuFragment(this)).
                commit();
    }

    @Override
    public void replaceContent(ContentValue content) {
        if (content == mContentValue) return;
        if (mRecyclerView == null) {
            mRecyclerView = new RecyclerView(this);
            mRecyclerView.setHasFixedSize(true);
        }
        if (mAdapter != null) {
            mAdapter.setOnItemChildClickListener(null);
            mAdapter = null;
        }
        mContentLayout.removeAllViews();
        switch (content) {
            case RecyclerViewOption:
                getFragmentManager().
                        beginTransaction().
                        replace(R.id.layout_content, new OptionRecyclerViewFragment(this)).
                        commit();
                break;
            case RecyclerViewGrid:
                mAdapter = new GridAdapter();
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                mAdapter.isFirstOnly(true);
                mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        Item item = (Item) adapter.getItem(position);
                        switch (view.getId()) {
                            case R.id.item_parent:
                                String content = "Grid:" + item.getName();
                                CustomToast.show(MainActivity.this, content);
                                break;
                        }
                    }
                });
                mRecyclerView.setAdapter(mAdapter);
                mContentLayout.addView(mRecyclerView);
                break;
            case RecyclerViewList:
                mAdapter = new ListAdapter();
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
                mAdapter.isFirstOnly(true);
                mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        Item item = (Item) adapter.getItem(position);
                        switch (view.getId()) {
                            case R.id.item_parent:
                                String content = "List:" + item.getName();
                                CustomToast.show(MainActivity.this, content);
                                break;
                        }
                    }
                });
                mRecyclerView.setAdapter(mAdapter);
                mContentLayout.addView(mRecyclerView);
                break;
        }

        mContentValue = content;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void closeDrawers() {
        mDrawerLayout.closeDrawers();
    }

    @Override
    public Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.quick_menu_share:
                CustomToast.show(this, "You click Share.");
                break;
            case R.id.quick_menu_search:
                CustomToast.show(this, "You click Search.");
                break;
            case R.id.quick_menu_settings:
                replaceContent(ContentValue.RecyclerViewOption);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
