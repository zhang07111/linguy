package cn.linguy.service.impl;

import cn.linguy.dao.RoleDao;
import cn.linguy.dao.UserDao;
import cn.linguy.domain.Role;
import cn.linguy.domain.UserInfo;
import cn.linguy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserDao userDao;

    //注入加密bean
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo userInfo = userDao.findByUsername(username);
//        User user = new User(userInfo.getUsername(), "{noop}" + userInfo.getPassword(), getAuthorities(userInfo.getRoles()));
        User user = new User(userInfo.getUsername(), userInfo.getPassword(), userInfo.getStatus() == 0 ? false : true,
                true, true, true, getAuthorities(userInfo.getRoles()));
        return user;
    }

    //作用就是返回一个list集合,集合装入的是角色描述
    public List<SimpleGrantedAuthority> getAuthorities(List<Role> roles) {

        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role r :
                roles) {
            list.add(new SimpleGrantedAuthority("ROLE_" + r.getRoleName()));
        }

        return list;
    }

    //查询所有用户信息
    @Override
    public List<UserInfo> findAll() {

        return userDao.findAll();
    }

    //保存用户
    @Override
    public void save(UserInfo userInfo) {
        //把密码进行加密
        userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        userDao.save(userInfo);
    }

    //根据id查询用户
    @Override
    public UserInfo findById(String id) {
        return userDao.findById(id);
    }

    //根据userid 查询可以使用的角色信息
    @Override
    public List<Role> findRoleByUserId(String userid) {
        return userDao.findRoleByUserId(userid);
    }

    //给用户添加角色信息
    @Override
    public void addRoleToUser(String userid, String[] roleids) {

        for (String roleid :
                roleids) {
            userDao.addRoleToUser(userid, roleid);
        }
    }
}
