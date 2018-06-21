package com.whoiszxl.xl.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.whoiszxl.xl.app.Starter;
import com.whoiszxl.xl.net.callback.IRequest;
import com.whoiszxl.xl.net.callback.ISuccess;
import com.whoiszxl.xl.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * @author whoiszxl
 * 异步下载
 * AsyncTask是一种轻量级的异步任务类，它可以在线程池中执行后台任务，然后把执行的进度和最终结果传递给主线程，并在主线程更新UI。
 * 从实现上来说，AsyncTask封装了Thread和Handler，通过AsyncTask可以很方便执行后台任务以及在主线程中访问UI。
 */
final class SaveFileTask extends AsyncTask<Object, Void, File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    SaveFileTask(IRequest REQUEST, ISuccess SUCCESS) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
    }

    /**
     * 在后台线程池中执行，可能会花费很长时间
     * @param params
     * @return
     */
    @Override
    protected File doInBackground(Object... params) {
        //获取到下载目录和文件扩展名
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        //获取到下载的body
        final ResponseBody body = (ResponseBody) params[2];
        //获取到name
        final String name = (String) params[3];
        //将需要下载的转换成字节流
        final InputStream is = body.byteStream();
        //不存在下载目录就搞个默认目录
        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "down_loads";
        }
        //后缀没有就置空
        if (extension == null || extension.equals("")) {
            extension = "";
        }
        //名字不存在就直接传扩展名大写进去
        if (name == null) {
            return FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(), extension);
        } else {
            //不然的话就直接传name吧
            return FileUtil.writeToDisk(is, downloadDir, name);
        }
    }

    /**
     * 在UI线程上执行。后台进程的结果被传递到这一步作为参数。
     * @param file
     */
    @Override
    protected void onPostExecute(File file) {
        //将doInBackground下载的文件传到主线程，然后回调，将文件的路径返回
        super.onPostExecute(file);
        if (SUCCESS != null) {
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
        //执行安装
        autoInstallApk(file);
    }

    /**
     * 自动安装apk文件
     * @param file
     */
    private void autoInstallApk(File file) {
        //如果文件的后缀为apk的话
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            //创建一个意图，添加标志和动作，还有数据
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Starter.getApplicationContext().startActivity(install);
        }
    }
}
