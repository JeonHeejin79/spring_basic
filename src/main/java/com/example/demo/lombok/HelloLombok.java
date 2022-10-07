package com.example.demo.lombok;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HelloLombok {

    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();

        helloLombok.setName("ABC");
        helloLombok.setAge(10);


        String name = helloLombok.getName();

        System.out.println("name = " + name);

        String s = helloLombok.toString();

        System.out.println("s = " + s);
    }
}
