package com.whoiszxl.taowu.controller.admin;


import com.whoiszxl.taowu.cqrs.admin.query.PayInfoQuery;
import com.whoiszxl.taowu.entity.PayInfo;
import com.whoiszxl.taowu.service.PayInfoService;
import com.whoiszxl.taowu.common.base.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 第三方支付信息表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Tag(name = "pay-info API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/pay-info")
public class PayInfoController extends BaseController<PayInfoService, PayInfo, PayInfo, PayInfoQuery, PayInfo, PayInfo> {

}

