package com.imooc.multi_thread.Class009;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 1.在最之前处理网络请求时，使用一个接受线程来轮询完成TCP三次连接的socket，每接受一个socket就会开启一个线程处理；
 * 2.缺点：1:1配比，很快耗尽系统资源
 */
public class TomcatThread {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            //（1）创建服务器监听套接字
            serverSocket = new ServerSocket(7001);
            System.out.println("server is started");

            //（2）循环监听客户端发送来的请求连接
            while (true) {
                //（2.1）获取客户端的连接套接字
                final Socket acceptSocket = serverSocket.accept();
                //（2.2）开启线程处理接受套接字
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        processAcceptSocket(acceptSocket);
                        System.out.println("开始处理");
                    }
                    private void processAcceptSocket(Socket acceptSocket) {
                        System.out.println("处理接受套接字");
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //（3）关闭服务监听套接字
            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
