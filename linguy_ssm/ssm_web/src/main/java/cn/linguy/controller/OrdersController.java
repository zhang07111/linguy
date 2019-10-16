package cn.linguy.controller;

import cn.linguy.domain.Orders;
import cn.linguy.service.OrdersService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    //查询全部订单 未分页
//    @RequestMapping("/findAll")
//    public ModelAndView findOrders() {
//        ModelAndView mav = new ModelAndView();
//
//        List<Orders> orders = ordersService.findAll();
//
//        mav.addObject("ordersList", orders);
//        mav.setViewName("orders-List");
//        return mav;
//    }

    @RequestMapping("/findAll")
    public ModelAndView findOrders(@RequestParam(name = "page", required = true, defaultValue = "1") int page,
                                   @RequestParam(name = "size", required = true, defaultValue = "5") int size) {
        ModelAndView mav = new ModelAndView();
        List<Orders> orders = ordersService.findAll(page, size);
        //就是一个分页的bean
        PageInfo pageInfo = new PageInfo(orders);
        mav.addObject("pageInfo", pageInfo);
        mav.setViewName("orders-page-list");

        return mav;
    }

    @RequestMapping("/findById")
    public ModelAndView findById(@RequestParam(name = "id", required = true) String ordersId) {
        ModelAndView mav = new ModelAndView();
        Orders orders = ordersService.findById(ordersId);
        mav.addObject("orders",orders);
        mav.setViewName("orders-Show");
        return mav;
    }
}
