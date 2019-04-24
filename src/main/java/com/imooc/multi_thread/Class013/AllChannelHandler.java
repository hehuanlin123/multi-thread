/*
package com.imooc.multi_thread.Class013;

import com.sun.deploy.nativesandbox.comm.Request;
import javax.print.DocFlavor;
import java.nio.channels.Channel;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

public class AllChannelHandler extends WrappedChannelHandler {

    //所有请求都交给业务线程池处理
    public AllChannelHandler(ChannelHandler handler, DocFlavor.URL url) {
        super(handler,url);
    }

    //连接事件，交给业务线程池处理
    public void connect(Channel channel) throws RemoteException {
        ExecutorService executor = getExecutorServcie();
        try {
            executor.execute(new ChannelEventRunnable(channel,handler,ChannelState.CONNECTED));
        } catch (Throwable t) {
            throw new ExecutionException("connect event" + channel,getClass() + "error when process connected event .",t);
        }
    }
    //连接断开事件，交给业务线程池处理
    public void disconnect(Channel channel) throws RemoteException {
        ExecutorService executor = getExecutorServcie();
        try {
            executor.execute(new ChannelEventRunnable(channel,handler,ChannelState.DISCONNECTED));
        } catch (Throwable t) {
            throw new ExecutionException("disconnect event" + channel,getClass() + "error when disprocess connected event .",t);
        }
    }
    //请求响应事件，交给业务线程池处理
    public void received(Channel channel,Object message) throws RemoteException {
        ExecutorService executor = getExecutorServcie();
        try {
            executor.execute(new ChannelEventRunnable(channel,handler,ChannelState.RECEIVE,message));
        } catch (Throwable t) {
            //TODO临时解决线程池满后，异常信息无法发送到对端的问题，待重构
            //fix线程池满了拒绝调用不返回，导致消费者一直等待超时
            if(message instanceof Request && t instanceof RejectedExecutionException){

            }
            throw new ExecutionException("received event" + channel,getClass() + "error when process received event .",t);
        }
    }
    //异常处理事件，交给业务线程池处理
    public void caught(Channel channel,Throwable exception) throws RemoteException {
        ExecutorService executor = getExecutorServcie();
        try {
            executor.execute(new ChannelEventRunnable(channel,handler,ChannelState.CAUGHT,exception));
        } catch (Throwable t) {
            throw new ExecutionException("caught event" + channel,getClass() + "error when process caught event .",t);
        }
    }
}
*/
