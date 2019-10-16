package cn.linguy.controller;

import cn.linguy.domain.Role;
import cn.linguy.domain.UserInfo;
import cn.linguy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAll")
    public ModelAndView findAll() {
        ModelAndView mav = new ModelAndView();
        List<UserInfo> userInfos = userService.findAll();
        mav.addObject("userList", userInfos);
        mav.setViewName("user-list");
        return mav;
    }

    @RequestMapping("/save")
    public String save(UserInfo userInfo) {
        userService.save(userInfo);
        return "redirect:findAll";
    }

    @RequestMapping("/findById")
    public ModelAndView findById(String id) {
        ModelAndView mav = new ModelAndView();
        UserInfo userInfo = userService.findById(id);
        mav.addObject("user", userInfo);
        mav.setViewName("user-show1");
        return mav;
    }

    @RequestMapping("/findUserByIdAndAllRole")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name = "id", required = true) String userid) {
        ModelAndView mav = new ModelAndView();
        //根据id 查询用户信息
        UserInfo userInfo = userService.findById(userid);
        //根据id 查询可选择的角色信息
        List<Role> roles = userService.findRoleByUserId(userid);
        mav.addObject("user", userInfo);
        mav.addObject("roleList", roles);
        mav.setViewName("user-role-add");
        return mav;
    }

    @RequestMapping("/addRoleToUser")
    public String addRoleToUser(@RequestParam(name = "userId", required = true) String userid,
                                @RequestParam(name = "ids", required = true) String[] roleids) {
        userService.addRoleToUser(userid, roleids);

        return "redirect:findAll";
    }
}
