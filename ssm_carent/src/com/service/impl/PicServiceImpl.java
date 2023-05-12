// 
// 
// 

package com.service.impl;

import java.util.List;
import com.entity.Pic;
import javax.annotation.Resource;
import com.dao.PicDao;
import org.springframework.stereotype.Service;
import com.service.PicService;

@Service("picService")
public class PicServiceImpl implements PicService
{
    @Resource
    private PicDao picDao;
    
    @Override
    public void insertBean(final Pic bean) {
        this.picDao.insertBean(bean);
    }
    
    @Override
    public void deleteBean(final int id) {
        this.picDao.deleteBean(id);
    }
    
    @Override
    public void updateBean(final Pic bean) {
        this.picDao.updateBean(bean);
    }
    
    @Override
    public Pic selectBeanById(final int id) {
        return this.picDao.selectBeanById(id);
    }
    
    @Override
    public List<Pic> selectBeanList(final int start, final int limit) {
        return this.picDao.selectBeanList(start, limit);
    }
    
    @Override
    public int selectBeanCount() {
        return this.picDao.selectBeanCount();
    }
}
