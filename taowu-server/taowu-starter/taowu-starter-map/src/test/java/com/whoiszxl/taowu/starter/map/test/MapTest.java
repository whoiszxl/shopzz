package com.whoiszxl.taowu.starter.map.test;

import com.whoiszxl.taowu.starter.map.session.MapSession;
import com.whoiszxl.taowu.starter.map.session.MapSessionFactory;
import com.whoiszxl.taowu.starter.map.session.impl.DefaultMapSessionFactory;
import com.whoiszxl.taowu.starter.map.cqrs.request.StaticMapRequest;
import com.whoiszxl.taowu.starter.map.test.api.MyOssUploadApi;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.*;

@Slf4j
public class MapTest {

    private MapSession mapSession;

    @Before
    public void init() {
        MapSessionFactory factory = new DefaultMapSessionFactory();
        this.mapSession = factory.openSession();
    }



    /**
     * 测试将对应经纬度的静态地图保存到机器本地
     *
     * https://restapi.amap.com/v3/staticmap
     * ?location=112.96846,28.19180
     * &zoom=13
     * &size=480*320
     * &markers=mid,0xFFaaaa,C:112.96846,28.19180
     * &key=e0e344323487736d8dad831d70c31d8a
     */
    @Test
    public void getMap() throws FileNotFoundException {
        StaticMapRequest request = new StaticMapRequest();
        request.setMarkers("mid,0xFFaaaa,C:112.96846,28.19180");
        request.setKey("e0e344323487736d8dad831d70c31d8a");
        request.setSize("480*320");
        request.setZoom(13);
        request.setLocation("112.96846,28.19180");
        ResponseBody staticMap = mapSession.getStaticMap(request);
        log.info("图片结果{}", staticMap.contentType());
        saveImageToFile(staticMap);
    }

    private static void saveImageToFile(ResponseBody body) throws FileNotFoundException {
        try {
            File file = new File("static_map.png");
            InputStream inputStream = body.byteStream();
            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();
            System.out.println("Image saved to " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void uploadStaticMapToMyOss() throws FileNotFoundException {
        // 创建 Retrofit 实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:20004/api/file/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        // 创建 ApiService 实例
        MyOssUploadApi myOssUploadApi = retrofit.create(MyOssUploadApi.class);

        // 构建文件对象
        File imageFile = new File("static_map.png");
        log.info("文件信息:{}", imageFile.getName());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", imageFile.getName(), requestFile);

        // 构建其他参数
        Long id = null;
        String bizId = "1";
        Integer bizType = 1;

        // 发起上传图片请求
        ResponseBody responseBody = myOssUploadApi.uploadImage(filePart, id, bizId, bizType).blockingGet();
        log.info("上传结果:{}", responseBody);

    }


}
