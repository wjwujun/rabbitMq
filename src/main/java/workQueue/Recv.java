package workQueue;

import com.rabbitmq.client.*;
import com.utils.rabbitMq.ConnectUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*
* 消费者获取消息
* */
public class Recv {
    private static final String QUEUE_NAME="work_queue";
    public static void main(String[] args) throws IOException, TimeoutException {

        //获取连接
        Connection connection = ConnectUtils.getConnection();
        //创建频道
        Channel channel = connection.createChannel();
        //队列申明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //定义队列的消费者
        DefaultConsumer consumer=new DefaultConsumer(channel){
            //触发消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String str = new String(body, "utf-8");
                System.out.println("[1]消费消息:"+str);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[1] done");
                }
            }
        };
        boolean autoAck=true;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);

    }


}
