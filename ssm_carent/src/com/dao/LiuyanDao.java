// 
// 
// 

package com.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.entity.Liuyan;

public interface LiuyanDao
{
    void insertBean(Liuyan p0);
    
    void deleteBean(int p0);
    
    void updateBean(Liuyan p0);
    
    Liuyan selectBeanById(@Param("id") int p0);
    
    List<Liuyan> selectBeanList(@Param("start") int p0, @Param("limit") int p1, @Param("ltitle") String p2, @Param("zhuangtai") String p3, @Param("kehuid") int p4, @Param("workid") int p5);
    
    int selectBeanCount(@Param("ltitle") String p0, @Param("zhuangtai") String p1, @Param("kehuid") int p2, @Param("workid") int p3);
}
