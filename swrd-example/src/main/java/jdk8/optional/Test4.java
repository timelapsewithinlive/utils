package jdk8.optional;

import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.Optional;

/**
 * @author xinghonglin
 * @date 2021/03/26
 */
public class Test4 {

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> stringStringHashMap = Optional.ofNullable(map)
                .filter(MapUtils::isNotEmpty)
                .map(HashMap::new)
                .orElse(new HashMap<>());
        System.out.println(stringStringHashMap);
    }
}
