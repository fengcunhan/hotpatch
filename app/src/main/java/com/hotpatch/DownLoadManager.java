package com.hotpatch;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Patch apk download
 * Created by renxuan on 15/7/29.
 */
class DownLoadManager {
    private static DownLoadManager INSTANCE=new DownLoadManager();
    private DownloadFile mDownloadFile;
    private DownLoadManager(){}

    public static DownLoadManager getInstance(){
        return INSTANCE;
    }
    public interface OnFileDownload{
        void fileDownload(String apkFilePath);
    }

    public DownloadFile getDownloadFile() {
        return mDownloadFile;
    }

    public void setDownloadFile(DownloadFile downloadFile) {
        mDownloadFile = downloadFile;
    }

    public interface DownloadFile{
        public void download(Context txt,String fileUrl,OnFileDownload download);
    }

}
