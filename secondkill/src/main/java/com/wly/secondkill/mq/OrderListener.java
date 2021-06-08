package com.wly.secondkill.mq;

import com.alibaba.fastjson.JSON;
import com.wly.secondkill.db.dao.OrderDao;
import com.wly.secondkill.db.dao.SeckillActivityDao;
import com.wly.secondkill.db.po.Order;
import com.wly.secondkill.util.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
@RocketMQMessageListener(topic = "seckill_order", consumerGroup = "seckill_order_group")
public class OrderListener implements RocketMQListener<MessageExt> {

    @Autowired
    private SeckillActivityDao seckillActivityDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private RedisService redisService;

    @Override
    @Transactional
    public void onMessage(MessageExt messageExt) {
        //1.parse the request
        String message = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        log.info("接收到创建订单请求：" + message);
        Order order = JSON.parseObject(message, Order.class);
        order.setCreateTime(new Date());

        //2.deduct the stock
        String requestId = UUID.randomUUID().toString();
        boolean lockStockResult;
        while (true) {
            if (redisService.tryGetDistributedLock("A", requestId,100)) {
                lockStockResult = seckillActivityDao.lockStock(order.getSeckillActivityId());
                break;
            }
        }

        //boolean lockStockResult = seckillActivityDao.lockStock(order.getSeckillActivityId());

        if (lockStockResult) {
            //订单状态 0:没有可用库存，无效订单 1:已创建等待付款
            order.setOrderStatus(1);

            //update purchase limit
            redisService.addLimitMember(order.getSeckillActivityId(), order.getUserId());
        } else {
            order.setOrderStatus(0);
        }

        //3.insert
        orderDao.insertOrder(order);
    }
}
