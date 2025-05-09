package com.learning.junit.functionalInterfaces;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {

    public static void main(String[] args) {
       List<String> users = new ArrayList<>();
       users.add("tho");
       users.add("alexandra");
       users.add("kerrie");

       users.removeIf( user -> {
           return user.length() < 4;
       });

        System.out.println(String.format("The lenght of the list is %s", users.size()));
        System.out.println(users);

        Consumer<String> out = System.out::println;

        out.andThen(out).accept("Hello Thando");
        out.accept("Hello world ...");

        Supplier<StringBuilder> stringBuilderSupplier = StringBuilder::new;

        StringBuilder builder = stringBuilderSupplier.get();
        builder.append("Thomas").append(" Alexandra");
        System.out.println(builder.toString());

        IPerson iPerson = Person::sum;
        iPerson.addMe(2, 4);

        List<String> names = Arrays.asList("Thomas", "Kerrie", "Alexandra");

        names.forEach(System.out::println);

        Parser p = StringParser::convert;
        System.out.println(p.parse("Mow"));

        System.out.println("-----------------------");
        Predicate<String> startsWithA = (text) -> text.startsWith("A");
        Predicate<String> endsWithX = (text) -> text.endsWith("x");

        Predicate<String> composed = startsWithA.and(endsWithX);

        String input = "A hardworking person must relax";
        boolean result = composed.test(input);
        System.out.println(result);

        Function<Integer, Integer> multiply = (value) -> value * 4;
        Function<Integer, Integer> add = (value) -> value + 6;

        Function<Integer, Integer> composedFunction = multiply.andThen(add);

        System.out.println(composedFunction.apply(5));
    }
}

class  Person {

    public static int sum(int x, int y) {
        System.out.println("I am a person sum");
        return x + y;
    }




}

@FunctionalInterface
interface IPerson {

    int addMe(int x, int y);
}

@FunctionalInterface
interface Parser {
    String parse(String str);
}

class StringParser {

    public static  String convert(String s) {
        if(s.length() <= 3) {
            s = s.toUpperCase();
        }
        else {
            s = s.toLowerCase();
        }

        return s;
    }
}



