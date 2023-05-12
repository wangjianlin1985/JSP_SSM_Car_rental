// 
// 
// 

package com.service.impl;

import java.util.List;
import com.entity.Yuding;
import javax.annotation.Resource;
import com.dao.YudingDao;
import org.springframework.stereotype.Service;
import com.service.YudingService;

@Service("yudingService")
public class YudingServiceImpl implements YudingService
{
    @Resource
    private YudingDao yudingDao;
    
    @Override
    public void insertBean(final Yuding bean) {
        this.yudingDao.insertBean(bean);
    }
    
    @Override
    public void deleteBean(final int id) {
        this.yudingDao.deleteBean(id);
    }
    
    @Override
    public void updateBean(final Yuding bean) {
        this.yudingDao.updateBean(bean);
    }
    
    @Override
    public Yuding selectBeanById(final int id) {
        return this.yudingDao.selectBeanById(id);
    }
    
    @Override
    public List<Yuding> selectBeanList(final int start, final int limit, final String chepai, final String sfz, final int kehuid, final int workid, final String zhuangtai) {
        return this.yudingDao.selectBeanList(start, limit, chepai, sfz, kehuid, workid, zhuangtai);
    }
    
    @Override
    public int selectBeanCount(final String chepai, final String sfz, final int kehuid, final int workid, final String zhuangtai) {
        return this.yudingDao.selectBeanCount(chepai, sfz, kehuid, workid, zhuangtai);
    }
}
