// 
// 
// 

package com.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.entity.Yue;

public interface YueDao
{
    void insertBean(Yue p0);
    
    void deleteBean(int p0);
    
    void updateBean(Yue p0);
    
    Yue selectBeanById(@Param("id") int p0);
    
    List<Yue> selectBeanList(@Param("start") int p0, @Param("limit") int p1, @Param("month") String p2);
    
    int selectBeanCount(@Param("month") String p0);
}
