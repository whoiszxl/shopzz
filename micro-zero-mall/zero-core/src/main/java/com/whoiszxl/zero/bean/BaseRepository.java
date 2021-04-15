package com.whoiszxl.zero.bean;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * JPA repository 基类
 *
 * @author whoiszxl
 * @date 2021/3/17
 */
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T,Long>, JpaSpecificationExecutor<T>, PagingAndSortingRepository<T, Long> {
}