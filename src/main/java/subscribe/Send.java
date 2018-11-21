package subscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.utils.rabbitMq.ConnectUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/*
* 订阅者(交换机)模式
* 一方面接受生产者的消息，一方面向队列推送消息
*
*
* */
public class Send {

    private static final String EXCHANGE_NAME="exchange_subscribe";


    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取一个连接
        Connection connection = ConnectUtils.getConnection();
        //从来接中获取一个通道
        Channel channel = connection.createChannel();
        /*
        * 声明交换机，
        * fanout  不处理路由key
        * direct  处理路由key
        * */
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");   //分发

        //发送消息
        String msg="hello exchange!";
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());


        System.out.println("msg");


        channel.close();
        connection.close();

    }

}
