package com.gupaoedu.rpc.consumer.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.gupaoedu.rpc.core.msg.InvokerMsg;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class RpcProxy {
	
	public static <T> T create(Class<?> clazz){
		
		MethodProxy methodProxy = new MethodProxy(clazz);
		
		T result = (T)Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, methodProxy);
		
		return result;
	}
	
	
	
}


class MethodProxy implements InvocationHandler{

	private Class<?> clazz;
	public MethodProxy(Class<?> clazz){
		this.clazz = clazz;
	}
	
	
	//代理，调用IRpcHello接口中每一个方法的时候，实际上就是发起一次网络请求
	
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		//如果传进来的是一个已经实现的具体的类（直接忽略）
		if(Object.class.equals(method.getDeclaringClass())){
			return method.invoke(this, args);
		}
		
		//如果传进来的是一个接口，我们就走远程调用
		else{
			return rpcInvoke(method,args);
		}
		
	}
	
	
	
	public Object rpcInvoke(Method method,Object[] args){
		
		InvokerMsg msg = new InvokerMsg();
		
		msg.setClassName(this.clazz.getName());
		msg.setMethodName(method.getName());
		msg.setParames(method.getParameterTypes());
		msg.setValues(args);
		
		EventLoopGroup group = new NioEventLoopGroup();
		
		
		final RpcProxyHandler handler = new RpcProxyHandler();
		try{
			Bootstrap b = new Bootstrap();
			
			b.group(group)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {
	
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					
					//处理的拆包、粘包的解、编码器
					pipeline.addLast("frameDecoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4,0,4));
					pipeline.addLast("frameEncoder",new LengthFieldPrepender(4));
					
					//处理序列化的解、编码器（JDK默认的序列化）
					pipeline.addLast("encoder",new ObjectEncoder());
					pipeline.addLast("decoder",new ObjectDecoder(Integer.MAX_VALUE,ClassResolvers.cacheDisabled(null)));
					
					//自己的业务逻辑
					pipeline.addLast("handler",handler);
				}
				
			});
			
			ChannelFuture f = b.connect("localhost",8080).sync();
			f.channel().writeAndFlush(msg).sync();
			f.channel().closeFuture().sync();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			group.shutdownGracefully();
		}
		
		return handler.getResult();
	}
	
}