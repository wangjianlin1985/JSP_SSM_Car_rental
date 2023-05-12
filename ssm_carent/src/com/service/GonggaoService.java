// 
// 
// 

package com.service;

import java.util.List;
import com.entity.Gonggao;

public interface GonggaoService
{
    void insertBean(Gonggao p0);
    
    void deleteBean(int p0);
    
    void updateBean(Gonggao p0);
    
    Gonggao selectBeanById(int p0);
    
    List<Gonggao> selectBeanList(int p0, int p1, String p2);
    
    int selectBeanCount(String p0);
}
