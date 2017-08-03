package com.wq.demo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wq.demo.R;
import com.wq.demo.bean.DataServer;
import com.wq.demo.bean.Item;

public class GridAdapter extends BaseQuickAdapter<Item, BaseViewHolder> {
    public GridAdapter() {
        super(R.layout.item_grid, DataServer.getSampleData(99));
    }

    @Override
    protected void convert(BaseViewHolder helper, Item item) {
        helper.addOnClickListener(R.id.item_parent);
        helper.setImageResource(R.id.img, R.drawable.ic_default);
        helper.setText(R.id.text, item.getName());
    }
}
