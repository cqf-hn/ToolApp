package com.cqf.hn.tool.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecyclerAdapter<T> extends
        RecyclerView.Adapter<BaseRecyclerViewWrapper.BaseRecyclerViewHolder> implements android.widget.Filterable {


    protected BaseActivity _activity;
    /*数据集合*/
    protected ArrayList<T> mData = new ArrayList<>();
    private ArrayList<T> checkData = new ArrayList<>();
    private Filter filter;

    public BaseRecyclerAdapter(BaseActivity activity) {
        this._activity = activity;
    }

    /**
     * 返回BaseViewHolder的子类
     */
    protected abstract BaseRecyclerViewWrapper<T> createViewWrapper(BaseActivity _activity, ViewGroup parent, int type);


    @Override
    public BaseRecyclerViewWrapper.BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final BaseRecyclerViewWrapper<T> wrapper = createViewWrapper(_activity, parent, viewType);
        wrapper.init(this, mData, viewType);
        return wrapper.getViewHolder();
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewWrapper.BaseRecyclerViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setDataAndRefresh(List<T> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
        notifyItemRangeChanged(0, getItemCount());
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public void addData(List<T> dataBeen) {
        mData.addAll(dataBeen);
    }

    public ArrayList<T> getData() {
        return mData;
    }

    public ArrayList<T> getCheckData() {
        return checkData;
    }

    public void addCheckData(T data) {
        if (!hacContainsData(data)) {
            checkData.add(data);
        }
    }

    public void removeCheckData(T data) {
        if (hacContainsData(data)) {
            checkData.remove(data);
        }
    }

    public boolean hacContainsData(T data) {
        return checkData.contains(data);
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}