package com.learning.junit.streams;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        int [] nums  = { 8,3,5,2, 7};

        IntStream.of(nums).min().ifPresent(System.out::println);

        Arrays.asList(" 1", " as", " adw").stream().filter( s -> s.length() >= 2).collect(Collectors.toList()).forEach(System.out::println);

        Stream.Builder<String> sbuilder = Stream.builder();
        sbuilder.accept("Thomas");
        sbuilder.accept("Kerrie");
        sbuilder.accept("Alexndra");
        sbuilder.accept("Thando");

        Stream<String> nameStream = sbuilder.build();

        //System.out.println(nameStream.collect(Collectors.toList()));

        //nameStream.map(String::toUpperCase).forEach(System.out::println);

        System.out.println(nameStream.toArray(String[]::new).length);

        List<List<String>> namesNested = Arrays.asList(
                Arrays.asList("Jeff", "Bezos"),
                Arrays.asList("Bill", "Gates"),
                Arrays.asList("Mark", "Zuckerberg"));

        List<String> namesFlatStream = namesNested.stream().flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(namesFlatStream);

        List<String> countries = Arrays.asList("zim", "moz", "rus").stream()
                .peek( e-> e.toUpperCase(Locale.ROOT) )
                .peek(System.out::println)
                .collect(Collectors.toList());

    }
}
