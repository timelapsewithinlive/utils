package utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 二进制与十进制字符转
 *
 */
public class BitUtils {
  /**
   * 2**n数字序列转数值,如1,2,4 => 7
   *
   * @param value 数字序列
   * @return 对应十进制数值
   */
  public static long valueOf(String value) {
    if (StringUtils.isEmpty(value)) {
      return 0;
    }
    String[] temp = value.split(",");
    int adResource = 0;
    for (int i = 0; i < temp.length; i++) {
      adResource |= Integer.valueOf(temp[i]);
    }
    return adResource;
  }

  /**
   * 数值转换为2**n对应的序列 如 7=> 1,2,4
   *
   * @param adResource
   * @return created by dumd on 2015年12月24日 下午4:43:30
   */
  public static String numbersOf(long adResource) {
    if (adResource == 0) {
      return "";
    }
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 1; i <= adResource; i = i << 1) {
      if ((i & adResource) == i) {
        stringBuffer.append(i).append(",");
      }
    }
    if (stringBuffer.length() > 0) {
      stringBuffer.setLength(stringBuffer.length() - 1);
    }
    return stringBuffer.toString();
  }
}
