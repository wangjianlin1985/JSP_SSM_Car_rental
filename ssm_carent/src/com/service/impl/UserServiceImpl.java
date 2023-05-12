// 
// 
// 

package com.service.impl;

import com.entity.Vip;
import java.util.List;
import com.entity.User;
import javax.annotation.Resource;
import com.dao.UserDao;
import org.springframework.stereotype.Service;
import com.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService
{
    @Resource
    private UserDao userDao;
    
    @Override
    public void insertBean(final User bean) {
        this.userDao.insertBean(bean);
    }
    
    @Override
    public void deleteBean(final int id) {
        this.userDao.deleteBean(id);
    }
    
    @Override
    public void updateBean(final User bean) {
        this.userDao.updateBean(bean);
    }
    
    @Override
    public User userlogin(final String username, final String password, final int role) {
        return this.userDao.userlogin(username, password, role);
    }
    
    @Override
    public User useryz(final String username) {
        return this.userDao.useryz(username);
    }
    
    @Override
    public User sfzyz(final String sfz) {
        return this.userDao.sfzyz(sfz);
    }
    
    @Override
    public User selectBeanById(final int id) {
        return this.userDao.selectBeanById(id);
    }
    
    @Override
    public List<User> selectBeanList(final int start, final int limit, final String username) {
        return this.userDao.selectBeanList(start, limit, username);
    }
    
    @Override
    public int selectBeanCount(final String username) {
        return this.userDao.selectBeanCount(username);
    }
    
    @Override
    public List<User> selectBeanList2(final int start, final int limit, final String username) {
        return this.userDao.selectBeanList2(start, limit, username);
    }
    
    @Override
    public int selectBeanCount2(final String username) {
        return this.userDao.selectBeanCount2(username);
    }
    
    @Override
    public List<Vip> selectBeanList3(final int start, final int limit, final String username) {
        return this.userDao.selectBeanList3(start, limit, username);
    }
    
    @Override
    public int selectBeanCount3(final String username) {
        return this.userDao.selectBeanCount3(username);
    }
    
    @Override
    public List<User> selectBeanList4(final int start, final int limit, final String username) {
        return this.userDao.selectBeanList4(start, limit, username);
    }
    
    @Override
    public int selectBeanCount4(final String username) {
        return this.userDao.selectBeanCount4(username);
    }
    
    @Override
    public User selectBeanByUsername(final String username) {
        return this.userDao.selectBeanByUsername(username);
    }
}
