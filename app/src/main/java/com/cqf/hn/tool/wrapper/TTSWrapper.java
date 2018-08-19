package com.cqf.hn.tool.wrapper;

import android.view.ViewGroup;

import com.cqf.hn.tool.R;
import com.cqf.hn.tool.base.BaseActivity;
import com.cqf.hn.tool.base.BaseRecyclerViewWrapper;

import java.io.File;

/**
 * @author cqf
 * @time 2018/8/19 0019  上午 12:36
 * @desc ${TODD}
 */
public class TTSWrapper extends BaseRecyclerViewWrapper<File> {
    public TTSWrapper(BaseActivity activity) {
        super(activity);
    }

    public TTSWrapper(BaseActivity activity, ViewGroup parent) {
        super(activity, parent);
    }

    @Override
    protected int onItemLayoutId() {
        return R.layout.item_tts;
    }

    @Override
    public void bindData(int position) {

    }
}
