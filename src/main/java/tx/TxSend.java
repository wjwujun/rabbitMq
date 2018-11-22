package tx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.utils.rabbitMq.ConnectUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/*
* 事物机制，生产者生产消息
* 会降低mq的吞吐量
* */
public class TxSend {
    private static final String QUEUE_NAME="queue_tx";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取一个连接
        Connection connection = ConnectUtils.getConnection();
        //从来接中获取一个通道
        Channel channel = connection.createChannel();

        //声明exchange
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg="事务机制";

        //开启事物
        try {
            channel.txSelect();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            int xx=1/0;

            channel.txCommit();
        }catch (Exception e){
            channel.txRollback();       //事物回滚
            System.out.println("send message rollback!");

        }

        System.out.println("成功");
        channel.close();
        connection.close();
    }

}
