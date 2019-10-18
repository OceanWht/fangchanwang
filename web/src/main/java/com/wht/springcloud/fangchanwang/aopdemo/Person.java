package com.wht.springcloud.fangchanwang.aopdemo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Table("person")
public class Person {

    @Column("name")
    private String name;

    @Column(value = "user_name")
    private String userName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public static String query(Object person){
        StringBuilder sb = new StringBuilder();

        Class p = person.getClass();
        if (!p.isAnnotationPresent(Table.class)){
            return null;
        }

        Table table = (Table) p.getAnnotation(Table.class);
        String tableName = table.value();

        //拼接sql
        sb.append("select * from ").append(tableName).append(" where 1=1");
        Field[] fields = p.getDeclaredFields();
        for (Field field:fields){
            boolean fExisted = field.isAnnotationPresent(Column.class);
            if (!fExisted){
                return null;
            }

            Column column = field.getAnnotation(Column.class);
            String columnName = column.value();
            String fieldName = field.getName();
            Object fieldValue = null;

            String getFieldMethod = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);

            try {
                Method method = p.getMethod(getFieldMethod);
                fieldValue = method.invoke(person);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            sb.append(" and ").append(columnName).append("='").append(fieldValue).append("'");

        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Person person = new Person();

        person.setName("wuhaitao");
        person.setUserName("WHT");

        System.out.println(query(person));
    }
}
