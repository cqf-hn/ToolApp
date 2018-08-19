package com.cqf.hn.tool.adapter;

import android.view.ViewGroup;

import com.cqf.hn.tool.base.BaseActivity;
import com.cqf.hn.tool.base.BaseRecyclerAdapter;
import com.cqf.hn.tool.base.BaseRecyclerViewWrapper;
import com.cqf.hn.tool.wrapper.FileWrapper;

import java.io.File;

/**
 * @author cqf
 * @time 2018/8/19 0019  上午 11:27
 * @desc ${TODD}
 */
public class FileAdapter extends BaseRecyclerAdapter<File> {
    public FileAdapter(BaseActivity activity) {
        super(activity);
    }

    @Override
    protected BaseRecyclerViewWrapper<File> createViewWrapper(BaseActivity _activity, ViewGroup parent, int type) {
        return new FileWrapper(_activity, parent);
    }


}
