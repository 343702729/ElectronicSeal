package com.nfc.electronicseal.api.util;

import android.content.Context;
import android.text.TextUtils;

import com.nfc.electronicseal.api.APIRetrofitUtil;
import com.nfc.electronicseal.base.BaseInfoUpdate;
import com.nfc.electronicseal.response.ImageResponse;
import com.nfc.electronicseal.util.AppToast;
import com.nfc.electronicseal.util.PictureUtil;
import com.nfc.electronicseal.util.TLog;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PicUploadUtil {

    /**
     * 异常图片上传
     * @param context
     * @param picLocalPath
     */
    public void uploadExceptionDo(String authorization, int abnormalType, final RxAppCompatActivity context, String picLocalPath, final BaseInfoUpdate infoUpdate){
        if(TextUtils.isEmpty(picLocalPath))
            return;
        String temporaryPath = PictureUtil.getTemporaryPic(picLocalPath);
        final File file = new File(temporaryPath);
        if(file==null||!file.exists())
            return;
        RequestBody body=RequestBody.create(MediaType.parse("multipart/form-data"),file);//表单类型

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
        //3.调用MultipartBody.Builder的addFormDataPart()方法添加表单数据

//        builder.addFormDataPart("abnormalType", abnormalType + "");
        builder.addFormDataPart("file", file.getName(), body); //添加图片数据，body创建的请求体
//        builder.addPart(Headers.of("Authorization", authorization), body);
        //4.创建List<MultipartBody.Part> 集合，
        //  调用MultipartBody.Builder的build()方法会返回一个新创建的MultipartBody
        //  再调用MultipartBody的parts()方法返回MultipartBody.Part集合
        List<MultipartBody.Part> parts=builder.build().parts();

        //5.最后进行HTTP请求，传入parts即可
        APIRetrofitUtil.getInstance().uploadImageException(authorization, parts.get(0)).compose(new RxHelper<ImageResponse>("正在上传，请稍候").io_main(context))
                .subscribe(new RxSubscriber<ImageResponse>() {
                    @Override
                    public void _onNext(ImageResponse entity) {
                        file.delete();
                        if(entity.isSuccess()){
                            TLog.log("Come into upload pic success");
                            if(infoUpdate!=null)
                                infoUpdate.update(entity.getData());
                        }else {
                            if(entity!=null&&!TextUtils.isEmpty(entity.getMessage()))
                                TLog.log(entity.getMessage());
                        }
                    }

                    @Override
                    public void _onError(String msg) {
                        file.delete();
                        AppToast.showShortText(context, "图片上传失败");
                    }
                });
    }

    /**
     * 施封图片上传
     * @param context
     * @param picLocalPath
     */
    public void uploadSealDo(String authorization, final RxAppCompatActivity context, String picLocalPath, final BaseInfoUpdate infoUpdate){
        if(TextUtils.isEmpty(picLocalPath))
            return;
        String temporaryPath = PictureUtil.getTemporaryPic(picLocalPath);
        final File file = new File(temporaryPath);
        if(file==null||!file.exists())
            return;
        RequestBody body=RequestBody.create(MediaType.parse("multipart/form-data"),file);//表单类型

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
        //3.调用MultipartBody.Builder的addFormDataPart()方法添加表单数据

//        builder.addFormDataPart("abnormalType", abnormalType + "");
        builder.addFormDataPart("file", file.getName(), body); //添加图片数据，body创建的请求体
//        builder.addPart(Headers.of("Authorization", authorization), body);
        //4.创建List<MultipartBody.Part> 集合，
        //  调用MultipartBody.Builder的build()方法会返回一个新创建的MultipartBody
        //  再调用MultipartBody的parts()方法返回MultipartBody.Part集合
        List<MultipartBody.Part> parts=builder.build().parts();

        //5.最后进行HTTP请求，传入parts即可
        APIRetrofitUtil.getInstance().uploadImageSeal(authorization, parts.get(0)).compose(new RxHelper<ImageResponse>("正在上传，请稍候").io_main(context))
                .subscribe(new RxSubscriber<ImageResponse>() {
                    @Override
                    public void _onNext(ImageResponse entity) {
                        file.delete();
                        if(entity.isSuccess()){
                            TLog.log("Come into upload pic success");
                            if(infoUpdate!=null)
                                infoUpdate.update(entity.getData());
                        }else {
                            if(entity!=null&&!TextUtils.isEmpty(entity.getMessage()))
                                TLog.log(entity.getMessage());
                        }
                    }

                    @Override
                    public void _onError(String msg) {
                        file.delete();
                        AppToast.showShortText(context, "图片上传失败");
                    }
                });
    }

    /**
     * 巡检图片上传
     * @param context
     * @param picLocalPath
     */
    public void uploadInspectDo(String authorization, final RxAppCompatActivity context, String picLocalPath, final BaseInfoUpdate infoUpdate){
        if(TextUtils.isEmpty(picLocalPath))
            return;
        String temporaryPath = PictureUtil.getTemporaryPic(picLocalPath);
        final File file = new File(temporaryPath);
        if(file==null||!file.exists())
            return;
        RequestBody body=RequestBody.create(MediaType.parse("multipart/form-data"),file);//表单类型

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
        //3.调用MultipartBody.Builder的addFormDataPart()方法添加表单数据

//        builder.addFormDataPart("abnormalType", abnormalType + "");
        builder.addFormDataPart("file", file.getName(), body); //添加图片数据，body创建的请求体
//        builder.addPart(Headers.of("Authorization", authorization), body);
        //4.创建List<MultipartBody.Part> 集合，
        //  调用MultipartBody.Builder的build()方法会返回一个新创建的MultipartBody
        //  再调用MultipartBody的parts()方法返回MultipartBody.Part集合
        List<MultipartBody.Part> parts=builder.build().parts();

        //5.最后进行HTTP请求，传入parts即可
        APIRetrofitUtil.getInstance().uploadImageInspect(authorization, parts.get(0)).compose(new RxHelper<ImageResponse>("正在上传，请稍候").io_main(context))
                .subscribe(new RxSubscriber<ImageResponse>() {
                    @Override
                    public void _onNext(ImageResponse entity) {
                        file.delete();
                        if(entity.isSuccess()){
                            TLog.log("Come into upload pic success");
                            if(infoUpdate!=null)
                                infoUpdate.update(entity.getData());
                        }else {
                            if(entity!=null&&!TextUtils.isEmpty(entity.getMessage()))
                                TLog.log(entity.getMessage());
                        }
                    }

                    @Override
                    public void _onError(String msg) {
                        file.delete();
                        AppToast.showShortText(context, "图片上传失败");
                    }
                });
    }

    /**
     * 拆封图片上传
     * @param context
     * @param picLocalPath
     */
    public void uploadUnsealDo(String authorization, final RxAppCompatActivity context, String picLocalPath, final BaseInfoUpdate infoUpdate){
        if(TextUtils.isEmpty(picLocalPath))
            return;
        String temporaryPath = PictureUtil.getTemporaryPic(picLocalPath);
        final File file = new File(temporaryPath);
        if(file==null||!file.exists())
            return;
        RequestBody body=RequestBody.create(MediaType.parse("multipart/form-data"),file);//表单类型

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
        //3.调用MultipartBody.Builder的addFormDataPart()方法添加表单数据

//        builder.addFormDataPart("abnormalType", abnormalType + "");
        builder.addFormDataPart("file", file.getName(), body); //添加图片数据，body创建的请求体
//        builder.addPart(Headers.of("Authorization", authorization), body);
        //4.创建List<MultipartBody.Part> 集合，
        //  调用MultipartBody.Builder的build()方法会返回一个新创建的MultipartBody
        //  再调用MultipartBody的parts()方法返回MultipartBody.Part集合
        List<MultipartBody.Part> parts=builder.build().parts();

        //5.最后进行HTTP请求，传入parts即可
        APIRetrofitUtil.getInstance().uploadImageUnseal(authorization, parts.get(0)).compose(new RxHelper<ImageResponse>("正在上传，请稍候").io_main(context))
                .subscribe(new RxSubscriber<ImageResponse>() {
                    @Override
                    public void _onNext(ImageResponse entity) {
                        file.delete();
                        if(entity.isSuccess()){
                            TLog.log("Come into upload pic success");
                            if(infoUpdate!=null)
                                infoUpdate.update(entity.getData());
                        }else {
                            if(entity!=null&&!TextUtils.isEmpty(entity.getMessage()))
                                TLog.log(entity.getMessage());
                        }
                    }

                    @Override
                    public void _onError(String msg) {
                        file.delete();
                        AppToast.showShortText(context, "图片上传失败");
                    }
                });
    }

    /**
     * 用户头像图片上传
     * @param context
     */
    public void uploadUserHeadDo(String authorization, final RxAppCompatActivity context, File  file, final BaseInfoUpdate infoUpdate){
        if(file==null||!file.exists())
            return;
        RequestBody body=RequestBody.create(MediaType.parse("multipart/form-data"),file);//表单类型

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
        //3.调用MultipartBody.Builder的addFormDataPart()方法添加表单数据

        builder.addFormDataPart("file", file.getName(), body); //添加图片数据，body创建的请求体
//        builder.addPart(Headers.of("Authorization", authorization), body);
        //4.创建List<MultipartBody.Part> 集合，
        //  调用MultipartBody.Builder的build()方法会返回一个新创建的MultipartBody
        //  再调用MultipartBody的parts()方法返回MultipartBody.Part集合
        List<MultipartBody.Part> parts=builder.build().parts();

        //5.最后进行HTTP请求，传入parts即可
        APIRetrofitUtil.getInstance().uploadImageUserHead(authorization, parts.get(0)).compose(new RxHelper<ImageResponse>("正在上传，请稍候").io_main(context))
                .subscribe(new RxSubscriber<ImageResponse>() {
                    @Override
                    public void _onNext(ImageResponse entity) {
//                        file.delete();
                        if(entity.isSuccess()){
                            TLog.log("Come into upload pic success");
                            if(infoUpdate!=null)
                                infoUpdate.update(entity.getData());
                        }else {
                            if(entity!=null&&!TextUtils.isEmpty(entity.getMessage()))
                                TLog.log(entity.getMessage());
                        }
                    }

                    @Override
                    public void _onError(String msg) {
//                        file.delete();
                        AppToast.showShortText(context, "图片上传失败");
                    }
                });
    }
}
