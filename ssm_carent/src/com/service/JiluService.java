// 
// 
// 

package com.service;

import java.util.List;
import com.entity.Jilu;

public interface JiluService
{
    void insertBean(Jilu p0);
    
    void deleteBean(int p0);
    
    void updateBean(Jilu p0);
    
    Jilu selectBeanById(int p0);
    
    List<Jilu> selectBeanList(int p0, int p1, String p2, String p3, String p4, String p5, int p6, int p7, int p8);
    
    int selectBeanCount(String p0, String p1, String p2, String p3, int p4, int p5, int p6);
}
