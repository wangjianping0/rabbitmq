package com.shareshopping.rabbitmq.work;

import com.rabbitmq.client.*;
import com.shareshopping.rabbitmq.util.RabbitConnectionUtil;

import java.io.IOException;
/**
 * 队列 模式
 */
public class Customer2 {
    public static void main(String[] args) throws Exception{
        //1.创建连接工厂
        //2.创建连接
        Connection connection = RabbitConnectionUtil.getConnection();
        //3.创建渠道
        Channel channel = connection.createChannel();
        //4.声明队列
        /**
         * 参数1:队列名称
         * 参数2:是否定义持久化队列(消息会持久化保存在服务器上)
         * 参数3:是否独占本连接
         * 参数4:是否在不使用的时候队列自动删除
         * 参数5:其他参数
         *
         */
        channel.queueDeclare(Producer.QUEUE_NAME, true, false, false, null);
        //每次预取多少个消息
        channel.basicQos(1);
        //5.创建消费者(接收消息并处理消息)
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
                System.out.println("消费者2------接收到的消息:"+new String(body, "UTF-8"));
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //确认消息
                /**
                 * 参数1:消息id
                 * 参数2:false表示只有当前这个消息被处理
                 */
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        //6.监听队列
        /**
         * 参数1:队列名
         * 参数2:是否要自动确认.
         * 设置为true标识消息接收到自动向MQ回复接受到了,MQ则会将消息从队列中删除;如果设置为false则需要手动确认;
         * 参数3:消费者
         *
         */
        channel.basicConsume(Producer.QUEUE_NAME,true,defaultConsumer);
    }
}
