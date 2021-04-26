package com.whoiszxl.zero.dao.impl;

import com.whoiszxl.zero.dao.CartDao;
import com.whoiszxl.zero.entity.Cart;
import com.whoiszxl.zero.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDaoImpl implements CartDao {

    @Autowired
    private CartRepository cartRepository;


    @Override
    public int deleteCartsByMemberId(Long memberId) {
        return cartRepository.deleteCartsByMemberId(memberId);
    }

    @Override
    public int deleteCartByIdAndMemberId(Long id, Long memberId) {
        return cartRepository.deleteCartByIdAndMemberId(id, memberId);
    }

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public List<Cart> findAll(Sort var1) {
        return cartRepository.findAll(var1);
    }

    @Override
    public List<Cart> findAllById(Iterable<Long> var1) {
        return cartRepository.findAllById(var1);
    }

    @Override
    public <S extends Cart> List<S> saveAll(Iterable<S> var1) {
        return cartRepository.saveAll(var1);
    }

    @Override
    public void flush() {
        cartRepository.flush();
    }

    @Override
    public <S extends Cart> S saveAndFlush(S var1) {
        return cartRepository.saveAndFlush(var1);
    }

    @Override
    public void deleteInBatch(Iterable<Cart> var1) {
        cartRepository.deleteInBatch(var1);
    }

    @Override
    public void deleteAllInBatch() {
        cartRepository.deleteAllInBatch();
    }

    @Override
    public Cart getOne(Long var1) {
        return cartRepository.getOne(var1);
    }

    @Override
    public <S extends Cart> List<S> findAll(Example<S> var1) {
        return cartRepository.findAll(var1);
    }

    @Override
    public <S extends Cart> List<S> findAll(Example<S> var1, Sort var2) {
        return cartRepository.findAll(var1, var2);
    }
}
