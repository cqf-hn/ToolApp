package com.cqf.hn.tool.activity;

import com.cqf.hn.tool.base.BaseActivity;
import com.cqf.hn.tool.R;
import com.cqf.hn.tool.util.TDevice;

/**
 * @author cqf
 * @time 2018/5/15 0015  上午 11:22
 * @desc ${TODD}
 */
public class NextActivity extends BaseActivity {
    @Override
    protected int onLayoutId() {
        return R.layout.activity_next;
    }

    @Override
    protected void onLayoutAfter() {
        TDevice.getInstance().addActivity(this);
    }
}
