// 
// 
// 

package com.service;

import com.entity.Vip;
import java.util.List;
import com.entity.User;

public interface UserService
{
    void insertBean(User p0);
    
    void deleteBean(int p0);
    
    void updateBean(User p0);
    
    User userlogin(String p0, String p1, int p2);
    
    User useryz(String p0);
    
    User sfzyz(String p0);
    
    User selectBeanById(int p0);
    
    User selectBeanByUsername(String p0);
    
    List<User> selectBeanList(int p0, int p1, String p2);
    
    int selectBeanCount(String p0);
    
    List<User> selectBeanList2(int p0, int p1, String p2);
    
    int selectBeanCount2(String p0);
    
    List<Vip> selectBeanList3(int p0, int p1, String p2);
    
    int selectBeanCount3(String p0);
    
    List<User> selectBeanList4(int p0, int p1, String p2);
    
    int selectBeanCount4(String p0);
}
