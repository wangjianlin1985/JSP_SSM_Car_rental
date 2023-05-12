// 
// 
// 

package com.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.entity.Ri;

public interface RiDao
{
    void insertBean(Ri p0);
    
    void deleteBean(int p0);
    
    void updateBean(Ri p0);
    
    Ri selectBeanById(@Param("id") int p0);
    
    List<Ri> selectBeanList(@Param("start") int p0, @Param("limit") int p1, @Param("ri") String p2);
    
    int selectBeanCount(@Param("ri") String p0);
}
