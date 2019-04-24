# 1.Netty的Reactor模型

- Netty是高性能异步网络通讯框架，应用范围：Dubbo、RocketMQ、SOFA、Zuul 2.0、Web Flux，使用Reactor
线程模型所以性能更加高效

- 每一个EventLoopGroup本身就是一个线程池，其中包含了自定义个数的NioEventLoop，每一个NioEventLoop
是一个线程，并且每一个NioEventLoop里面都有自己的selector选择器

- Netty服务端有两个EventLoopGroup：
```
Boss EventLoopGroup:专门用来接收客户端发来的【TCP连接请求】

Worker EventLoopGroup:专门用来具体处理完成TCP三次握手后的连接套接字的【网络IO请求】的
```

- 服务器Reactor线程模型结构
（1）多个client，一个NioServerSocketChannel，默认创建一个Boss EventLoopGroup（因为ServerSocketChannel在初始化阶段，
只会注册到某一个eventLoop上，而这个eventLoop只会有一个线程在运行），Worker EventLoopGroup的个数为内核CPU个数*2

***注意事项***
a.多个Server Bootstraps共享同一个NIOEventLoopGroup时，设置多个Boss EventLoopGroup比较好，因为当你在多个监听套接字监听
服务连接时，每个监听套接字可以绑定到NIOEventLoopGroup中不同的EventLoop上，同时达到多个监听套接字并行处理的效果；
b.Worker EventLoopGroup为了充分利用CPU，同时考虑线程上下文切换的开销，通常设置内核CPU数 * 2，这也是Netty提供的默认值

（2）Boss EventLoopGroup与 Worker EventLoopGroup之间使用NioSocketChannel进行通信

（3）Boss EventLoopGroup与 Worker EventLoopGroup内部都有多个EventLoop

- 与NIO线程模型的比较
Tomcat中NIO模式中监听套接字是同步的，Netty中的boss监听套接字是异步处理连接请求的