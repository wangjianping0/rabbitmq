package com.shareshopping.rabbitmq.fanout;

import com.rabbitmq.client.*;
import com.shareshopping.rabbitmq.util.RabbitConnectionUtil;

import java.io.IOException;

/**
 * 发布-订阅 模式
 */
public class Cutomer1 {
    public static void main(String[] args) throws  Exception{
        //1.创建连接
        Connection connection = RabbitConnectionUtil.getConnection();
        //2.创建频道
        Channel channel = connection.createChannel();
        //3.声明交换机
        /**
         * 参数1:交换机名称
         * 参数2:交换机类型(fanout,direct,topic)
         */
        channel.exchangeDeclare(Producer.FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);
        //4.声明队列
        channel.queueDeclare(Producer.FANOUT_QUEUE_1, true, false, false, null);
        //5.队列绑定到交换机
        channel.queueBind(Producer.FANOUT_QUEUE_1,Producer.FANOUT_EXCHANGE,"");
        //6.创建消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //路由key
                System.out.println("路由key:"+envelope.getRoutingKey());
                //交换机
                System.out.println("交换机:"+envelope.getExchange());
                //消息id
                System.out.println("消息id:"+envelope.getDeliveryTag());
                //接收到的消息
                System.out.println("客户1---接收到的消息:"+new String(body, "UTF-8"));
            }
        };
        //7.监听队列
        channel.basicConsume(Producer.FANOUT_QUEUE_1,true,defaultConsumer);
    }
}
