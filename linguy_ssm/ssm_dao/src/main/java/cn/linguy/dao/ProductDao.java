package cn.linguy.dao;

import cn.linguy.domain.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface ProductDao {

    /**
     * 根据id 去查询产品信息
     *
     * @param id
     * @return
     */
    @Select("select * from product where id = #{id}")
    Product findById(String id);

    /**
     * 查询所有信息
     *
     * @return
     * @throws Exception
     */
    @Select("select * from product")
    List<Product> findAll();

    /**
     * 保存产品信息
     *
     * @param product
     */
    @Insert("insert into product(productNum,productName,cityName,departureTime,productPrice,productDesc,productStatus) " +
            "values(#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
    void saveProduct(Product product);
}
