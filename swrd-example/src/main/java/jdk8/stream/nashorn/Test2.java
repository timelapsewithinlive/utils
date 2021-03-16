package jdk8.stream.nashorn;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * @author xinghonglin
 * @date 2021/03/15
 */
public class Test2 {
    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");

        String js;

        js = "var arr =[\"http://12312.apk\", \"http://12124343.apk\"];";
        js += "print(arr!=null && arr != '' && arr.length>0);";
        js += "var str='';";
        js+="arr.forEach(function (item, index) {\n" +
                "str += item + \",\";\n" +
                "});";
        js+= "if (str.length > 0) {str = str.substr(0,str.length - 1);}" ;
        js += "print(str);";
        js+="arr.join(',');";
        engine.eval(js);

    }
}
