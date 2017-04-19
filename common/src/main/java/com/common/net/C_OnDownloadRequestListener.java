package com.common.net;

/**
 * Created by ricky on 2016/05/24.
 * <p>
 * 通用的下载文件回调接口
 */
public abstract class C_OnDownloadRequestListener<T> extends C_OnSimpleNetRequestListener<T> {

    /**
     * 下载文件请求成功
     *
     * @param progress 已经下载的文件大小
     * @param total    文件总大小
     * @param done     是否完成下载
     */
    public abstract void onDownloading(long progress, long total, boolean done);

    @Override
    public void onRequestSuccess(int what, T data, boolean isRefresh) {

    }

    @Override
    public void onRequestFailed(int what, Throwable t, String url) {

    }
}
