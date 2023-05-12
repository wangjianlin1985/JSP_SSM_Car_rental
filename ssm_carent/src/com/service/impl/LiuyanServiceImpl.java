// 
// 
// 

package com.service.impl;

import java.util.List;
import com.entity.Liuyan;
import javax.annotation.Resource;
import com.dao.LiuyanDao;
import org.springframework.stereotype.Service;
import com.service.LiuyanService;

@Service("liuyanService")
public class LiuyanServiceImpl implements LiuyanService
{
    @Resource
    private LiuyanDao liuyanDao;
    
    @Override
    public void insertBean(final Liuyan bean) {
        this.liuyanDao.insertBean(bean);
    }
    
    @Override
    public void deleteBean(final int id) {
        this.liuyanDao.deleteBean(id);
    }
    
    @Override
    public void updateBean(final Liuyan bean) {
        this.liuyanDao.updateBean(bean);
    }
    
    @Override
    public Liuyan selectBeanById(final int id) {
        return this.liuyanDao.selectBeanById(id);
    }
    
    @Override
    public List<Liuyan> selectBeanList(final int start, final int limit, final String ltitle, final String zhuangtai, final int kehuid, final int workid) {
        return this.liuyanDao.selectBeanList(start, limit, ltitle, zhuangtai, kehuid, workid);
    }
    
    @Override
    public int selectBeanCount(final String ltitle, final String zhuangtai, final int kehuid, final int workid) {
        return this.liuyanDao.selectBeanCount(ltitle, zhuangtai, kehuid, workid);
    }
}
