package workQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.utils.rabbitMq.ConnectUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/*
* 生产者生产消息
* */
public class Send {

    private static final String QUEUE_NAME="work_queue";


    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取一个连接
        Connection connection = ConnectUtils.getConnection();
        //从来接中获取一个通道
        Channel channel = connection.createChannel();
        //创建队列申明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        for (int i=0;i<50;i++){
                String msg="hello!"+i;
                channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
                Thread.sleep(i*10);
        }
        channel.close();
        connection.close();
    }

}
