package com.whoiszxl.zhipin.im;

import cn.hutool.json.JSONUtil;
import com.whoiszxl.zhipin.im.constants.FieldConstants;
import com.whoiszxl.zhipin.im.pack.GroupChatPack;
import com.whoiszxl.zhipin.im.pack.PrivateChatPack;
import com.whoiszxl.zhipin.im.protocol.Command;
import com.whoiszxl.zhipin.tools.common.utils.HexUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Slf4j
public class MessageHexBuildTest {

    String user1_token;
    String user2_token;

    //设备号
    String imei = "imei-100";
    Integer commandType = Command.LOGIN;
    byte clientType = 1;

    String message = "您好，我是负责蜀国招聘的顾问姜维.我们正在寻访武将谋士，薪资open(一级将领可谈)，另有每月20-70两黄金的奖金，多地可选、多岗位招聘，看了您的简历觉得很匹配，方便详聊吗?";

    @Before
    public void init() throws IOException {
        user1_token = "dbfd226d-0cb4-4390-b49c-dab30cc9f97d";
        user2_token = "e8a257a1-211d-47f9-89dc-2202250791d6";
    }

    /**
     * user1 token 二进制: 000003ea0100000024000000080000003064626664323236642d306362342d343339302d623439632d646162333063633966393764696d65692d3130307b22746f6b656e223a2264626664323236642d306362342d343339302d623439632d646162333063633966393764227d
     *
     * user2 token 二进制: 000003ea0100000024000000080000003065386132353761312d323131642d343766392d383964632d323230323235303739316436696d65692d3130307b22746f6b656e223a2265386132353761312d323131642d343766392d383964632d323230323235303739316436227d
     * @throws JSONException
     */
    @Test
    public void buildLogin() throws JSONException {
        JSONObject data = new JSONObject();
        data.put(FieldConstants.TOKEN, user1_token);
        String jsonData = data.toString();
        String user1Token = build(user1_token, this.commandType, jsonData);

        data.put(FieldConstants.TOKEN, user2_token);
        jsonData = data.toString();
        String user2Token = build(user2_token, this.commandType, jsonData);
        log.info("user1 token 二进制: {}", user1Token);
        log.info("user2 token 二进制: {}", user2Token);
    }


    /**
     * 1 -> 2 的二进制消息：00000bb90100000024000000080000005064626664323236642d306362342d343339302d623439632d646162333063633966393764696d65692d3130307b2267726f75704964223a3130302c226d6573736167654964223a226d73672d303031222c2266726f6d4d656d6265724964223a312c22626f6479223a2268656c6c6f2065766572796f6e652121227d
     * @throws JSONException
     */
    @Test
    public void buildGroupChat() throws JSONException {
        GroupChatPack groupChatPack = GroupChatPack.builder()
                .groupId(100L)
                .fromMemberId(1L)
                .messageId("msg-001")
                .body("hello everyone!!")
                .build();
        String jsonData = JSONUtil.toJsonStr(groupChatPack);
        String message = build(user1_token, Command.GroupCommand.GROUP_CHAT, jsonData);

        log.info("消息体 二进制: {}", message);
    }

    /**
     * 1 -> 2 的二进制消息：000007d10100000024000000080000004864626664323236642d306362342d343339302d623439632d646162333063633966393764696d65692d3130307b226d6573736167654964223a226d73672d303031222c2266726f6d4d656d6265724964223a312c22746f4d656d6265724964223a322c22626f6479223a2268656c6c6f2121227d
     * 1 -> 4 的二进制消息：000007d10100000024000000080000012f64626664323236642d306362342d343339302d623439632d646162333063633966393764696d65692d3130307b226d6573736167654964223a226d73672d303031222c2266726f6d4d656d6265724964223a312c22746f4d656d6265724964223a342c22626f6479223a22e682a8e5a5bdefbc8ce68891e698afe8b49fe8b4a3e89c80e59bbde68b9be88198e79a84e9a1bee997aee5a79ce7bbb42ee68891e4bbace6ada3e59ca8e5afbbe8aebfe6ada6e5b086e8b08be5a3abefbc8ce896aae8b5846f70656e28e4b880e7baa7e5b086e9a286e58fafe8b08829efbc8ce58fa6e69c89e6af8fe69c8832302d3730e4b8a4e9bb84e98791e79a84e5a596e98791efbc8ce5a49ae59cb0e58fafe98089e38081e5a49ae5b297e4bd8de68b9be88198efbc8ce79c8be4ba86e682a8e79a84e7ae80e58e86e8a789e5be97e5be88e58cb9e9858defbc8ce696b9e4bebfe8afa6e8818ae590973f227d
     * @throws JSONException
     */
    @Test
    public void buildPrivateChat() throws JSONException {
        PrivateChatPack privateChatPack = PrivateChatPack.builder()
                .messageId("msg-001")
                .fromMemberId(1L)
                .toMemberId(4L)
                .body(message)
                .build();
        String jsonData = JSONUtil.toJsonStr(privateChatPack);
        String message = build(user1_token, Command.MessageCommand.PRIVATE_CHAT, jsonData);

        log.info("消息体 二进制: {}", message);
    }

    /**
     * 心跳二进制包：000003ec0100000024000000080000001564626664323236642d306362342d343339302d623439632d646162333063633966393764696d65692d3130307b2274657374223a2268656172745f62656174227d
     * @throws JSONException
     */
    @Test
    public void buildHeartBeat() throws JSONException {
        HashMap<String, String> map = new HashMap<>();
        map.put("test", "heart_beat");
        String jsonData = JSONUtil.toJsonStr(map);
        String message = build(user1_token, Command.HEART_BEAT, jsonData);

        log.info("消息体 二进制: {}", message);
    }

    private String build(String userToken, Integer userCommand, String jsonData) throws JSONException {
        //获取消息头信息
        byte[] imei = this.imei.getBytes();
        int imeiLength = imei.length;
        byte[] imeiLengthBytes = ByteBuffer.allocate(4).putInt(imeiLength).array();

        byte[] token = userToken.getBytes();
        int tokenLength = token.length;
        byte[] tokenLengthBytes = ByteBuffer.allocate(4).putInt(tokenLength).array();

        byte[] command = ByteBuffer.allocate(4).putInt(userCommand).array();
        byte[] clientType = ByteBuffer.allocate(1).put(this.clientType).array();

        byte[] body = jsonData.getBytes(StandardCharsets.UTF_8);
        int bodyLength = body.length;
        byte[] bodyLengthBytes = ByteBuffer.allocate(4).putInt(bodyLength).array();

        return HexUtils.bytesToHexString(
                command,
                clientType,
                tokenLengthBytes,
                imeiLengthBytes,
                bodyLengthBytes,
                token,
                imei,
                body);
    }



}

