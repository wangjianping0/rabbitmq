package com.shareshopping.rabbitmq.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.shareshopping.rabbitmq.util.RabbitConnectionUtil;

/**
 * 通配符 模式
 */
public class Producer {
    //交换机名称
    public static final String TOPIC_EXCHANGE = "topic_exchange";
    //队列名称
    public static final String TOPIC_QUEUE_1 = "topic_queue_1";
    public static final String TOPIC_QUEUE_2 = "topic_queue_2";
    public static final String TOPIC_QUEUE_3 = "topic_queue_3";
    public static final String TOPIC_QUEUE_4 = "topic_queue_4";

    public static void main(String[] args) throws Exception {
        //1.创建连接
        Connection connection = RabbitConnectionUtil.getConnection();
        //2.创建频道
        Channel channel = connection.createChannel();
        //3.声明交换机(fanout)
        /**
         * 参数1:交换机名称
         * 参数2:交换机类型(fanout,direct,topic)
         */
        channel.exchangeDeclare(TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);
        //4.声明队列
        /**
         * 参数1:队列名称
         * 参数2:是否定义持久化队列(消息会持久化保存在服务器上)
         * 参数3:是否独占本连接
         * 参数4:是否在不使用的时候队列自动删除
         * 参数5:其他参数
         *
         */
//        channel.queueDeclare(TOPIC_QUEUE_1, true, false, false, null);
//        channel.queueDeclare(TOPIC_QUEUE_2, true, false, false, null);
//        channel.queueDeclare(TOPIC_QUEUE_3, true, false, false, null);
//        channel.queueDeclare(TOPIC_QUEUE_4, true, false, false, null);
        //5.队列绑定到交换机
//        channel.queueBind(DIRECT_QUEUE_INSERT, TOPIC_EXCHANGE, "insert");
//        channel.queueBind(DIRECT_QUEUE_UPDATE, TOPIC_EXCHANGE, "update");
        //6.发送消息
        String message = "你好!topic模式--- topic Key 为 usa.news";
        /**
         * 参数1:交换机名称;如果没有则指定空字符串(表示使用默认的交换机)
         * 参数2:路由key,简单模式中可以使用队列名称
         * 参数3:消息其他属性
         * 参数4:消息内容
         */
        channel.basicPublish(TOPIC_EXCHANGE, "usa.news", null, message.getBytes());
        System.out.println("已发出消息:" + message);
        message = "你好!topic模式--- topic Key 为 usa.weather";
        channel.basicPublish(TOPIC_EXCHANGE, "usa.weather", null, message.getBytes());
        System.out.println("已发出消息:" + message);
        message = "你好!topic模式--- topic Key 为 europe.news";
        channel.basicPublish(TOPIC_EXCHANGE, "europe.news", null, message.getBytes());
        System.out.println("已发出消息:" + message);
        message = "你好!topic模式--- topic Key 为 europe.weather";
        channel.basicPublish(TOPIC_EXCHANGE, "europe.weather", null, message.getBytes());
        System.out.println("已发出消息:" + message);

        //7.关闭资源
        channel.close();
        connection.close();
    }
}
