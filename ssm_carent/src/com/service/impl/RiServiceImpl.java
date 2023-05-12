// 
// 
// 

package com.service.impl;

import java.util.List;
import com.entity.Ri;
import javax.annotation.Resource;
import com.dao.RiDao;
import org.springframework.stereotype.Service;
import com.service.RiService;

@Service("riService")
public class RiServiceImpl implements RiService
{
    @Resource
    private RiDao riDao;
    
    @Override
    public void insertBean(final Ri bean) {
        this.riDao.insertBean(bean);
    }
    
    @Override
    public void deleteBean(final int id) {
        this.riDao.deleteBean(id);
    }
    
    @Override
    public void updateBean(final Ri bean) {
        this.riDao.updateBean(bean);
    }
    
    @Override
    public Ri selectBeanById(final int id) {
        return this.riDao.selectBeanById(id);
    }
    
    @Override
    public List<Ri> selectBeanList(final int start, final int limit, final String ri) {
        return this.riDao.selectBeanList(start, limit, ri);
    }
    
    @Override
    public int selectBeanCount(final String ri) {
        return this.riDao.selectBeanCount(ri);
    }
}
