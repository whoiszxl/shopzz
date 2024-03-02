package com.whoiszxl.zhipin.im;

import com.whoiszxl.zhipin.im.constants.FieldConstants;
import com.whoiszxl.zhipin.im.protocol.Command;
import com.whoiszxl.zhipin.tools.common.utils.HexUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Slf4j
public class SendTest {

    String user1_token;
    String user2_token;
    @Before
    public void init() throws IOException {
        user1_token = "dbfd226d-0cb4-4390-b49c-dab30cc9f97d";
        user2_token = "e8a257a1-211d-47f9-89dc-2202250791d6";
    }

    @Test
    public void heartBeat() throws IOException, InterruptedException, JSONException {
        //创建客户端的Socket对象
        Socket s = new Socket("127.0.0.1",6666);

        //获取输出流，写数据
        OutputStream os = s.getOutputStream();

        //获取消息头信息
        byte[] imei = "888888888176540".getBytes();
        int imeiLength = imei.length;
        byte[] imeiLengthBytes = ByteBuffer.allocate(4).putInt(imeiLength).array();

        byte[] token = user1_token.getBytes();
        int tokenLength = token.length;
        byte[] tokenLengthBytes = ByteBuffer.allocate(4).putInt(tokenLength).array();

        byte[] command = ByteBuffer.allocate(4).putInt(Command.LOGIN).array();
        byte[] clientType = ByteBuffer.allocate(1).put((byte) 2).array();

        JSONObject data = new JSONObject();
        data.put(FieldConstants.TOKEN, user1_token);
        String jsonData = data.toString();
        byte[] body = jsonData.getBytes(StandardCharsets.UTF_8);
        int bodyLength = body.length;
        byte[] bodyLengthBytes = ByteBuffer.allocate(4).putInt(bodyLength).array();

        os.write(command);
        os.write(clientType);
        os.write(tokenLengthBytes);
        os.write(imeiLengthBytes);
        os.write(bodyLengthBytes);
        os.write(token);
        os.write(imei);
        os.write(body);
        os.flush();

        String s1 = HexUtils.bytesToHexString(
                command,
                clientType,
                tokenLengthBytes,
                imeiLengthBytes,
                bodyLengthBytes,
                token,
                imei,
                body);

        log.info("当前消息的二进制信息: {}",  s1);

        //登录后发送心跳，然后休眠10秒，等待心跳中断
        command = ByteBuffer.allocate(4).putInt(Command.HEART_BEAT).array();

        for (int i = 0; i < 10; i++) {
            os.write(command);
            os.write(clientType);
            os.write(tokenLengthBytes);
            os.write(imeiLengthBytes);
            os.write(bodyLengthBytes);
            os.write(token);
            os.write(imei);
            os.write(body);

            os.flush();
        }

        //释放资源
        s.close();
    }

    @Test
    public void loginAndLogout() throws IOException, JSONException, InterruptedException {
        //创建客户端的Socket对象
        Socket s = new Socket("127.0.0.1",6666);

        //获取输出流，写数据
        OutputStream os = s.getOutputStream();

        //获取消息头信息
        byte[] imei = "888888888176540".getBytes();
        int imeiLength = imei.length;
        byte[] imeiLengthBytes = ByteBuffer.allocate(4).putInt(imeiLength).array();

        byte[] token = user1_token.getBytes();
        int tokenLength = token.length;
        byte[] tokenLengthBytes = ByteBuffer.allocate(4).putInt(tokenLength).array();

        byte[] commandType = ByteBuffer.allocate(4).putInt(Command.LOGIN).array();
        byte[] clientType = ByteBuffer.allocate(1).put((byte) 2).array();

        JSONObject data = new JSONObject();
        data.put(FieldConstants.MEMBER_ID, "10088");
        String jsonData = data.toString();
        byte[] body = jsonData.getBytes(StandardCharsets.UTF_8);
        int bodyLength = body.length;
        byte[] bodyLengthBytes = ByteBuffer.allocate(4).putInt(bodyLength).array();


        os.write(commandType);
        os.write(clientType);
        os.write(tokenLengthBytes);
        os.write(imeiLengthBytes);
        os.write(bodyLengthBytes);
        os.write(token);
        os.write(imei);
        os.write(body);

        os.flush();

        Thread.sleep(1000000);
//
//        commandType = ByteBuffer.allocate(1).put(Command.LOGOUT).array();
//        os.write(commandType);
//        os.write(clientType);
//        os.write(imeiLengthBytes);
//        os.write(bodyLengthBytes);
//        os.write(imei);
//        os.write(body);
//
//        os.flush();


        //释放资源
        //s.close();
    }







    @Test
    public void sendMessage() throws IOException, JSONException, InterruptedException {
        //创建客户端的Socket对象
        Socket s = new Socket("127.0.0.1",6666);

        //获取输出流，写数据
        OutputStream os = s.getOutputStream();

        //获取消息头信息
        byte[] imei = "888888888176540".getBytes();
        int imeiLength = imei.length;
        byte[] imeiLengthBytes = ByteBuffer.allocate(4).putInt(imeiLength).array();

        byte[] token = user1_token.getBytes();
        int tokenLength = token.length;
        byte[] tokenLengthBytes = ByteBuffer.allocate(4).putInt(tokenLength).array();

        byte[] commandType = ByteBuffer.allocate(4).putInt(Command.MessageCommand.PRIVATE_CHAT).array();
        byte[] clientType = ByteBuffer.allocate(1).put((byte) 2).array();

        JSONObject data = new JSONObject();
        data.put(FieldConstants.MEMBER_ID, "10088");
        String jsonData = data.toString();
        byte[] body = jsonData.getBytes(StandardCharsets.UTF_8);
        int bodyLength = body.length;
        byte[] bodyLengthBytes = ByteBuffer.allocate(4).putInt(bodyLength).array();


        os.write(commandType);
        os.write(clientType);
        os.write(tokenLengthBytes);
        os.write(imeiLengthBytes);
        os.write(bodyLengthBytes);
        os.write(token);
        os.write(imei);
        os.write(body);

        os.flush();

        while(true) {
            log.info("sendMessage running ...");
            Thread.sleep(100);
        }
    }

}

