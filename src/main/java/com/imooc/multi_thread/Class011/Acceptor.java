/*
package com.imooc.multi_thread.Class011;

import com.alibaba.ans.shaded.com.alibaba.middleware.ushura.poller.Poller;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Acceptor extends AbstractEndpoint.Acceptor {

    /**
    * 下面的代码与BIO比较类似，不同之处在于NIO重写了setSocketOptions方法
    * /
    public void run() {

        int errorDelay = 0;

        //循环直到收到shutdown指令
        while (running) {
            try {
                //如果达到最大连接数,则等待
                countUpOrAwaitConnection();

                Socket socket = null;
                try {
                    //等待一个完成TCP三次握手的连接
                    socket = serverSocketFactory.acceptSocket(serverSocket);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //设置连接socket的选项并封装socket为任务，投递到线程池
                setSocketOptions(socket);
                processSocket(socket);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    protected boolean processSocket(Socket socket){
        //封装socket为SocketProcessor后投递到线程池处理
        try {
            SocketWrapper<Socket> wrapper = new SocketWrapper<Socket>(socket);
            wrapper.setKeepAliveLeft(getMaxKeepAliveRequest());
            wrapper.setSecure(isSSLEnable());
            getExcutor().excute(new SocketProcessor(wrapper));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    */
/**
     * 把socket转换为Channel，然后注册到某一个Poller的队列里
     * @param socket
     * @return
     *//*

    protected boolean setSocketOptions(SocketChannel socket){
        try {
            //设置连接套接字为非阻塞
            socket.configureBlocking(false);
            Socket sock = socket.socket();
            socketProperties.setProperties(sock);

            NioChannel channel = nioChannels.poll();
            channel.setIOChannel(socket);

             //注册连接套接字通道到一个Poller队列
            getPoller0().register(channel);

        } catch (Throwable throwable) {

            ExceptionUtils.handleThrowable(throwable);

            try {
                log.error("",throwable);
            } catch (Throwable throwable1) {
                ExceptionUtils.handleThrowable(throwable1);
            }
            return false;
        }
        return true;
    }

    */
/**
     * 注册到Poller
     * @param socket
     *//*

    public void register(final NioChannel socket){
        socket.setPoller(this);
        PollerEvent pollerEvent = new PollerEvent(socket,ka,OP_REGISTER);
        addEvent(pollerEvent);
    }

    public void addEvent(Runnable event){
        events.offer(event);
    }

    */
    /**
     * events的定义
     * 到这里完成TCP三次握手的socket套接字就被注册到某个Poller管理的队列里了
     *//*

    protected ConcurrentLinkedQueue<Runnable> events = new ConcurrentLinkedQueue<Runnable>();

    */
    /**
     * Poller本身是一个线程
     * run方法从队列里获取连接套接字，并将其注册到自己管理的selector上
     * Poller从自己管理的队列events中获取一个任务，如果存在任务，则执行任务的run方法
     * @return
     *//*

    public boolean events(){
        boolean result = false;

        Runnable r = null;

        for (int i = 0,size = events.size(); i < size && (r = events.poll()) != null; i++) {
            result = true;

            try {
                r.run();
            } catch (Throwable x) {
                log.error("",x);
            }
        }
        return result;
    }

    */
/**
     * events队列放入的是PollerEvent类型，看看它的run方法
     * 把连接socket注册到了Poller线程管理的selector上了
     *//*

    public void run(){
        if (interestOps == OP_REGISTER){
            try {
                socket.getIOChannel().register(socket.getPoller().getSelector(), SelectionKey.OP_READ,key);
            } catch (Exception x) {
                log.error("",x);
            }
        }
    }

    */
/**
     * Poller线程还做了一件事情，就是监控注册到自己管理的selector上socket读写事件，当有事件发生时，
     * 就将事件转换为任务放入线程池中执行
     * @param socket
     * @param status
     * @param dispactch
     * @return
     *//*

    public boolean processSocket(NioChannel socket,SocketStatus status,boolean dispactch){
        SocketProcessor sc = new SocketProcessor(socket,status);
        if(dispactch && getExcutor() != null){
            getExcutor().execute(sc);
        } else {
            sc.run();
        }
    }
}
*/
