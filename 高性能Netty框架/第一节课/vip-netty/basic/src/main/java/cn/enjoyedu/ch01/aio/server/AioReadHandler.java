package cn.enjoyedu.ch01.aio.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import static cn.enjoyedu.ch01.Ch01Const.response;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 类说明：读数据的处理器
 */
public class AioReadHandler
        implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel channel;

    public AioReadHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    //读取到消息后的处理
    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        //如果条件成立，说明客户端主动终止了TCP套接字，这时服务端终止就可以了
        if(result == -1) {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        //flip操作
        attachment.flip();
        byte[] message = new byte[attachment.remaining()];
        attachment.get(message);
        try {
            System.out.println(result);
            String msg = new String(message,"UTF-8");
            System.out.println("server accept message:"+msg);
            String responseStr = response(msg);
            //向客户端发送消息
            doWrite(responseStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发送消息
    private void doWrite(String result) {
        byte[] bytes = result.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        //异步写数据
        channel.write(writeBuffer, writeBuffer,
                new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if(attachment.hasRemaining()){
                    channel.write(attachment,attachment,this);
                }else{
                    //读取客户端传回的数据
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    //异步读数据
                    channel.read(readBuffer,readBuffer,
                            new AioReadHandler(channel));
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
