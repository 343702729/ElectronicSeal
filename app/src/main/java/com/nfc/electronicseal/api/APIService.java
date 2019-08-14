package com.nfc.electronicseal.api;

import com.nfc.electronicseal.bean.ExceptionInfoBean;
import com.nfc.electronicseal.bean.LoginBean;
import com.nfc.electronicseal.bean.ExceptionItemsBean;
import com.nfc.electronicseal.response.ExceptionInfoResponse;
import com.nfc.electronicseal.response.ExceptionItemsResponse;
import com.nfc.electronicseal.response.LoginResponse;
import com.nfc.electronicseal.response.MenusResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface APIService {
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("nfc-feign/app/loginApi/login")
    Observable<LoginResponse> getLoginData(@Body LoginBean bean);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("nfc-feign/app/functionApi/getAppMenus")
    Observable<MenusResponse> getMenusData(@Header("Authorization") String authorization);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("nfc-feign/app/nfcAbnormalApi/getAbnormalDeclareList")
    Observable<ExceptionItemsResponse> getExceptionItemsData(@Header("Authorization") String authorization, @Body ExceptionItemsBean bean);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("nfc-feign/app/nfcAbnormalApi/getAbnormalDetailInfo")
    Observable<ExceptionInfoResponse> getExceptionInfoData(@Header("Authorization") String authorization, @Body ExceptionInfoBean bean);

}
