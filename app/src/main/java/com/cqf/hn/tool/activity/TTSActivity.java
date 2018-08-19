package com.cqf.hn.tool.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cqf.hn.tool.R;
import com.cqf.hn.tool.adapter.TTSAdapter;
import com.cqf.hn.tool.base.BaseActivity;
import com.cqf.hn.tool.base.HeaderAndFooterWrapper;
import com.cqf.hn.tool.event.SelectFileEvent;
import com.cqf.hn.tool.holder.TTSFootHolder;
import com.cqf.hn.tool.util.EventBusUtil;
import com.cqf.hn.tool.util.UIHelper;
import com.cqf.hn.tool.view.TitleView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * @author cqf
 * @time 2018/8/18 0018  下午 11:21
 * @desc ${TODD}
 */
public class TTSActivity extends BaseActivity {
    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private TTSAdapter adapter;
    private HeaderAndFooterWrapper wrapper;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtil.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }

    @Override
    public void onLayoutBefore() {
        adapter = new TTSAdapter(_activity);
        wrapper = new HeaderAndFooterWrapper(adapter);
        manager = new LinearLayoutManager(this);
    }

    @Override
    protected int onLayoutId() {
        return R.layout.activity_tts;
    }

    @Override
    protected void onLayoutAfter() {
        super.onLayoutAfter();
        titleView.setTitle("语音合成", true)
                .setRightTxt("合成设置").setRightTxtClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.showTTSSetting(_activity);
            }
        });
        TTSFootHolder holder = new TTSFootHolder(_activity);
        wrapper.addFootView(holder.getRoot());
        recyclerView.setLayoutManager(manager);
        recyclerView.setPadding(0, 0, 0, 0);
        recyclerView.setAdapter(wrapper);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SelectFileEvent event) {
        adapter.setDataAndRefresh(event.getData());
    }
}
