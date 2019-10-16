package cn.linguy.dao;

import cn.linguy.domain.Role;
import cn.linguy.domain.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserDao {

    @Select("select * from users where username = #{username}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles", column = "id",
                    javaType = List.class, many = @Many(select = "cn.linguy.dao.RoleDao.findRoleByUserId"))
    })
    UserInfo findByUsername(String username);

    //查询所有用户信息
    @Select("select * from users")
    List<UserInfo> findAll();

    //保存用户信息
    @Insert("insert into users(email,username,password,phoneNum,status) values(#{email},#{username},#{password},#{phoneNum},#{status})")
    void save(UserInfo userInfo);

    @Select("select * from users where id = #{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles", column = "id", javaType = List.class,
                    many = @Many(select = "cn.linguy.dao.RoleDao.findRoleByUserId"))
    })
    UserInfo findById(String id);

    //查询用户可用的角色信息
    @Select("select * from role where id not in (select roleid from users_role where userid = #{userid})")
    List<Role> findRoleByUserId(String userid);

    //根据用户id添加该用户的角色信息
    @Insert("insert into users_role values(#{userid},#{roleid})")
    void addRoleToUser(@Param("userid") String userid, @Param("roleid") String roleid);
}
