// 
// 
// 

package com.service.impl;

import java.util.List;
import com.entity.Car;
import javax.annotation.Resource;
import com.dao.CarDao;
import org.springframework.stereotype.Service;
import com.service.CarService;

@Service("carService")
public class CarServiceImpl implements CarService
{
    @Resource
    private CarDao carDao;
    
    @Override
    public void insertBean(final Car bean) {
        this.carDao.insertBean(bean);
    }
    
    @Override
    public void deleteBean(final int id) {
        this.carDao.deleteBean(id);
    }
    
    @Override
    public void updateBean(final Car bean) {
        this.carDao.updateBean(bean);
    }
    
    @Override
    public Car selectBeanById(final int id) {
        return this.carDao.selectBeanById(id);
    }
    
    @Override
    public List<Car> selectBeanList(final int start, final int limit, final String chepai, final String pinpai, final String xinghao, final String yanse) {
        return this.carDao.selectBeanList(start, limit, chepai, pinpai, xinghao, yanse);
    }
    
    @Override
    public int selectBeanCount(final String chepai, final String pinpai, final String xinghao, final String yanse) {
        return this.carDao.selectBeanCount(chepai, pinpai, xinghao, yanse);
    }
}
