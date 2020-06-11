package com.shareshopping.rabbitmq.simple;

import com.rabbitmq.client.*;
import com.shareshopping.rabbitmq.util.RabbitConnectionUtil;

import java.io.IOException;
import java.nio.charset.Charset;

public class Customer {
    static final String QUEUE_NAME="simple_queue";
    public static void main(String[] args) throws Exception{
        //1.创建连接工厂(设置rabbitMQ连接参数)
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //主机;默认localhost
        connectionFactory.setHost("localhost");
        //连接端口;默认是5672
        connectionFactory.setPort(5672);
        //虚拟主机:默认/
        connectionFactory.setVirtualHost("/itcast");
        //用户名:默认guest
        connectionFactory.setUsername("admin");
        //密码:默认guest
        connectionFactory.setPassword("password");

        //2.创建连接
        Connection connection = connectionFactory.newConnection();
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
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
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
                System.out.println("接收到的消息:"+new String(body, "UTF-8"));
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
        channel.basicConsume(QUEUE_NAME,true,defaultConsumer);
    }
}
