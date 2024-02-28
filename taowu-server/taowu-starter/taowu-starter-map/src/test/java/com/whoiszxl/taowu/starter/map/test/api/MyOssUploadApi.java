package com.whoiszxl.taowu.starter.map.test.api;

import io.reactivex.Single;
import retrofit2.Call;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface MyOssUploadApi {

    @Multipart
    @POST("upload")
    Single<ResponseBody> uploadImage(
            @Part MultipartBody.Part file,
            @Query("id") Long id,
            @Query("bizId") String bizId,
            @Query("bizType") Integer bizType
    );
}
