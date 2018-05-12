package com.whoiszxl.controller.portal;

import javax.servlet.http.HttpSession;

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
import com.whoiszxl.service.ShippingService;

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
	
	@PostMapping("add")
	@ApiOperation(value = "添加一个收货地址")
	public ServerResponse add(HttpSession session,Shipping shipping) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		return shippingService.add(user.getId() , shipping);
	}
	
	@PostMapping("delete")
	@ApiOperation(value = "删除一个收货地址")
	public ServerResponse delete(HttpSession session,Integer shippingId) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		return shippingService.del(user.getId(),shippingId);
	}
	
	@PostMapping("update")
	@ApiOperation(value = "更新一个收货地址")
    public ServerResponse update(HttpSession session,Shipping shipping){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        return shippingService.update(user.getId(),shipping);
    }


    @GetMapping("selects")
    @ApiOperation(value = "通过用户id和地址id查询到一个收货地址")
    public ServerResponse<Shipping> select(HttpSession session,Integer shippingId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        return shippingService.select(user.getId(),shippingId);
    }


    @PostMapping("list")
    @ApiOperation(value = "查询当前用户的所有收货地址")
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                         HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        return shippingService.list(user.getId(),pageNum,pageSize);
    }
	
}
