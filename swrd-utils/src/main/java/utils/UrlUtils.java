package utils;


import com.google.common.net.InternetDomainName;
import com.google.thirdparty.publicsuffix.DomainUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */

public class UrlUtils {
    /**
     * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
     */
    protected static final Logger LOG = LoggerFactory.getLogger(UrlUtils.class);

    private static Pattern topPrimaryPattern = Pattern.compile("(?<=http://|https://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);

    /**
     * @param urlString
     */
    public static String getDomainName(String urlString) {
        if (StringUtils.isBlank(urlString)) {
            return StringUtils.EMPTY;
        }

        try {
            // 不适用URI的问题是，如果host包含中文，getHost返回null，getAuthority不为null，但是可能包含port
            URL url = new URL(urlString);
            return url.getHost();
        } catch (Exception ex) {
            return StringUtils.EMPTY;
        }
    }

    /**
     * 检查url是否包含公网可访问的域名，只允许 http和 https协议
     *
     * @param url
     * @return
     */
    public static boolean hasValidDomain(String url) {
        String domainName = getDomainName(url);

        if (domainName == null)
            return false;

        InternetDomainName domain = InternetDomainName.from(domainName);
        return domain.hasPublicSuffix();
    }

    /**
     * 检查给定URL是否符合规范，是否包含http或者https协议头，域名后缀是否是在公开列表内。域名不能是ip地址
     *
     * @param urlString
     * @return
     */
    public static boolean isValidUrl(String urlString) {
        if (urlString == null || urlString.isEmpty())
            return false;

        try {
            URL url = new URL(urlString);

            // 只允许 http和 https协议
            if (!"http".equals(url.getProtocol()) && !"https".equals(url.getProtocol())) {
                return false;
            }

            String domainName = url.getHost();
            if (domainName == null) {
                return false;
            }
            if (domainName.endsWith(".wang")) {
                return true;
            }
            InternetDomainName domain = InternetDomainName.from(domainName);
            return domain.hasPublicSuffix();
        } catch (Exception ex) {
            return false;
        }

    }

    /**
     * 检查指定的domain，是否与一个url的主域匹配
     *
     * @param privateDomain
     * @param url
     * @param ignoreCase
     * @return
     */
    public static boolean hasSamePrimaryDomain(InternetDomainName privateDomain, String url, boolean ignoreCase) {
        if (!isValidUrl(url)) {
            return false;
        }

        String domainName = getDomainName(url);

        if (privateDomain.toString().endsWith(".wang") && domainName.endsWith(".wang")) {
            return ignoreCase ? privateDomain.toString().equalsIgnoreCase(domainName) : privateDomain.toString().equals(domainName);
        }
        InternetDomainName domain2 = InternetDomainName.from(domainName).topPrivateDomain();

        if (ignoreCase) {
            return domain2 != null && privateDomain.toString().equalsIgnoreCase(domain2.toString());
        }

        return (privateDomain != null && domain2 != null && privateDomain.equals(domain2));
    }

    /**
     * 检查给定的两个url是否属于相同的主域名。如果任何一个给定的url不符合规范，直接返回false
     *
     * @param url1
     * @param url2
     * @return
     */
    public static boolean hasSamePrimaryDomain(String url1, String url2, boolean ignoreCase) {
        if (!isValidUrl(url1) || !isValidUrl(url2)) {
            return false;
        }
        String domain1 = getPrimaryDomain(url1);
        String domain2 = getPrimaryDomain(url2);
        if (StringUtils.isEmpty(url1) || StringUtils.isEmpty(url2)) {
            LOG.warn("解析主域出现异常,主域为空. url1:{},url2:{}", url1, url2);
            return false;
        }
        if (ignoreCase) {
            return domain1 != null && domain2 != null && domain1.toString().equalsIgnoreCase(domain2.toString());
        }

        return (domain1 != null && domain2 != null && domain1.equals(domain2));
    }

    public static String getPrimaryDomain(String url) {
        if (!isValidUrl(url)) {
            return StringUtils.EMPTY;
        }
        String domainName = getDomainName(url);
        if (StringUtils.isBlank(domainName)) {
            return StringUtils.EMPTY;
        }
        InternetDomainName domain = null;
        try {
            domain = DomainUtils.topPrivateDomain(domainName);
        } catch (IllegalStateException e) {
            LOG.warn("要获取主域的url不合法,使用正则方式尝试获取主域 url:{}", url);
            return getPrimaryDomainByRegex(url);
        }
        if (domain == null) {
            return StringUtils.EMPTY;
        }
        //去掉末尾的/
        String domainStr = domain.toString();
        while (domainStr.endsWith("/")) {
            domainStr = domainStr.substring(0, domainStr.length() - 1);
        }
        return domainStr;
    }

    private static String getPrimaryDomainByRegex(String url) {
        if (!isValidUrl(url)) {
            return StringUtils.EMPTY;
        }
        try {
            Matcher matcher = topPrimaryPattern.matcher(url);
            matcher.find();
            String domainStr = matcher.group();
            while (domainStr.endsWith("/")) {
                domainStr = domainStr.substring(0, domainStr.length() - 1);
            }
            return domainStr;
        } catch (IllegalStateException e1) {
            LOG.warn("要获取主域的url不合法,正则方式尝试获取主域失败 url:{}", url);
            return StringUtils.EMPTY;
        }
    }

    public static String prependHttp(String url) {
        if (url == null || url.trim().isEmpty()) {
            return url;
        }

        String smallUrl = url.trim().toLowerCase();

        if (smallUrl.startsWith("http://") || smallUrl.startsWith("https://")) {
            return url.trim();
        } else {
            // 强行加上http前缀。针对 ftp://xxx 的例子，后续isValidUrl检查会过滤掉
            return "http://" + url.trim();
        }
    }

    /**
     * 免审URL判定逻辑：
     * 1. 如果是空的URL，是免审URL
     * 2. 如果URL为形如："{$ xx $} xxx" 的URL和图片为免审的URL
     * 需求来源：https://yuque.antfin-inc.com/hc_audit/pgpx9p/zbptvd
     *
     * @param url
     * @return
     */
    public static boolean isFreeTrialUrl(String url) {
        return StringUtils.isBlank(url) ||  //空的URL不是正常的URL
                (url.trim().startsWith("{$") &&
                        url.trim().contains("$}")); // "{$ xx $}" 不是正常URL
    }
}
