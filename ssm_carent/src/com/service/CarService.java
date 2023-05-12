// 
// 
// 

package com.service;

import java.util.List;
import com.entity.Car;

public interface CarService
{
    void insertBean(Car p0);
    
    void deleteBean(int p0);
    
    void updateBean(Car p0);
    
    Car selectBeanById(int p0);
    
    List<Car> selectBeanList(int p0, int p1, String p2, String p3, String p4, String p5);
    
    int selectBeanCount(String p0, String p1, String p2, String p3);
}
