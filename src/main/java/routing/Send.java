package routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.utils.rabbitMq.ConnectUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/*
* 路由模式，生产者生产消息
* */
public class Send {
    private static final String EXCHANGE_NAME="exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取一个连接
        Connection connection = ConnectUtils.getConnection();
        //从来接中获取一个通道
        Channel channel = connection.createChannel();

        //声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");

        String msg="hello direct!";
        String routerKey="error";  //两个消费者都能接收到
        // String routerKey="info";     //只有一个消费者能接收到
        channel.basicPublish(EXCHANGE_NAME,routerKey,null,msg.getBytes());

        System.out.println("发送信息");
        channel.close();
        connection.close();
    }


}
