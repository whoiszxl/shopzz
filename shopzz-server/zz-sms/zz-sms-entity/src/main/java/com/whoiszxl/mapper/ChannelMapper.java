package com.whoiszxl.mapper;

import com.whoiszxl.entity.Channel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 短信通道表 Mapper 接口
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
public interface ChannelMapper extends BaseMapper<Channel> {


    @Select("select sc.* from sms_channel as sc " +
            "left join sms_channel_template as sct on sc.id = sct.channel_id " +
            "left join sms_channel_signature as scs on sc.id = scs.channel_id " +
            "where sc.status = 1 " +
            "and sct.template_id = #{templateId} " +
            "and scs.signature_id = #{signatureId} " +
            "order by sc.level asc")
    List<Channel> listByTemplateAndSignature(Long templateId, Long signatureId);

}
