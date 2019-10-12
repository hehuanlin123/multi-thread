package com.imooc.aspect_demo;

public aspect AccountAspect {

    // define a pointcut to pick up invoking Accont.withdraw
    pointcut callWithDraw(int amount, Account account):call(boolean Account.withdraw(int)) && args(amount) && target(account);

    // advice definition executing before enterring method body
    before(int amount, Account acc):callWithDraw(amount, acc) {
        System.out.println("Start withdraw " + amount + " from " + acc);
    }

    after(int amount, Account acc) returning (Object ret): callWithDraw(amount, acc) {
        System.out.print("Finish withdraw, return " + ret +", account after withdraw is: " +  acc);
    }
}
