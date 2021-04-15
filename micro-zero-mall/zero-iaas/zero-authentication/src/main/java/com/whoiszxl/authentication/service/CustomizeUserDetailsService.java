package com.whoiszxl.authentication.service;

import com.whoiszxl.authentication.constants.LoginConstant;
import com.whoiszxl.authentication.entity.Member;
import com.whoiszxl.authentication.entity.SysPrivilege;
import com.whoiszxl.authentication.entity.SysUser;
import com.whoiszxl.authentication.repository.MemberRepository;
import com.whoiszxl.authentication.repository.SysPrivilegeRepository;
import com.whoiszxl.authentication.repository.SysUserRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;
import java.util.stream.Collectors;

import static com.whoiszxl.authentication.constants.LoginConstant.ADMIN_ROLE_CODE;

/**
 * 自定义读数据库的UserDetailsService
 *
 * @author whoiszxl
 * @date 2021/4/8
 */
@Service
public class CustomizeUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysPrivilegeRepository sysPrivilegeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes == null) {
            throw new AuthenticationServiceException(LoginConstant.LOGIN_TYPE + "参数不存在");
        }

        String loginType = requestAttributes.getRequest().getParameter(LoginConstant.LOGIN_TYPE);
        if(StringUtils.isBlank(loginType)) {
            throw new AuthenticationServiceException(LoginConstant.LOGIN_TYPE + "参数不存在");
        }

        UserDetails userDetails = null;
        switch (loginType) {
            case LoginConstant.ADMIN_TYPE:
                userDetails = loadAdminByUsername(username);
                break;
            case LoginConstant.MEMBER_TYPE:
                userDetails = loadMemberByUsername(username);
                break;
            default:
                throw new AuthenticationServiceException("暂不支持的登录方式: " + loginType);
        }
        return userDetails;
    }

    private UserDetails loadMemberByUsername(String username) {
        Member member = memberRepository.findByUsernameOrPhoneOrEmailAndStatus(username, username, username, 1);
        return new User(
                String.valueOf(member.getId()),
                member.getPassword(),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("ROLE_MEMBER"))
        );
    }

    private UserDetails loadAdminByUsername(String username) {
        SysUser sysUser = sysUserRepository.findByUsernameAndStatus(username, 1);
        if(ObjectUtils.isNotEmpty(sysUser)) {
            Collection authorities;
            User user = new User(String.valueOf(sysUser.getId()),
                    sysUser.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    getSysUserPermissions(sysUser.getId()));

            return user;
        }
        return null;
    }

    /**
     * 通過用户ID获取用户权限
     * @param userId
     * @return
     */
    private Set<SimpleGrantedAuthority> getSysUserPermissions(Long userId) {
        String roleCode = sysUserRepository.getRoleCode(userId);

        List<SysPrivilege> privileges;

        if(ADMIN_ROLE_CODE.equals(roleCode)) {
            privileges = sysPrivilegeRepository.findAll();
        }else {
            privileges = sysPrivilegeRepository.findByUserId(userId);
        }

        if(ObjectUtils.isEmpty(privileges)) {
            return Collections.emptySet();
        }

        return privileges
                .stream()
                .distinct()
                .map( privilege -> new SimpleGrantedAuthority(privilege.getName()))
                .collect(Collectors.toSet());
    }
}
