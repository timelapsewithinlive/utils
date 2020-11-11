package zookeeper;

import com.sm.audit.commons.utils.Lambdas;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SuperTest {

    public static void main(String[] args) {
        ArrayList<Object> objects1 = new ArrayList<>();
        Map<String, Object> stringObjectMap = Lambdas.trans2Map(objects1, Object::toString);

        System.out.println(stringObjectMap);

        ArrayList<Object> objects = new ArrayList<>();
        objects.add(3);
        Object o = new Object();
        /*objects.forEach(x->{
            System.out.println(x);
        });*/

        Optional.ofNullable(o).ifPresent(x->{
            System.out.println(x);
        });

        //Optional.of(o).

        /*Optional.of(o).filter(x ->x ==null).ifPresent(x->{
            System.out.println(x);
        });*/
    }
}
