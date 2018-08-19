package com.cqf.hn.tool.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.cqf.hn.tool.base.BaseActivity;
import com.cqf.hn.tool.R;
import com.cqf.hn.tool.util.TDevice;
import com.cqf.hn.tool.util.UIHelper;
import com.cqf.hn.tool.view.TitleView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tvOpenWifiHot)
    TextView tvOpenWifiHot;
    @BindView(R.id.tvShowNext)
    TextView tvShowNext;
    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.tvTTS)
    TextView tvTTS;

    @Override
    protected int onLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onLayoutAfter() {
        super.onLayoutAfter();
        titleView.setTitle("主页");
    }

    @OnClick({R.id.tvOpenWifiHot, R.id.tvShowNext,R.id.tvTTS})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvOpenWifiHot:
                TDevice.openWifiHot(true);
                break;
            case R.id.tvShowNext:
                startActivity(new Intent(this, NextActivity.class));
                break;
            case R.id.tvTTS:
                UIHelper.showTTS(_activity);
                break;
        }
    }

}
