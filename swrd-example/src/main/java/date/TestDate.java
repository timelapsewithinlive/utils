package date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class TestDate {
    public static void main(String[] args){
        String params="{\"startTime\":\"2019-05-22 12:00:00\",\"endTime\":\"2019-05-22 12:50:43\"}";
        JSONObject jsonObject = JSON.parseObject(params);
        System.out.println(jsonObject.getDate("startTime"));
        System.out.println(DateUtil.parseDate(jsonObject.getString("startTime"),DateUtil.FORMAT_2));
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startTime",jsonObject.getDate("startTime"));
        param.put("endTime",jsonObject.getDate("endTime"));
        System.out.println(JSON.toJSONString(param));
    }
}
