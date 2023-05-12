// 
// 
// 

package com.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.entity.Yuding;

public interface YudingDao
{
    void insertBean(Yuding p0);
    
    void deleteBean(int p0);
    
    void updateBean(Yuding p0);
    
    Yuding selectBeanById(@Param("id") int p0);
    
    List<Yuding> selectBeanList(@Param("start") int p0, @Param("limit") int p1, @Param("chepai") String p2, @Param("sfz") String p3, @Param("kehuid") int p4, @Param("workid") int p5, @Param("zhuangtai") String p6);
    
    int selectBeanCount(@Param("chepai") String p0, @Param("sfz") String p1, @Param("kehuid") int p2, @Param("workid") int p3, @Param("zhuangtai") String p4);
}
