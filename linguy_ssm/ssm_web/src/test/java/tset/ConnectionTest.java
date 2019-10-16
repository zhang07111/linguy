package tset;

import cn.linguy.domain.Product;
import cn.linguy.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ConnectionTest {
    @Autowired
    private ProductService productService;
    @Test
    public void test(){
        List<Product> all = productService.findAll();
        System.out.println(all);
    }
}
