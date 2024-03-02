package com.whoiszxl.taowu.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.taowu.common.base.BaseController;
import com.whoiszxl.taowu.common.entity.PageQuery;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.cqrs.command.AttributeKeySaveCommand;
import com.whoiszxl.taowu.cqrs.command.AttributeKeyUpdateCommand;
import com.whoiszxl.taowu.entity.AttributeKey;
import com.whoiszxl.taowu.service.AttributeKeyService;
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
@RequestMapping("/attribute/key")
@Tag(name = "属性后台相关接口")
@RequiredArgsConstructor
public class AttributeKeyAdminController extends BaseController<AttributeKeyService, AttributeKey, AttributeKey, PageQuery, AttributeKeySaveCommand, AttributeKeyUpdateCommand> {

    private final AttributeKeyService attributeKeyService;

    @SaCheckLogin
    @PostMapping("/add")
    @Operation(summary = "新增属性key", description = "新增属性key")
    public ResponseResult<Boolean> save(@RequestBody AttributeKeySaveCommand attributeKeySaveCommand) {
        boolean saveFlag = attributeKeyService.save(attributeKeySaveCommand);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping("/update")
    @Operation(summary = "更新属性key", description = "更新属性key")
    public ResponseResult<Boolean> update(@RequestBody AttributeKeyUpdateCommand attributeKeyUpdateCommand) {
        boolean updateFlag = attributeKeyService.update(attributeKeyUpdateCommand);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除属性key", description = "删除属性key")
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = attributeKeyService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }


}

