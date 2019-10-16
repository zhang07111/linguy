package cn.linguy.controller;


import cn.linguy.domain.Product;
import cn.linguy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @RequestMapping("/saveProduct")
    @PreAuthorize("authentication.principal.username=='zhangsan'")
    public String saveProduct(Product product) {
        productService.saveProduct(product);
        return "redirect:findAll";
    }

    @RequestMapping("/findAll")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ModelAndView findAll() {
        ModelAndView mav = new ModelAndView();

        List<Product> products = productService.findAll();

        mav.addObject("productList", products);
        mav.setViewName("product-list1");
        return mav;
    }

}
