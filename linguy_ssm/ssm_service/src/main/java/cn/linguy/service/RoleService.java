package cn.linguy.service;

import cn.linguy.domain.Permission;
import cn.linguy.domain.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    void save(Role role);

    Role findById(String roleid);

    List<Permission> findPermissionByRoleId(String roleid);

    void addPermissionToRole(String roleId, String[] permissions);

}
