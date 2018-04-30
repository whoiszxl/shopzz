package com.whoiszxl.repo.primary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whoiszxl.entity.User;

/**
 * user表数据库层操作
 * @author Administrator
 *
 */
public interface UserRepository extends JpaRepository<User, Integer>{

}
