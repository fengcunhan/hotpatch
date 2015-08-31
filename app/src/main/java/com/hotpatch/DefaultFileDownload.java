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
 * Created by renxuan on 15/8/31.
 */
public class DefaultFileDownload implements DownLoadManager.DownloadFile {
    @Override
    public void download(Context ctx,String fileUrl,DownLoadManager.OnFileDownload download) {
        DownloadTask task=new DownloadTask(fileUrl,ctx,download);
        task.execute("");
    }

    public final class DownloadTask extends AsyncTask<String,Integer,String> {
        private String fileUrl;
        private Context ctx;
        private DownLoadManager.OnFileDownload download;
        public DownloadTask(String fileUrl,Context context,DownLoadManager.OnFileDownload download){
            this.fileUrl=fileUrl;
            ctx=context;
            this.download=download;
        }
        @Override
        protected String doInBackground(String... params) {
            InputStream inStream = null;
            FileOutputStream fs = null;
            String filePath=Utils.getCacheApkFilePath(ctx, fileUrl);
            try {
                URL url = new URL(fileUrl);
                URLConnection urlConnection = url.openConnection();
                inStream = urlConnection.getInputStream();
                File file=new File(filePath);
                if(!file.exists()){
                    file.createNewFile();
                }
                fs = new FileOutputStream(file);
                int byteread = 0;
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != inStream) {
                    try {
                        inStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (null != fs) {
                    try {
                        fs.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return filePath;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(null!=download){
                download.fileDownload(s);
            }
        }
    }
}
