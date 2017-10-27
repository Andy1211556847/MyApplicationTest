package com.hxl.test.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.hxl.test.Application;

import java.io.File;

/**
 * <p/>
 * Created by Michael on 15/12/10.
 */
public class Path {
    private final static String kUseExternalStorageRootPath = "UseExternalStorageRootPath";

    /**
     * 获取 Assets 绝对路径，如果 subPath 不为空则将它合成到 Assets 绝对路径的后面。
     * 该方法只返回路径字符串并不检查路径是否存在。
     *
     * @param subPath 子路径，可以传 null。如果传空则返回 Assets 绝对路径（结尾不含 "/"）。
     * @return Assets 绝对路径
     */
    public static String getAssetAbsolutePath(String subPath) {
        String path = "file:///android_asset";

        if (!TextUtils.isEmpty(subPath)) {
            if (subPath.startsWith(File.separator)) {
                path = path.concat(subPath);
            }
            else {
                path = path.concat(File.separator).concat(subPath);
            }
        }

        return path;
    }

    /**
     * 获取存文件存储的根目录的全路径。
     * 应用安装后第一次调用方法时，如果存在外部存储器则该方法返回一个外部存储器路径，如果不存在则返回应用目录下的存储路径（安全沙盒中）。
     * 通常情部况即使没有 SDCard 系统也会模似一个外部存储器。
     * 该目录不在安全沙箱中，是用于保存安全性要求不高的的文件。
     *
     * @return 文件存储的根目录（结尾含 “/”）
     */
    public static synchronized String getStorageRootPath() {
        String path;
        boolean canRWExternalStorage = false;
        boolean useExternalStorage = true;

        if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {
            if (Application.shareInstance().currentApplication().getExternalFilesDir(null) != null){
                canRWExternalStorage = true;
            }
        }

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Application.shareInstance().currentApplication());
        if (sp.contains(kUseExternalStorageRootPath)) {
            useExternalStorage = sp.getBoolean(kUseExternalStorageRootPath, true);
        }

        if (useExternalStorage && canRWExternalStorage) {
            path = Application.shareInstance().currentApplication().getExternalFilesDir(null).getAbsolutePath();
            path = String.format("%s%s%s%s", path, File.separator, "hi7", File.separator);
        }
        else {
            path = Application.shareInstance().currentApplication().getFilesDir().getAbsolutePath();
            path.concat("/storage/");
            useExternalStorage = false;
        }

        //保存结果
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(kUseExternalStorageRootPath, useExternalStorage);
        edit.apply();

        return path;
    }

    /**
     * 获取存文件存储的根目录的全路径。
     * 应用安装后第一次调用方法时，如果存在外部存储器则该方法返回一个外部存储器路径，如果不存在则返回内部存储器路径。
     * 之后再次调用则返回相同结果。
     * 该目录不在安全沙箱中，是用于保存安全性要求不高的的文件。
     *
     * @param subPath 子路径
     * @return 全路径
     */
    public static synchronized String getStorageAbsolutePath(String subPath) {
        if (TextUtils.isEmpty(subPath)){
            return getStorageRootPath();
        }

        String root = getStorageRootPath();

        if (subPath.startsWith("/") || subPath.startsWith("\\")){
            subPath = subPath.substring(1);
        }

        return root.concat(subPath);
    }

    /**
     * 获取私有文件存储根目录的全路径（data/data/files/private_root/）
     * 该目录是在安全沙箱中的外部应用不可访问，对于限制访问的文件可以保存在此目录中。
     *
     * @return 文件存储的根目录（结尾含 “/”）
     */
    public static String getPrivateStorageRootPath() {
        String path = Application.shareInstance().currentApplication().getFilesDir().getAbsolutePath();
        return path.concat("/storage/");
    }

    /**
     * 获取私有文件存储根目录的全路径（data/data/files/private_root/）
     * 该目录是在安全沙箱中的外部应用不可访问，对于限制访问的文件可以保存在此目录中。
     *
     * @return 文件存储的根目录（结尾不含 “/”）
     */
    public static String getPrivateStorageRootPathNoSeparator() {
        String path = Application.shareInstance().currentApplication().getFilesDir().getAbsolutePath();
        return path.concat("/storage");
    }

    /**
     * 确保某一个目录存在，如果不存在则尝试创建这个目录。
     *
     * @param path 目录的全路径
     * @return 如果目录存在 1，如果创建成功返回 2，如果创建失败则返回 0。
     */
    public static int ensureDirectory(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }

        File file = new File(path);

        boolean isExist = file.exists();

        if (!isExist) {
            return file.mkdirs() ? 2 : 0;
        }
        else {
            return 1;
        }
    }
}
