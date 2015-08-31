package com.hotpatch;

import android.app.DownloadManager;
import android.content.Context;
import android.text.TextUtils;

import com.taobao.android.dexposed.DexposedBridge;
import com.taobao.patch.PatchMain;

import java.io.File;

/**
 * HotpatchManager,do the follows：
 * 1.Get Patch infomation.(md5 and downlaod url of patch apk)
 * 2.Patch apk validate(md5 check and compare the signa
 * 3.load Patch包。
 * Created by renxuan on 15/7/29.
 *
 * Email:fengcunhan@126.com
 */
public class HotpatchManager {
    private static HotpatchManager INSTANCE=new HotpatchManager();

    public void setFileDownloader(DownLoadManager.DownloadFile fileDownloader){
        DownLoadManager.getInstance().setDownloadFile(fileDownloader);
    }

    private HotpatchManager() {

    }

    public static HotpatchManager getInstance(IPatchInfoRequest request){
        RequestManager.getInstance().setIPatchInfoRequest(request);
        return INSTANCE;
    }

    public boolean init(final Context ctx) {
        boolean isSupport = DexposedBridge.canDexposed(ctx);
        if (isSupport) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    check(ctx);
                }
            }).start();
        }
        return isSupport;
    }

    private  void check(final Context ctx) {
        RequestManager manager = RequestManager.getInstance();
        manager.setIPatchInfoRequest(RequestManager.getInstance().getIPatchInfoRequest());
        manager.reqeust(new OnRequestCallBackListener() {
            @Override
            public void onRequest(final PatchInfo info) {
                if (null != info) {
                    String apkPath = Utils.getCacheApkFilePath(ctx, info.apkFileUrl);
                    File file = new File(apkPath);
                    if (file.exists()) {
                        loadPath(info, ctx, apkPath);
                    } else {
                        DownLoadManager.DownloadFile downloadFile=DownLoadManager.getInstance().getDownloadFile();
                        if(null==downloadFile){
                            downloadFile=new DefaultFileDownload();
                            DownLoadManager.getInstance().setDownloadFile(downloadFile);
                        }
                        downloadFile.download(ctx, info.apkFileUrl, new DownLoadManager.OnFileDownload() {
                            @Override
                            public void fileDownload(String apkFilePath) {
                                loadPath(info, ctx, apkFilePath);
                            }
                        });
                    }
                }
            }
        });
    }

    private  void loadPath(PatchInfo info, Context ctx, String apkFilePath) {
        if (Utils.isSignEqual(ctx, apkFilePath) && TextUtils.equals(info.apkMd5, Utils.getMd5ByFile(new File(apkFilePath)))) {
            PatchMain.load(ctx, apkFilePath, null);
        }
    }

}
