// 
// 
// 

package com.service;

import java.util.List;
import com.entity.Yuding;

public interface YudingService
{
    void insertBean(Yuding p0);
    
    void deleteBean(int p0);
    
    void updateBean(Yuding p0);
    
    Yuding selectBeanById(int p0);
    
    List<Yuding> selectBeanList(int p0, int p1, String p2, String p3, int p4, int p5, String p6);
    
    int selectBeanCount(String p0, String p1, int p2, int p3, String p4);
}
