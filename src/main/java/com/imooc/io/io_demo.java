package com.imooc.io;

import java.io.*;

public class io_demo {

    public static void main(String[] args) {
        File file1 = new File ("/Users/szkfzx/workspace/Play-with-Spring/src/main/java/com/imooc/io/hello1.txt");
        try {
            FileInputStream in1 = new FileInputStream(file1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        File file2 = new File ("/Users/szkfzx/workspace/Play-with-Spring/src/main/java/com/imooc/io/hello2.txt");
        FileInputStream in2= null;
        try {
            in2 = new FileInputStream(file2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader inReader= null;
        try {
            inReader = new InputStreamReader(in2,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BufferedReader bufReader2=new BufferedReader(inReader);


        File file3 = new File ("/Users/szkfzx/workspace/Play-with-Spring/src/main/java/com/imooc/io/hello3.txt");
        FileReader fileReader= null;
        try {
            fileReader = new FileReader(file3);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufReader3 = new BufferedReader(fileReader);

    }
}
