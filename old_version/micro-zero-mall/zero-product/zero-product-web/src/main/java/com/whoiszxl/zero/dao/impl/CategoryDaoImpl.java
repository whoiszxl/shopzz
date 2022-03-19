package com.whoiszxl.zero.dao.impl;

import com.whoiszxl.zero.dao.CategoryDao;
import com.whoiszxl.zero.entity.Category;
import com.whoiszxl.zero.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findAll(Sort var1) {
        return categoryRepository.findAll(var1);
    }

    @Override
    public List<Category> findAllById(Iterable<Long> var1) {
        return categoryRepository.findAllById(var1);
    }

    @Override
    public <S extends Category> List<S> saveAll(Iterable<S> var1) {
        return categoryRepository.saveAll(var1);
    }

    @Override
    public void flush() {
        categoryRepository.flush();
    }

    @Override
    public <S extends Category> S saveAndFlush(S var1) {
        return categoryRepository.saveAndFlush(var1);
    }

    @Override
    public void deleteInBatch(Iterable<Category> var1) {
        categoryRepository.deleteInBatch(var1);
    }

    @Override
    public void deleteAllInBatch() {
        categoryRepository.deleteAllInBatch();
    }

    @Override
    public Category getOne(Long var1) {
        return categoryRepository.getOne(var1);
    }

    @Override
    public <S extends Category> List<S> findAll(Example<S> var1) {
        return categoryRepository.findAll(var1);
    }

    @Override
    public <S extends Category> List<S> findAll(Example<S> var1, Sort var2) {
        return categoryRepository.findAll(var1, var2);
    }
}
