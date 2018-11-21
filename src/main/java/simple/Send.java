package simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.utils.rabbitMq.ConnectUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/*
* 生产者生产消息
* */
public class Send {
    private static final String QUEUE_NAME="test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取一个连接
        Connection connection = ConnectUtils.getConnection();
        //从来接中获取一个通道
        Channel channel = connection.createChannel();

        //创建队列申明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg="hello!";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());


        channel.close();
        connection.close();
    }


}
