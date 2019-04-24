/*
package com.imooc.multi_thread.Class012;

import com.alibaba.ans.shaded.org.apache.log4j.lf5.LogLevel;
import java.nio.channels.Channel;
import java.nio.channels.SocketChannel;

public class NettyServer {

    static final int PORT = Integer.parseInt(System.getProperty("port","8007"));

    public static void main(String[] args) throws Exception {
        //1.创建主从Reactor线程池

        //通常情况下，bossGroup只需要设置为1即可，因为ServerSocketChannel在初始化阶段，
        //只会注册到某一个eventLoop上，而这个eventLoop只会有一个线程在运行
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //2.创建启动类ServerBootstrap实例，用来设置客户端相关参数
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    //2.1设置主从线程池组
                    .channel(NioServerSocketChannel.class)
                     //2.2指定用于创建客户端NIO通道的Class对象，这里为NioServerSocketChannel
                    .option(ChannelOption.SO_BACKLOG,100)
                    //2.3设置客户端套接字参数，这里SO_BACKLOG大小为100，监听套接字在接受客户端时会维护两个队列，
                    //一个是存放已经完成TCP三次握手的套接字的队列，一个是存放还没有完成TCP三次握手的套接字队列，
                    //SO_BACKLOG是两个队列大小的和
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //2.4设置日志Handler
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        //2.5设置用户自定义Handler，这里在管线里面添加了用户自定义的NettyServerHandler
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });
            //3.启动服务器：绑定监听端口，等待连接完成
            ChannelFuture f = b.bind(PORT).sync();
            System.out.println("----Server Start----");
            //4.同步等待socket关闭
            f.channel().closeFuture().sync();
        } finally {
            //5.优雅关闭线程池组
            bossGroup.shutDownGracefully();
            workerGroup.shutDownGracefully();
        }
    }
}
*/
