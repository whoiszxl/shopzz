package com.whoiszxl.taowu.im.entity;

import lombok.Builder;
import lombok.Data;

/**
 * 账号session
 * @author whoiszxl
 */
@Data
@Builder
public class MemberSession {

    private String memberId;

    private Byte clientType;

    private String imei;

    private Integer connectStatus;

    private String nodeId;

    private String nodeHost;
}
