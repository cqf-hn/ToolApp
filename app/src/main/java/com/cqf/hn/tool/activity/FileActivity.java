package com.cqf.hn.tool.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqf.hn.tool.R;
import com.cqf.hn.tool.adapter.FileAdapter;
import com.cqf.hn.tool.base.BaseActivity;
import com.cqf.hn.tool.base.BaseApplication;
import com.cqf.hn.tool.event.ShowNewFileEvent;
import com.cqf.hn.tool.util.EventBusUtil;
import com.cqf.hn.tool.util.FileUtils;
import com.cqf.hn.tool.util.TDevice;
import com.cqf.hn.tool.view.TitleView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

/**
 * @author cqf
 * @time 2018/8/19 0019  上午 1:14
 * @desc ${TODD}
 */
public class FileActivity extends BaseActivity {
    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.llFileContent)
    LinearLayout llFileContent;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.scrollView)
    HorizontalScrollView scrollView;
    private ArrayList<File> files = new ArrayList<>();
    private LinearLayout.LayoutParams params;
    private LinearLayout.LayoutParams separatorParams;
    private int dp5;
    private int dp20;
    private int textColor;
    private FileAdapter adapter;

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
    protected void onLayoutBefore() {
        super.onLayoutBefore();
        dp5 = (int) TDevice.dpToPixel(5);
        dp20 = (int) TDevice.dpToPixel(20);
        textColor = Color.parseColor("#666666");
        params = new LinearLayout.LayoutParams((int) TDevice.dpToPixel(75), ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        separatorParams = new LinearLayout.LayoutParams(dp20, dp20);
        separatorParams.gravity = Gravity.CENTER_VERTICAL;
    }

    @Override
    protected int onLayoutId() {
        return R.layout.activity_file;
    }

    @Override
    protected void onLayoutAfter() {
        super.onLayoutAfter();
        recyclerView.setLayoutManager(new LinearLayoutManager(_activity));
        recyclerView.setPadding(0, 0, 0, 0);
        adapter = new FileAdapter(_activity);
        recyclerView.setAdapter(adapter);
        renderUI(FileUtils.getExternalStorageDirectory(), true);
    }

    @Override
    public void onBackPressed() {
        if (files.size() > 1) {
            files.remove(files.size() - 1);
            renderUI(files.get(files.size() - 1), false);
            return;
        }
        super.onBackPressed();

    }

    private void renderUI(File file, boolean isAddFile) {
        //创建头部目录
        llFileContent.removeAllViews();
        addFileTitle(file);
        adapter.setDataAndRefresh(Arrays.asList(file.listFiles()));
        if (isAddFile) {
            files.add(file);
        } else {
            files.remove(file);
        }
        BaseApplication.getInstance().post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(HorizontalScrollView.FOCUS_DOWN);
            }
        });
    }

    private void addFileTitle(File file) {
        if (llFileContent.getChildCount() != 0) {
            View view = new View(_activity);
            view.setLayoutParams(separatorParams);
            view.setBackgroundResource(R.mipmap.ic_file_title_arrow);
            view.setPadding(dp5, 0, dp5, 0);
            llFileContent.addView(view, 0);
        }
        TextView tv = createTextView(file);
        llFileContent.addView(tv, 0);
        if (file.getParentFile() != null) {
            addFileTitle(file.getParentFile());
        } else {
            TextView root = createTextView(file);
            llFileContent.addView(root, 0);
        }
    }

    @NonNull
    private TextView createTextView(File file) {
        final TextView tv = new TextView(_activity);
        tv.setTag(file);
        tv.setText(file.getName());
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(0, dp5, 0, dp5);
        tv.setTextColor(textColor);
        tv.setBackgroundResource(R.drawable.bg_file_title);
        tv.setLayoutParams(params);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File tvFile = (File) tv.getTag();
                if (!tvFile.equals(files.get(files.size() - 1))) {
                    renderUI(tvFile, true);
                }
            }
        });
        return tv;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ShowNewFileEvent event) {
        renderUI(event.getFile(), true);
    }
}
