package com.lebron.rabbitmq.common;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receive
{
    //队列名称
    private static final String QUEUE_NAME = "local.log.user.queue.name1";
    
    public static void main(String[] args)
    {
        try
        {
            //获取连接
            Connection connection = ConnectionUtil.getConnection();
            //从连接中获取一个通道
            Channel channel = connection.createChannel();
            //声明队列
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            //定义消费者
            DefaultConsumer consumer = new DefaultConsumer(channel)
            {
                //当消息到达时执行回调方法
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                           byte[] body) throws IOException
                {
                    String message = new String(body, "utf-8");
                    System.out.println("[Receive]：" + message);
                }
            };
            //监听队列
            channel.basicConsume(QUEUE_NAME, true, consumer);
        }
        catch (IOException | ShutdownSignalException | ConsumerCancelledException | TimeoutException e)
        {
            e.printStackTrace();
        }
    }
}