package com.whoiszxl.zero.dao.impl;

import com.whoiszxl.zero.dao.RecommendDao;
import com.whoiszxl.zero.entity.Recommend;
import com.whoiszxl.zero.repository.RecommendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品推荐 dao实现
 *
 * @author whoiszxl
 * @date 2021/4/9
 */
@Repository
public class RecommendDaoImpl implements RecommendDao {

    @Autowired
    private RecommendRepository recommendRepository;


    @Override
    public Page<Recommend> findAll(Specification<Recommend> var1, Pageable var2) {
        return recommendRepository.findAll(var1, var2);
    }

    @Override
    public List<Recommend> findAll() {
        return recommendRepository.findAll();
    }

    @Override
    public List<Recommend> findAll(Sort var1) {
        return recommendRepository.findAll(var1);
    }

    @Override
    public List<Recommend> findAllById(Iterable<Long> var1) {
        return recommendRepository.findAllById(var1);
    }

    @Override
    public <S extends Recommend> List<S> saveAll(Iterable<S> var1) {
        return recommendRepository.saveAll(var1);
    }

    @Override
    public void flush() {
        recommendRepository.flush();
    }

    @Override
    public <S extends Recommend> S saveAndFlush(S var1) {
        return recommendRepository.saveAndFlush(var1);
    }

    @Override
    public void deleteInBatch(Iterable<Recommend> var1) {
        recommendRepository.deleteInBatch(var1);
    }

    @Override
    public void deleteAllInBatch() {
        recommendRepository.deleteAllInBatch();
    }

    @Override
    public Recommend getOne(Long var1) {
        return recommendRepository.getOne(var1);
    }

    @Override
    public <S extends Recommend> List<S> findAll(Example<S> var1) {
        return recommendRepository.findAll(var1);
    }

    @Override
    public <S extends Recommend> List<S> findAll(Example<S> var1, Sort var2) {
        return recommendRepository.findAll(var1, var2);
    }

}
