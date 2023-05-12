// 
// 
// 

package com.service;

import java.util.List;
import com.entity.Ri;

public interface RiService
{
    void insertBean(Ri p0);
    
    void deleteBean(int p0);
    
    void updateBean(Ri p0);
    
    Ri selectBeanById(int p0);
    
    List<Ri> selectBeanList(int p0, int p1, String p2);
    
    int selectBeanCount(String p0);
}
