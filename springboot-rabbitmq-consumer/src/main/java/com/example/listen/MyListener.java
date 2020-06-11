package com.example.listen;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener {
    @RabbitListener(queues = "item_queue")
    public void myListen1(String message){
        System.out.println("消费者接受到的消息:"+message);
    }
}
