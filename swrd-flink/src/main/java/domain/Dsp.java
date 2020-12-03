package domain;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinghonglin
 * @date 2020/12/03
 */
public class Dsp implements Serializable {
    public Long dspId;
    public int count;
    public List<Long> entityIds;
    public List<DspIdea> dspIdeas;

    public Dsp() {
    }

    public Dsp(Long dspId, List<Long> entityIds) {
        this.dspId = dspId;
        this.entityIds = entityIds;
    }

    @Override
    public String toString() {
        return "DspIdea{" +
                "dspId='" + dspId + '\'' +
                "count='" + count + '\'' +
                ", entityIds=" + JSON.toJSONString(entityIds) +
                ", dspIdeas=" + JSON.toJSONString(dspIdeas) +
                '}';
    }
}
