// 
// 
// 

package com.service.impl;

import java.util.List;
import com.entity.Gonggao;
import javax.annotation.Resource;
import com.dao.GonggaoDao;
import org.springframework.stereotype.Service;
import com.service.GonggaoService;

@Service("gonggaoService")
public class GonggaoServiceImpl implements GonggaoService
{
    @Resource
    private GonggaoDao gonggaoDao;
    
    @Override
    public void insertBean(final Gonggao bean) {
        this.gonggaoDao.insertBean(bean);
    }
    
    @Override
    public void deleteBean(final int id) {
        this.gonggaoDao.deleteBean(id);
    }
    
    @Override
    public void updateBean(final Gonggao bean) {
        this.gonggaoDao.updateBean(bean);
    }
    
    @Override
    public Gonggao selectBeanById(final int id) {
        return this.gonggaoDao.selectBeanById(id);
    }
    
    @Override
    public List<Gonggao> selectBeanList(final int start, final int limit, final String gbiaoti) {
        return this.gonggaoDao.selectBeanList(start, limit, gbiaoti);
    }
    
    @Override
    public int selectBeanCount(final String gbiaoti) {
        return this.gonggaoDao.selectBeanCount(gbiaoti);
    }
}
