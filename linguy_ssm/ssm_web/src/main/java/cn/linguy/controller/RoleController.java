package cn.linguy.controller;

import cn.linguy.domain.Permission;
import cn.linguy.domain.Role;
import cn.linguy.service.RoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/findAll")
    public ModelAndView findAll() {
        ModelAndView mav = new ModelAndView();
        List<Role> roles = roleService.findAll();
        mav.addObject("roleList", roles);
        mav.setViewName("role-list");
        return mav;
    }

    @RequestMapping("/save")
    public String save(Role role) {
        roleService.save(role);

        return "redirect:findAll";
    }

    @RequestMapping("/findRoleToPermission")
    public ModelAndView findRoleToPermission(@RequestParam(name = "id") String roleid) {
        ModelAndView mav = new ModelAndView();
        //跟据roleid 查询role
        Role role = roleService.findById(roleid);
        //根据 roleid 查询role可以添加的权限
        List<Permission> permissions = roleService.findPermissionByRoleId(roleid);
        mav.addObject("role", role);
        mav.addObject("PermissionList", permissions);
        mav.setViewName("role-permission-add");
        return mav;
    }

    @RequestMapping("/addPermissionToRole")
    public String addPermissionToRole(@RequestParam(name = "roleId") String roleId,
                                      @RequestParam(name = "ids") String[] permissions) {
        roleService.addPermissionToRole(roleId, permissions);

        return "redirect:findAll";
    }

}
