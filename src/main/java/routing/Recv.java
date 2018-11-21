package routing;

import com.rabbitmq.client.*;
import com.utils.rabbitMq.ConnectUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*
* 消费者获取消息
* */
public class Recv {
    private static final String EXCHANGE_NAME="exchange_direct";
    private static final String QUEUE_NAME="queue_direct1";

    public static void main(String[] args) throws IOException, TimeoutException {

        //获取连接
        Connection connection = ConnectUtils.getConnection();
        //创建频道
        final  Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.basicQos(1);//保证一次只分发一个
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"error");

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
                    //手动回复生产者信息
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        boolean autoAck=false;//自动应答 改成false
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);


    }


}
