package com.wly.secondkill.services;

import com.wly.secondkill.db.dao.SeckillActivityDao;
import com.wly.secondkill.db.po.SeckillActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleDecrementService {

    @Autowired
    private SeckillActivityDao seckillActivityDao;

    public String productDeduct(long activityId) {
        SeckillActivity seckillActivity = seckillActivityDao.querySeckillActivityById(activityId);
        Integer availableStock = seckillActivity.getAvailableStock();
        String result;

        if (availableStock > 0) {
            result = "Congratulate! Purchase Succeeds";
            System.out.println(result);
            availableStock = availableStock - 1;
            seckillActivity.setAvailableStock(availableStock);
            //seckillActivity.setAvailableStock(new Integer("" + availableStock));
            seckillActivityDao.updateSeckillActivity(seckillActivity);
        } else {
            result = "Sorry! Purchase Fails";
            System.out.println(result);
        }
        return result;
    }
}
