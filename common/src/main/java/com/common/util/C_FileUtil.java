package com.common.util;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileObserver;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by ricky on 2016/06/21.
 * <p/>
 * 文件相关的处理工具
 */
public class C_FileUtil {
    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File tempFile : fileList) {
                if (tempFile.isDirectory()) {
                    size = size + getFolderSize(tempFile);

                } else {
                    size = size + tempFile.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath 是否删除路径下的文件
     * @param filePath       文件路径
     */
    public void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (File tempFile : files) {
                        deleteFolderFile(tempFile.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        if (file.delete()) {
                            C_L.d("file", "delete success");
                        } else {
                            C_L.d("file", "delete failed");
                        }
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            if (file.delete()) {
                                C_L.d("listFile", "delete success");
                            } else {
                                C_L.d("listFile", "delete failed");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 转换文件大小 byte 转换 K M G
     *
     * @param size long 大小
     * @return x.x K / x.x M / x.x G
     */
    public static String formatSize(long size) {
        String fileSize;
        DecimalFormat df = new DecimalFormat("0.0");
        if (size < 1024) {
            return "0.0K";
        } else if (size < 1048576) {
            fileSize = df.format(size / 1024) + "K";
        } else if (size < 1073741824) {
            fileSize = df.format(size / 1048576) + "M";
        } else {
            fileSize = df.format(size / 1073741824) + "G";
        }
        return fileSize;
    }

    /**
     * 根据Uri获取文件的绝对路径
     */
    @SuppressLint("NewApi")
    public static String getPathByUri(final Context context, final Uri uri) {

        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    public static boolean deleteFile(Context context, String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File file = new File(path);
        boolean delSuccess = file.isFile() && file.delete();
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(file));
        context.sendBroadcast(scanIntent);
        return delSuccess;
    }

    public static void deleteAllFiles(String path, FileDeleteListener listener) {
        deleteAllFiles(new File(path), true, listener);
    }

    public static void deleteAllFileWithoutDirctory(String path, FileDeleteListener listener) {
        deleteAllFiles(new File(path), false, listener);
    }

    private static void deleteAllFiles(File root, boolean withDirectory, final FileDeleteListener listener) {
        final String rootPath = root.getAbsolutePath() + File.separator;
        FileObserver observer = new FileObserver(rootPath) {
            @Override
            public void onEvent(int event, String path) {
                switch (event) {
                    case DELETE:
                        if (listener != null) {
                            listener.onFileDeleting(getFileCount(rootPath));
                        }
                        break;
                }
            }
        };
        observer.startWatching();
        if (listener != null) {
            listener.onFileDeleteStart(getFileCount(rootPath));
        }
        deleteAllFiles(root, withDirectory);
        observer.stopWatching();
        if (listener != null) {
            listener.onFileDeleteEnd(getFileCount(rootPath));
        }
    }

    private static boolean deleteAllFiles(File root, boolean withDirectory) {
        File files[] = root.listFiles();
        boolean delSuccess = false;
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    if (withDirectory) {
                        deleteAllFiles(f, true);
                        delSuccess = f.delete();
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f, withDirectory);
                        delSuccess = f.delete();
                    }
                }
            }
        } else {
            delSuccess = root.delete();
        }
        return delSuccess;
    }

    public static long getFileCount(String path) {
        return getFileCount(new File(path));
    }

    //递归求取目录文件个数
    public static long getFileCount(File f) {
        long size = 0;
        File files[] = f.listFiles();
        size = files.length;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                size = size + getFileCount(files[i]);
                size--;
            }
        }
        return size;
    }

    public interface FileDeleteListener {

        void onFileDeleteStart(long count);

        void onFileDeleting(long count);

        void onFileDeleteEnd(long count);
    }
}
