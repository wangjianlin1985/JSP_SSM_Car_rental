// 
// 
// 

package com.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.entity.Gonggao;

public interface GonggaoDao
{
    void insertBean(Gonggao p0);
    
    void deleteBean(int p0);
    
    void updateBean(Gonggao p0);
    
    Gonggao selectBeanById(@Param("id") int p0);
    
    List<Gonggao> selectBeanList(@Param("start") int p0, @Param("limit") int p1, @Param("gbiaoti") String p2);
    
    int selectBeanCount(@Param("gbiaoti") String p0);
}
