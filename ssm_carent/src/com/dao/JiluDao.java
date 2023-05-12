// 
// 
// 

package com.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.entity.Jilu;

public interface JiluDao
{
    void insertBean(Jilu p0);
    
    void deleteBean(int p0);
    
    void updateBean(Jilu p0);
    
    Jilu selectBeanById(@Param("id") int p0);
    
    List<Jilu> selectBeanList(@Param("start") int p0, @Param("limit") int p1, @Param("chepai") String p2, @Param("sfz") String p3, @Param("xingming") String p4, @Param("zhuangtai") String p5, @Param("workid") int p6, @Param("jishuid") int p7, @Param("kehuid") int p8);
    
    int selectBeanCount(@Param("chepai") String p0, @Param("sfz") String p1, @Param("xingming") String p2, @Param("zhuangtai") String p3, @Param("workid") int p4, @Param("jishuid") int p5, @Param("kehuid") int p6);
}
