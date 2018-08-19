package com.cqf.hn.tool.adapter;

import android.view.ViewGroup;

import com.cqf.hn.tool.base.BaseActivity;
import com.cqf.hn.tool.base.BaseRecyclerAdapter;
import com.cqf.hn.tool.base.BaseRecyclerViewWrapper;
import com.cqf.hn.tool.wrapper.TTSWrapper;

import java.io.File;

/**
 * @author cqf
 * @time 2018/8/19 0019  上午 12:34
 * @desc ${TODD}
 */
public class TTSAdapter extends BaseRecyclerAdapter<File> {


    public TTSAdapter(BaseActivity activity) {
        super(activity);
    }

    @Override
    protected BaseRecyclerViewWrapper createViewWrapper(BaseActivity _activity, ViewGroup parent, int type) {
        return new TTSWrapper(_activity, parent);
    }
}
