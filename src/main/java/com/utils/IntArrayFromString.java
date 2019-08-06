package com.utils;

public class IntArrayFromString {
    public static int[] arrayFromString(String input){
        String[] string = input.split(",");
        int[] result = new int[string.length];
        for(int i=0;i<result.length;i++){
            result[i] = Integer.parseInt(string[i]);
        }
        return result;
    }
}
