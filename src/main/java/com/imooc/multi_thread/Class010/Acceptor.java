/*
package com.imooc.multi_thread.Class010;

import java.net.Socket;

*/
/**
 * Tomcat中的BIO线程模型
 * 1.本质：使用一个线程来接受连接请求，然后使用一个线程池来处理所有的请求
 * 2.接受线程接受一个完成TCP三次握手的socket后，把socket封装成一个任务，投递到线程池中进行异步处理
 * 3.接受线程继续阻塞到监听socket的accept方法处等待下一个连接的完成
 *//*

public class Acceptor extends AbstractEndpoint.Acceptor{
    public void run(){
        //循环直到收到shutdown指令
        while(running){
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
}
*/
