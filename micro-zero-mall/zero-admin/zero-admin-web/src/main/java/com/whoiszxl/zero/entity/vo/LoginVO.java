package com.whoiszxl.zero.entity.vo;

import com.whoiszxl.zero.entity.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "登录的结果")
public class LoginVO {

    @ApiModelProperty(value = "JWT TOKEN")
    private String token;

    @ApiModelProperty(value = "系统用户的菜单数据")
    private List<SysMenu> menus ;

    @ApiModelProperty(value = "系统用户的权限数据")
    private List<SimpleGrantedAuthority> authorities;
}
