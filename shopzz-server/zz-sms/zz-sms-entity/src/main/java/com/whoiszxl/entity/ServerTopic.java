package com.whoiszxl.entity;

import com.whoiszxl.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerTopic {
    public static final String INIT_CONNECT = "INIT_CONNECT";
    public static final String USE_NEW_CONNECT = "USE_NEW_CONNECT";

    private String option;
    private String value;

    public static ServerTopic load(String deserialize) {
        return JsonUtil.fromJson(deserialize, ServerTopic.class);
    }

    public String toString() {
        return JsonUtil.toJson(this);
    }
}
