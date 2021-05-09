package utils;

import com.google.common.net.InternetDomainName;
import com.google.thirdparty.publicsuffix.PublicSuffixPatterns;
import com.google.thirdparty.publicsuffix.PublicSuffixType;
import org.apache.commons.lang3.StringUtils;

/**
 * 放在与google同一包 解决ro.com一类bug, 用于获取主域名
 */
public class DomainUtils {
  /**
   * 获取主域名
   * @param domainName 域名 形为 www.baidu.com 不带协议头
   * @return
   */
  public static InternetDomainName topPrivateDomain(String domainName) {
    if(StringUtils.isBlank(domainName)){
      return null;
    }
    InternetDomainName internetDomainName = InternetDomainName.from(domainName);
    if (PublicSuffixPatterns.EXACT.get(domainName) == PublicSuffixType.PRIVATE) {
      return internetDomainName;
    }
    InternetDomainName topPrivateDomain = internetDomainName.topPrivateDomain();
    if (topPrivateDomain != null && topPrivateDomain.isTopPrivateDomain()) {
      if (topPrivateDomain.hasParent()) {
        InternetDomainName topParent = topPrivateDomain.parent();
        if (PublicSuffixPatterns.EXACT.get(topParent.toString()) == PublicSuffixType.PRIVATE) {
          return topParent;
        }
      }
    }
    return topPrivateDomain;
  }
}
