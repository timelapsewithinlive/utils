package jdk.jdk8;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Test {
    public static void main(String[] args){
        Person person = new Person();
        person.setAge(18);
        person.setName("laoliu");

        Person person1 = new Person();
        person1.setAge(18);
        person1.setName("laowang");

        BiFunction<Person,Person,Boolean> function = (p1,p2) ->p1.equals(p2);
        Boolean sss = function.apply(person,person1);

        List<Person> persons = new ArrayList<>();
        persons.add(person);
        persons.add(person1);

        Optional<Person> first = persons.stream().filter(person2 -> person.getAge() == 18).findFirst();
        Person person2 = first.get();
        System.out.println(first.get());

    }
}

class Person{

    private int age;

    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}