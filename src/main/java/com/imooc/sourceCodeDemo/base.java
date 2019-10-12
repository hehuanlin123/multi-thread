package com.imooc.sourceCodeDemo;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections.map.LinkedMap;

import java.util.*;
import java.util.concurrent.*;

public class base {

    String str = null;

    Long l = 1L;

    Arrays arrays;

    Collections collections;

    static ArrayList arrayList = new ArrayList(1000);

    LinkedList linkedList;

    List list;

    HashMap hashMap;

    static TreeMap treeMap;

    LinkedMap linkedMap;

    Map map;

    Set set;

    HashSet hashSet;

    TreeSet treeSet;

    Vector vector;

    LinkedBlockingQueue linkedBlockingQueue;

    ConcurrentLinkedQueue concurrentLinkedQueue;

    static SynchronousQueue synchronousQueue;

    static DelayQueue delayQueue;

    ArrayBlockingQueue arrayBlockingQueue;

    public static void main(String[] args) throws InterruptedException {

        arrayList.add("11");

        treeMap.entrySet();

        Map<String, Integer> salary = ImmutableMap.<String, Integer> builder()
                .put("John", 1000)
                .put("Jane", 1500)
                .put("Adam", 2000)
                .put("Tom", 2000)
                .build();

        System.out.println(salary.get("Tom"));

        synchronousQueue.toArray();

    }
}
