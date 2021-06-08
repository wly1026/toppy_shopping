package com.wly.secondkill.services;

import com.wly.secondkill.util.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisDecrementService {

    @Autowired
    private RedisService redisService;

    public boolean productDeduct(long activityId) {
        if (redisService.stockDeductValidator("stock:" + activityId)) {
            System.out.println("Congratulate! Purchase Succeeds");
            return true;
        } else {
            System.out.println("Sorry! Purchase Fails");
            return false;
        }
    }
}
