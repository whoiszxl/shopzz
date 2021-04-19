package com.whoiszxl.zero.service.impl;

import com.whoiszxl.zero.dao.CartDao;
import com.whoiszxl.zero.entity.dto.CartDTO;
import com.whoiszxl.zero.entity.params.CartAddParam;
import com.whoiszxl.zero.entity.params.CartDeleteParam;
import com.whoiszxl.zero.entity.params.CartQuantityUpdateParam;
import com.whoiszxl.zero.service.CartService;
import com.whoiszxl.zero.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    @Override
    public int deleteAll() {
        JwtUtils.getUsername()
        return 0;
    }

    @Override
    public int delete(CartDeleteParam cartDeleteParam) {
        return 0;
    }

    @Override
    public int quantityUpdate(CartQuantityUpdateParam cartQuantityUpdateParam) {
        return 0;
    }

    @Override
    public int add(CartAddParam cartAddParam) {
        return 0;
    }

    @Override
    public List<CartDTO> list() {
        return null;
    }
}
