// 
// 
// 

package com.service;

import java.util.List;
import com.entity.Liuyan;

public interface LiuyanService
{
    void insertBean(Liuyan p0);
    
    void deleteBean(int p0);
    
    void updateBean(Liuyan p0);
    
    Liuyan selectBeanById(int p0);
    
    List<Liuyan> selectBeanList(int p0, int p1, String p2, String p3, int p4, int p5);
    
    int selectBeanCount(String p0, String p1, int p2, int p3);
}
