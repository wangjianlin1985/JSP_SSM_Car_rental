// 
// 
// 

package com.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.entity.Vip;

public interface VipDao
{
    void insertBean(Vip p0);
    
    void deleteBean(int p0);
    
    void updateBean(Vip p0);
    
    Vip selectBeanByUsername(@Param("username") String p0);
    
    Vip selectBeanByKehuid(@Param("kehuid") int p0);
    
    List<Vip> selectBeanList(@Param("start") int p0, @Param("limit") int p1, @Param("username") String p2, @Param("points") int p3, @Param("level") int p4, @Param("enddate") String p5, @Param("consumed") Double p6, @Param("kehuid") int p7);
    
    int selectBeanCount(@Param("username") String p0);
}
