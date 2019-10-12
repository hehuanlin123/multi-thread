package com.imooc.io;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class io_tools {
    //第一种获取文件内容方式
    public byte[] readByte(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file "+ file.getName());
        }
        fi.close();
        return buffer;
    }

    //第二种获取文件内容方式
    public byte[] readByte2(String filePath) throws IOException
    {
        FileInputStream in=new FileInputStream(filePath);
        ByteArrayOutputStream out=new ByteArrayOutputStream(1024);
        System.out.println("bytes available:"+in.available());
        byte[] temp=new byte[1024];
        int size=0;
        while((size=in.read(temp))!=-1)
        {
            out.write(temp,0,size);
        }
        in.close();
        byte[] bytes=out.toByteArray();
        System.out.println("bytes size got is:"+bytes.length);
        return bytes;
    }
    //将byte数组写入文件
    public void WriteByteToFile(String path, byte[] content) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        fos.write(content);
        fos.close();
    }
    /* 方法1：
     * 将byte数组(追加)写入文件
     *
     * */
    public void AppendByteToFile(String path, byte[] content, boolean Appendable) throws IOException {
//         程序写好之后每次存储数据都刷新
        //FileOutputStream fos = new FileOutputStream(path);
//         研究了一下，原来FileOutPutStream也可以设置模式的，只是和openFileOutput不一样 我这样写FileOutputStream fos=new FileOutputStream(_sdpath1,Appendable)；就可以实现数据追加功能
        FileOutputStream fos=new FileOutputStream(path,Appendable);
        fos.write(content);
        fos.write("\r\n".getBytes());
        fos.close();
    }
    /**
     * 方法二：
     * 根据byte数组，生成文件
     */
    public  void AppendByteToFile(byte[] bfile, String filePath,String fileName) {
        BufferedOutputStream bos = null;

        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath+"\\"+fileName);
            /* 使用以下2行代码时，不追加方式*/
            /*bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(bfile); */
            /* 使用以下3行代码时，追加方式*/
            bos = new BufferedOutputStream(new FileOutputStream(file, true));
            bos.write(bfile);
            bos.write("\r\n".getBytes());

            bos.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }
}
