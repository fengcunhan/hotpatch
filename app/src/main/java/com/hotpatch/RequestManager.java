package com.hotpatch;

/**
 * Patch info request implement register
 * Created by renxuan on 15/7/29.
 */
class RequestManager {
    private static final RequestManager INSTANCE=new RequestManager();
    private RequestManager(){

    }

    public static RequestManager getInstance(){
        return INSTANCE;
    }

    private IPatchInfoRequest mIPatchInfoRequest;

    public  void reqeust(OnRequestCallBackListener listener){
        if(null!=mIPatchInfoRequest){
           mIPatchInfoRequest.getPatchInfo(listener,"");
        }
    }

    public IPatchInfoRequest getIPatchInfoRequest() {
        return mIPatchInfoRequest;
    }

    public void setIPatchInfoRequest(IPatchInfoRequest IPatchInfoRequest) {
        mIPatchInfoRequest = IPatchInfoRequest;
    }
}
