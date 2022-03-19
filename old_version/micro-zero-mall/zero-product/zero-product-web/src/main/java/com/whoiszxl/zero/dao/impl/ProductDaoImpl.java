package com.whoiszxl.zero.dao.impl;

import com.whoiszxl.zero.dao.ProductDao;
import com.whoiszxl.zero.entity.Product;
import com.whoiszxl.zero.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Page<Product> findAll(Specification<Product> var1, Pageable var2) {
        return productRepository.findAll(var1, var2);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findAll(Sort var1) {
        return productRepository.findAll(var1);
    }

    @Override
    public List<Product> findAllById(Iterable<Long> var1) {
        return productRepository.findAllById(var1);
    }

    @Override
    public <S extends Product> List<S> saveAll(Iterable<S> var1) {
        return productRepository.saveAll(var1);
    }

    @Override
    public void flush() {
        productRepository.flush();
    }

    @Override
    public <S extends Product> S saveAndFlush(S var1) {
        return productRepository.saveAndFlush(var1);
    }

    @Override
    public void deleteInBatch(Iterable<Product> var1) {
        productRepository.deleteInBatch(var1);
    }

    @Override
    public void deleteAllInBatch() {
        productRepository.deleteAllInBatch();
    }

    @Override
    @Transactional
    public Product getOne(Long var1) {
        return productRepository.getOne(var1);
    }

    @Override
    public <S extends Product> List<S> findAll(Example<S> var1) {
        return productRepository.findAll(var1);
    }

    @Override
    public <S extends Product> List<S> findAll(Example<S> var1, Sort var2) {
        return productRepository.findAll(var1, var2);
    }
}
