package com.cqf.hn.tool.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqf.hn.tool.R;
import com.cqf.hn.tool.base.BaseHolder;
import com.cqf.hn.tool.util.UIHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author cqf
 * @time 2018/8/19 0019  上午 1:06
 * @desc ${TODD}
 */
public class TTSFootHolder extends BaseHolder {

    @BindView(R.id.tvShowFile)
    TextView tvShowFile;

    public TTSFootHolder(Context context) {
        super(context);
    }

    public TTSFootHolder(Context context, ViewGroup parent) {
        super(context, parent);
    }

    @Override
    public int onLayoutId() {
        return R.layout.view_tts_foot;
    }

    @OnClick({R.id.tvShowFile})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvShowFile:
                UIHelper.showFile(_activity);
                break;
        }
    }

    protected ViewGroup.LayoutParams onGetLayoutParams() {
        return new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
