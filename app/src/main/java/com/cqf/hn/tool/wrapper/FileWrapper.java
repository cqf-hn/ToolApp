package com.cqf.hn.tool.wrapper;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqf.hn.tool.R;
import com.cqf.hn.tool.base.BaseActivity;
import com.cqf.hn.tool.base.BaseRecyclerViewWrapper;
import com.cqf.hn.tool.event.ShowNewFileEvent;
import com.cqf.hn.tool.util.FileUtils;
import com.cqf.hn.tool.util.ToastUtil;
import com.cqf.hn.tool.view.ConfirmDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author cqf
 * @time 2018/8/19 0019  上午 11:28
 * @desc ${TODD}
 */
public class FileWrapper extends BaseRecyclerViewWrapper<File> {
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tvFileName)
    TextView tvFileName;
    @BindView(R.id.llFileInfo)
    LinearLayout llFileInfo;
    private File file;
    private String fileType;

    public FileWrapper(BaseActivity activity) {
        super(activity);
    }

    public FileWrapper(BaseActivity activity, ViewGroup parent) {
        super(activity, parent);
    }

    @Override
    protected int onItemLayoutId() {
        return R.layout.item_file;
    }

    @Override
    protected void onLayoutAfter() {
        super.onLayoutAfter();
        llFileInfo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (file.isDirectory()) {
                    new ConfirmDialog(_activity).render("是否扫描该文件下的所有文本？", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ToastUtil.showToast(file.getName());
                        }
                    }).show();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void bindData(int position) {
        file = getItem(position);
        tvFileName.setText(file.getName());
        if (file.isDirectory()) {
            iv.setImageResource(R.mipmap.ic_file);
            tvFileName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            llFileInfo.setSelected(false);
        } else {
            String fileName = file.getName();
            fileType = FileUtils.getFileType(fileName);
            if (FileUtils.FileType.TYPE_TXT.equals(fileType)) {
                if(adapter.hacContainsData(file)) {
                    tvFileName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_file_checked, 0);
                    llFileInfo.setSelected(true);
                } else {
                    tvFileName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_file_un_check, 0);
                    llFileInfo.setSelected(false);
                }
            } else {
                tvFileName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                llFileInfo.setSelected(false);
            }
            if (FileUtils.FileType.TYPE_TXT.equals(fileType)) {
                iv.setImageResource(R.mipmap.ic_file_txt);
            } else if (FileUtils.FileType.TYPE_MUSIC.equals(fileType)) {
                iv.setImageResource(R.mipmap.ic_file_mp3);
            } else if (FileUtils.FileType.TYPE_VIDEO.equals(fileType)) {
                iv.setImageResource(R.mipmap.ic_file_video);
            } else if (FileUtils.FileType.TYPE_WORD.equals(fileType)) {
                iv.setImageResource(R.mipmap.ic_file_word);
            } else if (FileUtils.FileType.TYPE_PDF.equals(fileType)) {
                iv.setImageResource(R.mipmap.ic_file_pdf);
            } else if (FileUtils.FileType.TYPE_ZIP.equals(fileType)) {
                iv.setImageResource(R.mipmap.ic_file_zip);
            } else {
                iv.setImageResource(R.mipmap.ic_unknow_file);
            }
        }
    }

    @OnClick({R.id.llFileInfo})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llFileInfo:
                if (FileUtils.FileType.TYPE_TXT.equals(fileType)) {
                    if (llFileInfo.isSelected()) {
                        adapter.removeCheckData(file);
                        tvFileName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_file_un_check, 0);
                        llFileInfo.setSelected(false);
                    } else {
                        adapter.addCheckData(file);
                        tvFileName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_file_checked, 0);
                        llFileInfo.setSelected(true);
                    }
                } else if (file.isDirectory()) {
                    ShowNewFileEvent.getInstance().send(file);
                }
                break;
        }
    }
}
