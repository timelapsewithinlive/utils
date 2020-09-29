package designpatterns.chan2;

import lombok.extern.slf4j.Slf4j;

/**
 * sdk物料抽取自定义函数
 *
 * @Author honglin.xhl
 * @Date 2020/8/28 11:39 上午
 */
@Slf4j
public class ParseSdkAd {

    /**
     * 抽取sdk待审物料信息
     *
     * @param content
     * @return
     */
    public String evaluate(String content) {
        Request request = new Request(content, null);
        DefaultPipeline pipeline = new DefaultPipeline(request);

        pipeline.fireReceiveRequest()
                .fireReturnResponse();
        Response response = pipeline.response();
        if (response.getCause() != null) {
            log.info("ParseSdkAd exception: ", response.getCause());
            return null;
        }
        Object data = pipeline.response().getData();
        System.out.println(data);
        return null;
    }
}
