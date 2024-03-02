package com.whoiszxl.taowu.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.taowu.common.base.BaseController;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.cqrs.command.AttributeValueSaveCommand;
import com.whoiszxl.taowu.cqrs.command.AttributeValueUpdateCommand;
import com.whoiszxl.taowu.cqrs.query.AttributeValueQuery;
import com.whoiszxl.taowu.entity.AttributeValue;
import com.whoiszxl.taowu.service.AttributeValueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 属性键表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@RestController
@RequestMapping("/attribute/value")
@Tag(name = "属性后台相关接口")
@RequiredArgsConstructor
public class AttributeValueAdminController extends BaseController<AttributeValueService, AttributeValue, AttributeValue, AttributeValueQuery, AttributeValueSaveCommand, AttributeValueUpdateCommand> {

    private final AttributeValueService attributeValueService;

    @SaCheckLogin
    @PostMapping("/add")
    @Operation(summary = "新增属性value", description = "新增属性value")
    public ResponseResult<Boolean> add(@RequestBody AttributeValueSaveCommand attributeValueSaveCommand) {
        boolean saveFlag = attributeValueService.save(attributeValueSaveCommand);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping("/update")
    @Operation(summary = "更新属性value", description = "更新属性value")
    public ResponseResult<Boolean> update(@RequestBody AttributeValueUpdateCommand attributeValueUpdateCommand) {
        boolean updateFlag = attributeValueService.update(attributeValueUpdateCommand);
        return ResponseResult.buildByFlag(updateFlag);
    }
}

