package confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.utils.rabbitMq.ConnectUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/*
*confirm机制，生产者生产消息
*单条,普通模式，同步
* 一旦进入confirm模式，在该信道上面发布的消息都会被指派唯一一个id（从1开始），一旦消息投递到匹配的队列后，
* broker就会发送一个确认给生产者(包含消息的唯一id)，这就使得生产者知道消息已经到达目标队列了，如果消息和队列是可持久化的，那么确认的消息会写入磁盘后发出。
*
* */
public class Send1 {
    private static final String QUEUE_NAME="queue_confirm1";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取一个连接
        Connection connection = ConnectUtils.getConnection();
        //从来接中获取一个通道
        Channel channel = connection.createChannel();

        //声明通道
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);


        //生产者调用confirm，将channel设置成
        channel.confirmSelect();

        String msg="confirm机制";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());


        if(!channel.waitForConfirms()){
            System.out.println("message send faild!");
        }else {
            System.out.println("message send   success!");
        }

        channel.close();
        connection.close();
    }

}
