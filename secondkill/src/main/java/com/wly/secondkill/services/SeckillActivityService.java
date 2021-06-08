package com.wly.secondkill.services;

import com.alibaba.fastjson.JSON;
import com.wly.secondkill.db.dao.OrderDao;
import com.wly.secondkill.db.dao.SeckillActivityDao;
import com.wly.secondkill.db.dao.SeckillCommodityDao;
import com.wly.secondkill.db.po.Order;
import com.wly.secondkill.db.po.SeckillActivity;
import com.wly.secondkill.db.po.SeckillCommodity;
import com.wly.secondkill.mq.RocketMQService;
import com.wly.secondkill.util.RedisService;
import com.wly.secondkill.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class SeckillActivityService {
    @Autowired
    private SeckillActivityDao seckillActivityDao;
    @Autowired
    private RocketMQService rocketMQService;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SeckillCommodityDao seckillCommodityDao;

    private SnowFlake snowFlake = new SnowFlake(1, 1);

    public Order createOrder(long seckillActivityId, long userId) throws Exception {
        // create the order
        SeckillActivity seckillActivity = seckillActivityDao.querySeckillActivityById(seckillActivityId);
        Order order = new Order();

        // generate ID
        order.setOrderNo(String.valueOf(snowFlake.nextId()));
        order.setSeckillActivityId(seckillActivity.getId());
        order.setUserId(userId);
        order.setOrderAmount(seckillActivity.getSeckillPrice().longValue());

        // send message
        rocketMQService.sendMessage("seckill_order", JSON.toJSONString(order));

        /*
         * 发送订单付款状态校验消息
         * 开源RocketMQ支持延迟消息，但是不支持秒级精度。默认支持18个level的延迟消息，这是通过broker端的messageDelayLevel配置项确定的，如下：
         * messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
         */
        rocketMQService.sendDelayMessage("pay_check",JSON.toJSONString(order),5);
        return order;
    }

    public void payOrderProcess(String orderNo) throws Exception {
        log.info("完成支付订单 订单号：" + orderNo);
        Order order = orderDao.queryOrder(orderNo);
        // version1: 直接deduct
        /*
        boolean deductStockResult = seckillActivityDao.deductStock(order.getSeckillActivityId());
        if (deductStockResult) {
            order.setPayTime(new Date());
            // 订单状态 0、没有可用库存，无效订单  1、已创建等待支付  2、完成支付
            order.setOrderStatus(2);
            orderDao.updateOrder(order);
        }
        */

        // version2: deduct 改为在 pay-done listener端处理
        /*
         * 1.判断订单是否存在
         * 2.判断订单状态是否为未支付状态
         */
        if (order == null) {
            log.error("订单号对应订单不存在：" + orderNo);
            return;
        } else if(order.getOrderStatus() != 1 ) {
            log.error("订单状态无效：" + orderNo);
            return;
        }
        /*
         * 2.订单支付完成
         */
        order.setPayTime(new Date());
        //订单状态 0:没有可用库存，无效订单 1:已创建等待付款 ,2:支付完成
        order.setOrderStatus(2);
        orderDao.updateOrder(order);
        /*
         *3.发送订单付款成功消息
         */
        rocketMQService.sendMessage("pay_done", JSON.toJSONString(order));
    }

    //将秒杀详情相关信息倒入redis
    public void pushSeckillInfoToRedis(long seckillActivityId) {
        SeckillActivity seckillActivity = seckillActivityDao.querySeckillActivityById(seckillActivityId);
        redisService.setValue("seckillActivity:" + seckillActivityId, JSON.toJSONString(seckillActivity));

        SeckillCommodity seckillCommodity = seckillCommodityDao.querySeckillCommodityById(seckillActivity.getCommodityId());
        redisService.setValue("seckillCommodity:" + seckillActivity.getCommodityId(), JSON.toJSONString(seckillCommodity));
    }
}
