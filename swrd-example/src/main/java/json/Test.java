package json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test {
    public static void main(String[] args){
        JSONObject jsonObject = new JSONObject();
        List<Long> objects = new ArrayList<>();
        objects.add(60113683142069l);
        objects.add(60148874681023l);
        jsonObject.put("orderIds", JSON.toJSONString(objects));
        /*HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        jsonObject.put("startTime","2019-05-22 12:01:03");
        jsonObject.put("endTime","2019-05-22 12:50:43");
        objectObjectHashMap.put("params", jsonObject.toJSONString());*/
        System.out.println(jsonObject.toJSONString());
    }
}
