package com.whoiszxl.zero.dao.impl;

import com.whoiszxl.zero.dao.BrandDao;
import com.whoiszxl.zero.entity.Brand;
import com.whoiszxl.zero.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BrandDaoImpl implements BrandDao {

    @Autowired
    private BrandRepository brandRepository;



    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public List<Brand> findAll(Sort var1) {
        return brandRepository.findAll(var1);
    }

    @Override
    public List<Brand> findAllById(Iterable<Long> var1) {
        return brandRepository.findAllById(var1);
    }

    @Override
    public <S extends Brand> List<S> saveAll(Iterable<S> var1) {
        return brandRepository.saveAll(var1);
    }

    @Override
    public void flush() {
        brandRepository.flush();
    }

    @Override
    public <S extends Brand> S saveAndFlush(S var1) {
        return brandRepository.saveAndFlush(var1);
    }

    @Override
    public void deleteInBatch(Iterable<Brand> var1) {
        brandRepository.deleteInBatch(var1);
    }

    @Override
    public void deleteAllInBatch() {
        brandRepository.deleteAllInBatch();
    }

    @Override
    public Brand getOne(Long var1) {
        return brandRepository.getOne(var1);
    }

    @Override
    public <S extends Brand> List<S> findAll(Example<S> var1) {
        return brandRepository.findAll(var1);
    }

    @Override
    public <S extends Brand> List<S> findAll(Example<S> var1, Sort var2) {
        return brandRepository.findAll(var1, var2);
    }
}
