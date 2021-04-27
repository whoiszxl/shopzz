package com.whoiszxl.zero.dao.impl;

import com.whoiszxl.zero.dao.MemberAddressDao;
import com.whoiszxl.zero.entity.MemberAddress;
import com.whoiszxl.zero.repository.MemberAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberAddressDaoImpl implements MemberAddressDao {

    @Autowired
    private MemberAddressRepository memberAddressRepository;


    @Override
    public List<MemberAddress> findAllByMemberIdOrderByIsDefaultDesc(Long memberId) {
        return memberAddressRepository.findAllByMemberIdOrderByIsDefaultDesc(memberId);
    }

    @Override
    public void deleteById(Long id) {
        memberAddressRepository.deleteById(id);
    }

    @Override
    public MemberAddress findByIdAndMemberId(Long id, Long memberId) {
        return memberAddressRepository.findByIdAndMemberId(id, memberId);
    }

    @Override
    public List<MemberAddress> findAll() {
        return memberAddressRepository.findAll();
    }

    @Override
    public List<MemberAddress> findAll(Sort var1) {
        return memberAddressRepository.findAll(var1);
    }

    @Override
    public List<MemberAddress> findAllById(Iterable<Long> var1) {
        return memberAddressRepository.findAllById(var1);
    }

    @Override
    public <S extends MemberAddress> List<S> saveAll(Iterable<S> var1) {
        return memberAddressRepository.saveAll(var1);
    }

    @Override
    public void flush() {
        memberAddressRepository.flush();
    }

    @Override
    public <S extends MemberAddress> S saveAndFlush(S var1) {
        return memberAddressRepository.saveAndFlush(var1);
    }

    @Override
    public void deleteInBatch(Iterable<MemberAddress> var1) {
        memberAddressRepository.deleteInBatch(var1);
    }

    @Override
    public void deleteAllInBatch() {
        memberAddressRepository.deleteAllInBatch();
    }

    @Override
    public MemberAddress getOne(Long var1) {
        return memberAddressRepository.getOne(var1);
    }

    @Override
    public <S extends MemberAddress> List<S> findAll(Example<S> var1) {
        return memberAddressRepository.findAll(var1);
    }

    @Override
    public <S extends MemberAddress> List<S> findAll(Example<S> var1, Sort var2) {
        return memberAddressRepository.findAll(var1, var2);
    }
}
