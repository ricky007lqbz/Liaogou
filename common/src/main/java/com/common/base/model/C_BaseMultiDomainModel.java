package com.common.base.model;

import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.common.Common;
import com.common.bean.C_BaseBean;
import com.common.bean.C_DataWrapper;
import com.common.net.C_OnNetRequestListener;
import com.common.util.C_ArrayUtil;
import com.common.util.C_L;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ricky on 2016/05/30.
 * <p/>
 * 通过网络请求获取原始数据，并返回View所需要的数据（domain）的model基类
 *
 * @param <T> 网络请求返回的原始数据
 * @param <P> View所需要的数据
 */
public abstract class C_BaseMultiDomainModel<T, P extends C_BaseBean> extends C_BaseListDomainModel<T, P> implements C_OnNetRequestListener<C_DataWrapper<List<P>>> {

    protected SparseArray<List<P>> mapData;
    protected C_OnNetRequestListener<C_DataWrapper<List<P>>> listener;
    protected SparseIntArray requestQueue;
    protected List<String> listParams;
    protected Map<String, String> mapParams;

    protected C_BaseListDomainModel getFirstModel() {
        return null;
    }

    protected C_BaseListDomainModel getSecondModel() {
        return null;
    }

    protected C_BaseListDomainModel getThirdModel() {
        return null;
    }

    /**
     * 获取其他请求的总数
     */
    protected int getOtherRequestCount() {
        int count = 0;
        if (getFirstModel() != null) {
            count++;
        }
        if (getSecondModel() != null) {
            count++;
        }
        if (getThirdModel() != null) {
            count++;
        }
        return count;
    }


    /**
     * 统一请求其他请求
     *
     * @param isRefresh 是否刷新，刷新才请求
     */
    protected void requestOtherData(boolean isRefresh) {
        if (isRefresh) {
            requestData(Common.net.FIRST_QUEUE, getFirstModel());
            requestData(Common.net.SECOND_QUEUE, getSecondModel());
            requestData(Common.net.THIRD_QUEUE, getThirdModel());
        }
    }

    /**
     * 请求其他请求
     *
     * @param what  队列
     * @param model model
     */
    protected void requestData(int what, C_BaseListDomainModel model) {
        if (model != null) {
            if (!C_ArrayUtil.isEmpty(model.getListParams(null))) {
                model.requestData(model.getListParams(null), what, true, this);
            } else if (!C_ArrayUtil.isEmpty(model.getMapParams(null))) {
                model.requestData(model.getMapParams(null), what, true, this);
            } else {
                model.requestData(what, true, this);
            }
        }
    }

    /**
     * 刷新其他次请求
     */
    public void refreshOtherData() {
        if (listener != null) {
            requestOtherData(true);
            mapData.remove(Common.net.FIRST_QUEUE);
            mapData.remove(Common.net.SECOND_QUEUE);
            mapData.remove(Common.net.THIRD_QUEUE);
        }
    }

    @Override
    public void requestData(int what, boolean isRefresh, C_OnNetRequestListener<C_DataWrapper<List<P>>> listener) {
        this.listener = listener;
        requestOtherData(isRefresh);
        super.requestData(what, isRefresh, this);
    }

    /**
     * 刷新主请求
     */
    public void refreshMainData() {
        reRequestMainData(true);
    }

    /**
     * 刷新主请求
     */
    public void refreshMainData(List<String> listParams) {
        reRequestMainData(true, listParams);
    }

    /**
     * 刷新主请求
     */
    public void refreshMainData(Map<String, String> mapParams) {
        reRequestMainData(true, mapParams);
    }


    /**
     * 重新请求主请求
     */
    public void reRequestMainData(boolean isRefresh) {
        if (listener != null) {
            if (!C_ArrayUtil.isEmpty(listParams)) {
                super.requestData(listParams, Common.net.SINGLE_QUEUE, isRefresh, this);
            } else if (!C_ArrayUtil.isEmpty(mapParams)) {
                super.requestData(mapParams, Common.net.SINGLE_QUEUE, isRefresh, this);
            } else {
                super.requestData(Common.net.SINGLE_QUEUE, isRefresh, this);
            }
        }
    }

