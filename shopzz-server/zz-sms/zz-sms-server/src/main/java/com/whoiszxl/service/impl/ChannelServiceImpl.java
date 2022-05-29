package com.whoiszxl.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.cqrs.dto.ChannelDTO;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Channel;
import com.whoiszxl.entity.SendLog;
import com.whoiszxl.enums.StatusEnum;
import com.whoiszxl.mapper.ChannelMapper;
import com.whoiszxl.service.ChannelService;
import com.whoiszxl.service.SendLogService;
import com.whoiszxl.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>
 * 短信通道表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Slf4j
@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel> implements ChannelService {

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private SendLogService sendLogService;

    @Autowired
    private DozerUtils dozerUtils;

    @Override
    public List<Channel> listByTemplateAndSignature(Long templateId, Long signatureId) {
        return channelMapper.listByTemplateAndSignature(templateId, signatureId);
    }

    @Override
    public List<Channel> listForNewConnect() {
        List<Channel> channelList = this.list(Wrappers.<Channel>lambdaQuery()
                .eq(Channel::getChannelType, 1)
                .eq(Channel::getStatus, StatusEnum.OPEN.getCode())
                .orderByAsc(Channel::getLevel));

        Iterator<Channel> iterator = channelList.iterator();
        Channel firstChannel = null;
        if(iterator.hasNext()) {
            firstChannel = iterator.next();
        }

        //获取除第一通道的其他通道列表
        List<Channel> otherChannelList = this.list(Wrappers.<Channel>lambdaQuery()
                .ne(Channel::getId, firstChannel.getId())
                .eq(Channel::getChannelType, 1)
                .eq(Channel::getStatus, StatusEnum.OPEN.getCode())
                .orderByAsc(Channel::getLevel));

        DateTime dateTime = DateUtil.offsetHour(new Date(), -1);
        Date date = dateTime.toJdkDate();

        AtomicBoolean sendFlag = new AtomicBoolean(false);

        List<ChannelDTO> channelDTOList = new ArrayList<>();

        otherChannelList.forEach(channel -> {
            ChannelDTO channelDTO = dozerUtils.map(channel, ChannelDTO.class);
            List<SendLog> sendLogList = sendLogService.list(Wrappers.<SendLog>lambdaQuery()
                    .eq(SendLog::getChannelId, channel.getId())
                    .ge(SendLog::getCreatedAt, date)
                    .orderByDesc(SendLog::getCreatedAt));

            if(CollectionUtils.isNotEmpty(sendLogList)) {
                if(sendLogList.get(0).getStatus() == 1) {
                    channelDTO.setLastSuccessNumInAnHour(sendLogList.size());
                    sendFlag.set(true);
                }
            }
            channelDTOList.add(channelDTO);
        });

        //根据最近一小时发送的成功数与level进行排序
        channelDTOList.sort(
                Comparator.comparing(ChannelDTO::getLastSuccessNumInAnHour, Collections.reverseOrder())
                        .thenComparing(ChannelDTO::getLevel, Collections.reverseOrder()));
        log.info("ChannelServiceImpl|第一轮通道重排序后的状态|{}", channelDTOList);

        //如果最近一个小时没有发送成功的通道，就查找最后一次发送成功的通道进行排序
        if(!sendFlag.get()) {
            for (ChannelDTO channelDTO : channelDTOList) {
                SendLog sendLog = sendLogService.getOne(Wrappers.<SendLog>lambdaQuery()
                        .eq(SendLog::getChannelId, channelDTO.getId())
                        .orderByDesc(SendLog::getCreatedAt)
                        .last("LIMIT 1"));
                if(sendLog != null) {
                    channelDTO.setLastSuccessNum(1);
                }
            }

            channelDTOList.sort(
                    Comparator.comparing(ChannelDTO::getLastSuccessNum, Collections.reverseOrder())
                            .thenComparing(ChannelDTO::getLevel));
            log.info("ChannelServiceImpl|第二轮通道重排序后的状态|{}", channelDTOList);

        }

        //将当前的channel列表的level家1
        int level = 1;
        otherChannelList.clear();
        for (ChannelDTO channelDTO : channelDTOList) {
            Channel channel = dozerUtils.map(channelDTO, Channel.class);
            channel.setLevel(level++);
            otherChannelList.add(channel);
        }

        //查询不可用通道
        List<Channel> unActiveList = this.list(Wrappers.<Channel>lambdaQuery()
                .eq(Channel::getStatus, StatusEnum.OPEN.getCode())
                .ne(Channel::getId, firstChannel.getId())
                .orderByAsc(Channel::getLevel));

        for (Channel channel : unActiveList) {
            channel.setLevel(level++);
            otherChannelList.add(channel);
        }

        firstChannel.setLevel(99);
        firstChannel.setStatus(StatusEnum.CLOSE.getCode());
        otherChannelList.add(firstChannel);

        log.info("ChannelServiceImpl|通道重排成功|{}", otherChannelList);
        return otherChannelList;
    }
}
