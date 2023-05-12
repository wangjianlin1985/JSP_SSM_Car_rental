// 
// 
// 

package com.service;

import java.util.List;
import com.entity.Yue;

public interface YueService
{
    void insertBean(Yue p0);
    
    void deleteBean(int p0);
    
    void updateBean(Yue p0);
    
    Yue selectBeanById(int p0);
    
    List<Yue> selectBeanList(int p0, int p1, String p2);
    
    int selectBeanCount(String p0);
}
