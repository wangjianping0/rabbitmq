package com.shareshopping.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitConnectionUtil {
    public static Connection getConnection(){
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
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
