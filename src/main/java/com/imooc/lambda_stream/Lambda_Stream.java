package com.imooc.lambda_stream;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Stream;

public class Lambda_Stream {

    public static void main(String[] args) {
        String[] array1 = {"a", "b", "c"};
        for(Integer i : Lists.newArrayList(1,2,3)){
            Stream.of(array1).map(item -> Strings.padEnd(item, i, '@')).forEach(System.out::println);
        }

        String[] array2 = {"a", "b", "c"};
        for(int j = 1; j<4; j++){
            int finalJ = j;
            Stream.of(array2).map(item -> Strings.padEnd(item, finalJ, '@')).forEach(System.out::println);
        }

        List<Integer> nums = Lists.newArrayList(1,1,null,2,3,4,null,5,6,7,8,9,10);
        System.out.println("sum is:"+nums.stream().filter(num -> num != null)
                //1,1,2,3,4,5,6,7,8,9,10
                //.peek(x -> System.out.println("peek0: "+x))
                .distinct()
                //1,2,3,4,5,6,7,8,9,10
                .mapToInt(num -> num * 2)
                //2,4,6,8,10,12
                .skip(2)
                //6,8,10,12,14,16,18,20
                .limit(4)
                .peek(System.out::println)
                //6,8,10,12
                .sum());
                 //36
    }
}
