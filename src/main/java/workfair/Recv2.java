package workfair;

import com.rabbitmq.client.*;
import com.utils.rabbitMq.ConnectUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*
* 消费者获取消息
* */
public class Recv2 {
    private static final String QUEUE_NAME="work_queue";
    public static void main(String[] args) throws IOException, TimeoutException {

        //获取连接
        Connection connection = ConnectUtils.getConnection();
        //创建频道
       final Channel channel = connection.createChannel();
        /*
        * 队列声明
        * channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
        * 消息持久化durable,已经定义好的队列管道，将durable=false 改成true的是不可以的,尽管代码是正确的,但是不会运行成功，因为已经定义体好的queue是未持久化的，不能重新定义,除非删掉rabbitMq中的queue
        *
        *
        * */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.basicQos(1);//保证一次只分发一个

        //定义队列的消费者
        DefaultConsumer consumer=new DefaultConsumer(channel){
            //触发消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String str = new String(body, "utf-8");
                System.out.println("[2]消费消息:"+str);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[2] done");
                    //手动回复生产者信息
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        /*
        * 应答
        * true   自动应答，一旦rabbitmq将消息发送给消费者，就会从内存中删除,如果杀死正在执行的消费者，就会丢失正在处理的消息。
        * false  手动应答，如果有一个消费者挂掉，就会交付给其他消费者，rabbit支持消息应答，消费者发出一个消息应答告诉rabbitMq,这个消息我已完成，你可以删除内存中的消息了
        * 消息默认是打开的，false
        * */
        boolean autoAck=false;

        channel.basicConsume(QUEUE_NAME,autoAck,consumer);

    }

}
