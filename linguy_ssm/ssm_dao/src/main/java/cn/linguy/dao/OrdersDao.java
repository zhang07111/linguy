package cn.linguy.dao;

import cn.linguy.domain.Member;
import cn.linguy.domain.Orders;
import cn.linguy.domain.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersDao {

    @Select("select * from orders")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "orderNum", column = "orderNum"),
            @Result(property = "orderTime", column = "orderTime"),
            @Result(property = "orderStatus", column = "orderStatus"),
            @Result(property = "peopleCount", column = "peopleCount"),
            @Result(property = "payType", column = "payType"),
            @Result(property = "orderDesc", column = "orderDesc"),
            @Result(property = "product", column = "productId", javaType = Product.class,
                    one = @One(select = "cn.linguy.dao.ProductDao.findById"))
    })
    List<Orders> findAll();

    @Select("select * from orders where id = #{ordersId}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "orderNum", column = "orderNum"),
            @Result(property = "orderTime", column = "orderTime"),
            @Result(property = "orderStatus", column = "orderStatus"),
            @Result(property = "peopleCount", column = "peopleCount"),
            @Result(property = "payType", column = "payType"),
            @Result(property = "orderDesc", column = "orderDesc"),
            @Result(property = "product", column = "productId", javaType = Product.class,
                    one = @One(select = "cn.linguy.dao.ProductDao.findById")
            ),
            @Result(property = "member", column = "memberId", javaType = Member.class,
                    one = @One(select = "cn.linguy.dao.MemberDao.findById")
            ),
            @Result(property = "travellers", column = "id", javaType = List.class,
                    many = @Many(select = "cn.linguy.dao.TravellerDao.findById")
            )
    })
    Orders findById(String ordersId);
}
