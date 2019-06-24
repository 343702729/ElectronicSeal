package com.nfc.electronicseal.api;

import com.nfc.electronicseal.api.util.RetrofitServiceUtil;

public class APIRetrofitUtil extends RetrofitServiceUtil {
    private  static APIRetrofitUtil mAPIWrapper;

    public APIRetrofitUtil(){
        super();
    }

    public static APIRetrofitUtil getInstance(){
        if(mAPIWrapper == null) {
            mAPIWrapper = new APIRetrofitUtil();
        }
        return mAPIWrapper;
    }

}
