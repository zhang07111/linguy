package cn.linguy.service;

import cn.linguy.domain.Role;
import cn.linguy.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService extends UserDetailsService {

    //查询所有用户
    List<UserInfo> findAll();

    void save(UserInfo userInfo);

    //根据用户id查询
    UserInfo findById(String id);

    List<Role> findRoleByUserId(String userid);

    void addRoleToUser(String userid, String[] roleids);
}
