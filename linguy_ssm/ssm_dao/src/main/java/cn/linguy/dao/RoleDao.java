package cn.linguy.dao;

import cn.linguy.domain.Permission;
import cn.linguy.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleDao {

    //根据用户id 查询账号对应的角色信息
    @Select("select * from role where  id in (select roleId from users_role where userid = #{userId})")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "roleName", column = "roleName"),
            @Result(property = "roleDesc", column = "roleDesc"),
            @Result(property = "permissions", column = "id", javaType = List.class,
                    many = @Many(select = "cn.linguy.dao.PermissionDao.findByRoleId"))
    })
    List<Role> findRoleByUserId(String userId);

    //查询所有角色
    @Select("select * from role")
    List<Role> findAll();

    //保存用户信息
    @Insert("insert into role(rolename,roledesc) values(#{roleName},#{roleDesc})")
    void save(Role role);

    //根据 id 查询 role角色
    @Select("select * from role where id = #{roleid}")
    Role findRoleById(String roleid);

    //根据 role查询可用的权限信息
    @Select("select * from permission where id not in" +
            "(select permissionId from role_permission where roleId = #{roleid} )")
    List<Permission> findPermissionByRoleId(String roleid);

    // 让 roleId 和 权限 关联
    @Insert("insert into role_permission values(#{permission},#{roleId})")
    void addPermissionToRole(@Param("roleId") String roleId, @Param("permission") String permission);
}
