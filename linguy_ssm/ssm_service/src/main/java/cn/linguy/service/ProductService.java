package cn.linguy.service;

import cn.linguy.domain.Product;

import java.util.List;

public interface ProductService {
    /**
     * 查询所有产品信息
     *
     * @return
     */
    List<Product> findAll();

    /**
     * 保存产品信息
     *
     * @param product
     */
    void saveProduct(Product product);
}
