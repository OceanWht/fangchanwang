package com.wht.springcloud.fangchanwang.interceptor;

import com.wht.springcloud.fangchanwang.model.UserModel;

import java.lang.ref.WeakReference;

public class Test0001 {

    public static void main(String[] args) {
        UserModel userModel = new UserModel();

        WeakReference<UserModel> weakReference = new WeakReference<UserModel>(userModel);
        int i = 0;
        while (true){
            System.out.println("用户名: "+userModel);
            if (weakReference.get() != null){
                i++;
                System.out.println("弱引用没有被回收"+i+" "+weakReference);
            }else {
                System.out.println("弱引用已经被回收");
                break;
            }

            if (i > 100000) break;
        }
    }
}
