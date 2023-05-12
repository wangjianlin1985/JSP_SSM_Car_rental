// 
// 
// 

package com.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.entity.Pic;

public interface PicDao
{
    void insertBean(Pic p0);
    
    void deleteBean(int p0);
    
    void updateBean(Pic p0);
    
    Pic selectBeanById(@Param("id") int p0);
    
    List<Pic> selectBeanList(@Param("start") int p0, @Param("limit") int p1);
    
    int selectBeanCount();
}
