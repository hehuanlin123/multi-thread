# 1.Dubbo的线程模型

- Dubbo默认的底层网络通讯使用的是Netty

- NettyServer使用两级线程池，EventLoopGroup(boss)主要用来接受客户端的连接请求,并将它们分发给
EventLoopGroup(worker)来处理，boss和worker线程组称为IO线程

- 服务端请求处理较快，或者不会发起新的IO请求，直接在IO线程上处理；服务端请求较慢，或者会发起新的
IO请求，IO线程必须派发请求到新的线程池处理，防止IO线程阻塞

- 根据请求的消息被IO线程处理还是被业务线程处理，Dubbo提供了五种线程模型：

（1）all(AllDispatcher类)：所有消息都派发到业务线程池，请求、响应、连接事件、断开事件、心跳

（2）direct(DirectDispatcher类)：所有消息都不派发到业务线程池，直接在IO线程池处理

（3）message(MessageOnlyDispatcher类)：只有请求响应消息发到业务线程池，其他连接断开事件、心跳
等消息直接接在IO线程池上执行

（4）execution(ExecutionDispatcher类)：只把请求类消息派发到业务线程池处理，响应和其他连接断开
事件、心跳等事件直接在IO线程上执行

（5）connection(ConnectionOrderedDispatcher类)：在IO线程上，将连接断开事件放入队列，有序逐个
执行，其他消息派发到业务线程池处理

- Dubbo提供了几种业务线程池，扩展接口ThreadPool的SPI实现有三种：

（1）fixed:固定大小线程池，启动时建立线程，不关闭，一直持有（默认）

（2）cached:缓存线程池，空闲一分钟自动删除，需要时重建

（3）limited:可伸缩线程池，但池中的线程数只会增长不会收缩，目的是为了避免收缩时突然带来大流量引起的
性能问题

- 使用ThreadPoolExecutor创建了: 核心线程数=最大线程池数=threads的线程池；Dubbo线程池扩展，可以
根据自己的需要进行扩展定制，在服务提供者启动流程时，我们会看到什么时候加载的线程池扩展实现
