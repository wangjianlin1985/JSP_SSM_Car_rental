// 
// 
// 

package com.service.impl;

import java.util.List;
import com.entity.Yue;
import javax.annotation.Resource;
import com.dao.YueDao;
import org.springframework.stereotype.Service;
import com.service.YueService;

@Service("yueService")
public class YueServiceImpl implements YueService
{
    @Resource
    private YueDao yueDao;
    
    @Override
    public void insertBean(final Yue bean) {
        this.yueDao.insertBean(bean);
    }
    
    @Override
    public void deleteBean(final int id) {
        this.yueDao.deleteBean(id);
    }
    
    @Override
    public void updateBean(final Yue bean) {
        this.yueDao.updateBean(bean);
    }
    
    @Override
    public Yue selectBeanById(final int id) {
        return this.yueDao.selectBeanById(id);
    }
    
    @Override
    public List<Yue> selectBeanList(final int start, final int limit, final String month) {
        return this.yueDao.selectBeanList(start, limit, month);
    }
    
    @Override
    public int selectBeanCount(final String month) {
        return this.yueDao.selectBeanCount(month);
    }
}
