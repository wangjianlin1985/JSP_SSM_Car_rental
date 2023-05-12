// 
// 
// 

package com.service.impl;

import java.util.List;
import com.entity.Jilu;
import javax.annotation.Resource;
import com.dao.JiluDao;
import org.springframework.stereotype.Service;
import com.service.JiluService;

@Service("jiluService")
public class JiluServiceImpl implements JiluService
{
    @Resource
    private JiluDao jiluDao;
    
    @Override
    public void insertBean(final Jilu bean) {
        this.jiluDao.insertBean(bean);
    }
    
    @Override
    public void deleteBean(final int id) {
        this.jiluDao.deleteBean(id);
    }
    
    @Override
    public void updateBean(final Jilu bean) {
        this.jiluDao.updateBean(bean);
    }
    
    @Override
    public Jilu selectBeanById(final int id) {
        return this.jiluDao.selectBeanById(id);
    }
    
    @Override
    public List<Jilu> selectBeanList(final int start, final int limit, final String chepai, final String sfz, final String xingming, final String zhuangtai, final int workid, final int jishuid, final int kehuid) {
        return this.jiluDao.selectBeanList(start, limit, chepai, sfz, xingming, zhuangtai, workid, jishuid, kehuid);
    }
    
    @Override
    public int selectBeanCount(final String chepai, final String sfz, final String xingming, final String zhuangtai, final int workid, final int jishuid, final int kehuid) {
        return this.jiluDao.selectBeanCount(chepai, sfz, xingming, zhuangtai, workid, jishuid, kehuid);
    }
}
