package com.whoiszxl.taowu.file.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whoiszxl.taowu.file.entity.FmsFile;
import com.whoiszxl.taowu.file.mapper.FileMapper;
import com.whoiszxl.taowu.common.token.TokenHelper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileInfoPersistenceService extends ServiceImpl<FileMapper, FmsFile> implements FileRecorder {

    private ObjectMapper objectMapper = new ObjectMapper();

    private final TokenHelper tokenHelper;

    @SneakyThrows
    @Override
    public boolean save(FileInfo info) {
        FmsFile file = BeanUtil.copyProperties(info,FmsFile.class,"metadata","userMetadata","thMetadata","thUserMetadata","attr");

        //这是手动获 元数据 并转成 json 字符串，方便存储在数据库中
        file.setMetadata(valueToJson(info.getMetadata()));
        file.setUserMetadata(valueToJson(info.getUserMetadata()));
        file.setThMetadata(valueToJson(info.getThMetadata()));
        file.setThUserMetadata(valueToJson(info.getThUserMetadata()));
        //这是手动获取 附加属性字典 并转成 json 字符串，方便存储在数据库中
        file.setAttr(valueToJson(info.getAttr()));

        // 自定义字段逻辑
        file.setMemberId(tokenHelper.getAppMemberId());

        boolean b = save(file);
        if (b) {
            info.setId(file.getId());
        }
        return b;
    }

    /**
     * 根据 url 查询文件信息
     */
    @SneakyThrows
    @Override
    public FileInfo getByUrl(String url) {
        FmsFile detail = getOne(new QueryWrapper<FmsFile>().eq(FmsFile.COL_URL,url));
        FileInfo info = BeanUtil.copyProperties(detail,FileInfo.class,"metadata","userMetadata","thMetadata","thUserMetadata","attr");

        //这是手动获取数据库中的 json 字符串 并转成 元数据，方便使用
        info.setMetadata(jsonToMetadata(detail.getMetadata()));
        info.setUserMetadata(jsonToMetadata(detail.getUserMetadata()));
        info.setThMetadata(jsonToMetadata(detail.getThMetadata()));
        info.setThUserMetadata(jsonToMetadata(detail.getThUserMetadata()));
        //这是手动获取数据库中的 json 字符串 并转成 附加属性字典，方便使用
        info.setAttr(jsonToDict(detail.getAttr()));
        return info;
    }

    @Override
    public boolean delete(String url) {
        remove(new QueryWrapper<FmsFile>().eq(FmsFile.COL_URL,url));
        return true;
    }


    /**
     * 将指定值转换成 json 字符串
     */
    public String valueToJson(Object value) throws JsonProcessingException {
        if (value == null) return null;
        return objectMapper.writeValueAsString(value);
    }

    /**
     * 将 json 字符串转换成元数据对象
     */
    public Map<String, String> jsonToMetadata(String json) throws JsonProcessingException {
        if (StrUtil.isBlank(json)) return null;
        return objectMapper.readValue(json,new TypeReference<Map<String, String>>() {
        });
    }

    /**
     * 将 json 字符串转换成字典对象
     */
    public Dict jsonToDict(String json) throws JsonProcessingException {
        if (StrUtil.isBlank(json)) return null;
        return objectMapper.readValue(json,Dict.class);
    }
}
