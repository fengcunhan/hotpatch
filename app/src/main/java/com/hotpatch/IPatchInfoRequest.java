package com.hotpatch;

/**
 * Patch信息请求接口，有一个默认实现  @see DefaultPatchInfoRequest
 * Created by renxuan on 15/7/29.
 */
public interface IPatchInfoRequest {
     void getPatchInfo(RequestManager.OnRequestCallBackListener listener, String currentVersion, Object... objects);
}
