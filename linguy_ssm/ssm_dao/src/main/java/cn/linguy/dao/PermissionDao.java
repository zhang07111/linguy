package cn.linguy.dao;

import cn.linguy.domain.Permission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionDao {

    //根据 role id 查询
    @Select("select * from permission where id in " +
            "(select permissionid from role_permission where roleid = #{id})")
    List<Permission> findByRoleId(String id);

    //查询所有信息
    @Select("select * from permission")
    List<Permission> findAll();

    //保存权限信息
    @Insert("insert into permission(permissionName,url) values(#{permissionName},#{url})")
    void save(Permission permission);
}
