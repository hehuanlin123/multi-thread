# 1.BIO同步阻塞(Tomcat中的BIO线程模型)

- 本质：使用一个线程来接受连接请求，然后使用一个线程池来处理所有的请求；使用有限的线程来处理大量的
请求，提高线程的复用性

- BIO线程模型结构：多个client、一个Accept-Thread、一个ThreadPoolExecutor；ThreadPoolExecutor
包括：任务队列、workers；workers包括：多个core、多个thread；

- 接受线程接受一个完成TCP三次握手的socket【连接套接字】后，把socket封装成一个SocketProcessor【连接
套接字任务】后，投递到线程池中进行异步处理

- 接受线程继续阻塞到监听socket的accept方法处等待下一个连接的完成

- 任务队列的方式

- 本质还是一个请求一个线程来处理，如：线程池最大线程10个，同时接受100个请求，10个线程分别同时处理一个
请求，如果Tomcat设置的最大连接数大于200，那么有90个请求是被放到线程池的队列里缓存起来的，等线程池有空
闲再从队列里获取连接进行处理