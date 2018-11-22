package confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.utils.rabbitMq.ConnectUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/*
 *confirm机制，生产者生产消息
 *批量,普通模式，异步回调
 *
 *
* */
public class Send3 {
    private static final String QUEUE_NAME="queue_confirm3";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取一个连接
        Connection connection = ConnectUtils.getConnection();
        //从来接中获取一个通道
        Channel channel = connection.createChannel();

        //声明exchange
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //生产者调用confirm，将channel设置成
        channel.confirmSelect();

        //未确认的消息标识



        /*批量发送*/
        for(int i=0;i<10;i++){
            String msg="confirm机制,批量:"+i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        }




        if(!channel.waitForConfirms()){
            System.out.println("message send faild!");
        }else {
            System.out.println("message send   success!");
        }

        channel.close();
        connection.close();
    }

}
