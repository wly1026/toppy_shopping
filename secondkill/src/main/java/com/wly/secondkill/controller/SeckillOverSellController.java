package com.wly.secondkill.controller;

import com.wly.secondkill.services.RedisDecrementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SeckillOverSellController {

//    @Autowired
//    private SimpleDecrementService simpleDecrementService;
//
//    /**
//     * Simple Version of deducting stocks, leading to oversell
//     * @param seckillActivityId
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("/seckill/{seckillActivityId}")
//    public String processPurchase(@PathVariable long seckillActivityId) {
//        return simpleDecrementService.productDeduct(seckillActivityId);
//    }

    @Autowired
    private RedisDecrementService redisDecrementService;

    /**
     * Redis Version of deducting stocks, leading to oversell
     * @param seckillActivityId
     * @return
     */
    @ResponseBody
    @RequestMapping("/seckill/{seckillActivityId}")
    public boolean processPurchase(@PathVariable long seckillActivityId) {
        return redisDecrementService.productDeduct(seckillActivityId);
    }
}
