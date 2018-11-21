package com.utils.rabbitMq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class ConnectUtils {


    public  static Connection getConnection() throws IOException, TimeoutException {
        //定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务器地址
        factory.setHost("127.0.0.1");
        //amqp 5672
        factory.setPort(5672);
        //vhost
        factory.setVirtualHost("/mmr");

        //设置用户名
        factory.setUsername("wj");
        factory.setPassword("123");
        return  factory.newConnection();
    }


}
