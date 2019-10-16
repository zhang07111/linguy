package cn.linguy.service.impl;

import cn.linguy.dao.RoleDao;
import cn.linguy.domain.Permission;
import cn.linguy.domain.Role;
import cn.linguy.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    public Role findById(String roleid) {
        return roleDao.findRoleById(roleid);
    }

    @Override
    public List<Permission> findPermissionByRoleId(String roleid) {
        return roleDao.findPermissionByRoleId(roleid);
    }

    @Override
    public void addPermissionToRole(String roleId, String[] permissions) {

        for (String permission :
                permissions) {
            roleDao.addPermissionToRole(roleId, permission);
        }
    }
}
