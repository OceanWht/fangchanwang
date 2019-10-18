package com.wht.springcloud.fangchanwang.aopdemo;

public class Student {

    @Mu(name = "WuHaiTao",age = 30,score = {99,66,77})
    public void study(int times){
        for (int i = 0; i < times;i++){
            System.out.println("Good Good Study,Day Day Up!");
        }
    }
}
