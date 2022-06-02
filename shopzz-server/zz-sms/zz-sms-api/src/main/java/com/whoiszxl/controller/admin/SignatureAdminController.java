package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.PageQuery;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.SignatureSaveCommand;
import com.whoiszxl.cqrs.command.SignatureUpdateCommand;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Signature;
import com.whoiszxl.service.SignatureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signature")
@Api(tags = "签名后台相关接口")
public class SignatureAdminController {
    
    @Autowired
    private SignatureService signatureService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取签名列表", notes = "分页获取签名列表", response = Signature.class)
    public ResponseResult<IPage<Signature>> list(@RequestBody PageQuery query) {
        Page<Signature> result = signatureService.page(new Page<>(query.getPage(), query.getSize()), null);
        return ResponseResult.buildSuccess(result);
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增签名", notes = "新增签名", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody @Validated SignatureSaveCommand signatureSaveCommand) {
        boolean saveFlag = signatureService.save(dozerUtils.map(signatureSaveCommand, Signature.class));
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新签名", notes = "更新签名", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody SignatureUpdateCommand signatureUpdateCommand) {
        boolean updateFlag = signatureService.updateById(dozerUtils.map(signatureUpdateCommand, Signature.class));
        return ResponseResult.buildByFlag(updateFlag);
    }


    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除签名", notes = "删除签名", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = signatureService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

