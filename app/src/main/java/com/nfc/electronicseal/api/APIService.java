package com.nfc.electronicseal.api;

import com.nfc.electronicseal.bean.ChipCheckBean;
import com.nfc.electronicseal.bean.ExceptionAddBean;
import com.nfc.electronicseal.bean.ExceptionInfoBean;
import com.nfc.electronicseal.bean.HeadImgUpdateBean;
import com.nfc.electronicseal.bean.LoginBean;
import com.nfc.electronicseal.bean.ExceptionItemsBean;
import com.nfc.electronicseal.bean.PasswordUpdateBean;
import com.nfc.electronicseal.bean.SealBean;
import com.nfc.electronicseal.bean.SealInfoBean;
import com.nfc.electronicseal.bean.SearchRecordBean;
import com.nfc.electronicseal.response.ChipCheckResponse;
import com.nfc.electronicseal.response.ExceptionInfoResponse;
import com.nfc.electronicseal.response.ExceptionItemsResponse;
import com.nfc.electronicseal.response.ImageResponse;
import com.nfc.electronicseal.response.LoginResponse;
import com.nfc.electronicseal.response.MenusResponse;
import com.nfc.electronicseal.response.Response;
import com.nfc.electronicseal.response.SealInfoResponse;
import com.nfc.electronicseal.response.SealItemResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("nfc-feign/app/nfcAbnormalApi/abnormalDeclare")
    Observable<Response> exceptionAddDo(@Header("Authorization") String authorization, @Body ExceptionAddBean bean);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("nfc-feign/app/employeeApi/updatePwd")
    Observable<Response> passwordUpdateDo(@Header("Authorization") String authorization, @Body PasswordUpdateBean bean);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("nfc-feign/app/employeeApi/updateEmployeeInfo")
    Observable<Response> userHeadImgUpdateDo(@Header("Authorization") String authorization, @Body HeadImgUpdateBean bean);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("nfc-feign/app/nfcChipCheckApi/nfcChipCheck")
    Observable<ChipCheckResponse> chipCheckDo(@Header("Authorization") String authorization, @Body ChipCheckBean bean);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("nfc-feign/app/nfcSealApi/sealElectronic")
    Observable<Response> sealSubmitDo(@Header("Authorization") String authorization, @Body SealBean bean);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("nfc-feign/app/nfcEleRecordApi/getEleRecordList")
    Observable<SealItemResponse> getSearchRecordsData(@Header("Authorization") String authorization, @Body SearchRecordBean bean);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("nfc-feign/app/nfcEleRecordApi/getEleRecordDetailInfo")
    Observable<SealInfoResponse> getSealInfoData(@Header("Authorization") String authorization, @Body SealInfoBean bean);

    @Multipart
    @POST("nfc-feign/app/fileUploadApi/uploadAbnormalImage")
    Observable<ImageResponse> uploadImageException(@Header("Authorization") String authorization, @Part MultipartBody.Part part);

    @Multipart
    @POST("nfc-feign/app/fileUploadApi/uploadSealImage ")
    Observable<ImageResponse> uploadImageSeal(@Header("Authorization") String authorization, @Part MultipartBody.Part part);

    @Multipart
    @POST("nfc-feign/app/fileUploadApi/uploadEmployeeImage")
    Observable<ImageResponse> uploadImageUserHead(@Header("Authorization") String authorization, @Part MultipartBody.Part part);
}