    /**
     * 重新请求主请求
     */
    public void reRequestMainData(boolean isRefresh, List<String> listParams) {
        if (listener != null) {
            if (!C_ArrayUtil.isEmpty(listParams)) {
                super.requestData(listParams, Common.net.SINGLE_QUEUE, isRefresh, this);
            } else {
                super.requestData(Common.net.SINGLE_QUEUE, isRefresh, this);
            }
        }
    }

    /**
     * 重新请求主请求
     */
    public void reRequestMainData(boolean isRefresh, Map<String, String> mapParams) {
        if (listener != null) {
            if (!C_ArrayUtil.isEmpty(mapParams)) {
                super.requestData(mapParams, Common.net.SINGLE_QUEUE, isRefresh, this);
            } else {
                super.requestData(Common.net.SINGLE_QUEUE, isRefresh, this);
            }
        }
    }

    /**
     * 重新请求主请求
     */
    public void reRequestMainData(List<String> listParams, boolean isRefresh) {
        if (listener != null) {
            super.requestData(listParams, Common.net.SINGLE_QUEUE, isRefresh, this);
        }
    }

    /**
     * 重新请求主请求
     */
    public void reRequestMainData(Map<String, String> mapParams, boolean isRefresh) {
        if (listener != null) {
            super.requestData(mapParams, Common.net.SINGLE_QUEUE, isRefresh, this);
        }
    }

    @Override
    public void requestData(List<String> params, int what, boolean isRefresh, C_OnNetRequestListener<C_DataWrapper<List<P>>> listener) {
        this.listener = listener;
        this.listParams = params;
        requestOtherData(isRefresh);
        super.requestData(params, what, isRefresh, this);
    }

    @Override
    public void requestData(Map<String, String> params, int what, boolean isRefresh, C_OnNetRequestListener<C_DataWrapper<List<P>>> listener) {
        this.listener = listener;
        this.mapParams = params;
        requestOtherData(isRefresh);
        super.requestData(params, what, isRefresh, this);
    }

    @Override
    public void onRequestStart(int what) {
        if (requestQueue == null) {
            requestQueue = new SparseIntArray();
        }
        if (requestQueue.size() == getOtherRequestCount() + 1) {
            listener.onRequestStart(what);
        }
        if (requestQueue.size() == 0) {
            listener.onRequestStart(what);
        }
    }

    @Override
    public void onRequestFinish(int what) {
        requestQueue.put(what, what);
        if (requestQueue.size() == getOtherRequestCount() + 1) {
            listener.onRequestFinish(what);
            requestQueue.clear();
        }
    }

    @Override
    public void onRequestSuccess(int what, C_DataWrapper<List<P>> data, boolean isRefresh) {
        if (mapData == null) {
            mapData = new SparseArray<>();
        }
        mapData.put(what, data.getData());
        listener.onRequestSuccess(what, mergeData(data, isRefresh), isRefresh);
    }

    /**
     * 合并数据
     */
    private C_DataWrapper<List<P>> mergeData(C_DataWrapper<List<P>> data, boolean isRefresh) {
        if (data == null) {
            return null;
        }
        if (!C_ArrayUtil.isEmpty(mapData)) {
            List<P> temp = new ArrayList<>();
            if (isRefresh) { //只有刷新才添加其他的数据
                if (getFirstModel() != null) {
                    addMapItemToList(temp, Common.net.FIRST_QUEUE);
                }
                if (getSecondModel() != null) {
                    addMapItemToList(temp, Common.net.SECOND_QUEUE);
                }
                if (getThirdModel() != null) {
                    addMapItemToList(temp, Common.net.THIRD_QUEUE);
                }
            }
            addMapItemToList(temp, Common.net.SINGLE_QUEUE);
            data.setData(temp);
        }
        return data;
    }

    /**
     * 把保存的其他请求合并到list里
     */
    private void addMapItemToList(List<P> temp, int what) {
        if (temp == null) {
            return;
        }
        if (!C_ArrayUtil.isEmpty(mapData) && !C_ArrayUtil.isEmpty(mapData.get(what))) {
            temp.addAll(mapData.get(what));
        }
    }

    @Override
    public void onRequestFailed(int what, Throwable t, String url) {
        if (t != null && !TextUtils.isEmpty(t.getMessage())) {
            C_L.e(t.getMessage());
        }
        listener.onRequestFailed(what, t, url);
    }
}
