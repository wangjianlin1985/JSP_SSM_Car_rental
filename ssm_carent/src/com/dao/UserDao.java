// 
// 
// 

package com.dao;

import com.entity.Vip;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.entity.User;

public interface UserDao
{
    void insertBean(User p0);
    
    void deleteBean(int p0);
    
    void updateBean(User p0);
    
    User userlogin(@Param("username") String p0, @Param("password") String p1, @Param("role") int p2);
    
    User useryz(@Param("username") String p0);
    
    User sfzyz(@Param("sfz") String p0);
    
    User selectBeanById(@Param("id") int p0);
    
    User selectBeanByUsername(@Param("username") String p0);
    
    List<User> selectBeanList(@Param("start") int p0, @Param("limit") int p1, @Param("username") String p2);
    
    int selectBeanCount(@Param("username") String p0);
    
    List<User> selectBeanList2(@Param("start") int p0, @Param("limit") int p1, @Param("username") String p2);
    
    int selectBeanCount2(@Param("username") String p0);
    
    List<Vip> selectBeanList3(@Param("start") int p0, @Param("limit") int p1, @Param("username") String p2);
    
    int selectBeanCount3(@Param("username") String p0);
    
    List<User> selectBeanList4(@Param("start") int p0, @Param("limit") int p1, @Param("username") String p2);
    
    int selectBeanCount4(@Param("username") String p0);
}
