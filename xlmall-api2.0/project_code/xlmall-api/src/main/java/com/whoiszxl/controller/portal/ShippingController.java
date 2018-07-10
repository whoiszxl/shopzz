package com.whoiszxl.controller.portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.Shipping;
import com.whoiszxl.entity.User;
import com.whoiszxl.jwt.JwtUserService;
import com.whoiszxl.service.ShippingService;
import com.whoiszxl.utils.CookieUtil;
import com.whoiszxl.utils.RedisShardedPoolUtil;
import com.whoiszxl.utils.UserUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author whoiszxl
 *
 */
@Api(value="前台收货地址管理模块",description="前台收货地址管理模块")
@RestController
@RequestMapping("/shipping/")
public class ShippingController {

	@Autowired
	private ShippingService shippingService;
	
	@Autowired
	private JwtUserService jwtUserService;
	
	@PostMapping("add")
	@ApiOperation(value = "添加一个收货地址")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical=Logical.OR)
	public ServerResponse add(HttpServletRequest request,Shipping shipping) {
		User user = jwtUserService.getCurrentUser(request);
		return shippingService.add(user.getId() , shipping);
	}
	
	@PostMapping("delete")
	@ApiOperation(value = "删除一个收货地址")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical=Logical.OR)
	public ServerResponse delete(HttpServletRequest request,Integer shippingId) {
		User user = jwtUserService.getCurrentUser(request);
		return shippingService.del(user.getId(),shippingId);
	}
	
	@PostMapping("update")
	@ApiOperation(value = "更新一个收货地址")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical=Logical.OR)
    public ServerResponse update(HttpServletRequest request,Shipping shipping){
		User user = jwtUserService.getCurrentUser(request);
        return shippingService.update(user.getId(),shipping);
    }


    @GetMapping("selects")
    @ApiOperation(value = "通过用户id和地址id查询到一个收货地址")
    @RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical=Logical.OR)
    public ServerResponse<Shipping> select(HttpServletRequest request,Integer shippingId){
    	User user = jwtUserService.getCurrentUser(request);
        return shippingService.select(user.getId(),shippingId);
    }


    @PostMapping("list")
    @ApiOperation(value = "查询当前用户的所有收货地址")
    @RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical=Logical.OR)
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                         HttpServletRequest request){
    	User user = jwtUserService.getCurrentUser(request);
        return shippingService.list(user.getId(),pageNum,pageSize);
    }
    
    
    @PostMapping("setdeafult")
	@ApiOperation(value = "将地址设置为默认地址")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical=Logical.OR)
    public ServerResponse setDefault(HttpServletRequest request,int shippingId){
		User user = jwtUserService.getCurrentUser(request);
        return shippingService.setDefault(user.getId(), shippingId);
    }
	
}
