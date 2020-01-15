package jdk.string;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class TestTrim {


    public static void main(String[] args) throws Exception{
        String sss = new String("我".getBytes(),"utf-8");
        byte[] bytes = sss.getBytes();

        //byte[] bytes = "".getBytes();
       /* String fsa="邢宏  林";
        String s = StringUtils.trimAllWhitespace(fsa.trim());
        System.out.println(s);*/

    /*    Person person = new Person();
        person.eat("萝卜白菜大冬瓜");
        Method method = person.getClass().getMethod("eat", String.class);
        Parameter[] parameters = method.getParameters();*/
        //System.out.println(parameters[0].get);
    }

    static class Person{

        public void eat(String food){
            System.out.println(food);
        }
    }
}
