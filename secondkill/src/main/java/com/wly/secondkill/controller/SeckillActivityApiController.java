package com.wly.secondkill.controller;

import com.wly.secondkill.db.dao.OrderDao;
import com.wly.secondkill.db.dao.SeckillActivityDao;
import com.wly.secondkill.db.dao.SeckillCommodityDao;
import com.wly.secondkill.db.po.Order;
import com.wly.secondkill.db.po.PurchaseResponse;
import com.wly.secondkill.db.po.SeckillActivity;
import com.wly.secondkill.services.RedisDecrementService;
import com.wly.secondkill.services.SeckillActivityService;
import com.wly.secondkill.util.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@Controller
@RequestMapping("/api")
@Slf4j
public class SeckillActivityApiController {

    @Autowired
    private SeckillActivityDao seckillActivityDao;
    @Autowired
    private SeckillCommodityDao seckillCommodityDao;
    @Autowired
    private RedisDecrementService redisDecrementService;
    @Autowired
    private SeckillActivityService seckillActivityService;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private RedisService redisService;

    @RequestMapping("/seckills")
    @ResponseBody
    List<SeckillActivity> getProducts() {
        return seckillActivityDao.querySeckillActivitysByStatus(1);
    }

    @RequestMapping("/seckill/item/{seckillActivityId}")
    @ResponseBody
    SeckillActivity getProduct(@PathVariable long seckillActivityId) {
        return seckillActivityDao.querySeckillActivityById(seckillActivityId);
    }

    @RequestMapping("/buy/{userId}/{seckillActivityId}")
    @ResponseBody
    PurchaseResponse purchase(@PathVariable long userId, @PathVariable long seckillActivityId) {
        boolean stockValidateResult = false;

        PurchaseResponse purchaseResponse = new PurchaseResponse();
        purchaseResponse.setCreateOrderSuccess(false);

        try {
            // purchase limit
//            if (redisService.isInLimitMember(seckillActivityId, userId)) {
//                purchaseResponse.setInfo("对不起，您已经在限购名单中");
//                return purchaseResponse;
//            }

            // check stock by redis lua
            stockValidateResult = redisDecrementService.productDeduct(seckillActivityId);
            if (stockValidateResult) {
                Order order = seckillActivityService.createOrder(seckillActivityId, userId);
                purchaseResponse.setOrderId(order.getOrderNo());
                purchaseResponse.setInfo("秒杀成功，订单创建中");
                purchaseResponse.setCreateOrderSuccess(true);
            } else {
                purchaseResponse.setInfo("对不起，商品库存不足");
            }
        } catch (Exception e) {
            log.error("秒杀系统异常" + e.toString());
            purchaseResponse.setInfo("秒杀失败");
        }

        return purchaseResponse;
    }

    @RequestMapping("/orderQuery/{orderNo}")
    @ResponseBody
    public Order orderQuery(@PathVariable String orderNo) {
        return orderDao.queryOrder(orderNo);
    }

    @PostMapping("/payOrder/{orderNo}")
    @ResponseBody
    public Order payOrder(@PathVariable String orderNo) throws Exception{
        seckillActivityService.payOrderProcess(orderNo);
        return orderDao.queryOrder(orderNo);
    }
}
