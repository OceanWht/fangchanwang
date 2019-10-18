package com.wht.springcloud.fangchanwang.aopdemo;

import java.lang.reflect.Method;

public class TestAnnotation {
    public static void main(String[] args) {
        Class<Student> studentClass = Student.class;
        try {
            Class student = Class.forName("com.wht.springcloud.fangchanwang.aopdemo.Student");
            Method method = student.getMethod("study", int.class);
            if (method.isAnnotationPresent(Mu.class)){
               Mu mu = method.getAnnotation(Mu.class);
                System.out.println("name="+mu.name()+",age="+mu.age()+",score="+mu.score()[1]);
            }else {

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
