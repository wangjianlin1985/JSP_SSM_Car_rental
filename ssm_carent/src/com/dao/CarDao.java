// 
// 
// 

package com.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.entity.Car;

public interface CarDao
{
    void insertBean(Car p0);
    
    void deleteBean(int p0);
    
    void updateBean(Car p0);
    
    Car selectBeanById(@Param("id") int p0);
    
    List<Car> selectBeanList(@Param("start") int p0, @Param("limit") int p1, @Param("chepai") String p2, @Param("pinpai") String p3, @Param("xinghao") String p4, @Param("yanse") String p5);
    
    int selectBeanCount(@Param("chepai") String p0, @Param("pinpai") String p1, @Param("xinghao") String p2, @Param("yanse") String p3);
}
