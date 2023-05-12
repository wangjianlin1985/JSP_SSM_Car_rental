// 
// 
// 

package com.service;

import java.util.List;
import com.entity.Pic;

public interface PicService
{
    void insertBean(Pic p0);
    
    void deleteBean(int p0);
    
    void updateBean(Pic p0);
    
    Pic selectBeanById(int p0);
    
    List<Pic> selectBeanList(int p0, int p1);
    
    int selectBeanCount();
}
