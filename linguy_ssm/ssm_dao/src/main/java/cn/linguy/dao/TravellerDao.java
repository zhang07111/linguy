package cn.linguy.dao;

import cn.linguy.domain.Traveller;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TravellerDao {

    @Select("select * from traveller where id in (select TRAVELLERID from order_traveller where ORDERID = #{id})")
    List<Traveller> findById(String id);

}
