// 
// 
// 

package com.service.impl;

import java.util.List;
import com.entity.Vip;
import javax.annotation.Resource;
import com.dao.VipDao;
import org.springframework.stereotype.Service;
import com.service.VipService;

@Service("VipService")
public class VipServiceImpl implements VipService
{
    @Resource
    private VipDao vipDao;
    
    @Override
    public void insertBean(final Vip bean) {
        this.vipDao.insertBean(bean);
    }
    
    @Override
    public void deleteBean(final int id) {
        this.vipDao.deleteBean(id);
    }
    
    @Override
    public void updateBean(final Vip bean) {
        this.vipDao.updateBean(bean);
    }
    
    @Override
    public Vip selectBeanByUsername(final String username) {
        return this.vipDao.selectBeanByUsername(username);
    }
    
    @Override
    public List<Vip> selectBeanList(final int start, final int limit, final String username, final int points, final int level, final String enddate, final Double consumed, final int kehuid) {
        return this.vipDao.selectBeanList(start, limit, username, points, level, enddate, consumed, kehuid);
    }
    
    @Override
    public int selectBeanCount(final String username) {
        return this.vipDao.selectBeanCount(username);
    }
    
    @Override
    public Vip selectBeanByKehuid(final int kehuid) {
        return this.vipDao.selectBeanByKehuid(kehuid);
    }
}
