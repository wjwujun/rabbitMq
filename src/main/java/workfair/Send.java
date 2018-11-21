package workfair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.utils.rabbitMq.ConnectUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/*
* 生产者生产消息
* 公平分发
* */
public class Send {

    private static final String QUEUE_NAME="work_queue";


    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取一个连接
        Connection connection = ConnectUtils.getConnection();
        //从来接中获取一个通道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        /*
         *每个消费者发送确认消息之前,消息队列不发送下一个消息到消费者,一次只处理一个消息
         *限制发送给同一个消费者不得超过一条消息
         * */
        int prefectchCount=1;
        channel.basicQos(prefectchCount);

        for (int i=0;i<50;i++){
                String msg="hello!"+i;
                channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
                Thread.sleep(i*5);
        }
        channel.close();
        connection.close();
    }

}
