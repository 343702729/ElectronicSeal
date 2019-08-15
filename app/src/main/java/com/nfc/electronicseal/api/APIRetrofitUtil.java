package com.nfc.electronicseal.api;

import com.nfc.electronicseal.api.util.RetrofitServiceUtil;
import com.nfc.electronicseal.bean.ExceptionAddBean;
import com.nfc.electronicseal.bean.ExceptionInfoBean;
import com.nfc.electronicseal.bean.LoginBean;
import com.nfc.electronicseal.bean.ExceptionItemsBean;
import com.nfc.electronicseal.bean.PasswordUpdateBean;
import com.nfc.electronicseal.response.ExceptionInfoResponse;
import com.nfc.electronicseal.response.ExceptionItemsResponse;
import com.nfc.electronicseal.response.ImageResponse;
import com.nfc.electronicseal.response.LoginResponse;
import com.nfc.electronicseal.response.MenusResponse;
import com.nfc.electronicseal.response.Response;

import java.util.List;

import okhttp3.MultipartBody;
import rx.Observable;

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

    /**
     * 用户登录
     * @param bean
     * @return
     */
    public Observable<LoginResponse> getLoginData(LoginBean bean){
        return getAPIService().getLoginData(bean);
    }

    /**
     * 获取菜单
     * @param authorization
     * @return
     */
    public Observable<MenusResponse> getMenusData(String authorization){
        return getAPIService().getMenusData(authorization);
    }

    /**
     * 查询异常申报列表
     * @param authorization
     * @param bean
     * @return
     */
    public Observable<ExceptionItemsResponse> getExceptionItemsData(String authorization, ExceptionItemsBean bean){
        return  getAPIService().getExceptionItemsData(authorization, bean);
    }

    /**
     * 查询异常申报详细信息
     * @param authorization
     * @param bean
     * @return
     */
    public Observable<ExceptionInfoResponse> getExceptionInfoData(String authorization, ExceptionInfoBean bean){
        return getAPIService().getExceptionInfoData(authorization, bean);
    }

    /**
     * 异常申报
     * @param authorization
     * @param bean
     * @return
     */
    public Observable<Response> exceptionAddDo(String authorization, ExceptionAddBean bean){
        return getAPIService().exceptionAddDo(authorization, bean);
    }

    /**
     * 修改密码
     * @param authorization
     * @param bean
     * @return
     */
    public Observable<Response> passwordUpdateDo(String authorization, PasswordUpdateBean bean){
        return getAPIService().passwordUpdateDo(authorization, bean);
    }

    /**
     * 异常图片上传接口
     * @param authorization
     * @param part
     * @return
     */
    public Observable<ImageResponse> uploadImageException(String authorization, MultipartBody.Part part){
        return getAPIService().uploadImageException(authorization, part);
    }

    /**
     * 异常图片上传接口
     * @param authorization
     * @param part
     * @return
     */
    public Observable<ImageResponse> uploadImageUserHead(String authorization, MultipartBody.Part part){
        return getAPIService().uploadImageException(authorization, part);
    }
}