package com.whoiszxl.taowu.common.base;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.taowu.common.annotation.Query;
import com.whoiszxl.taowu.common.entity.PageQuery;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.entity.response.PageResponse;
import com.whoiszxl.taowu.common.token.enums.PermissionNameEnum;
import com.whoiszxl.taowu.common.utils.ExcelUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 基础控制器
 * @author whoiszxl
 */
@Slf4j
@NoArgsConstructor
public abstract class  BaseController<S extends IService<M>, M, R, Q, A, U> {

    @Autowired
    protected S baseService;

    protected Class<M> entityClass = (Class<M>) ReflectionKit.getSuperClassGenericType(this.getClass(), BaseController.class, 1);
    protected Class<R> responseClass = (Class<R>) ReflectionKit.getSuperClassGenericType(this.getClass(), BaseController.class, 2);

    @Operation(summary = "查看详情")
    @Parameter(name = "id", description = "ID", in = ParameterIn.PATH)
    @ResponseBody
    @GetMapping("/{id}")
    protected ResponseResult<R> get(@PathVariable Long id) {
        Long start = System.currentTimeMillis();
        this.checkPermission(PermissionNameEnum.LIST.getName());
        M detail = baseService.getById(id);
        R response = BeanUtil.copyProperties(detail, responseClass);
        Long end = System.currentTimeMillis();
        System.out.println(end - start);
        return ResponseResult.buildSuccess(response);
    }


    @Operation(summary = "分页查询列表")
    @ResponseBody
    @GetMapping("/page")
    protected ResponseResult<PageResponse<R>> page(@Validated Q query, @Validated PageQuery pageQuery) {
        this.checkPermission(PermissionNameEnum.LIST.getName());
        QueryWrapper<M> queryWrapper = Wrappers.query();

        Class<?> queryClazz = query.getClass();
        List<Field> queryFieldList = this.getClassField(queryClazz, new ArrayList<>());
        queryFieldList.forEach(field -> buildQuery(query, field, queryWrapper));

        IPage<M> page = baseService.page(pageQuery.toPage(), queryWrapper);
        PageResponse<R> pageResponse = PageResponse.convert(page, responseClass);
        return ResponseResult.buildSuccess(pageResponse);
    }


    @Operation(summary = "查询列表")
    @ResponseBody
    @GetMapping("/list")
    protected ResponseResult<List<R>> list(@Validated Q query) {
        this.checkPermission(PermissionNameEnum.LIST.getName());
        List<R> resultList = responseList(query);
        return ResponseResult.buildSuccess(resultList);
    }

    @Operation(summary = "新增数据")
    @ResponseBody
    @PostMapping
    protected ResponseResult<Boolean> add(@RequestBody A addBody) {
        this.checkPermission(PermissionNameEnum.ADD.getName());
        M entity = BeanUtil.copyProperties(addBody, entityClass);
        boolean flag = baseService.save(entity);
        return ResponseResult.buildByFlag(flag);
    }

    @Operation(summary = "修改数据")
    @ResponseBody
    @PutMapping
    protected ResponseResult update(@RequestBody U updateBody) {
        this.checkPermission(PermissionNameEnum.UPDATE.getName());
        M entity = BeanUtil.copyProperties(updateBody, entityClass);
        boolean flag = baseService.updateById(entity);
        return ResponseResult.buildByFlag(flag);
    }

    @Operation(summary = "删除数据")
    @Parameter(name = "ids", description = "ID 列表", in = ParameterIn.PATH)
    @ResponseBody
    @DeleteMapping("/{ids}")
    protected ResponseResult delete(@PathVariable List<Long> ids) {
        this.checkPermission(PermissionNameEnum.DELETE.getName());
        boolean flag = baseService.removeBatchByIds(ids);
        return ResponseResult.buildByFlag(flag);
    }

    @Operation(summary = "导出数据")
    @GetMapping("/export")
    protected void export(@Validated Q query, HttpServletResponse response) {
        this.checkPermission(PermissionNameEnum.EXPORT.getName());
        List<R> resultList = responseList(query);
        ExcelUtil.export(resultList, "export", responseClass, response);
    }


