# 1.Leader-Follower线程模型

- 目的：解决【连接接受线程】和【业务处理线程池】中【线程上下文切换】以及【线程间通信数据拷贝】所带来的开销，并且不需要维护一个队列

- 每个线程有三种模式：Leader、Follower和Processing

- Leader-Follower线程模型原理：一开始会创建一个线程池，并且会选择【一个线程】作为Leader线程，Leader线程负责【监听网络请求】，
【其他线程】为Follower处于waiting状态。Leader线程接受到请求后，会【释放】自己的作为Leader的权利，然后从Follower线程中选择
一个线程进行【激活】，新激活的线程被选择为【新的】Leader线程作为服务监听，然后老的Leader线程处理自己接受到的请求（现在【老的】
Leader线程状态变为Processing），处理完成后，状态从Processing变为Follower模式。

- Leader-Follower线程模型优点：【接受请求】和【进行处理】使用的是同一个线程，这避免了线程上下文切换以及线程间通信数据拷贝