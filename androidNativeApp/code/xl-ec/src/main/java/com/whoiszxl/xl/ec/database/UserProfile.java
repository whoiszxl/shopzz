package com.whoiszxl.xl.ec.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author whoiszxl
 * 用户信息
 */
@Entity(nameInDb = "user_profile")
public class UserProfile {

    /**
     * token密钥
     */
    private String jwtToken = null;

    @Generated(hash = 41485937)
    public UserProfile(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Generated(hash = 968487393)
    public UserProfile() {
    }

    public String getJwtToken() {
        return this.jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

}