    /**
     * 获取class和class父类的所有属性
     * @param clazz 类型
     * @param fieldList 属性列表
     * @return 查询条件里的所有字段
     * @param <Q> 查询条件类型
     */
    private <Q> List<Field> getClassField(Class<Q> clazz, List<Field> fieldList) {
        if(clazz != null) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            getClassField(clazz.getSuperclass(), fieldList);
        }
        return fieldList;
    }

    private static <M> void parse(Query queryAnnotation,
                                  String fieldName,
                                  Object fieldValue,
                                  QueryWrapper<M> queryWrapper) {
        // 解析多属性模糊查询
        String blurry = queryAnnotation.blurry();
        if (StrUtil.isNotBlank(blurry)) {
            String[] propertyArr = blurry.split(",");
            queryWrapper.and(wrapper -> {
                for (String property : propertyArr) {
                    wrapper.or().like(StrUtil.toUnderlineCase(property), fieldValue);
                }
            });
            return;
        }

        // 解析单个属性查询
        String property = queryAnnotation.property();
        fieldName = StrUtil.blankToDefault(property, fieldName);
        String columnName = StrUtil.toUnderlineCase(fieldName);
        switch (queryAnnotation.type()) {
            case EQUAL:
                queryWrapper.eq(columnName, fieldValue);
                break;
            case NOT_EQUAL:
                queryWrapper.ne(columnName, fieldValue);
                break;
            case GREATER_THAN:
                queryWrapper.gt(columnName, fieldValue);
                break;
            case LESS_THAN:
                queryWrapper.lt(columnName, fieldValue);
                break;
            case GREATER_THAN_OR_EQUAL:
                queryWrapper.ge(columnName, fieldValue);
                break;
            case LESS_THAN_OR_EQUAL:
                queryWrapper.le(columnName, fieldValue);
                break;
            case BETWEEN:
                List<Object> between = new ArrayList<>((List<Object>)fieldValue);
                queryWrapper.between(columnName, between.get(0), between.get(1));
                break;
            case LEFT_LIKE:
                queryWrapper.likeLeft(columnName, fieldValue);
                break;
            case INNER_LIKE:
                queryWrapper.like(columnName, fieldValue);
                break;
            case RIGHT_LIKE:
                queryWrapper.likeRight(columnName, fieldValue);
                break;
            case IN:
                if (CollUtil.isNotEmpty((List<Object>)fieldValue)) {
                    queryWrapper.in(columnName, (List<Object>)fieldValue);
                }
                break;
            case NOT_IN:
                if (CollUtil.isNotEmpty((List<Object>)fieldValue)) {
                    queryWrapper.notIn(columnName, (List<Object>)fieldValue);
                }
                break;
            case IS_NULL:
                queryWrapper.isNull(columnName);
                break;
            case NOT_NULL:
                queryWrapper.isNotNull(columnName);
                break;
            default:
                break;
        }
    }

    private void checkPermission(String permission) {
        RequestMapping requestMapping =this.getClass().getDeclaredAnnotation(RequestMapping.class);
        String[] value = requestMapping.value();
        String permissionPrefix = String.join(StrPool.COLON, StrUtil.splitTrim(value[0], StrPool.SLASH));
        StpUtil.checkPermission(String.format("%s:%s", permissionPrefix, permission));
    }

    private <Q, M> void buildQuery(Q query, Field field, QueryWrapper<M> queryWrapper) {
        boolean accessible = field.isAccessible();
        try {
            field.setAccessible(true);
            Query queryAnno = field.getAnnotation(Query.class);
            if(queryAnno == null) {
                return;
            }

            Object fieldValue = field.get(query);
            if(ObjectUtil.isEmpty(fieldValue)) {
                return;
            }

            parse(queryAnno, field.getName(), fieldValue, queryWrapper);
        }catch (Exception e) {
            log.error("BaseController|构建查询条件出错|{}", query, e);
        }finally {
            field.setAccessible(accessible);
        }
    }

    /**
     * response list查询
     * @param query 查询条件
     * @return
     */
    public List<R> responseList(Q query) {
        QueryWrapper<M> queryWrapper = Wrappers.query();

        Class<?> queryClazz = query.getClass();
        List<Field> queryFieldList = this.getClassField(queryClazz, new ArrayList<>());
        queryFieldList.forEach(field -> buildQuery(query, field, queryWrapper));
        List<M> list = baseService.list(queryWrapper);

        return BeanUtil.copyToList(list, responseClass);
    }

}
