// 
// 
// 

package com.service;

import java.util.List;
import com.entity.Vip;

public interface VipService
{
    void insertBean(Vip p0);
    
    void deleteBean(int p0);
    
    void updateBean(Vip p0);
    
    Vip selectBeanByUsername(String p0);
    
    Vip selectBeanByKehuid(int p0);
    
    List<Vip> selectBeanList(int p0, int p1, String p2, int p3, int p4, String p5, Double p6, int p7);
    
    int selectBeanCount(String p0);
}
