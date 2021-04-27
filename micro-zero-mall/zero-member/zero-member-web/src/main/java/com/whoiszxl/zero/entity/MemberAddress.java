package com.whoiszxl.zero.entity;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * ums_member_address
 * @author 
 */
@Entity
@Table(name="ums_member_address")
@Data
public class MemberAddress extends AbstractObject implements Serializable {
    /**
     * 主键ID
     */
    @Id
    private Long id;

    /**
     * 用户ID
     */
    private Long memberId;

    /**
     * 收货人
     */
    private String reciverName;

    /**
     * 收货人电话号码
     */
    private String reciverPhone;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 收货地址
     */
    private String detailAddress;

    /**
     * 是否默认 1:默认 2:非默认
     */
    private Boolean isDefault;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    private static final long serialVersionUID = 1L;
}