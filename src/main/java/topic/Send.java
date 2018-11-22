package topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.utils.rabbitMq.ConnectUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/*
* 路由模式，生产者生产消息
* */
public class Send {
    private static final String EXCHANGE_NAME="exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取一个连接
        Connection connection = ConnectUtils.getConnection();
        //从来接中获取一个通道
        Channel channel = connection.createChannel();

        //声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        String msg="商品数据";
        //channel.basicPublish(EXCHANGE_NAME,"goods.add",null,msg.getBytes());
        channel.basicPublish(EXCHANGE_NAME,"goods.delete",null,msg.getBytes());

        System.out.println("成功");
        channel.close();
        connection.close();
    }


}
