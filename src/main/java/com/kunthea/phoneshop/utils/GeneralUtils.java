package com.kunthea.phoneshop.utils;

import java.util.List;

public class GeneralUtils {

    //convert list of string to list of integer
    public static List<Integer> toIntegerList(List<String> list){
        return list.stream()
                .map(String::length)
                .toList();
    }
    //test even
    public static List<Integer> getEvenNumber(List<Integer> list){
        return list.stream()
                .filter(x -> x%2==0)
                .toList();
    }
}
