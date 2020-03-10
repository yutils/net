package com.yujing.net.dowload;

import android.os.Handler;

import java.io.File;

/**
 * 网络权限
 * uses-permission android:name="android.permission.INTERNET"
 * 删除文件权限
 * uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"
 * 写入数据权限
 * uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
 * 获取根目录：String path = Environment.getExternalStorageDirectory() + "/";
 * 文件下载
 *
 * @author YuJing 2017年6月2日17:12:38
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class YDownloadAndroid extends YDownload {
    private Handler mHandler = new Handler();

    public YDownloadAndroid(String url, String path, String fileName) {
        super(url, path, fileName);
    }

    public YDownloadAndroid(String url, File file) {
        super(url, file);
    }

    // 快捷设置方法
    public static YDownload Download(String url, String path, String fileName, final YDownloadListener downloadListener) {
        final Handler mHandler = new Handler();
        YDownloadListener listener = (downloadedSize, fileSize, progress, isfail, file) -> mHandler.post(() -> downloadListener.response(downloadedSize, fileSize, progress, isfail, file));
        YDownloadAndroid yDownloadFile = new YDownloadAndroid(url, path, fileName);
        yDownloadFile.setDownloadListener(listener);
        yDownloadFile.setTimeOut(10000);// 设置连接超时
        yDownloadFile.setBackTime(100);// 设置回调时间
        yDownloadFile.start();// 开始下载，把下载线程扔入线程队列
        return yDownloadFile;
    }

    // 快捷设置方法
    public static YDownload Download(String url, File file, final YDownloadListener downloadListener) {
        final Handler mHandler = new Handler();
        YDownloadListener listener = (downloadedSize, fileSize, progress, isfail, file1) -> mHandler.post(() -> downloadListener.response(downloadedSize, fileSize, progress, isfail, file1));
        YDownloadAndroid yDownloadFile = new YDownloadAndroid(url, file);
        yDownloadFile.setDownloadListener(listener);
        yDownloadFile.setTimeOut(10000);// 设置连接超时
        yDownloadFile.setBackTime(100);// 设置回调时间
        yDownloadFile.start();// 开始下载，把下载线程扔入线程队列
        return yDownloadFile;
    }

    @Override
    public void setDownloadListener(final YDownloadListener downloadListener) {
        YDownloadListener listener = (downloadedSize, fileSize, progress, isfail, file) -> mHandler.post(() -> downloadListener.response(downloadedSize, fileSize, progress, isfail, file));
        super.setDownloadListener(listener);
    }
}
