# 1.NIO异步非阻塞

- 使用Java NIO来异步进行处理的

- Tomcat的NIO线程模型结构：多个client，一个Acceptor，多个Poller，一个ThreadPoolExecutor

- Accepter是套接字接受线程（Socket Acceptor Thread），接受用户请求，并把请求封装为事件任务放入Poller
的队列中

- Poller套接字处理线程（Socket Poller Thread），每一个Poller内部都有独立的队列，并且都有一个selector；

- Poller从自己的队列里面获取具体的事件任务进行执行，具体是把接受到的连接套接字注册到该Poller的selector上，
一旦一个套接字注册到了一个Poller的selector上，那么其一生就绑定到该Poller上，不会再改变

- Poller还负责轮询注册到自己管理的selector上的连接套接字的读写事件，当有读写事件时，就交给后端线程池进行
处理

- NIO线程模型进一步提高了线程的复用度

- 使用一个Poller线程来管理多个连接套接字的读写事件，NIO没有一开始就分配线程池中线程给连接socket，而是等
Poller线程发现有socket读写线程时才分配线程

# 2.NIO缺点
- Tomcat中的NIO线程模型并没有最大化利用NIO的性能，比如监听套接字还是使用的阻塞模式，只是接受的连接套接字
使用非阻塞模式（使用了Poller），另外基于Servlet规范的约束，也限制了NIO的性能

- 平行于Servlet的技术栈——WebFlux，底层使用Reactor+Netty，最大化地利用了Netty的异步高性能来处理网络请求