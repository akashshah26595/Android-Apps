package com.example.akash.loginregister;

/**
 * Created by akash on 26/1/16.
 */
public class User {
    String name,username,password;
    String age;

    public User(String name,String username,String password,String age){
        this.age=age;
        this.name=name;
        this.username=username;
        this.password=password;
    }
    public User(String username,String password){
        this.password=password;
        this.username=username;
        this.age= "";
        this.name="";
    }

}
