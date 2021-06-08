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

@Slf4j
@Component
@RocketMQMessageListener(topic = "pay_check", consumerGroup = "pay_check_group")
public class PayCheckListener implements RocketMQListener<MessageExt> {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private SeckillActivityDao seckillActivityDao;
    @Autowired
    private RedisService redisService;

    @Override
    @Transactional
    public void onMessage(MessageExt messageExt) {
        String message = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        log.info("接收支付校验请求：" + message);
        Order order = JSON.parseObject(message, Order.class);

        Order orderInfo = orderDao.queryOrder(order.getOrderNo());
        // 不能直接get status from message, because there is no order id when sending message
        if (orderInfo.getOrderStatus() != 2) {
            log.info("未完成支付关闭订单,订单号：" + order.getOrderNo());
            orderInfo.setOrderStatus(99);
            orderDao.updateOrder(orderInfo);

            // data consistence
            // database: lock + available
            seckillActivityDao.revertStock(order.getSeckillActivityId());
            // redis
            redisService.revertStock("stock:" + order.getSeckillActivityId());

            // update purchase limit
            redisService.removeLimitMember(order.getSeckillActivityId(), order.getUserId());
        }
    }
}
