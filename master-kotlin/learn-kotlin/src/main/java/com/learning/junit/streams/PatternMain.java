package com.learning.junit.streams;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMain {

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("[^abc]");
        Matcher matcher = pattern.matcher("abofoio");
        while (matcher.find()) {
            System.out.print(matcher.group());
            System.out.println(matcher.start());
        }

        pattern = Pattern.compile("[bcr]at");
        matcher = pattern.matcher("cat");
        System.out.println("----------------");
        while (matcher.find()) {
            System.out.print(matcher.group());
            System.out.println(matcher.start());
        }




    }
}
