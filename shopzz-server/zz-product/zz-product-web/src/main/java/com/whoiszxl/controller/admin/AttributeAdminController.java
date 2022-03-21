package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.PageQuery;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.AttributeKeySaveCommand;
import com.whoiszxl.cqrs.command.AttributeKeyUpdateCommand;
import com.whoiszxl.cqrs.command.AttributeValueSaveCommand;
import com.whoiszxl.cqrs.command.AttributeValueUpdateCommand;
import com.whoiszxl.cqrs.query.AttributeValueQuery;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.AttributeKey;
import com.whoiszxl.entity.AttributeValue;
import com.whoiszxl.service.AttributeKeyService;
import com.whoiszxl.service.AttributeValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/attribute")
@Api(tags = "属性后台相关接口")
public class AttributeAdminController {

    @Autowired
    private AttributeKeyService attributeKeyService;

    @Autowired
    private AttributeValueService attributeValueService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/key/list")
    @ApiOperation(value = "分页获取属性key列表", notes = "分页获取属性key列表", response = AttributeKey.class)
    public ResponseResult<IPage<AttributeKey>> list(@RequestBody PageQuery query) {
        Page<AttributeKey> result = attributeKeyService.page(new Page<>(query.getPage(), query.getSize()), null);
        return ResponseResult.buildSuccess(result);
    }

    @SaCheckLogin
    @PostMapping("/key")
    @ApiOperation(value = "新增属性key", notes = "新增属性key", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody AttributeKeySaveCommand attributeKeySaveCommand) {
        boolean saveFlag = attributeKeyService.save(attributeKeySaveCommand);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping("/key")
    @ApiOperation(value = "更新属性key", notes = "更新属性key", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody AttributeKeyUpdateCommand attributeKeyUpdateCommand) {
        boolean updateFlag = attributeKeyService.update(attributeKeyUpdateCommand);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/key/{id}")
    @ApiOperation(value = "删除属性key", notes = "删除属性key", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = attributeKeyService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }


    @SaCheckLogin
    @PostMapping("/value/list")
    @ApiOperation(value = "通过属性key ID分页获取属性value列表", notes = "通过属性key ID分页获取属性value列表", response = AttributeValue.class)
    public ResponseResult<IPage<AttributeValue>> valueList(@RequestBody AttributeValueQuery query) {
        LambdaQueryWrapper<AttributeValue> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(AttributeValue::getKeyId, query.getKeyId());
        Page<AttributeValue> result = attributeValueService.page(new Page<>(query.getPage(), query.getSize()), lambdaQueryWrapper);
        return ResponseResult.buildSuccess(result);
    }

    @SaCheckLogin
    @PostMapping("/value")
    @ApiOperation(value = "新增属性value", notes = "新增属性value", response = ResponseResult.class)
    public ResponseResult<Boolean> valueSave(@RequestBody AttributeValueSaveCommand attributeValueSaveCommand) {
        boolean saveFlag = attributeValueService.save(attributeValueSaveCommand);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping("/value")
    @ApiOperation(value = "更新属性value", notes = "更新属性value", response = ResponseResult.class)
    public ResponseResult<Boolean> valueUpdate(@RequestBody AttributeValueUpdateCommand attributeValueUpdateCommand) {
        boolean updateFlag = attributeValueService.update(attributeValueUpdateCommand);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/value/{id}")
    @ApiOperation(value = "删除属性value", notes = "删除属性value", response = ResponseResult.class)
    public ResponseResult<Boolean> valueDelete(@PathVariable Long id) {
        boolean removeFlag = attributeValueService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

