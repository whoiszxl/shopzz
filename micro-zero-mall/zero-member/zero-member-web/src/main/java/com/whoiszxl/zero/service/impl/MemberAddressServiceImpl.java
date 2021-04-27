package com.whoiszxl.zero.service.impl;

import com.whoiszxl.zero.dao.MemberAddressDao;
import com.whoiszxl.zero.dao.MemberDao;
import com.whoiszxl.zero.dao.MemberInfoDao;
import com.whoiszxl.zero.entity.Member;
import com.whoiszxl.zero.entity.MemberAddress;
import com.whoiszxl.zero.entity.MemberInfo;
import com.whoiszxl.zero.entity.dto.MemberAddressDTO;
import com.whoiszxl.zero.entity.vo.MemberDetailVO;
import com.whoiszxl.zero.entity.vo.MemberInfoVO;
import com.whoiszxl.zero.entity.vo.MemberVO;
import com.whoiszxl.zero.exception.ExceptionCatcher;
import com.whoiszxl.zero.service.MemberAddressService;
import com.whoiszxl.zero.service.MemberService;
import com.whoiszxl.zero.utils.BeanCopierUtils;
import com.whoiszxl.zero.utils.DateProvider;
import com.whoiszxl.zero.utils.IdWorker;
import com.whoiszxl.zero.utils.JwtUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class MemberAddressServiceImpl implements MemberAddressService {

    @Autowired
    private MemberAddressDao memberAddressDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private DateProvider dateProvider;

    @Override
    public List<MemberAddressDTO> list() {
        List<MemberAddress> memberAddressList = memberAddressDao.findAllByMemberIdOrderByIsDefaultDesc(JwtUtils.getMemberId());
        return ObjectUtils.isEmpty(memberAddressList) ? null : BeanCopierUtils.copyListProperties(memberAddressList, MemberAddressDTO::new);
    }

    @Override
    public void save(MemberAddressDTO memberAddressDTO) {
        MemberAddress memberAddress = memberAddressDTO.clone(MemberAddress.class);
        memberAddress.setId(idWorker.nextId());
        memberAddress.setMemberId(JwtUtils.getMemberId());

        Date now = dateProvider.dateNow();
        memberAddress.setCreatedAt(now);
        memberAddress.setUpdatedAt(now);
        memberAddressDao.saveAndFlush(memberAddress);
    }

    @Override
    public void update(MemberAddressDTO memberAddressDTO) {
        Long memberId = JwtUtils.getMemberId();
        MemberAddress memberAddress = memberAddressDao.findByIdAndMemberId(memberAddressDTO.getId(), memberId);
        if(memberAddress == null) {
            ExceptionCatcher.catchAuthFailEx();
        }
        MemberAddress saveMemberAddress = memberAddressDTO.clone(MemberAddress.class);
        saveMemberAddress.setUpdatedAt(dateProvider.dateNow());
        memberAddressDao.saveAndFlush(saveMemberAddress);
    }

    @Override
    public void delete(Long id) {
        Long memberId = JwtUtils.getMemberId();
        MemberAddress memberAddress = memberAddressDao.findByIdAndMemberId(id, memberId);
        if(memberAddress == null) {
            ExceptionCatcher.catchAuthFailEx();
        }
        memberAddressDao.deleteById(id);
    }
}
