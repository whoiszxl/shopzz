package com.whoiszxl.zero.dao.impl;

import com.whoiszxl.zero.dao.HomeBannerDao;
import com.whoiszxl.zero.entity.HomeBanner;
import com.whoiszxl.zero.repository.HomeBannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 主页banner dao实现
 *
 * @author whoiszxl
 * @date 2021/4/9
 */
@Repository
public class HomeBannerDaoImpl implements HomeBannerDao {

    @Autowired
    private HomeBannerRepository homeBannerRepository;


    @Override
    public List<HomeBanner> findAllByTypeAndStatusOrderBySortDesc(Integer type, Integer status) {
        return homeBannerRepository.findAllByTypeAndStatusOrderBySortDesc(type, status);
    }

    @Override
    public List<HomeBanner> findAll() {
        return homeBannerRepository.findAll();
    }

    @Override
    public List<HomeBanner> findAll(Sort var1) {
        return homeBannerRepository.findAll(var1);
    }

    @Override
    public List<HomeBanner> findAllById(Iterable<Long> var1) {
        return homeBannerRepository.findAllById(var1);
    }

    @Override
    public <S extends HomeBanner> List<S> saveAll(Iterable<S> var1) {
        return homeBannerRepository.saveAll(var1);
    }

    @Override
    public void flush() {
        homeBannerRepository.flush();
    }

    @Override
    public <S extends HomeBanner> S saveAndFlush(S var1) {
        return homeBannerRepository.saveAndFlush(var1);
    }

    @Override
    public void deleteInBatch(Iterable<HomeBanner> var1) {
        homeBannerRepository.deleteInBatch(var1);
    }

    @Override
    public void deleteAllInBatch() {
        homeBannerRepository.deleteAllInBatch();
    }

    @Override
    public HomeBanner getOne(Long var1) {
        return homeBannerRepository.getOne(var1);
    }

    @Override
    public <S extends HomeBanner> List<S> findAll(Example<S> var1) {
        return homeBannerRepository.findAll(var1);
    }

    @Override
    public <S extends HomeBanner> List<S> findAll(Example<S> var1, Sort var2) {
        return homeBannerRepository.findAll(var1, var2);
    }
}
